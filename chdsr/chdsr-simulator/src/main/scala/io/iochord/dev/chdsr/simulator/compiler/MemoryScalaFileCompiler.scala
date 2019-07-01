package io.iochord.dev.chdsr.simulator.compiler

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

class MemoryScalaFileCompiler(filePath: String) {
  import reflect.runtime.currentMirror
  import tools.reflect.ToolBox
  val toolbox = currentMirror.mkToolBox()
  import toolbox.u._
  import scala.io.Source
  
  val fileContents = Source.fromFile(filePath).getLines().mkString("\n")
  val tree = toolbox.parse(
      "import io.iochord.dev.chdsr.simulator.compiler._; \n"+
      "import io.iochord.dev.chdsr.model.cpn.v1.impl._; \n"+
      "import io.iochord.dev.chdsr.model.cpn.v1._; \n"+
      "import io.iochord.dev.chdsr.simulator.engine.Simulator; \n"+
      "import io.iochord.dev.chdsr.simulator.engine.observer.MarkingObserver; \n"+
      "import io.iochord.dev.chdsr.simulator.engine.subject.MarkingObservable; \n"+
      "import scala.collection.mutable._; \n"+
      "import scala.util.control.Breaks._; \n"+
      "import breeze.stats.distributions.Gaussian; \n"+
      "import breeze.stats.distributions.Binomial; \n"+
      "import breeze.stats.distributions.ChiSquared; \n"+
      "import breeze.stats.distributions.Exponential; \n"+
      "import breeze.stats.distributions.Gamma; \n"+
      "import breeze.stats.distributions.Geometric \n"+
      "import breeze.stats.distributions.Laplace; \n"+
      "import breeze.stats.distributions.LogNormal; \n"+
      "import breeze.stats.distributions.NegativeBinomial; \n"+
      "import breeze.stats.distributions.StudentsT; \n"+
      "import breeze.stats.distributions.Uniform; \n"+
      "import breeze.stats.distributions.Rayleigh \n"+
      "new Simulation {\n"+fileContents+"\n}")
  
      val compiledCode = toolbox.compile(tree)
  
  def getInstance = compiledCode().asInstanceOf[Simulation]
}