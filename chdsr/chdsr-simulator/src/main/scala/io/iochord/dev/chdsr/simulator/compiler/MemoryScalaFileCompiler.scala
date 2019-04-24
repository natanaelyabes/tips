package io.iochord.dev.chdsr.simulator.compiler

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

case class MemoryScalaFileCompiler(filePath: String) {
  import reflect.runtime.currentMirror
  import tools.reflect.ToolBox
  val toolbox = currentMirror.mkToolBox()
  import toolbox.u._
  import scala.io.Source
  
  val fileContents = Source.fromFile(filePath).getLines().mkString("\n")
  val tree = toolbox.parse("import io.iochord.dev.chdsr.simulator.compiler._; " + fileContents)
  val compiledCode = toolbox.compile(tree)
  
  def getCompiledCode: Simulation = compiledCode().asInstanceOf[Simulation]
}