package io.iochord.dev.chdsr.simulator.engine

import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition
import io.iochord.dev.chdsr.simulator.util.ChdsrRandom

object Simulator {
  var globtime:Long = 0
  
  private def enabledTransitions(transitions: List[Transition[_]]) = {
    transitions.filter(t => {/*println("Check "+t.getId()+" "+t.isEnabled(globtime));*/t.isEnabled(globtime)})
  }

  def run(net: CPNGraph, steps: Int = 10) = {
    
    val allTransitions = net.allTransitions
    
    var c = 0
    var transitions:List[Transition[_]] = null
    while ({transitions = enabledTransitions(allTransitions); transitions.size} > 0 && steps > c) {
      val transition = ChdsrRandom.selectRandom(transitions)
      println("Step ",c)
      println("Before")
      net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; println(place.getId(),multiset) })
      transition.execute(globtime)
      println("After")
      net.allPlaces.foreach(place => { val multiset = place.getcurrentMarking().multiset; println(place.getId(),multiset) })
      c += 1
    }
    
    if (c == steps)
      println("step : "+steps)
    else
      println("stop - no more enabled transitions")
  }

  def fastRun[T](net: CPNGraph, stopCrit:T => Boolean, inpStopCrit:T) = {
    val allTransitions = net.allTransitions
    
    var c = 0
    var transitions:List[Transition[_]] = null
    while ({transitions = enabledTransitions(allTransitions); transitions.size} > 0 && stopCrit(inpStopCrit)) {
      
    }
  }
  
  def getGlobTime(): Long = { globtime }
  
  def addGlobTime(addTime:Long): Unit = { globtime = globtime+addTime }
}