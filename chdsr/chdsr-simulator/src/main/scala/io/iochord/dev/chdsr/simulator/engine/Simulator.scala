package io.iochord.dev.chdsr.simulator.engine

import io.iochord.dev.chdsr.simulator.model.CPNGraph
import io.iochord.dev.chdsr.simulator.model.Transition
import io.iochord.dev.chdsr.simulator.util.Random

object Simulator {
  private def enabledTransitions(transitions: List[Transition]) = {
    transitions
  }

  def enabledBindings(transitions: List[Transition]) = enabledTransitions(transitions).size > 0
  
  def run(net: CPNGraph, steps: Int = 10) = {
    
    val allTransitions = net.allTransitions
    
    var c = 0
    while (enabledBindings(allTransitions) && steps > c) {
      
      val transitions = enabledTransitions(allTransitions)

      val transition = Random.selectRandom(transitions)
      
      c += 1
    }
    if (c == steps)
      println("step : "+steps)
    else
      println("stop - no more enabled transitions")
  }

  def fastRun[T](net: CPNGraph, stCriteria:(T) => Boolean) = {
    
  }
}