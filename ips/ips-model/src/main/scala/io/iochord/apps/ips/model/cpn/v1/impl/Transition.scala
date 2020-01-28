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
  
  def addIn[T](arc: Arc[T,B]) {
    if(arc.getIsBase())
      in = arc :: in
    else
      in = in ::: List[Arc[_,B]](arc)
  }

  def addOut[T](arc: Arc[T,B]) {
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
    var lbe = List[B]()
    
    val iterator = in.iterator
    
    breakable{while(iterator.hasNext){
      val arc = iterator.next() match { case arc:Arc[_,B] => arc }
      val map = arc.getPlace().getCurrentMarking().multiset.filter(_._1._2 <= globtime).groupBy(_._1._1).map({ 
        case (k,v) => k -> (v map (_._2) sum) 
      }).filter(_._2 >= arc.noTokArcExp)
      
      if(arc.getIsBase()) {
        val lbo = map.map(m => { 
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
        else
          lbe = lbe.flatMap(b1 => { 
            lbo.collect{ 
              case b2 if(eval(b1,b2)) => merge(b1,b2)
            } 
        })
      }
      else {
        val lto = map.map(m => { 
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
    (lbe.length > 0, lbe)
  }
  
  def isArcEnabledLooksRecur(globtime:Long):(Boolean,List[B]) = {
    var lbe = List[B]()
    
    val b = recursive(in,0,0,None)
    if(b != None)
      lbe = b.get::lbe
    
    (lbe.length > 0, lbe)   
  }
  
  def recursive(inp:List[Arc[_,B]],seq:Int,globtime:Long,bprev:Option[B]):Option[B] = {
    var bchoose:Option[B] = None
    
    val arc = inp(seq)
    
    val map = arc.getPlace().getCurrentMarking().multiset.filter(_._1._2 <= globtime).groupBy(_._1._1).map({ 
      case (k,v) => k -> (v map (_._2) sum) 
    }).filter(_._2 >= arc.noTokArcExp)
    
    if(arc.getIsBase()) {
      val lbo = map.map(m => { 
        val t = m._1
        t match { 
          case t:arc.coltype => { 
            val b = arc.computeTokenToBind(t)
            b 
          } 
        } 
      } ).filter(_ != None).map(_.get).toList
      
      for(b <- lbo) {
        if(seq == 0) {
          recursive(inp,seq+1,globtime,Some(b))
        }
        else {
          if(eval(bprev.get,b)) {
            val bnext = merge(bprev.get,b)
            if(seq < inp.length-1)
              recursive(inp,seq+1,globtime,Some(bnext))
            else
              bchoose = Some(bnext)
          }
        }
      }
    }
    else {
      val lto = map.map(m => { 
        val t = m._1
        t
      } ).toList
      
      for(t <- lto) {
        if(arc.BtoTV(bprev.get) == t) {
          if(seq < inp.length-1)
            recursive(inp,seq+1,globtime,Some(bprev.get))
          else
            bchoose = Some(bprev.get)
        }
      }
    }
    
    bchoose
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
    val r = new java.util.Random()
    val bChosen = lbeBase(r.nextInt(lbeBase.length))
    
    in.foreach(arc => { 
      val tChosen = arc.BtoTV(bChosen)
      if(tChosen != None) {
        val lTchosenComp = arc.getPlace().getCurrentMarking().multiset.filter(_._1._2 <= globtime).groupBy(_._1._1).filter(_._1 == tChosen ).head._2
        val nConsume = 0
        breakable{
          for(tChosenComp <- lTchosenComp) {
            val tWt = tChosenComp._1
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
        arc.getPlace().addTokenWithTime((tGen, timetoken),arc.noTokArcExp)
      }
    } )
    bModify
  }
  
  def setEval(eval:(B,B) => Boolean) = { this.eval = eval }
  
  def setMerge(merge:(B,B) => B) = { this.merge = merge }
  
  def setLbeBase(lbeBase:List[B]) = { this.lbeBase = lbeBase }
  
  override def toString = name
}