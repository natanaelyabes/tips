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
  
  def runStopCritSimulation(stopCritLoc:Any => Boolean = this.stopCrit, inpStopCritLoc:Any = this.inpStopCrit): Unit = {
    this.stopCrit = stopCritLoc
    this.inpStopCrit = inpStopCritLoc
    simulator.fastRun(cgraph, stopCrit, inpStopCrit, globtime, subject)
  }
  
  val vars = HashMap[String,Any]() // some value generated from simulation will be saved here
  
  def printDebugging(txtDebugging: String): Unit = {
    println(txtDebugging)
  }
  
  def getVar(nvar1:String): Any = {
    return vars.get(nvar1).getOrElse(null)
  }
  
  def putVar[T](key:String, var1:T): Unit = {
    vars.put(key,var1)
  }
}
