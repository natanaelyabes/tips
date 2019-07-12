package io.iochord.dev.chdsr.simulator.engine

import scala.util.control.Breaks._

import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition
import io.iochord.dev.chdsr.model.cpn.v1.impl.GlobalTime
import io.iochord.dev.chdsr.simulator.engine.subject._

import scala.collection.mutable._

case class Simulator() {
  
  var c = 0
    
  private def enabledTransitions(transitions: List[Transition[_]],globtime:GlobalTime) = {
    transitions.filter(t => {t.isEnabled(globtime.time)})
  }

  private def evalGlobalTime(net:CPNGraph, globtime:GlobalTime):(Boolean,List[Transition[_]]) = {
    var times = List[Long]()
    net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; multiset.keys.filter(_._2 > globtime.time).foreach(key => { times = key._2::times }) })
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
  
  def run(net:CPNGraph, stepsRef:Int = 10, globtime:GlobalTime = new GlobalTime(0L), subject:MarkingObservable = null) {
    val steps = c+stepsRef;
    
    val allTransitions = net.allTransitions
    
    var transitions:List[Transition[_]] = null
    breakable {
      while (steps > c) {
        transitions = enabledTransitions(allTransitions,globtime)
        if(transitions.size == 0) {
          val (reseval, transitions_tmp) = evalGlobalTime(net, globtime)
          if(!reseval)
            break
          transitions = transitions_tmp
        }
        
        val r = new java.util.Random()
        val transition = transitions(r.nextInt(transitions.length))
        
        val markbefore = Map[String,Any]()
        val markafter = Map[String,Any]()
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markbefore.put(arc.getPlace().getName(),multiset) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markbefore.put(arc.getPlace().getName(),multiset) } )
        
        val bindingChosen = transition.execute(globtime.time)
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markafter.put(arc.getPlace().getName(),multiset) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markafter.put(arc.getPlace().getName(),multiset) } )
        
        if(subject != null) {
          println("================ Step: "+c+" | globtime: "+globtime.time+" ================")
          println("Transition: "+transition.getId(),transition.getName())
          subject.setMarking((markbefore,markafter,transition.getId()+" - "+bindingChosen,globtime.getTime()))  
        }
        c += 1
      }
    }
    
    if (c != steps)
      println("stop - no more enabled transitions")
  }

  def fastRun(net: CPNGraph, stopCrit:Any => Boolean, inpStopCrit:Any, globtime:GlobalTime = new GlobalTime(0), subject:MarkingObservable = null) {
    
    val allTransitions = net.allTransitions
    
    var transitions:List[Transition[_]] = null
    
    breakable {
      while (!stopCrit(inpStopCrit)) {
        transitions = enabledTransitions(allTransitions, globtime)
        if(transitions.size == 0) {
          val (reseval, transitions_tmp) = evalGlobalTime(net, globtime)
          if(!reseval)
            break
          transitions = transitions_tmp
        }
        
        val r = new java.util.Random();
        val transition = transitions(r.nextInt(transitions.length))
        
        val markbefore = Map[String,Any]()
        val markafter = Map[String,Any]()
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markbefore.put(arc.getPlace().getName(),multiset) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markbefore.put(arc.getPlace().getName(),multiset) } )
        
        val bindingChosen = transition.execute(globtime.time)
        
        transition.getIn().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markafter.put(arc.getPlace().getName(),multiset) } )
        transition.getOut().foreach(arc => { val multiset = arc.getPlace().getcurrentMarking().multiset; markafter.put(arc.getPlace().getName(),multiset) } )
        
        if(subject != null) {
          println("================ Step: "+c+" | globtime: "+globtime.time+" ================")
          println("Transition: "+transition.getId(),transition.getName())
          subject.setMarking((markbefore,markafter,transition.getId()+" - "+bindingChosen,globtime.getTime()))  
        }
        c += 1
      }
    }
    
    if (stopCrit(inpStopCrit))
      println("stop criteria meet in step : "+c)
    else
      println("stop - no more enabled transitions")
  }
}