package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._
import io.iochord.dev.chdsr.simulator.engine.Simulator
import io.iochord.dev.chdsr.simulator.engine.observer.MarkingObserver
import io.iochord.dev.chdsr.simulator.engine.subject.MarkingObservable
import io.iochord.dev.chdsr.simulator.compiler.MemoryScalaFileCompiler

import scala.collection.mutable._
import scala.util.control.Breaks._

object CompileFromFileTest {
  
  def main(args: Array[String]) {
    //val pathfile = "testSimul.txt"
    val pathfile = "portHanjinEx.txt"
    val memoryScalaFactory = new MemoryScalaFileCompiler(pathfile)
    val memoryScala = memoryScalaFactory.getInstance
    memoryScala.runSimulation()
  }
}