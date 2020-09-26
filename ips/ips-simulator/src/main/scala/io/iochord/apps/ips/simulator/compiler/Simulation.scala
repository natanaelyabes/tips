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
 * Abstract simulation class that will be used in compiler class
 * This class cannot be instantiated directly because use abstract modifier 
 *
 */

abstract class Simulation(val simulator:Simulator = new Simulator(true)) {
  var cgraph = new CPNGraph()
  
  val globtime = new GlobalTime(0)
  var stopCrit:Any => Boolean = null
  var inpStopCrit:Any = null
  var subject:MarkingObservable = new MarkingObservable()
  var fileReportPath:String = "ReportCSV.csv"
  
  def setFileReportPath(fileReportPath:String) = { this.fileReportPath = fileReportPath }
  
  def getFileReportPath():String = this.fileReportPath
  
  /**
   * @return graph of petri net this current simulation object
   */
  def getPetriNet():CPNGraph = {
    this.cgraph
  }
  
  /**
   * @param observer : add observer object that will monitor the result of each simulation step
   */
  def addObserver(observer:Observer) {
    subject.addObserver(observer)
  }
  
  /**
   * @param n : assign and run the simulation until number of specific step
   */
  def runStep(n:Int): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.run(cgraph, stopCrit, inpStopCrit, n, globtime, subject, fileReportPath)
  }
  
  /**
   * @param n : assign and run the simulation by activate concurrent checking of enabled transition until number of specific step
   */
  def runStepWithCon(n:Int): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.runWithAsync(cgraph, stopCrit, inpStopCrit, n, globtime, subject)
  }
  
  /**
   * @param timeUntil : assign and run the simulation until specific global time is reached
   * @param n : assign and run the simulation until number of specific step
   * 
   * Run simulation with specific number of step or stop if current global time exceeds timeUntil (OR condition)
   */
  def runStepTime(timeUntil:Long, n:Int) = {
    this.stopCrit = (globT:Any) => globT match { case globT:GlobalTime => globT.time >= timeUntil }
    this.inpStopCrit = globtime
    
    if(n <= 0)
      simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject, fileReportPath)
    else
      simulator.run(cgraph, stopCrit, inpStopCrit, n, globtime, subject, fileReportPath)
  }
  
  /**
   * @return current step of simulation
   */
  def getCurrentStep() = {
    simulator.getCurrentStep()
  }
  
  /**
   * @return current global time of simulation
   */
  def getCurrentTime() = {
    globtime.getTime()
  }
  
  /**
   * @return average time of enabled transition (only used in testing performance)
   */
  def getAvgEnTrTime() = {
    simulator.getAvgTimeEnTr()
  }
  
  /**
   * run simulation without defined stopping criteria at all.
   * If max arrival is defined in generator, the simulation will stop if that condition (max arrival) is meet.
   * But if no max arrival is defined, the simulation will run infinitely
   */
  def runUntilMaxArrival(): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    this.inpStopCrit = false
    
    simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject, fileReportPath)
  }
  
  /**
   * run simulation until global time simulation reach at some point based on param.
   */
  def runUntilGlobalTime(stopTime:Long): Unit = {
    this.stopCrit = (stop:Any) => stop match { case stop:Long => {globtime.getTime() > stop} }
    this.inpStopCrit = stopTime
    
    simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject, fileReportPath)
  }
  
  /**
   * @param stopCritLoc : assign function for stopping criteria and give default anonymous function to stop directly if this stopCirtLoc function is not defined)
   * @param inpStopCritLoc : input for function stopCritLoc above
   * 
   * Running simulation until specific criteria defined is meet.
   */
  def runStopCriteria(stopCritLoc:Any => Boolean = (stop:Any) => stop match { case stop:Boolean => stop }, inpStopCritLoc:Any = false): Unit = {
    this.stopCrit = stopCritLoc
    this.inpStopCrit = inpStopCritLoc
    simulator.run(cgraph, stopCrit, inpStopCrit, -1, globtime, subject, fileReportPath)
  }
}
