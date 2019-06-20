package io.iochord.dev.chdsr.model.cpn.v1.impl

import scala.util.control.Breaks._
import io.iochord.dev.chdsr.model.cpn.v1._

class Transition[B <:Bind] (
  private var id: String,
  private var name: String,
  private var guard: Guard[B] = null,
  private var action: Action[B] = null) extends Element with Node {
  
  private var in = List[Arc[_,_]]()
  private var out = List[Arc[_,_]]()
  
  private var eval:(B,B) => Boolean = null
  private var merge:(B,B) => B = null
  private var evalLast:B => Boolean = null
  
  private var lbeBase:List[B] = null
  
  private val eval_full = (b1:B, b2:B, arc:Arc[_,B], transArcExp:(Any,Arc[_,B]) => Any) => {
    //println(arc.getId(),"In",arc.getIsBase())
    if(arc.getIsBase()) {
      if(transArcExp(b2,arc) == None)
        false
      else
        getEval()(b1, b2)
    }
    else {
      getEval()(arc.computeTokenToBind(transArcExp(b1,arc)),b2)
    }
  }
    
  private val merge_full = (b1:B,b2:B) => { 
    getMerge()(b1, b2)
  }
  
  def addIn(arc: Arc[_,_]) {
    if(arc.getIsBase())
      in = arc :: in
    else
      in = in ::: List[Arc[_,_]](arc)
  }

  def addOut(arc: Arc[_,_]) {
    out = arc :: out
  }

  def removeIn(arc: Arc[_,_]) {
    in = in.filterNot(_ == arc)
  }

  def removeOut(arc: Arc[_,_]) {
    out = out.filterNot(_ == arc)
  }
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
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
      val tokensBefGlobTime = arc.getPlace().getcurrentMarking().multiset.keys.filter(tokenWT => tokenWT._2 <= globtime)
      
      //println(arc.getId(),"first")
      
      if(tokensBefGlobTime.isEmpty) {
        lbe = List[B]()
        break
      }
      
      val listbinding = tokensBefGlobTime.map(token => token match { 
        // we want to change from token to bind (need to change to bind to have common form of data type over all arcs for this transition
        case (colset:Any, _:Long) => { arc.computeTokenToBind(if(arc.getIsBase())arc.computeArcExp(colset) else colset).asInstanceOf[B] }
      } ).toList
      
      lbe = if(lbe.isEmpty) listbinding else listbinding.flatMap(b2 => lbe.collect { case b1 if eval_full(b1,b2,arc,transArcExp) => merge_full(b1, b2) } )
      if(lbe.isEmpty)
        break
    }}
    
    lbe = lbe.filter(getEvalLast()(_))
    
    (lbe.length > 0, lbe) 
  }
  
  def isEnabled(globtime:Long):Boolean = {
    val (isArcEn, lbe) = isArcEnabled(globtime)
    //println(isArcEn,lbe)
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
  
  def execute(globtime:Long) {
    val r = new java.util.Random();
    val bindingChosen = lbeBase(r.nextInt(lbeBase.length))
    in.foreach(arc => { 
      val setTokenWTChosen = arc.getPlace().getcurrentMarking().multiset.keys.filter(tokenWT => { val token = arc.computeArcExp(arc.computeBindToToken(bindingChosen)); tokenWT._2 <= globtime && token == tokenWT._1 } )
      if(setTokenWTChosen.size > 0) {
        val tokenWTChosen = setTokenWTChosen.head
        arc.getPlace().removeTokenWithTime(tokenWTChosen)
      }
    } )
    out.foreach(arc => {
      var bindingCombine:B = bindingChosen
      if(action != null)
      {
        val bindingAction = action.computeTokenToBind(action.computeActionFun(action.computeBindToToken(bindingChosen))) 
        println("Output with Action",arc.getId(),bindingAction)
        bindingCombine = getMerge()(bindingChosen,bindingAction)
        //println(arc.getId(),bindingCombine)
      }
      val tokenChosen = arc.computeArcExp(arc.computeBindToToken(bindingCombine))
      val timetoken = if(arc.getAddTime() == null) globtime else globtime+arc.computeAddTime()
      arc.getPlace().addTokenWithTime((tokenChosen, timetoken))
    } )
  }
  
  def transArcExp(bind:Any, arc: Arc[_,B]) = (bind,arc) match { case (bind:B, arc:Arc[_,B]) => transform(bind,arc.getBindToToken(),arc.getArcExp()) }
  
  def transform(bind:B, toToken:Any=>Any, arcIns:Any=>Any) = {
    //println("Eval true",bind)
    val token = toToken(bind)
    arcIns(token)
  }
  
  def setEval(eval:(B,B) => Boolean) = { this.eval = eval }
  
  def getEval():((B,B) => Boolean) = { this.eval }
  
  def setMerge(merge:(B,B) => B) = { this.merge = merge }
  
  def getMerge():((B,B) => B) = { this.merge }
  
  def setEvalLast(evalLast:B => Boolean) = { this.evalLast = evalLast }
  
  def getEvalLast():(B => Boolean) = { this.evalLast }
  
  def setLbeBase(lbeBase:List[B]) = { this.lbeBase = lbeBase }
  
  def getLbeBase():List[B] = { this.lbeBase }
  
  override def toString = name
}