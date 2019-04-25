package io.iochord.dev.chdsr.simulator.compiler

import scala.collection.mutable.HashMap

import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition
import io.iochord.dev.chdsr.model.cpn.v1.impl.Place
import io.iochord.dev.chdsr.model.cpn.v1.impl.Arc

abstract class Simulation {
  val cpnGraph = CPNGraph()
  
  val vars = HashMap[String,Any]() // some value generated from simulation will be saved here
  
  def runSimulation(cpnGraph: CPNGraph): Unit
  def expState(): Unit
  
  def calcKPI[T](kpiFunc:T): Double
  
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
