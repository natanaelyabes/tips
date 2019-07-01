package io.iochord.dev.chdsr.simulator.compiler

import scala.collection.mutable.HashMap

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.simulator.engine.subject.MarkingObservable
import io.iochord.dev.chdsr.simulator.engine.Simulator

abstract class Simulation {
  val cgraph = CPNGraph()
  val globtime = new GlobalTime(0)
  var step:Int = 10
  var stopCrit:Any => Boolean = null
  var inpStopCrit:Any = null
  var subject:MarkingObservable = null
 
  def runSimulation(): Unit = {
    if(stopCrit != null || inpStopCrit != null) {
      Simulator.fastRun(cgraph, stopCrit, inpStopCrit, globtime, subject)
    }
    else {
      Simulator.run(cgraph, step, globtime, subject)
    }
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
