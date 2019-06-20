package io.iochord.dev.chdsr.simulator.compiler.test

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

object PortHanjinSimulationWithoutResource {
  
  def main(args: Array[String]) {
    case class CaseData(name:String)
    
    case class BindTransInit(x:Option[Int]) extends Bind
    
    val cgraph = CPNGraph()
    
    type colset_CASEID = Int
    
    //ENVIRONMENT
    //---------------------- place 1 ------------------
    val ms1 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    ms1 + ((1,0L))
    val pplace1 = new Place("p1","next_case_id",ms1)
    
    //---------------------- place 2 ------------------
    val ms2 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace2 = new Place("p2","place2",ms2)
    
    val eval_trans1 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans1 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans1 = (b: BindTransInit) => { b.x != None }
    
    val ttrans1 = new Transition[BindTransInit]("tr1","Init")
    ttrans1.setEval(eval_trans1)
    ttrans1.setMerge(merge_trans1)
    ttrans1.setEvalLast(eval_last_trans1)
    
    // x (int) input arc for init from next_case_id
    val arcExpArc1 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc1 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc1 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc1 = new Arc[colset_CASEID,BindTransInit]("arc1", pplace1, ttrans1, Direction.PtT)
    arc1.setIsBase(true)
    arc1.setArcExp(arcExpArc1)
    arc1.setTokenToBind(TtoBArc1)
    arc1.setBindToToken(BtoTArc1)
    
    // x (int) output arc for init to start
    val arcExpArc2 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc2 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc2 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc2 = new Arc[colset_CASEID,BindTransInit]("arc2", pplace2, ttrans1, Direction.TtP)
    arc2.setArcExp(arcExpArc2)
    arc2.setTokenToBind(TtoBArc2)
    arc2.setBindToToken(BtoTArc2)
    
    // x (int) output arc for init to start
    val arcExpArc3 = (inp:Any) => inp match { case x:Int => { x+1  } } //arc2 exp
    val TtoBArc3 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc3 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc3 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc3 = new Arc[colset_CASEID,BindTransInit]("arc3", pplace1, ttrans1, Direction.TtP)
    arc3.setArcExp(arcExpArc3)
    arc3.setTokenToBind(TtoBArc3)
    arc3.setBindToToken(BtoTArc3)
    arc3.setAddTime(addTimeArc3)
    //End ENVIRONMENT
    
    //PROCESS
    //---------------------- place 3 ------------------
    val ms3 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    ms3 + ((1,0L))
    val pplace3 = new Place("p3","place 3",ms3)
    
    //---------------------- place 4 ------------------
    val ms4 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace4 = new Place("p4","place 4",ms4)
    
    //---------------------- place 5 ------------------
    val ms5 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace5 = new Place("p5","place 5",ms5)
    
    //---------------------- place 6 ------------------
    val ms6 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace6 = new Place("p6","place 6",ms6)
    
    //---------------------- place 7 ------------------
    val ms7 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace7 = new Place("p7","place 7",ms7)
    
    val eval_trans2 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans2 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans2 = (b: BindTransInit) => { b.x != None }
    
    val ttrans2 = new Transition[BindTransInit]("tr2","DS_QUAYSIDE")
    ttrans2.setEval(eval_trans2)
    ttrans2.setMerge(merge_trans2)
    ttrans2.setEvalLast(eval_last_trans2)
    
    val eval_trans3 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans3 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans3 = (b: BindTransInit) => { b.x != None }
    
    val ttrans3 = new Transition[BindTransInit]("tr3","LD_YARDSIDE")
    ttrans3.setEval(eval_trans3)
    ttrans3.setMerge(merge_trans3)
    ttrans3.setEvalLast(eval_last_trans3)
    
    val eval_trans4 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans4 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans4 = (b: BindTransInit) => { b.x != None }
    
    val ttrans4 = new Transition[BindTransInit]("tr4","DS_MOVE")
    ttrans4.setEval(eval_trans4)
    ttrans4.setMerge(merge_trans4)
    ttrans4.setEvalLast(eval_last_trans4)
    
    val eval_trans5 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans5 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans5 = (b: BindTransInit) => { b.x != None }
    
    val ttrans5 = new Transition[BindTransInit]("tr5","LD_MOVE")
    ttrans5.setEval(eval_trans5)
    ttrans5.setMerge(merge_trans5)
    ttrans5.setEvalLast(eval_last_trans5)
    
    val eval_trans6 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans6 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans6 = (b: BindTransInit) => { b.x != None }
    
    val ttrans6 = new Transition[BindTransInit]("tr6","DS_YARDSIDE")
    ttrans6.setEval(eval_trans6)
    ttrans6.setMerge(merge_trans6)
    ttrans6.setEvalLast(eval_last_trans6)
    
    val eval_trans7 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans7 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val eval_last_trans7 = (b: BindTransInit) => { b.x != None }
    
    val ttrans7 = new Transition[BindTransInit]("tr7","LD_QUAYSIDE")
    ttrans7.setEval(eval_trans7)
    ttrans7.setMerge(merge_trans7)
    ttrans7.setEvalLast(eval_last_trans7)
    
    // x (int) input arc for ds_quayside from start
    val arcExpArc4 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc4 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc4 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc4 = new Arc[colset_CASEID,BindTransInit]("arc4", pplace2, ttrans2, Direction.PtT)
    arc4.setIsBase(true)
    arc4.setArcExp(arcExpArc4)
    arc4.setTokenToBind(TtoBArc4)
    arc4.setBindToToken(BtoTArc4)
    
    // x (int) output arc for ds_quayside to place 3
    val arcExpArc5 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc5 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc5 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc5 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc5 = new Arc[colset_CASEID,BindTransInit]("arc5", pplace3, ttrans2, Direction.TtP)
    arc5.setArcExp(arcExpArc5)
    arc5.setTokenToBind(TtoBArc5)
    arc5.setBindToToken(BtoTArc5)
    arc5.setAddTime(addTimeArc5)
    
    // x (int) input arc for ld_yardside from start
    val arcExpArc6 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc6 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc6 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc6 = new Arc[colset_CASEID,BindTransInit]("arc6", pplace2, ttrans3, Direction.PtT)
    arc6.setIsBase(true)
    arc6.setArcExp(arcExpArc6)
    arc6.setTokenToBind(TtoBArc6)
    arc6.setBindToToken(BtoTArc6)
    
    // x (int) output arc for ld_yardside to place 4
    val arcExpArc7 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc7 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc7 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc7 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc7 = new Arc[colset_CASEID,BindTransInit]("arc7", pplace4, ttrans3, Direction.TtP)
    arc7.setArcExp(arcExpArc7)
    arc7.setTokenToBind(TtoBArc7)
    arc7.setBindToToken(BtoTArc7)
    arc7.setAddTime(addTimeArc7)
    
    // x (int) input arc for ds_move from place 3
    val arcExpArc8 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc8 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc8 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc8 = new Arc[colset_CASEID,BindTransInit]("arc8", pplace3, ttrans4, Direction.PtT)
    arc8.setIsBase(true)
    arc8.setArcExp(arcExpArc8)
    arc8.setTokenToBind(TtoBArc8)
    arc8.setBindToToken(BtoTArc8)
    
    // x (int) output arc for ds_move to place 5
    val arcExpArc9 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc9 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc9 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc9 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc9 = new Arc[colset_CASEID,BindTransInit]("arc9", pplace5, ttrans4, Direction.TtP)
    arc9.setArcExp(arcExpArc9)
    arc9.setTokenToBind(TtoBArc9)
    arc9.setBindToToken(BtoTArc9)
    arc9.setAddTime(addTimeArc9)
    
    // x (int) input arc for ld_move from place 4
    val arcExpArc10 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc10 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc10 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc10 = new Arc[colset_CASEID,BindTransInit]("arc10", pplace4, ttrans5, Direction.PtT)
    arc10.setIsBase(true)
    arc10.setArcExp(arcExpArc10)
    arc10.setTokenToBind(TtoBArc10)
    arc10.setBindToToken(BtoTArc10)
    
    // x (int) output arc for ld_move to place 6
    val arcExpArc11 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc11 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc11 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc11 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc11 = new Arc[colset_CASEID,BindTransInit]("arc11", pplace6, ttrans5, Direction.TtP)
    arc11.setArcExp(arcExpArc11)
    arc11.setTokenToBind(TtoBArc11)
    arc11.setBindToToken(BtoTArc11)
    arc11.setAddTime(addTimeArc11)
    
    // x (int) input arc for ds_yardside from place 5
    val arcExpArc12 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc12 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc12 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc12 = new Arc[colset_CASEID,BindTransInit]("arc12", pplace5, ttrans6, Direction.PtT)
    arc12.setIsBase(true)
    arc12.setArcExp(arcExpArc12)
    arc12.setTokenToBind(TtoBArc12)
    arc12.setBindToToken(BtoTArc12)
    
    // x (int) output arc for ld_yardside to place 4
    val arcExpArc13 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc13 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc13 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc13 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc13 = new Arc[colset_CASEID,BindTransInit]("arc13", pplace7, ttrans6, Direction.TtP)
    arc13.setArcExp(arcExpArc13)
    arc13.setTokenToBind(TtoBArc13)
    arc13.setBindToToken(BtoTArc13)
    arc13.setAddTime(addTimeArc13)
    
    // x (int) input arc for ld_quayside from place 6
    val arcExpArc14 = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc14 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc14 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val arc14 = new Arc[colset_CASEID,BindTransInit]("arc14", pplace6, ttrans7, Direction.PtT)
    arc14.setIsBase(true)
    arc14.setArcExp(arcExpArc14)
    arc14.setTokenToBind(TtoBArc14)
    arc14.setBindToToken(BtoTArc14)
    
    // x (int) output arc for ld_quayside to place 7
    val arcExpArc15 = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc15 = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc15 = (inp:Any) => inp match { case inp:BindTransInit => { inp.x.get } }
    val addTimeArc15 = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    val arc15 = new Arc[colset_CASEID,BindTransInit]("arc15", pplace7, ttrans7, Direction.TtP)
    arc15.setArcExp(arcExpArc15)
    arc15.setTokenToBind(TtoBArc15)
    arc15.setBindToToken(BtoTArc15)
    arc15.setAddTime(addTimeArc15)
    ///End PROCESS
    
    //add transitions
    cgraph.addTransition(ttrans1)
    cgraph.addTransition(ttrans2)
    cgraph.addTransition(ttrans2)
    cgraph.addTransition(ttrans3)
    cgraph.addTransition(ttrans4)
    cgraph.addTransition(ttrans5)
    cgraph.addTransition(ttrans6)
    cgraph.addTransition(ttrans7)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    cgraph.addPlace(pplace3)
    cgraph.addPlace(pplace4)
    cgraph.addPlace(pplace5)
    cgraph.addPlace(pplace6)
    cgraph.addPlace(pplace7)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    cgraph.addArc(arc3)
    cgraph.addArc(arc4)
    cgraph.addArc(arc5)
    cgraph.addArc(arc6)
    cgraph.addArc(arc7)
    cgraph.addArc(arc8)
    cgraph.addArc(arc9)
    cgraph.addArc(arc10)
    cgraph.addArc(arc11)
    cgraph.addArc(arc12)
    cgraph.addArc(arc13)
    cgraph.addArc(arc14)
    cgraph.addArc(arc15)
    
    val stopCrit = (p: Any) => p match { case p:Place[Int] => p.getcurrentMarking().multiset.size > 1000 }
    Simulator.fastRun(cgraph, stopCrit, pplace7)
  }
}