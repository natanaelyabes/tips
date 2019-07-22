package io.iochord.dev.chdsr.simulator.compiler

import scala.collection.mutable.HashMap

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.simulator.engine.subject.MarkingObservable
import io.iochord.dev.chdsr.simulator.engine.Simulator
import java.util.Observer

abstract class Simulation(val simulator:Simulator = new Simulator()) {
  val cgraph = CPNGraph()
  val globtime = new GlobalTime(0)
  var step:Int = 10
  var stopCrit:Any => Boolean = null
  var inpStopCrit:Any = null
  var subject:MarkingObservable = new MarkingObservable()
  
  def addObserver(observer:Observer) {
    subject.addObserver(observer)
  }
  
  def runStepSimulation(n:Int): Unit = {
    step = n
    simulator.run(cgraph, step, globtime, subject)
  }
  
  def runUntilMaxArrival(): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.fastRun(cgraph, stopCrit, inpStopCrit, globtime, subject)
  }
  
  def runStopCriteriaSimulation(stopCritLoc:Any => Boolean = (stop:Any) => stop match { case stop:Boolean => stop }, inpStopCritLoc:Any = false): Unit = {
    simulator.fastRun(cgraph, stopCrit, inpStopCrit, globtime, subject)
  }
}
