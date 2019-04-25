package io.iochord.dev.chdsr.simulator.compiler

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox


case class MemoryScalaCompiler(scalaSource: String) {
  import reflect.runtime.currentMirror
  import tools.reflect.ToolBox
  
  val toolbox = ToolBox(currentMirror).mkToolBox()
  
  import toolbox.u._
  
  val tree = toolbox.parse("import io.iochord.dev.chdsr.simulator.compiler._; \n"+
      "import io.iochord.dev.chdsr.simulator.model.CPNGraph; \n"+   
      scalaSource)
      
  val compiledCode = toolbox.compile(tree)
  
  println("Class Name : "+compiledCode.getClass.toString())
  
  def getInstance = compiledCode().asInstanceOf[Simulation]
}