package io.iochord.dev.chdsr.simulator.compiler

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._

import scala.collection.mutable.Map
import scala.util.control.Breaks._

//Example class to check binding using exhaustive search not recommended for var type with large combination
//In cpntools small colset s colset wth type with combination less than 10

object MemoryScalaCompilerTest6 {

  var x:Int = _
  var y:String = _
  
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
    
    val ms1 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    ms1 + (((1,"Bert"),2L))
    ms1 + (((2,"Test"),2L))
    ms1 + (((3,"Merdeka"),2L))
    
    val pplace1 = new Place("id1","woo1",ms1)
    
    val ms2 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    ms2 + (((1,"Amg"),2L))
    ms2 + (((3,"Test"),2L))
    ms2 + (((4,"Test"),2L))
    ms2 + (((5,"Ferari"),2L))
    ms2 + (((6,"Lambo"),2L))
    
    val pplace2 = new Place("id2","woo2",ms2)
    
    case class Bind1(x:Option[Int],y:Option[String]) extends Bind
    
    //for 1st arc from place1
    def bindarc(arcins:Any => Bind1, token:Any):Bind1 = {
      arcins(token)
    }
    
    //arc1
    val x_arc1 = (inp: Int) => 2*inp
    val y_arc1 = (inp: String) => inp
    //arc2
    val x_arc2 = (inp: Int) => 3*inp
    val y_arc2 = (inp: String) => inp
    
    var globtime = 100

    val listtoken1 = pplace1.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset1 => Bind1(Some(token._1._1), Some(token._1._2))
      }
    ).toList
    
    val listtoken2 = pplace2.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset1 => Bind1(Some(token._1._1), Some(token._1._2))
      }
    ).toList
    
    var lbe = List[Bind1]()
    
    for(token1 <- listtoken1) { // first is as bases x from place1
      //here exhaustive search
      val start = Int.MinValue
      val stop = Int.MaxValue
      
      var is_exist_token1 = false
      
      breakable {
        for(i <- start to stop) {
          if(x_arc1(i) == token1.x.get) {
              x = i
              is_exist_token1 = true
              break
          }
        }
      }
      
      y = token1.y.get
      
      if(is_exist_token1)
        for(token2 <- listtoken2) {
          var is_exist_token2 = false
          //println(x_arc2(x)+" : "+token2.x.get+" - "+(x_arc2(x) == token2.x.get))
          //println(y_arc2(y)+" : "+token2.y.get+" - "+(y_arc2(y) == token2.y.get))
          if(x_arc2(x) == token2.x.get && y_arc2(y) == token2.y.get) {
            is_exist_token2 = true
            lbe = lbe.::( Bind1(Some(x),Some(y)) )
          }
            
        }
    }
    
    println("YYY")
    
    for (be <- lbe) println(be.x +" - "+be.y)
    
    def guard_bind[B <: Bind](eval: List[B] => Boolean, inp: List[B]): Boolean = {
      eval(inp)
    }
    
    //guard if x > 1 then true
    println(guard_bind[Bind1](listb => listb.exists(be => be.x.map(_ >= 1).get),lbe))
    
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