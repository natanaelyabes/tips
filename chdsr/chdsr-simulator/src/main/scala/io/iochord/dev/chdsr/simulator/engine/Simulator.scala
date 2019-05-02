package io.iochord.dev.chdsr.simulator.engine

import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition
import io.iochord.dev.chdsr.simulator.util.ChdsrRandom

object Simulator {
  val globTime:Long = 0
  
  private def enabledTransitions(transitions: List[Transition]) = {
    transitions
  }

  def enabledBindings(transitions: List[Transition]) = enabledTransitions(transitions).size > 0
  
  def run(net: CPNGraph, steps: Int = 10) = {
    
    val allTransitions = net.allTransitions
    
    var c = 0
    while (enabledBindings(allTransitions) && steps > c) {
      
      val transitions = enabledTransitions(allTransitions)

      val transition = ChdsrRandom.selectRandom(transitions)
      
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
    while (enabledBindings(allTransitions) && stopCrit(inpStopCrit)) {
      
    }
  }
  
  def getGlobTime(): Long = { globTime }
  
  def addGlobTime(addTime:Long): Unit = { globTime+addTime }
  
}