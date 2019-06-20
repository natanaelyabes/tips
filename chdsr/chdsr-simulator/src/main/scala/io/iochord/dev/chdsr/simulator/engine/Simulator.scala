package io.iochord.dev.chdsr.simulator.engine

import scala.util.control.Breaks._
import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition
import io.iochord.dev.chdsr.model.cpn.v1.impl.GlobalTime

object Simulator {
  
  private def enabledTransitions(transitions: List[Transition[_]],globtime:GlobalTime) = {
    transitions.filter(t => {t.isEnabled(globtime.time)})
  }

  private def evalGlobalTime(net:CPNGraph, globtime:GlobalTime = GlobalTime(0L)):(Boolean,List[Transition[_]]) = {
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
  
  def run(net:CPNGraph, steps:Int = 10, globtime:GlobalTime = GlobalTime(0L)) {
    
    val allTransitions = net.allTransitions
    
    var c = 0
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
        
        val r = new java.util.Random();
        val transition = transitions(r.nextInt(transitions.length))
        println("================ Step: "+c+" | globtime: "+globtime+" ================")
        println("Transition: "+transition.getId(),transition.getName())
        println("Before")
        net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; println(place.getId(),multiset) })
        transition.execute(globtime.time)
        println("After")
        net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; println(place.getId(),multiset) })
        c += 1
      }
    }
    
    if (c == steps)
      println("step : "+steps)
    else
      println("stop - no more enabled transitions")
  }

  def fastRun(net: CPNGraph, stopCrit:Any => Boolean, inpStopCrit:Any, globtime:GlobalTime = GlobalTime(0)) {
    
    val allTransitions = net.allTransitions
    
    var c = 0
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
        println("================ Step: "+c+" | globtime: "+globtime+" ================")
        println("Transition: "+transition.getId(),transition.getName())
        println("Before")
        net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; println(place.getId(),multiset) })
        transition.execute(globtime.time)
        println("After")
        net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; println(place.getId(),multiset) })
        c += 1
      }
    }
  }
}