package io.iochord.dev.chdsr.simulator.compiler

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._
import io.iochord.dev.chdsr.simulator.engine.Simulator

import scala.collection.mutable.Map
import scala.util.control.Breaks._

//Example class to check binding using exhaustive search not recommended for var type with large combination
//In cpntools small colset s colset wth type with combination less than 10

object SimulationTestRunWithBinding {

  var x:Int = _
  var y:String = _
  
  def main(args: Array[String]) {
    
    val myclassSyntax = "new Simulation {\n"+
      "val test1 = \"test1\"\n"+
      "override def runSimulation(cpnGraph: CPNGraph):Unit ="+
        "{ println(\"Run Simulation\"); Simulator.run }\n"+ 
      "override def expState():Unit ="+
        "{ println(\"Explore State\") }\n"+ 
      "override def calcKPI[T](kpiFunc:T):Double ="+
        "{ println(\"Calculate KPI\"); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()); return 5.0 }\n "+  
      "def test(var1:String):Unit = "+
        "{ println(var1) }\n"+
      "class Coba1 { val varc1 = \"other test\" }\n"+
    "}";
    
    val cgraph = CPNGraph()
    
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
    
    var globtime = 10
    
    case class Bind1(x:Option[Int],y:Option[String]) extends Bind
    
    val listtoken1 = pplace1.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset1 => Bind1(Some(token._1._1), Some(token._1._2))
      }
    ).toList
    
    val listtoken2 = pplace2.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset1 => Bind1(Some(token._1._1), Some(token._1._2))
      }
    ).toList
    
    def exp_arc1(varc: colset1) = (2*varc._1, varc._2)
    def exp_arc2(varc: colset1) = (3*varc._1, varc._2)
    
    val ttrans1 = new Transition("tr1","transition1", new Guard()) {
      override def isArcEnabled():(Boolean,List[Bind1]) = {
        var is_binding_arc1 = false
        var is_binding_arc2 = false
        
        var lbe = List[Bind1]()
        
        //start from arc1 with input pplace1
        for(token1 <- listtoken1) { // first place (place1) is as bases variable initialization.. but here arc inscription use function
          //arc from place1 to transition have 
          //here exhaustive search
          val start = 0//Int.MinValue
          val stop = 1000//Int.MaxValue
          
          breakable {
            y = token1.y.get
            
            for(i <- start to stop) {
              if(exp_arc1(i, y) == (token1.x.get, token1.y.get)) {
                  x = i
                  is_binding_arc1 = true
                  break
              }
            }
          }
          
          //continue with arc2 with input pplace2
          if(is_binding_arc1)
            for(token2 <- listtoken2) {
              if(exp_arc2(x, y) == (token2.x.get, token2.y.get)) {
                is_binding_arc2 = true
                //if all arc inscription is binding then add Binding class into list binding combination
                lbe = lbe.::( Bind1(Some(x),Some(y)) )
                //print(x)
              }
            }
        }
        
        //if all 
        (is_binding_arc1 && is_binding_arc2, lbe)
      }
    }
    
    //guard is x >= 1
    def guard_exp_ttrans1(x:Int) = x >= 1
    
    def anyfunc(x:Int):Int = 2*x
    
    val gen_guard_exp_ttrans1 = (listb: List[Any]) => listb match { case list: List[Bind1] => { list.exists(be =>  be.x.map(x => guard_exp_ttrans1(anyfunc(x))).get) } case _ => false }
    ttrans1.getGuard().setGuardBind(gen_guard_exp_ttrans1)
    
    val arc1 = new Arc("arc1", pplace1, ttrans1, Direction.PtT)
    arc1.setArcExp(exp_arc1)
    
    val arc2 = new Arc("arc2", pplace2, ttrans1, Direction.PtT)
    arc2.setArcExp(exp_arc2)
    
    ttrans1.addIn(arc1)
    ttrans1.addIn(arc2)
    
    pplace1.addOut(arc1)
    pplace1.addOut(arc2)
    
    //add transitions
    cgraph.addTransition(ttrans1)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    
    Simulator.run(cgraph, 10)
    
    /*
    val (isArcEn, lbe) = ttrans1.isArcEnabled()
    for (be <- lbe) println(be)
    
    println(ttrans1.getGuard().evalGuard(lbe))
    */
  }
}