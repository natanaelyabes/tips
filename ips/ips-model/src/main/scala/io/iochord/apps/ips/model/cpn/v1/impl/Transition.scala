package io.iochord.apps.ips.model.cpn.v1.impl

import scala.util.control.Breaks._
import scala.collection.mutable.Map
import io.iochord.apps.ips.model.cpn.v1._

class Transition[B <:Bind] (
  private var id: String,
  private var name: String,
  private var guard: Guard[B] = null,
  private var action: Action[B] = null) extends Element with Node {
  
  private var in = List[Arc[_,B]]()
  private var out = List[Arc[_,B]]()
  
  private var eval:(B,B) => Boolean = null
  private var merge:(B,B) => B = null
  private var evalLast:B => Boolean = null
  
  private var lbeBase:List[B] = null
  
  private var origin:Map[String,String] = null
  private var attributes:Map[String,Any] = null
  
  private def evalFull[T] = (b1:B, b2:B, noToken:Int, arc:Arc[T,B]) => {
    val b = if(arc.getIsBase()) b1 else arc.computeTokenToBind(arc.computeArcExp(arc.computeBindToToken(b1)).get)
    getEval()(b, b2) && noToken >= arc.getNoTokArcExp()
  }
    
  private def mergeFull = (b1:B,b2:B) => { 
    getMerge()(b1,b2)
  }
  
  def addIn(arc: Arc[_,B]) {
    if(arc.getIsBase())
      in = arc :: in
    else
      in = in ::: List[Arc[_,B]](arc)
  }

  def addOut(arc: Arc[_,B]) {
    out = arc :: out
  }

  def removeIn(arc: Arc[_,_]) {
    in = in.filterNot(_ == arc)
  }

  def removeOut(arc: Arc[_,_]) {
    out = out.filterNot(_ == arc)
  }
  
  def getIn():List[Arc[_,_]] = in
  
  def getOut():List[Arc[_,_]] = out
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
  def getOrigin(): Map[String,String] = origin
  
  def setOrigin(origin: Map[String,String]) { this.origin = origin }
  
  def getAttributes(): Map[String,Any] = attributes
  
  def setAttributes(attributes: Map[String,Any]) { this.attributes = attributes }
  
  def getGuard(): Guard[B] = guard
  
  def setGuard(guard: Guard[B]) { this.guard = guard }
  
  def getAction(): Action[B] = action
  
  def setAction(action: Action[B]) { this.action = action }
  
  def isArcEnabled(globtime:Long):(Boolean,List[B]) = { 
    val iterator = in.iterator
    var lbe = List[B]()
    
    breakable{ while(iterator.hasNext)
    {
      val arc = iterator.next() match { case arc:Arc[_,B] => arc }
      
      val tokensBefGlobTime = arc.getPlace().getCurrentMarking().multiset.filter(tokenWT => tokenWT._1._2 <= globtime)
      
      val mapListBinding = Map[B,Int]()
      var listBinding = List[B]()
      
      //if arc base with optToken is equal None -> dismiss token (using this representation we don't have to use evalLast) => more efficient
      tokensBefGlobTime.foreach(tokenWT => tokenWT match { 
        case ((colset:arc.coltype, _:Long),num:Int) => { val optToken:Option[arc.coltype] = if(arc.getIsBase()) arc.computeArcExp(colset) else Some(colset); if(optToken != None) { val bind = arc.computeTokenToBind(optToken.get).asInstanceOf[B]; mapListBinding += (bind -> (mapListBinding.getOrElse(bind, 0)+num)) } }
      } )
      
      if(arc.getIsBase())
        listBinding = mapListBinding.filter(bindingWN => bindingWN._2 >= arc.getNoTokArcExp()).map(_._1).toList
      else
        listBinding = mapListBinding.keys.toList
      
      //change representation to consider lbe first than listBinding
      //we need to change to this representation to handle case as follow
      //if the place is empty but arc have None inscription, binding still should be enabled, previously it will be rejected
      //code previously as follow lbe = if(lbe.isEmpty) listBinding else listBinding.flatMap(b2 => lbe.collect { case b1 if evalFull(b1,b2,mapListBinding(b2),arc) => mergeFull(b1, b2) } )
      lbe = if(lbe.isEmpty) 
              listBinding 
            else 
              lbe.flatMap(b1 => {
                if(!arc.getIsBase()) { 
                  val optToken = arc.computeArcExp(arc.computeBindToToken(b1))
                  if(optToken == None) { 
                    List(b1)
                  } 
                  else {
                    listBinding.collect { case b2 if evalFull(b1,b2,mapListBinding(b2),arc) => mergeFull(b1, b2) }
                  } 
                }
                else {
                  listBinding.collect { case b2 if evalFull(b1,b2,mapListBinding(b2),arc) => mergeFull(b1, b2) }
                }
              })
      //println(arc.getId(),lbe,arc.getArcExp().toString())
      if(lbe.isEmpty)
        break
    }}
    
    (lbe.length > 0, lbe) 
  }
  
  def isEnabled(globtime:Long):Boolean = {
    val (isArcEn, lbe) = isArcEnabled(globtime)
    
    if(getGuard() != null) {
      val resEvalG = getGuard().evalGuard(lbe)
      setLbeBase(resEvalG._2)
      resEvalG._1
    }
    else {
      setLbeBase(lbe)
      isArcEn
    }
  }
  
  def execute(globtime:Long):B = {
    val r = new java.util.Random();
    val bindingChosen = lbeBase(r.nextInt(lbeBase.length))
    in.foreach(arc => { 
      val optTokenChosen = arc.computeArcExp(arc.computeBindToToken(bindingChosen))
      if(optTokenChosen != None) {
        val setTokenWTChosen = arc.getPlace().getCurrentMarking().multiset.keys.filter(tokenWT => { tokenWT._2 <= globtime && optTokenChosen.get == tokenWT._1 } ).toIterator
        for(i <- 1 to arc.getNoTokArcExp()) {
          if(setTokenWTChosen.hasNext)
          {
            arc.getPlace().removeTokenWithTime(setTokenWTChosen.next(),arc.getNoTokArcExp())
          }
        }
      }
    } )
    var bindingCombine:B = bindingChosen
    if(action != null)
    {
      val bindingAction = action.computeActionFun(bindingChosen)
      bindingCombine = getMerge()(bindingChosen,bindingAction)
    }
    out.foreach(arc => {
      val optTokenChosen = arc.computeArcExp(arc.computeBindToToken(bindingCombine))
      if(optTokenChosen != None) {
        val timetoken = if(arc.getAddTime() == null) globtime else globtime+arc.computeAddTime(bindingChosen)
        arc.getPlace().addTokenWithTime((optTokenChosen.get, timetoken),arc.getNoTokArcExp())
      }
    } )
    bindingChosen
  }
  
  def setEval(eval:(B,B) => Boolean) = { this.eval = eval }
  
  def getEval():((B,B) => Boolean) = { this.eval }
  
  def setMerge(merge:(B,B) => B) = { this.merge = merge }
  
  def getMerge():((B,B) => B) = { this.merge }
  
  def setLbeBase(lbeBase:List[B]) = { this.lbeBase = lbeBase }
  
  def getLbeBase():List[B] = { this.lbeBase }
  
  override def toString = name
}