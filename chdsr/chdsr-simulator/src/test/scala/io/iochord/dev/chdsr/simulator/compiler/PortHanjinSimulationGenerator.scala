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

object PortHanjinSimulationGenerator {
  
  def main(args: Array[String]) {
    case class BindTransInit(x:Option[Int]) extends Bind
    
    val cgraph = CPNGraph()
    
    type colset_CASEID = Int
    
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
    
    //add transitions
    cgraph.addTransition(ttrans1)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    cgraph.addArc(arc3)
    
    val stopCrit = (p: Any) => p match { case p:Place[Int] => p.getcurrentMarking().multiset.keys.filter(tokenWT => tokenWT._1 > 50).size > 0 }
    Simulator.fastRun(cgraph, stopCrit, pplace1)
  }
}