package io.iochord.apps.ips.simulator.compiler

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import io.iochord.apps.ips.model.cpn.v1.impl.CPNGraph
import java.util.LinkedHashMap
import java.io.FileWriter
import io.iochord.apps.ips.common.util.LoggerUtil

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Compile class that take Map of scala string as input (per module)
 * Notice here we use LinkedHashMap from java.util not from scala library directly
 * Bacause we send LinkedHashMap from class from Java (Converter), we must use LinkedHashMap based on java in this scala class (not LinkHashMap of scala)
 */

class MemoryScalaCompilerPerModule(scalaSource: LinkedHashMap[String,String]) {
  
  val toolbox = ToolBox(currentMirror).mkToolBox()
  import toolbox.u._
  
  val importStm = "import io.iochord.apps.ips.simulator.compiler._; \n"+
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
      "import breeze.stats.distributions.Rayleigh \n"
      
  val tree = toolbox.parse(importStm+"new Simulation { }")
      
  val compiledCode = toolbox.compile(tree)
  
  
  /**
   * @return instance of simulation class after compiled
   * It compiled incrementally per module. 
   * You should able to differentiate between getInstance in here and  (MemoryScalaCompiler and MemoryScalaFileCompiler)
   */
  def getInstance = { 
    import java.io.PrintWriter
    val sim = compiledCode().asInstanceOf[Simulation]
    val cgraph = new CPNGraph()
    LoggerUtil.logInfo("Modules: " + scalaSource.values().size());
    val it = scalaSource.entrySet().parallelStream()
      .map[CPNGraph](new java.util.function.Function[java.util.Map.Entry[String, String], CPNGraph] {
        override def apply(value: java.util.Map.Entry[String, String]): CPNGraph = {
          LoggerUtil.logInfo("Compiling: " + value.getKey());
          val strcomp = importStm+"\nval cgraph = new CPNGraph()\n"+value.getValue
          try {
            val compcode = toolbox.compile(toolbox.parse(strcomp))
            val cgraphnew = compcode().asInstanceOf[CPNGraph]
            cgraph.synchronized {
              cgraphnew.getPlaces().forEach(p => {cgraph.addPlaceMerge(p)})
              cgraphnew.getTransitions().forEach(t => {cgraph.addTransitionMerge(t)})
              cgraphnew.getArcs().forEach(a => {cgraph.addArcMerge(a) })
            }
            return cgraphnew
          } catch {
            case e: Exception => { LoggerUtil.logInfo(value.getKey) }
          }
          return null
        }
      })
      .count()
    sim.cgraph = cgraph
    sim
  }
}