package io.iochord.dev.chdsr.simulator.compiler

import scala.collection.mutable.HashMap
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

import io.iochord.dev.chdsr.simulator.model.CPNGraph
import io.iochord.dev.chdsr.simulator.model.Transition
import io.iochord.dev.chdsr.simulator.model.Place
import io.iochord.dev.chdsr.simulator.model.Arc

abstract class Simulation {
  val cpnGraph = CPNGraph()
  
  val vars = HashMap[String,Any]() // some value generated from simulation will be saved here
  
  def stepSimulation(cpnGraph: CPNGraph, nStep:Int): Unit
  def fastSimulation[T](cpnGraph: CPNGraph, stCriteria:(T) => Boolean): Unit
  
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

case class MemoryScalaCompiler(scalaSource: String) {
  import reflect.runtime.currentMirror
  import tools.reflect.ToolBox
  
  val toolbox = ToolBox(currentMirror).mkToolBox()
  
  import toolbox.u._
  
  val tree = toolbox.parse("import io.iochord.dev.chdsr.simulator.compiler._; " + scalaSource)
  val compiledCode = toolbox.compile(tree)
  
  println("Class Name : "+compiledCode.getClass.toString())
  
  def getInstance = compiledCode().asInstanceOf[Simulation]
}