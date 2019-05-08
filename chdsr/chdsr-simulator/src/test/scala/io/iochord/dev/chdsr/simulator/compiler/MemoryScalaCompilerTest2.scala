package io.iochord.dev.chdsr.simulator.compiler

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._

import scala.collection.mutable.Map

object MemoryScalaCompilerTest2 {
  
  def main(args: Array[String]) {
    
    val myclassSyntax = "new Simulation {\n"+
      "val test1 = \"test1\"\n"+
      "override def runSimulation(cpnGraph: CPNGraph):Unit ="+
        "{ println(\"Run Simulation\") }\n"+ 
      "override def expState():Unit ="+
        "{ println(\"Explore State\") }\n"+ 
      "override def calcKPI[T](kpiFunc:T):Double ="+
        "{ println(\"Calculate KPI\"); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()); return 5.0 }\n "+  
      "def test(var1:String):Unit = "+
        "{ println(var1) }\n"+
      "class Coba1 { val varc1 = \"other test\" }\n"+
    "}";
    
    type colset1 = (Int,String)
    
    val ms1 = new Multiset[colset1](Map[(colset1,Long),Int](), "Person")
    ms1 + (((1,""),2L))
    ms1 + (((2,""),2L))
    ms1 + (((3,""),2L))
    
    val pplace1 = new Place("id1","woo1",ms1)
    
    val ms2 = new Multiset[colset1](Map[(colset1,Long),Int](), "Person")
    ms2 + (((1,""),2L))
    ms2 + (((3,""),2L))
    ms2 + (((5,""),2L))
    ms2 + (((6,""),2L))
    
    val pplace2 = new Place("id2","woo2",ms2)
    
    case class Bind1(x:Option[Int]) extends Bind
    
    //for 1st arc from place1
    def bindarc(arcins:Any => Bind1, token:Any):Bind1 = {
      arcins(token)
    }
    
    val lbe1 = pplace1.getcurrentMarking().multiset.keys.map(token => token match {      
        case colset1 => Bind1(Some(token._1._1))
      }
    ).toList
    
    val lbe2 = pplace2.getcurrentMarking().multiset.keys.map(token => token match {      
        case colset1 => Bind1(Some(token._1._1))
      }
    ).toList
    
    lbe2.collect { case b2 => {print(b2.x); b2}}
    println("YYY")
    
    val eval_bind = (b1: Bind1, b2: Bind1) => { b1.x.map(_ * 2) == b2.x }
    val merge_bind = (b1: Bind1, b2: Bind1) => {
      val x = if (b1.x == None) b2.x else b1.x
      new Bind1(x)
    }
    
    def combineBind[B <: Bind](eval: (B, B) => Boolean, merge: (B, B) => B,bes1: List[B], bes2: List[B]) =
    if (bes1.isEmpty || bes2.isEmpty)
      bes1 ::: bes2
    else
      bes2.flatMap(b2 => bes1.collect { case b1 if eval(b1, b2) => merge(b1, b2) })
      
    val merge = combineBind(eval_bind,merge_bind,lbe1,lbe2)
    
    for (be <- merge) println(be.x)
    /*
    val ttrans1 = new Transition("tr1","ya", new Guard())
    //ttrans1.getGuard().cond1[BB](testh2,bb1)
    
    val cc1x = (inp1:Int,inp2:String) => (inp1,inp2)
    val cc1 = (inp:colset1) => inp
    
    val arc1 = new Arc("arc1",pplace1,ttrans1,Direction.PtT)
    arc1.setArcExp(cc1,(1,"tes"))
    
    val arc2 = new Arc("arc2",pplace2,ttrans1,Direction.PtT)
    arc1.setArcExp(cc1,(1,"tes"))
    
    ttrans1.addIn(arc1)
    ttrans1.addIn(arc2)
  
    */
    
    /*
    val memoryScalaFactory = MemoryScalaCompiler(myclassSyntax)
    val memoryScala = memoryScalaFactory.getInstance
    memoryScala.calcKPI("just test kpi")
    memoryScala.putVar("test3","test put var 3")
    memoryScala.getVar("test1")
    val attrs = memoryScala.getVar("test2").getClass().getDeclaredField("varc1")
    attrs.setAccessible(true)
    println(attrs.get(memoryScala.getVar("test2")).asInstanceOf[String])
    */
    //val mtd = memoryScala.getClass.getMethods.map(_.getName)
    //println(mtd.length)
    //mtd.foreach{println}
    //mtd.invoke(compiledClass)
  }
}