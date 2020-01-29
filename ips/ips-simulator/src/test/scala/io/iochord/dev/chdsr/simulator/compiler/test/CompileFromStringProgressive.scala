package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.apps.ips.model.cpn.v1.impl._
import io.iochord.apps.ips.model.cpn.v1._
import io.iochord.apps.ips.simulator.compiler.Simulation
import io.iochord.apps.ips.simulator.engine.Simulator
import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver
import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompiler

import scala.collection.mutable._
import scala.util.control.Breaks._

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import scala.io.Source
    
object CompileFromStringProgressive {
  
  def main(args: Array[String]) {
    val toolbox = ToolBox(currentMirror).mkToolBox()
    import toolbox.u._
    
    var cgraph:CPNGraph = null
    
    val start = System.currentTimeMillis()
    for(i <- 1 to 1) {
      val sb = new StringBuilder()
      
      sb.clear()
      sb.append("import io.iochord.apps.ips.model.cpn.v1.impl._; \n")
      sb.append("import io.iochord.apps.ips.model.cpn.v1._; \n")
      sb.append("import io.iochord.apps.ips.simulator.engine.Simulator; \n")
      sb.append("import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver; \n")
      sb.append("import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable; \n")
      sb.append("import scala.collection.mutable._; \n")
      sb.append("import scala.util.control.Breaks._; \n")
      sb.append("import breeze.stats.distributions.Gaussian; \n")
      sb.append("import breeze.stats.distributions.Binomial; \n")
      sb.append("import breeze.stats.distributions.ChiSquared; \n")
      sb.append("import breeze.stats.distributions.Exponential; \n")
      sb.append("import breeze.stats.distributions.Gamma; \n")
      sb.append("import breeze.stats.distributions.Geometric \n")
      sb.append("import breeze.stats.distributions.Laplace; \n")
      sb.append("import breeze.stats.distributions.LogNormal; \n")
      sb.append("import breeze.stats.distributions.NegativeBinomial; \n")
      sb.append("import breeze.stats.distributions.StudentsT; \n")
      sb.append("import breeze.stats.distributions.Uniform; \n")
      sb.append("import breeze.stats.distributions.Rayleigh \n")
      
      sb.append("val minig = new CPNGraph() \n")
      
      sb.append("type colset00000001 = (Int,String)\n")
      sb.append("case class Binding00000001(entity:Option[colset00000001])\n")
      
      
      sb.append("val Eval00000001 = (b1:Binding00000001, b2:Binding00000001) => {\n")
      sb.append("\t(b1.entity == b2.entity || b1.entity == None || b2.entity == None)\n")
      sb.append("}\n")
      sb.append("val Merge00000001 = (b1:Binding00000001, b2:Binding00000001) => {\n")
      sb.append("\tval entity = if(b1.entity == None) b2.entity else b1.entity\n")
      sb.append("Binding00000001(entity)")
      sb.append("}\n\n")
      
      sb.append("val map0000000" + i + "1 = Map[(colset00000001,Long),Int]( (((1,\"satu\"),0L),1)  )\n")
      sb.append("val ms0000000" + i + "1 = new Multiset[colset00000001](map0000000" + i + "1)\n")
      sb.append("val place0000000" + i + "1 = new Place(\"place0000000" + i + "1\",\"Tr" + i + "_p1\",ms0000000" + i + "1)\n")
      sb.append("place0000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p1\")))\n")
      sb.append("minig.addPlace(place0000000" + i + "1)\n\n")
      sb.append("val map0000000" + i + "2 = Map[(colset00000001,Long),Int]( (((1,\"satu\"),0L),1) )\n")
      sb.append("val ms0000000" + i + "2 = new Multiset[colset00000001](map0000000" + i + "2)\n")
      sb.append("val place0000000" + i + "2 = new Place(\"place0000000" + i + "2\",\"Tr" + i + "_p2\",ms0000000" + i + "2)\n")
      sb.append("place0000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p2\")))\n")
      sb.append("minig.addPlace(place0000000" + i + "2)\n\n")
      sb.append("val map0000000" + i + "3 = Map[(colset00000001,Long),Int](  )\n")
      sb.append("val ms0000000" + i + "3 = new Multiset[colset00000001](map0000000" + i + "3)\n")
      sb.append("val place0000000" + i + "3 = new Place(\"place0000000" + i + "3\",\"Tr" + i + "_p3\",ms0000000" + i + "3)\n")
      sb.append("place0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p3\")))\n")
      sb.append("minig.addPlace(place0000000" + i + "3)\n\n")
      sb.append("val trans0000000" + i + " = new Transition[Binding00000001](\"trans0000000" + i + "\",\"Transition " + i + "\",null,null)\n")
      sb.append("trans0000000" + i + ".setEval(Eval00000001)\n")
      sb.append("trans0000000" + i + ".setMerge(Merge00000001)\n")
      sb.append("trans0000000" + i + ".setOrigin(Map[String,String]((\"Tr" + i + "\",\"Transition " + i + "\")))\n")
      sb.append("minig.addTransition(trans0000000" + i + ")\n\n")
      
      sb.append("val arcexp0000000" + i + "1 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb0000000" + i + "1 = (inp:Any) => Some(Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt0000000" + i + "1 = (b:Binding00000001) => {b.entity.get}\n")
      sb.append("val arc0000000" + i + "1 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "1\",place0000000" + i + "1,trans0000000" + i + ",Direction.PtT)\n")
      sb.append("arc0000000" + i + "1.setIsBase(true)\n")
      sb.append("arc0000000" + i + "1.setTokenToBind(tTb0000000" + i + "1)\n")
      sb.append("arc0000000" + i + "1.setBindToToken(bTt0000000" + i + "1)\n")
      sb.append("arc0000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "1\")))\n")
      sb.append("minig.addArc(arc0000000" + i + "1)\n\n")
      sb.append("val arcexp0000000" + i + "2 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb0000000" + i + "2 = (inp:Any) => Some(Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt0000000" + i + "2 = (b:Binding00000001) => {b.entity.get}\n")
      sb.append("val arc0000000" + i + "2 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "2\",place0000000" + i + "2,trans0000000" + i + ",Direction.PtT)\n")
      sb.append("arc0000000" + i + "2.setIsBase(true)\n")
      sb.append("arc0000000" + i + "2.setTokenToBind(tTb0000000" + i + "2)\n")
      sb.append("arc0000000" + i + "2.setBindToToken(bTt0000000" + i + "2)\n")
      sb.append("arc0000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "2\")))\n")
      sb.append("minig.addArc(arc0000000" + i + "2)\n\n")
      sb.append("val arcexp0000000" + i + "3 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb0000000" + i + "3 = (inp:Any) => Some(Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt0000000" + i + "3 = (b:Binding00000001) => {b.entity.get}\n")
      sb.append("val arc0000000" + i + "3 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "3\",place0000000" + i + "3,trans0000000" + i + ",Direction.TtP)\n")
      sb.append("arc0000000" + i + "3.setIsBase(true)\n")
      sb.append("arc0000000" + i + "3.setTokenToBind(tTb0000000" + i + "3)\n")
      sb.append("arc0000000" + i + "3.setBindToToken(bTt0000000" + i + "3)\n")
      sb.append("arc0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "3\")))\n")
      sb.append("minig.addArc(arc0000000" + i + "3)\n")
      
      sb.append("minig\n\n")
      
      val compcode = toolbox.compile(toolbox.parse(sb.toString()))
      cgraph = compcode().asInstanceOf[CPNGraph]
      println("Size of places beginning "+cgraph.getPlaces().size())
      //val arc = minigraph.allArcs.head
      
      //cgraph.addPlace(place)
      //cgraph.addArc(arc)
      
      sb.clear()
      sb.append("import io.iochord.apps.ips.model.cpn.v1.impl._; \n")
      sb.append("import io.iochord.apps.ips.model.cpn.v1._; \n")
      sb.append("import io.iochord.apps.ips.simulator.engine.Simulator; \n")
      sb.append("import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver; \n")
      sb.append("import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable; \n")
      sb.append("import scala.collection.mutable._; \n")
      sb.append("import scala.util.control.Breaks._; \n")
      sb.append("import breeze.stats.distributions.Gaussian; \n")
      sb.append("import breeze.stats.distributions.Binomial; \n")
      sb.append("import breeze.stats.distributions.ChiSquared; \n")
      sb.append("import breeze.stats.distributions.Exponential; \n")
      sb.append("import breeze.stats.distributions.Gamma; \n")
      sb.append("import breeze.stats.distributions.Geometric \n")
      sb.append("import breeze.stats.distributions.Laplace; \n")
      sb.append("import breeze.stats.distributions.LogNormal; \n")
      sb.append("import breeze.stats.distributions.NegativeBinomial; \n")
      sb.append("import breeze.stats.distributions.StudentsT; \n")
      sb.append("import breeze.stats.distributions.Uniform; \n")
      sb.append("import breeze.stats.distributions.Rayleigh \n")
      
      sb.append("val minig = new CPNGraph() \n")
      
      sb.append("type colset00000001 = (Int,String)\n")
      sb.append("type colset10000001 = (Int,String)\n")
      sb.append("case class Binding10000001(entity:Option[colset10000001])\n")
      
      sb.append("val Eval10000001 = (b1:Binding10000001, b2:Binding10000001) => {\n")
      sb.append("\t(b1.entity == b2.entity || b1.entity == None || b2.entity == None)\n")
      sb.append("}\n")
      sb.append("val Merge10000001 = (b1:Binding10000001, b2:Binding10000001) => {\n")
      sb.append("\tval entity = if(b1.entity == None) b2.entity else b1.entity\n")
      sb.append("Binding10000001(entity)")
      sb.append("}\n\n")
      
      sb.append("val map0000000" + i + "3 = Map[(colset00000001,Long),Int](  )\n")
      sb.append("val ms0000000" + i + "3 = new Multiset[colset00000001](map0000000" + i + "3)\n")
      sb.append("val place0000000" + i + "3 = new Place(\"place0000000" + i + "3\",\"Tr" + i + "_p3\",ms0000000" + i + "3)\n")
      sb.append("place0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p3\")))\n")
      sb.append("minig.addPlace(place0000000" + i + "3)\n\n")
      sb.append("val map1000000" + i + "1 = Map[(colset10000001,Long),Int]( (((1,\"satu\"),0L),1) )\n")
      sb.append("val ms1000000" + i + "1 = new Multiset[colset10000001](map1000000" + i + "1)\n")
      sb.append("val place1000000" + i + "1 = new Place(\"place1000000" + i + "1\",\"Tr" + i + "_p1\",ms1000000" + i + "1)\n")
      sb.append("place1000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p1\")))\n")
      sb.append("minig.addPlace(place1000000" + i + "1)\n\n")
      sb.append("val map1000000" + i + "2 = Map[(colset10000001,Long),Int]( (((1,\"satu\"),0L),1) )\n")
      sb.append("val ms1000000" + i + "2 = new Multiset[colset10000001](map1000000" + i + "2)\n")
      sb.append("val place1000000" + i + "2 = new Place(\"place1000000" + i + "2\",\"Tr" + i + "_p2\",ms1000000" + i + "2)\n")
      sb.append("place1000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p2\")))\n")
      sb.append("minig.addPlace(place1000000" + i + "2)\n\n")
      sb.append("val map1000000" + i + "3 = Map[(colset10000001,Long),Int](  )\n")
      sb.append("val ms1000000" + i + "3 = new Multiset[colset10000001](map1000000" + i + "3)\n")
      sb.append("val place1000000" + i + "3 = new Place(\"place1000000" + i + "3\",\"Tr" + i + "_p3\",ms1000000" + i + "3)\n")
      sb.append("place1000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p3\")))\n")
      sb.append("minig.addPlace(place1000000" + i + "3)\n\n")
      sb.append("val trans1000000" + i + " = new Transition[Binding10000001](\"trans1000000" + i + "\",\"Transition " + i + "\",null,null)\n")
      sb.append("trans1000000" + i + ".setEval(Eval10000001)\n")
      sb.append("trans1000000" + i + ".setMerge(Merge10000001)\n")
      sb.append("trans1000000" + i + ".setOrigin(Map[String,String]((\"Tr" + i + "\",\"Transition " + i + "\")))\n")
      sb.append("minig.addTransition(trans1000000" + i + ")\n\n")
      
      sb.append("val arcexp2000000" + i + "1 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb2000000" + i + "1 = (inp:Any) => Some(Binding10000001(inp match { case entity:colset00000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt2000000" + i + "1 = (b:Binding10000001) => {b.entity.get}\n")
      sb.append("val arc2000000" + i + "1 = new Arc[colset00000001,Binding10000001](\"arc2000000" + i + "1\",place0000000" + i + "3,trans1000000" + i + ",Direction.PtT)\n")
      sb.append("arc2000000" + i + "1.setIsBase(true)\n")
      sb.append("arc2000000" + i + "1.setTokenToBind(tTb2000000" + i + "1)\n")
      sb.append("arc2000000" + i + "1.setBindToToken(bTt2000000" + i + "1)\n")
      sb.append("arc2000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc2000000" + i + "1\")))\n")
      sb.append("minig.addArc(arc2000000" + i + "1)\n\n")
      
      sb.append("val arcexp1000000" + i + "1 = (inp:Any) => inp match { case entity:colset10000001 => { Some(entity) } }\n")
      sb.append("val tTb1000000" + i + "1 = (inp:Any) => Some(Binding10000001(inp match { case entity:colset10000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt1000000" + i + "1 = (b:Binding10000001) => {b.entity.get}\n")
      sb.append("val arc1000000" + i + "1 = new Arc[colset10000001,Binding10000001](\"arc1000000" + i + "1\",place1000000" + i + "1,trans1000000" + i + ",Direction.PtT)\n")
      sb.append("arc1000000" + i + "1.setIsBase(true)\n")
      sb.append("arc1000000" + i + "1.setTokenToBind(tTb1000000" + i + "1)\n")
      sb.append("arc1000000" + i + "1.setBindToToken(bTt1000000" + i + "1)\n")
      sb.append("arc1000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc1000000" + i + "1\")))\n")
      sb.append("minig.addArc(arc1000000" + i + "1)\n\n")
      sb.append("val arcexp1000000" + i + "2 = (inp:Any) => inp match { case entity:colset10000001 => { Some(entity) } }\n")
      sb.append("val tTb1000000" + i + "2 = (inp:Any) => Some(Binding10000001(inp match { case entity:colset10000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt1000000" + i + "2 = (b:Binding10000001) => {b.entity.get}\n")
      sb.append("val arc1000000" + i + "2 = new Arc[colset10000001,Binding10000001](\"arc1000000" + i + "2\",place1000000" + i + "2,trans1000000" + i + ",Direction.PtT)\n")
      sb.append("arc1000000" + i + "2.setIsBase(true)\n")
      sb.append("arc1000000" + i + "2.setTokenToBind(tTb1000000" + i + "2)\n")
      sb.append("arc1000000" + i + "2.setBindToToken(bTt1000000" + i + "2)\n")
      sb.append("arc1000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc1000000" + i + "2\")))\n")
      sb.append("minig.addArc(arc1000000" + i + "2)\n\n")
      sb.append("val arcexp1000000" + i + "3 = (inp:Any) => inp match { case entity:colset10000001 => { Some(entity) } }\n")
      sb.append("val tTb1000000" + i + "3 = (inp:Any) => Some(Binding10000001(inp match { case entity:colset10000001 => Some(entity); case _ => None }))\n")
      sb.append("val bTt1000000" + i + "3 = (b:Binding10000001) => {b.entity.get}\n")
      sb.append("val arc1000000" + i + "3 = new Arc[colset10000001,Binding10000001](\"arc1000000" + i + "3\",place1000000" + i + "3,trans1000000" + i + ",Direction.TtP)\n")
      sb.append("arc1000000" + i + "3.setIsBase(true)\n")
      sb.append("arc1000000" + i + "3.setTokenToBind(tTb1000000" + i + "3)\n")
      sb.append("arc1000000" + i + "3.setBindToToken(bTt1000000" + i + "3)\n")
      sb.append("arc1000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc1000000" + i + "3\")))\n")
      sb.append("minig.addArc(arc1000000" + i + "3)\n")
      sb.append("minig\n\n")
      
      val compcode2 = toolbox.compile(toolbox.parse(sb.toString()))
      val minigraph2 = compcode2().asInstanceOf[CPNGraph]
      val place2 = minigraph2.allPlaces.last
      
      println("Size of places added "+minigraph2.getPlaces().size())
      
      minigraph2.getPlaces().forEach(p => {cgraph.addPlaceMerge(p)})
      minigraph2.getTransitions().forEach(t => {cgraph.addTransitionMerge(t)})
      minigraph2.getArcs().forEach(a => {cgraph.addArcMerge(a)})
      
      println("Size of places now "+cgraph.getPlaces().size())
      println("Size of transitions now "+cgraph.getTransitions().size())
      println("Size of arcs nows "+cgraph.getArcs().size())
      println(cgraph.getTransitions().forEach(t => {println(t.getIn())}))
    }
    
    val finish = System.currentTimeMillis()
    val diff = finish-start
    println(diff)
    println("Finish")
    
    val memoryScalaFactory = new MemoryScalaCompiler("")
    val simulation = memoryScalaFactory.getInstance
    simulation.cgraph = cgraph
    simulation.addObserver(new MarkingObserver())
    
    val stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    val inpStopCritLoc = false
    
    simulation.runStopCriteria(stopCrit, inpStopCritLoc)
  }
}