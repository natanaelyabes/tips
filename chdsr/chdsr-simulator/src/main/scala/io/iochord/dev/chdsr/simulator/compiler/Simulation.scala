package io.iochord.dev.chdsr.simulator.compiler

import scala.collection.mutable.HashMap

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.simulator.engine.subject.MarkingObservable
import io.iochord.dev.chdsr.simulator.engine.Simulator
import java.util.Observer

abstract class Simulation(val simulator:Simulator = new Simulator(true)) {
  val cgraph = CPNGraph()
  val globtime = new GlobalTime(0)
  var stopCrit:Any => Boolean = null
  var inpStopCrit:Any = null
  var subject:MarkingObservable = new MarkingObservable()
  
  def getPetriNet():CPNGraph = {
    this.cgraph
  }
  
  def addObserver(observer:Observer) {
    subject.addObserver(observer)
  }
  
  def runStep(n:Int): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.run(cgraph, stopCrit, inpStopCrit, n, globtime, subject)
  }
  
  def runStepTime(timeUntil:Long, n:Int) = {
    this.stopCrit = (globT:Any) => globT match { case globT:GlobalTime => globT.time >= timeUntil }
    this.inpStopCrit = globtime
    
    if(n <= 0)
      simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject)
    else
      simulator.run(cgraph, stopCrit, inpStopCrit, n, globtime, subject)
  }
  
  def getCurrentStep() = {
    simulator.getCurrentStep()
  }
  
  def getCurrentTime() = {
    globtime.getTime()
  }
  
  def getAvgEnTrTime() = {
    simulator.getAvgTimeEnTr()
  }
  
  def runUntilMaxArrival(): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject)
  }
  
  def runStopCriteria(stopCritLoc:Any => Boolean = (stop:Any) => stop match { case stop:Boolean => stop }, inpStopCritLoc:Any = false): Unit = {
    simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject)
  }
}
