package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.apps.ips.model.cpn.v1.impl._
import io.iochord.apps.ips.model.cpn.v1._
import io.iochord.apps.ips.simulator.engine.Simulator
import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver
import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable
import io.iochord.apps.ips.simulator.compiler.MemoryScalaFileCompiler

import scala.collection.mutable._
import scala.util.control.Breaks._

object CompileFromFileTest {
  
  def main(args: Array[String]) {
    val pathfile = "simulscala.txt"
    val memoryScalaFactory = new MemoryScalaFileCompiler(pathfile)
    val simulation = memoryScalaFactory.getInstance
    simulation.addObserver(new MarkingObserver())
    
    val stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    val inpStopCritLoc = false
    
    simulation.runStopCriteria(stopCrit, inpStopCritLoc)
  }
}