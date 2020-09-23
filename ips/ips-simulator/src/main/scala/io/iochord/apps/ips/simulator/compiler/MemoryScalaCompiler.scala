package io.iochord.apps.ips.simulator.compiler

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import java.io.FileWriter

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Compile class that take scala string as input
 */

class MemoryScalaCompiler(scalaSource: String) {
  
  val toolbox = ToolBox(currentMirror).mkToolBox()
  import toolbox.u._
  
  val tree = toolbox.parse(
      "import io.iochord.apps.ips.simulator.compiler._; \n"+
      "import io.iochord.apps.ips.model.cpn.v1.impl._; \n"+
      "import io.iochord.apps.ips.model.cpn.v1.impl.token._; \n"+
      "import io.iochord.apps.ips.model.cpn.v1._; \n"+
      "import io.iochord.apps.ips.simulator.engine.Simulator; \n"+
      "import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver; \n"+
      "import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable; \n"+
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
      "new Simulation {\n"+scalaSource+"\n}")
  
  val compiledCode = toolbox.compile(tree)
  
  /**
   * @return instance of simulation class after compiled
   */
  def getInstance = compiledCode().asInstanceOf[Simulation]
}