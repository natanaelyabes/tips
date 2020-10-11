package io.iochord.apps.ips.model.cpn.v1.impl

import scala.util.control.Breaks._
import scala.collection.mutable.Map
import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

class Transition[B] (
  private var id: String,
  private var name: String,
  private var guard: Guard[B] = null,
  private var action: Action[B] = null) extends Element with Node {
  
  private var in = List[Arc[_,B]]()
  private var out = List[Arc[_,B]]()
    
  private var eval:(B,B) => Boolean = null
  private var merge:(B,B) => B = null
  
  private var lbeBase:List[B] = null
  
  private var origin:Map[String,String] = null
  private var attributes:Map[String,Any] = null
  
  var statusEnOrUn: Int = 0 // 0 mean could be enabled (not yet evaluated), 1 mean enabled (with rest of LBE kept / lbeTmp), 2 mean unenabled (some token in each input places exist but not yet evaluated because time > globtime), 3 mean unenable (no token in some input place) 
  var statusEvalTime: Long = 0 // the global time when the last status evaluation is conducted / we need to keep this info because even though the transition status is enabled previously but when the global time is evolve, we need to recalculate binding (because some others token need to be considered)
  var lbeTmp:List[B] = null
  
  /**
   * @param arc : add new input arc to this transition
   * Base arc should be on first position of the list
   */
  def addIn[T](arc: Arc[T,B]) {
    arc.getPlace().transitions =  arc.getPlace().transitions :+ getId()
    
    if(arc.getIsBase())
      in = arc :: in
    else
      in = in ::: List[Arc[_,B]](arc)
  }

  /**
   * @param arc : add new output arc to this transition
   */
  def addOut[T](arc: Arc[T,B]) {
    out = arc :: out
  }

  /**
   * @param arc : remove specific input arc from this transition
   */
  def removeIn(arc: Arc[_,_]) {
    in = in.filterNot(_ == arc)
  }

  /**
   * @param arc : remove specific output arc from this transition
   */
  def removeOut(arc: Arc[_,_]) {
    out = out.filterNot(_ == arc)
  }
  
  /**
   * @return list of all input arcs from this transition
   */
  def getIn():List[Arc[_,_]] = in
  
  /**
   * @return list of all output arcs from this transition
   */
  def getOut():List[Arc[_,_]] = out
  
  /**
   * @return id of this transition. (see trait Element for detail explanation)
   */
  def getId(): String = id
  
  /**
   * @param id : assign id of this transition. (see trait Element for detail explanation)
   */
  def setId(id: String) { this.id = id }
  
  /**
   * @return name of this transition. (see trait Node for detail explanation)
   */
  def getName():String = name
  
  /**
   * @param name : assign name of this transition. (see trait Node for detail explanation)
   */
  def setName(name: String) { this.name = name }
  
  /**
   * @return origin of this transition. (see trait Element for detail explanation)
   */
  def getOrigin(): Map[String,String] = origin
  
  /**
   * @param origin : assign origin of this transition. (see trait Element for detail explanation)
   */
  def setOrigin(origin: Map[String,String]) { this.origin = origin }
  
  /**
   * @return attributes. Not used nor defined
   * Previously requested by Java Dev (Iq) but not yet clear what should be returned by this function 
   */
  def getAttributes(): Map[String,Any] = attributes
  
  /**
   * @param attributes. Not used nor defined
   * Previously requested by Java Dev (Iq) but not yet clear what should be defined by this function 
   */
  def setAttributes(attributes: Map[String,Any]) { this.attributes = attributes }
  
  /**
   * @return guard object of this transition
   * Guard is used to evaluate enabled transition (together with arc expression) 
   */
  def getGuard(): Guard[B] = guard
  
  /**
   * @param guard : assign guard object of this transition
   * Guard is used to evaluate enabled transition (together with arc expression) 
   */
  def setGuard(guard: Guard[B]) { this.guard = guard }
  
  /**
   * @return action object of this transition
   * Action is used to transform some binding variables values 
   */
  def getAction(): Action[B] = action
  
  /**
   * @param action : assign action object of this transition
   * Action is used to transform some binding variables values 
   */
  def setAction(action: Action[B]) { this.action = action }
  
  /**
   * @param globtime : accept input current global time of the simulation to be used to evaluate enabling transition
   * @return boolean value if this transition is enabled or not
   * This function combine between isArcEnabled and guard function
   * You change isArcEnabled to isArcEnabledLooksRecur to compare different method of arc enabled evaluation 
   */
  def isEnabled(globtime:Long):Boolean = {
    if(statusEnOrUn == 1) {
      lbeBase = lbeTmp
      return true
    }
      
    val (isArcEn, lbe) = isArcEnabled(globtime)
    
    if(isArcEn == 1 && getGuard() != null) {
      val resEvalG = getGuard().evalGuard(lbe)
      setLbeBase(resEvalG._2)
      changeStatus(resEvalG._1,globtime, resEvalG._2)
      resEvalG._1 == 1
    }
    else {
      setLbeBase(lbe)
      changeStatus(isArcEn,globtime,lbe)
      isArcEn == 1
    }
  }
  
  def changeStatus(enOrUn:Int, evalTime:Long, lbeBase:List[_]) {
    statusEnOrUn = if(enOrUn == 1 && lbeBase.size == 1) 0 else enOrUn
    statusEvalTime = evalTime
  }
  
  /**
   * @param globtime: accept input current global time of the simulation to be used for evaluate arc enabled binding
   * @return pair of Boolean value if this arc is enabled or not and List of binding after arc evaluation
   * This arc enabled function will evaluate all token in each place of input arc in this transition 
   */
  def isArcEnabled(globtime:Long):(Int,List[B]) = { 
    var lbe = List[B]()
    
    val iterator = in.iterator
    
    breakable{while(iterator.hasNext){
     val arc = iterator.next() match { case arc:Arc[_,B] => arc }
      
      val map = arc.getPlace().getCurrentMarking().multiset
      
      if(map.size == 0)
        return (3, List[B]())
      
      val mapF = map.filter(_._1._2 <= globtime).groupBy(_._1._1).map({ 
        case (k,v) => k -> (v map (_._2) sum) 
      }).filter(_._2 >= arc.noTokArcExp)
      
      if(mapF.size == 0)
        return (2, List[B]())
      
      if(arc.getIsBase()) {
        val lbo = mapF.map(m => { 
          val t = m._1
          t match { 
            case t:arc.coltype => { 
              val b = arc.computeTokenToBind(t)
              b 
            } 
          } 
        } ).filter(_ != None).map(_.get).toList
        
        if(lbe.isEmpty)
          lbe = lbo
        else {
          lbe = lbe.flatMap(b1 => { 
            lbo.collect{ 
              case b2 if(eval(b1,b2)) => merge(b1,b2)
            }
          })
        }
      }
      else {
        val lto = mapF.map(m => { 
          val t = m._1
          t
        } ).toList
        lbe = lbe.flatMap(b => { 
          lto.collect{
            case t if(arc.BtoTV(b) == t) => b
          } 
        })
      }
      
      if(lbe.isEmpty)
          break
    }}
    
    val ret = if(lbe.length > 0) 1 else 2
    (ret, lbe)
  }
  
  /**
   * @param globtime: accept input current global time of the simulation
   * @return new binding after transition is executed (actually this return value is not used in the simulation engine, only used just for reporting and debugging)
   * This function will consume or generate new token in input and output arc respectively.  
   */
  def execute(globtime:Long, cgraph:CPNGraph):B = {
    val r = new java.util.Random()
    val idxBindTake = r.nextInt(lbeBase.length)
    val bChosen = lbeBase(idxBindTake)
    
    lbeTmp = lbeBase.collect({
      case (b:B,i) if(i != idxBindTake) => {b}
    })
        
    in.foreach(arc => { 
      val tChosen = arc.BtoTV(bChosen)
      if(tChosen != None) {
        val place = arc.getPlace()
        place.transitions.foreach(t => {
          if(cgraph.transitions(t).statusEnOrUn == 1)
            cgraph.transitions(t).statusEnOrUn = 0 
        })
        val lTchosenComp = place.getCurrentMarking().multiset.filter(_._1._2 <= globtime).groupBy(_._1._1).filter(_._1 == tChosen ).head._2
        val nConsume = 0
        breakable{
          for(tChosenComp <- lTchosenComp) {
            val tWt = tChosenComp._1.asInstanceOf[arc.coltype]
            val nAvail = tChosenComp._2
            val tConsume = Math.min(arc.noTokArcExp - nConsume, nAvail)
            arc.getPlace().removeTokenWithTime(tWt, tConsume)
            
            if(nConsume == arc.noTokArcExp)
              break
          }
        }
      }
    } )
    
    var bModify:B = bChosen
    
    if(action != null)
    {
      val bAction = action.computeActionFun(bChosen)
      bModify = merge(bChosen,bAction)
    }
    
    out.foreach(arc => {
      val tGen = arc.BtoTV(bModify)
      if(tGen != None) {
        val timetoken = if(arc.addTime == null) globtime else globtime+arc.addTime(bChosen)
        arc.getPlace().transitions.foreach(t => {
          if(timetoken <= globtime)
            cgraph.transitions(t).statusEnOrUn = 0
          else {
            if(cgraph.transitions(t).statusEnOrUn != 1)
              cgraph.transitions(t).statusEnOrUn = 0  
          }
        })
        arc.getPlace().addTokenWithTime((tGen, timetoken),arc.noTokArcExp)
      }
    } )
    bModify
  }
  
  /**
   * @param eval: assign eval function to be used for comparing value between two base arc
   * This function will compare two binding value between two base arc
   * For comparing between other type of arc (base vs non base, non base vs non base) we don't use this function   
   */
  def setEval(eval:(B,B) => Boolean) = { this.eval = eval }
  
  /**
   * @param merge: assign merge function to be used to merge binding value between two arc
   * This function will be executed if evaluation between two arc is binding (some combination value is bind)    
   */
  def setMerge(merge:(B,B) => B) = { this.merge = merge }
  
  /**
   * @param lbeBase: assign combination list of binding that eligible currently 
   */
  def setLbeBase(lbeBase:List[B]) = { this.lbeBase = lbeBase }
  
  /**
   * @return name of this transition if toString method is called from this object class
   */
  override def toString = name
}