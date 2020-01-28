package io.iochord.apps.ips.simulator.compiler

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import io.iochord.apps.ips.model.cpn.v1.impl.CPNGraph
import java.util.LinkedHashMap

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
  
  def getInstance = { 
    val sim = compiledCode().asInstanceOf[Simulation]
    val cgraph = new CPNGraph()
    val it = scalaSource.keySet().iterator()
    while(it.hasNext()) {
      val key = it.next()
      val strcomp = importStm+"\nval cgraph = new CPNGraph()\n"+scalaSource.get(key)
      val compcode = toolbox.compile(toolbox.parse(strcomp))
      val cgraphnew = compcode().asInstanceOf[CPNGraph]
      cgraphnew.getPlaces().forEach(p => {cgraph.addPlaceMerge(p)})
      cgraphnew.getTransitions().forEach(t => {cgraph.addTransitionMerge(t)})
      cgraphnew.getArcs().forEach(a => {cgraph.addArcMerge(a) })
    }
    sim.cgraph = cgraph
    sim
  }
}