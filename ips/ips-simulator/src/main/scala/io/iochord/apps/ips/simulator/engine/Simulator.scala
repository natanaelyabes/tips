package io.iochord.apps.ips.simulator.engine

import scala.util.control.Breaks._

import io.iochord.apps.ips.model.cpn.v1.impl.CPNGraph
import io.iochord.apps.ips.model.cpn.v1.impl.Transition
import io.iochord.apps.ips.model.cpn.v1.impl.GlobalTime
import io.iochord.apps.ips.simulator.engine.subject._

import scala.collection.mutable._
import scala.concurrent.Future
import scala.collection.Seq
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Simulator engine class
 *
 */

case class Simulator(calcAvgTimeEnTr:Boolean = false) {
  
  var c = 0
  
  var stTimeEnTr = 0L
  var avgTimeEnTr = 0L
  
  /**
   * @param transitions : list of transition exist in the graph
   * @param globtime : current global time of simulation
   * @return list of transition that is enabled
   */
  private def enabledTransitions(transitions: List[Transition[_]],globtime:GlobalTime) = {
    transitions.filter(t => {t.isEnabled(globtime.time)})
  }

  /**
   * @param transitions : list of transition exist in the graph
   * @param globtime : current global time of simulation
   * @return list of transition that is enabled
   * 
   * Implement concurrent checking of enabled transition
   */
  private def enabledAsyncTransitions(transitions: List[Transition[_]],globtime:GlobalTime) = {
    val enFutureTransitions: Future[List[Transition[_]]] = 
      Future.sequence(transitions.map(t => Future{t.isEnabled(globtime.time)})).map {
          bs => (transitions zip bs).filter(_._2).map(_._1)
        }
    enFutureTransitions
  }
  
  /**
   * @param net : cpngraph for this simulation
   * @param globtime : current global time of simulation
   * @return the increment of global time simulation that make one of transition could be enabled
   */
  private def evalGlobalTime(net:CPNGraph, globtime:GlobalTime):(Boolean,List[Transition[_]]) = {
    var times = List[Long]()
    net.allPlaces.foreach(place => { 
      val multiset = place.getCurrentMarking().multiset
      multiset.keys.filter(_._2 > globtime.time).foreach(key => { times = key._2::times }) 
    })
    times = times.distinct.sorted
    times.foreach(time => {
      val trans = net.allTransitions.filter(t => {t.isEnabled(time)})
      if(trans.size > 0) {
        globtime.time = time
        return (true, trans)
      }
    })
    return (false,null)
  }
  
  /**
   * @param net : cpngraph for this simulation
   * @param globtime : current global time of simulation
   * @return the increment of global time simulation that make one of transition could be enabled
   * 
   * Implement concurrent checking of enabled transition in this function
   */
  private def evalAsyncGlobalTime(net:CPNGraph, globtime:GlobalTime):(Boolean,List[Transition[_]]) = {
    var trans:List[Transition[_]] = null
    var times = List[Long]()
    net.allPlaces.foreach(place => { 
      val multiset = place.getCurrentMarking().multiset
      multiset.keys.filter(_._2 > globtime.time).foreach(key => { times = key._2::times }) 
    })
    times = times.distinct.sorted
    times.foreach(time => {
      val enF: Future[List[Transition[_]]] = 
      Future.sequence(net.allTransitions.map(t => Future{t.isEnabled(time)})).map {
          bs => (net.allTransitions zip bs).filter(_._2).map(_._1)
        }
      val finF = enF andThen {
        case Success(res) => trans = res
        case Failure(ex)  => println("Exception: " + ex.getMessage)
      }
      
      Await.ready(finF, Duration.Inf)
      if(trans.size > 0) {
        globtime.time = time
        return (true, trans)
      }
    })
    return (false,null)
  }
  
  /**
   * @return current step of simulation
   */
  def getCurrentStep() = {
    c
  }
  
  /**
   * @return average time of enabled transition (only used for testing)
   */
  def getAvgTimeEnTr() = {
    avgTimeEnTr
  }
  
  /**
   * @param net : cpngraph for this simulation
   * @param stopCrit : stopping criteria function
   * @param inpStopCrit : input for stopCrit function
   * @param stepsRef : how many step simulation should run
   * @param globtime : current global time of simulation
   * @param subject : subject in observer pattern to publish the event
   */
  def run(net: CPNGraph, stopCrit:Any => Boolean, inpStopCrit:Any, stepsRef:Int = 0, globtime:GlobalTime = new GlobalTime(0), subject:MarkingObservable = null) {
    val steps = c+stepsRef;
    
    val allTransitions = net.allTransitions
    
    var transitions:List[Transition[_]] = null
    
    breakable {
      while ((stepsRef < 0 || steps > c) && !stopCrit(inpStopCrit)) {
        
        if(calcAvgTimeEnTr)
          stTimeEnTr = System.nanoTime()
        
        transitions = enabledTransitions(allTransitions,globtime)
        
        if(transitions.size == 0) {
          val (reseval, transitions_tmp) = evalGlobalTime(net, globtime)
          if(!reseval)
            break
          transitions = transitions_tmp
        }
        if(calcAvgTimeEnTr) {
          val tnow = System.nanoTime()
          val enTr = tnow - stTimeEnTr
          avgTimeEnTr = if(c == 0) enTr else (avgTimeEnTr + enTr)/2 // increment averaging
          //println(c + " : " + avgTimeEnTr + " , " + enTr + " , " + stTimeEnTr + " , " +tnow)
        }
        
        val r = new java.util.Random();
        val transition = transitions(r.nextInt(transitions.length))
        
        val markbefore = Map[(String,Map[String,String]),Any]()
        val markafter = Map[(String,Map[String,String]),Any]()
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markbefore.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markbefore.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        //println(markbefore)
        //println(transition.getName())
        
        val bindingChosen = transition.execute(globtime.time)
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markafter.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markafter.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        //println(markafter)
        
        if(subject != null) {
          //println("================ Step: "+c+" | globtime: "+globtime.time+" ================")
          //println("Transition: "+transition.getId(),transition.getName())
          subject.setMarking((globtime.getTime(), c+1, (transition.getId(), transition.getOrigin()),markbefore,markafter)) 
        }
        c += 1
      }
    }
    
    if (stopCrit(inpStopCrit))
      println("stop criteria meet in step : "+c)
    else
      println("stop - at step "+c)
  }
  
  /**
   * @param net
   * @param stopCrit
   * @param inpStopCrit
   * @param stepsRef
   * @param globtime
   * @param subject
   * 
   * Same as above but using concurrent checking of enabled transition
   */
  def runWithAsync(net: CPNGraph, stopCrit:Any => Boolean, inpStopCrit:Any, stepsRef:Int = 0, globtime:GlobalTime = new GlobalTime(0), subject:MarkingObservable = null) {
    val steps = c+stepsRef;
    
    val allTransitions = net.allTransitions
    
    var transitions:List[Transition[_]] = null
    
    breakable {
      while ((stepsRef < 0 || steps > c) && !stopCrit(inpStopCrit)) {
        
        if(calcAvgTimeEnTr)
          stTimeEnTr = System.nanoTime()
        
        val enF = enabledAsyncTransitions(allTransitions, globtime)
        val finishF = enF andThen {
          case Success(res) => transitions = res
          case Failure(ex)  => println("Exception: " + ex.getMessage)
        }
        
        Await.ready(finishF, Duration.Inf)
        
        if(transitions.size == 0) {
          val (reseval, transitions_tmp) = evalAsyncGlobalTime(net, globtime)
          if(!reseval)
            break
          transitions = transitions_tmp
        }
        if(calcAvgTimeEnTr) {
          val tnow = System.nanoTime()
          val enTr = tnow - stTimeEnTr
          avgTimeEnTr = if(c == 0) enTr else (avgTimeEnTr + enTr)/2
          //println(c + " : " + avgTimeEnTr + " , " + enTr + " , " + stTimeEnTr + " , " +tnow)
        }
        
        val r = new java.util.Random()
        val transition = transitions(r.nextInt(transitions.length))
        
        val markbefore = Map[(String,Map[String,String]),Any]()
        val markafter = Map[(String,Map[String,String]),Any]()
        
        transition.getIn().foreach(arc => { 
          val multiset = arc.getPlace().getCurrentMarking().multiset;
          markbefore.put((arc.getPlace().getId(), arc.getPlace().getOrigin()), multiset.clone());
        });
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markbefore.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        //println(markbefore)
        //println(transition.getName())
        
        val bindingChosen = transition.execute(globtime.time)
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markafter.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getCurrentMarking().multiset; markafter.put((arc.getPlace().getId(),arc.getPlace().getOrigin()),multiset.clone()) } )
        //println(markafter)
        
        if(subject != null) {
          //println("================ Step: "+c+" | globtime: "+globtime.time+" ================")
          //println("Transition: "+transition.getId(),transition.getName())
          subject.setMarking((globtime.getTime(), c+1, (transition.getId(), transition.getOrigin()),markbefore,markafter))   
        }
        c += 1
      }
    }
    
    if (stopCrit(inpStopCrit))
      println("stop criteria meet in step : "+c)
    else
      println("stop - at step "+c)
  }
}