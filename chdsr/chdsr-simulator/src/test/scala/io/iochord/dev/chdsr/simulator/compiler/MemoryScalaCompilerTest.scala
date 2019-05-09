package io.iochord.dev.chdsr.simulator.compiler

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._

import scala.collection.mutable.Map

object MemoryScalaCompilerTest {
  
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
    
    def myfunc(x:Int,y:Int): Boolean = {
      x > y
    }
    
    def testh(x:Int,y:Int): Boolean = {
      x > y
    }
    
    def testh2(bb:BB):Boolean = {
      val bone = bb
      bone.i1 > bone.i2 && myfunc(bone.i1,bone.i2)
    }
    
    //how to set this arc expression inside this function
    def testh3(bb:BB):Boolean = {
      val bone = bb
      bone.i1 == 2*bone.i2 && myfunc(bone.i1,bone.i2)
    }
    
    type colset1 = (Int,String)
    
    val ms1 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    ms1 + (((1,""),2L))
    ms1 + (((2,""),2L))
    
    val pplace1 = new Place("id1","woo1",ms1)
    
    val ms2 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    ms2 + (((1,""),2L))
    ms2 + (((2,""),2L))
    
    val pplace2 = new Place("id2","woo2",ms2)
    
    case class BC(x:Int) extends Bind
    
    case class BB(i1:Int,i2:Int) extends Bind
    
    val bb1 = new BB(1,1)
    val bb2 = new BB(2,2)
    val bb3 = new BB(3,4)
    
    val ttrans1 = new Transition("tr1","ya", new Guard())
    ttrans1.getGuard().cond1[BB](testh2,bb1)
    
    val cc1x = (inp1:Int,inp2:String) => (inp1,inp2)
    val cc1 = (inp:colset1) => inp
    
    val arc1 = new Arc("arc1",pplace1,ttrans1,Direction.PtT)
    
    val arc2 = new Arc("arc2",pplace2,ttrans1,Direction.PtT)
    
    ttrans1.addIn(arc1)
    ttrans1.addIn(arc2)
    
    val filt_pre = pplace1.getcurrentMarking().multiset.filter(d => pplace2.getcurrentMarking().multiset.iterator.filter(c => d._1 == c._1).size > 0) //binding
    filt_pre.foreach(print)
    val filt = pplace1.getcurrentMarking().multiset.exists(e => pplace2.getcurrentMarking().multiset.iterator.exists(d => e._1 == d._1)) //guard
    println(filt)
    
    val bblist = List[BB]()
    val bblist1 = List[BB](bb1) ++ bblist
    val bblist2 = List[BB](bb2) ++ bblist1
    
    val pp1 = new BB(1,2)
    val pp2 = new BB(6,3)
    val pp3 = new BB(7,4)
    
    val pplist = List[BB]()
    val pplist1 = List[BB](pp1) ++ pplist
    val pplist2 = List[BB](pp2) ++ pplist1
    
    val ss1 = new BB(5,2)
    val ss2 = new BB(6,3)
    val ss3 = new BB(7,4)
    
    val sslist = List[BB]()
    val sslist1 = List[BB](ss1) ++ sslist
    val sslist2 = List[BB](ss2) ++ sslist1
    
    bblist2.foreach(print)
    println()
    pplist2.foreach(print)
    println()
    
    /*
    val filt = pplist2.filter(x => x.i1 == bblist2)
    val filt = pplist2.filter(bblist2.contains(_))
    filt.foreach(print)
    println()
    println("Kosong")
    */
    
    //val filt_pre = pplist2.filter(d => bblist2.filter(d.i1 == _.i1).size > 0) //binding
    //filt_pre.foreach(print)
    //val filt = sslist2.exists(e => pplist2.exists(d => bblist2.exists(e.i1 == d.i1 && d.i1 > _.i1))) //guard
    //println(filt)
    
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