package io.iochord.dev.chdsr.simulator.compiler.test

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import scala.io.Source

import io.iochord.apps.ips.model.cpn.v1.impl._;

object TestBinding {
  def main(args: Array[String]) {
  
    val toolbox = ToolBox(currentMirror).mkToolBox()
    import toolbox.u._,toolbox._
    
    val oks = q"val b = 2; b"
    val babs = 2
    val cg = new CPNGraph()
    val strnya = s"""val b = $cg; b"""
    //val treenya = typecheck(parse(strnya))
    val compcode = toolbox.compile(toolbox.parse(strnya))
    val valuenya = compcode().asInstanceOf[CPNGraph]
    println(valuenya)
    
    val b = i"10001"
  }
  
  implicit class IntContext(val sc: StringContext) {
    def i(args: Any*): Int = {
      val orig = sc.s(args: _*)
      orig.replace(" ", "").toInt
    }
  }
}

