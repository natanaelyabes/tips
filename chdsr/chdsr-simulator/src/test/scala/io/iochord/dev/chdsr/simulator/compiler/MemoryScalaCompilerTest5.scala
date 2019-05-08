package io.iochord.dev.chdsr.simulator.compiler

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._

import scala.collection.mutable.Map

object MemoryScalaCompilerTest5 {
  //example if var only determined by 1 arc only
  
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
    ms1 + (((1,"Bert"),2L))
    ms1 + (((2,"Bum"),2L))
    ms1 + (((3,"Test"),2L))
    
    val pplace1 = new Place("id1","woo1",ms1)
    
    val ms2 = new Multiset[colset1](Map[(colset1,Long),Int](), "Person")
    ms2 + (((1,"Amg"),2L))
    ms2 + (((3,"Test"),2L))
    ms2 + (((4,"Test"),2L))
    ms2 + (((5,"Ferari"),2L))
    ms2 + (((6,"Lambo"),2L))
    
    val pplace2 = new Place("id2","woo2",ms2)
    
    case class Bind1(x:Option[Int],y:Option[String],z:Option[Int]) extends Bind
    
    //for 1st arc from place1
    def bindarc(arcins:Any => Bind1, token:Any):Bind1 = {
      arcins(token)
    }
    
    var globtime = 100
    
    val lbe1 = pplace1.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset1 => Bind1(Some(token._1._1), Some(token._1._2), None)
      }
    ).toList
    
    val lbe2 = pplace2.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset1 => Bind1(None, Some(token._1._2), Some(token._1._1))
      }
    ).toList
    
    lbe2.collect { case b2 => {print(b2.x); b2}}
    println("YYY")
    
    //arc1
    val x_arc1 = (inp: Int) => inp
    val y_arc1 = (inp: String) => inp
    //arc2
    val z_arc2 = (inp: Int) => inp
    val y_arc2 = (inp: String) => inp
    
    val eval_bind = (b1: Bind1, b2: Bind1) => { b1.y.map(inp => y_arc2(inp)) == b2.y.map(inp => y_arc1(inp)) }
    val merge_bind = (b1: Bind1, b2: Bind1) => {
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      val z = if (b1.z == None) b2.z else b1.z
      new Bind1(x,y,z)
    }
    
    def combineBind[B <: Bind](eval: (B, B) => Boolean, merge: (B, B) => B,bes1: List[B], bes2: List[B]) =
    if (bes1.isEmpty || bes2.isEmpty)
      bes1 ::: bes2
    else
      bes2.flatMap(b2 => bes1.collect { case b1 if eval(b1, b2) => merge(b1, b2) })
      
    val merge = combineBind(eval_bind,merge_bind,lbe1,lbe2)
    
    for (be <- merge) println(be.x +" - "+be.y)
    
    def guard_bind[B <: Bind](eval: List[B] => Boolean, inp: List[B]): Boolean = {
      eval(inp)
    }
    
    println(guard_bind[Bind1](listb => listb.exists(be => be.x.map(_ >= 3).get),merge))
    
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