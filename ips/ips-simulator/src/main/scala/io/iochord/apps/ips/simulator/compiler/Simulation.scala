package io.iochord.apps.ips.simulator.compiler

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map

import io.iochord.apps.ips.model.cpn.v1.impl._
import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable
import io.iochord.apps.ips.simulator.engine.Simulator
import java.util.Observer

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

abstract class Simulation(val simulator:Simulator = new Simulator(true)) {
  var cgraph = new CPNGraph()
  
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
  
  def runStepWithCon(n:Int): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.runWithAsync(cgraph, stopCrit, inpStopCrit, n, globtime, subject)
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
    this.stopCrit = stopCritLoc
    this.inpStopCrit = inpStopCritLoc
    simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject)
  }
}
