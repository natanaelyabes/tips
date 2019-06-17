package io.iochord.dev.chdsr.simulator.compiler

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._
import io.iochord.dev.chdsr.simulator.engine.Simulator

import scala.collection.mutable._
import scala.util.control.Breaks._

import breeze.stats.distributions.Gaussian
import breeze.stats.distributions.Binomial
import breeze.stats.distributions.ChiSquared
import breeze.stats.distributions.Exponential
import breeze.stats.distributions.Gamma
import breeze.stats.distributions.Geometric
import breeze.stats.distributions.Laplace
import breeze.stats.distributions.LogNormal
import breeze.stats.distributions.NegativeBinomial
import breeze.stats.distributions.StudentsT
import breeze.stats.distributions.Uniform
import breeze.stats.distributions.Rayleigh

import io.iochord.dev.chdsr.simulator.dist._

object TestSimulatorNew1 {
  
  def main(args: Array[String]) {
    case class BindTransition1(x:Option[Int], y:Option[String]) extends Bind
    
    def anyfunc(x:Int):Int = 2*x
    
    val cgraph = CPNGraph()
    
    type colset1 = Int
    type colset2 = String
    type colset3 = (Int, String)
    
    //---------------------- place 1 ------------------
    val ms1 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    ms1 + ((1,0L)) //colset1 int with available time 0
    ms1 + ((2,0L)) //colset1 int with available time 0
    
    val pplace1 = new Place("p1","place1",ms1)
    
    //---------------------- place 2 ------------------
    val ms2 = new Multiset[colset2](Map[(colset2,Long),Int](), classOf[colset2])
    ms2 + (("Yes",0L)) //colset2 string with available time 0
    ms2 + (("No",0L)) //colset2 string with available time 0
    
    val pplace2 = new Place("p2","place2",ms2)
    
    //---------------------- place 3 ------------------
    val ms3 = new Multiset[colset3](Map[(colset3,Long),Int](), classOf[colset3])
    ms3 + (((2,"Yes"),0L)) //colset3 int,string with available time 0
    ms3 + (((3,"No"),0L)) //colset3 int,string with available time 0
    
    val pplace3 = new Place("p3","place3",ms3)
    
    //---------------------- place 4 ------------------
    val ms4 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    ms4 + ((1,0L)) //colset1 int with available time 0
    ms4 + ((8,0L)) //colset1 int with available time 0
    
    val pplace4 = new Place("p4","place4",ms4)
    
    //---------------------- place 5 ------------------
    val ms5 = new Multiset[colset1](Map[(colset1,Long),Int](), classOf[colset1])
    val pplace5 = new Place("p5","place5",ms5)
    
    val eval_trans1 = (b1: BindTransition1, b2: BindTransition1) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None) 
    }
    
    val merge_trans1 = (b1: BindTransition1, b2: BindTransition1) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransition1(x,y)
    }
    
    val gtrans1 = new Guard[BindTransition1]()
    val guard_exp_ttrans1 = (x:Int) => x >= 1
    val gen_guard_exp_ttrans1 = (bind: BindTransition1) => guard_exp_ttrans1(anyfunc(bind.x.get))
    gtrans1.setGuardBind(gen_guard_exp_ttrans1)
    val ttrans1 = new Transition[BindTransition1]("tr1","transition1", gtrans1)
    ttrans1.setEval(eval_trans1)
    ttrans1.setMerge(merge_trans1)
    
    // x (int)
    val arcExpArc1 = (inp:Any) => inp match { case x:Int => { x }; case _ => { None } } //arc1 exp
    val TtoBArc1 = (inp:Any) => BindTransition1(inp match { case None => None; case x:Int => Some(x) }, None )//inp match { case (token:colset1) => Bind(Some(token), None) } //token to bind in place1
    val BtoTArc1 = (inp:Any) => inp match { case inp:BindTransition1 => { inp.x match { case None => None; case x:Option[Int] => x.get } } } //bind to token in place1
    val arc1 = new Arc[colset1,BindTransition1]("arc1", pplace1, ttrans1, Direction.PtT)
    arc1.setIsBase(true)
    arc1.setArcExp(arcExpArc1)
    arc1.setTokenToBind(TtoBArc1)
    arc1.setBindToToken(BtoTArc1)
    
    // y (string)
    val arcExpArc2 = (inp:Any) => inp match { case y:String => { y }; case _ => { None } } //arc2 exp
    val TtoBArc2 = (inp:Any) => BindTransition1(None, inp match { case None => None; case y:String => Some(y) } )//inp match { case (token:colset2) => Bind(None, Some(token)) } //token to bind in place2
    val BtoTArc2 = (inp:Any) => inp match { case inp:BindTransition1 => { inp.y match { case None => None; case y:Option[String] => y.get } } } //bind to token in place2
    val arc2 = new Arc[colset2,BindTransition1]("arc2", pplace2, ttrans1, Direction.PtT)
    arc2.setIsBase(true)
    arc2.setArcExp(arcExpArc2)
    arc2.setTokenToBind(TtoBArc2)
    arc2.setBindToToken(BtoTArc2)
    
    // (x,y) (int,string)
    val arcExpArc3 = (inp:Any) => inp match { 
      case (x:Int,y:String) => { (x,y) }
      case (None,y:String) => { (None,y) }
      case (x:Int,None) => { (x,None) }
      case (None,None) => { (None,None) }
    } //arc5 exp //arc3 exp
    val TtoBArc3 = (inp:Any) => inp match { case inp:(Any,Any) => { BindTransition1(inp._1 match { case None => None; case x:Int => Some(x) }, inp._2 match { case None => None; case y:String => Some(y) } ) } }//inp match { case (token:colset3) => Bind(Some(token._1), Some(token._2)) } //token to bind in place3
    val BtoTArc3 = (inp:Any) => inp match { case inp:BindTransition1 => { (inp.x match { case None => None; case x:Option[Int] => x.get }, inp.y match { case None => None; case y:Option[String] => y.get } ) } } //bind to token in place3
    val arc3 = new Arc[colset3,BindTransition1]("arc3", pplace3, ttrans1, Direction.PtT)
    arc3.setArcExp(arcExpArc3)
    arc3.setTokenToBind(TtoBArc3)
    arc3.setBindToToken(BtoTArc3)
    
    // x (int)
    val arcExpArc4 = (inp:Any) => inp match { case x:Int => { 4*x }; case _ => { None } } //arc4 exp
    val TtoBArc4 = (inp:Any) => BindTransition1(inp match { case None => None; case x:Int => Some(x) }, None )//inp match { case (token:colset1) => Bind(Some(token), None) } //token to bind in place4
    val BtoTArc4 = (inp:Any) => inp match { case inp:BindTransition1 => { inp.x match { case None => None; case x:Option[Int] => x.get } } } //bind to token in place4
    val arc4 = new Arc[colset2,BindTransition1]("arc4", pplace4, ttrans1, Direction.PtT)
    arc4.setArcExp(arcExpArc4)
    arc4.setTokenToBind(TtoBArc4)
    arc4.setBindToToken(BtoTArc4)
    
    // x (int)
    val arcExpArc5 = (inp:Any) => inp match { case x:Int => { 3*x }; case _ => { None } } //arc5 exp
    val TtoBArc5 = (inp:Any) => BindTransition1(inp match { case None => None; case x:Int => Some(x) }, None )//inp match { case (token:colset1) => Bind(Some(token), None) } //token to bind in place5
    val BtoTArc5 = (inp:Any) => inp match { case inp:BindTransition1 => { inp.x match { case None => None; case x:Option[Int] => x.get } } } //bind to token in place5
    val addTimeArc5 = (inp:Any) => Math.round(Gaussian(100, 10).draw())+(inp match { case inp:Long => inp; case _ => 0 })
    val arc5 = new Arc[colset2,BindTransition1]("arc5", pplace5, ttrans1, Direction.TtP)
    arc5.setArcExp(arcExpArc5)
    arc5.setTokenToBind(TtoBArc5)
    arc5.setBindToToken(BtoTArc5)
    arc5.setAddTime(addTimeArc5)
    
    //add transitions
    cgraph.addTransition(ttrans1)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    cgraph.addPlace(pplace3)
    cgraph.addPlace(pplace4)
    cgraph.addPlace(pplace5)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    cgraph.addArc(arc3)
    cgraph.addArc(arc4)
    cgraph.addArc(arc5)
    
    Simulator.run(cgraph, 10)
  }
}