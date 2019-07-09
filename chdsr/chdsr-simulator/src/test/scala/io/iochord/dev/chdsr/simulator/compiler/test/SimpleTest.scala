package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.dev.chdsr.model.cpn.v1.impl._
import io.iochord.dev.chdsr.model.cpn.v1._
import io.iochord.dev.chdsr.simulator.engine.Simulator
import io.iochord.dev.chdsr.simulator.engine.observer.MarkingObserver
import io.iochord.dev.chdsr.simulator.engine.subject.MarkingObservable

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

object SimpleTest {
  
  def main(args: Array[String]) {
    
    val subject = new MarkingObservable()
    subject.addObserver(new MarkingObserver())
    
    case class BindTransInit(x:Option[Int]) extends Bind
    
    val cgraph = CPNGraph()
    
    type colset_CASEID = Int
    
    //ENVIRONMENT
    //---------------------- place 1 ------------------
    val map_1 = Map[(colset_CASEID,Long),Int]( ((1,0L),1) )
    val ms1 = new Multiset[colset_CASEID](map_1)
    val pplace1 = new Place("p1","place1",ms1)
    
    //---------------------- place 2 ------------------
    val map_2 = Map[(colset_CASEID,Long),Int]( ((1,0L),1) )
    val ms2 = new Multiset[colset_CASEID](map_2)
    val pplace2 = new Place("p2","place2",ms2)
    
    //---------------------- place 2 ------------------
    val ms3 = new Multiset[colset_CASEID](Map[(colset_CASEID,Long),Int]())
    val pplace3 = new Place("p3","place3",ms3)
    
    val eval_trans1 = (b1: BindTransInit, b2: BindTransInit) => {
      (b1.x == b2.x || b1.x == None || b2.x == None)
    }
    
    val merge_trans1 = (b1: BindTransInit, b2: BindTransInit) => { 
      val x = if (b1.x == None) b2.x else b1.x
      BindTransInit(x)
    }
    
    val ttrans1 = new Transition[BindTransInit]("tr1","Init")
    ttrans1.setEval(eval_trans1)
    ttrans1.setMerge(merge_trans1)
    
    // x (int) input arc for init from place1_id
    val arcExpArc1:(colset_CASEID => Option[colset_CASEID]) = (inp:Any) => inp match { case(x:colset_CASEID) => { Some(x) } } //arc1 exp
    val TtoBArc1:(colset_CASEID => BindTransInit) = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc1:(BindTransInit => colset_CASEID) = (b:BindTransInit) => { b.x.get }
    val arc1 = new Arc[colset_CASEID,BindTransInit]("arc1", pplace1, ttrans1, Direction.PtT, classOf[colset_CASEID])
    arc1.setIsBase(true)
    arc1.setArcExp(arcExpArc1)
    arc1.setTokenToBind(TtoBArc1)
    arc1.setBindToToken(BtoTArc1)
    
    // x (int) input arc for init from place2_id
    val arcExpArc2:(colset_CASEID => Option[colset_CASEID]) = (inp:Any) => inp match { case(x:colset_CASEID) => { None } } //arc2 exp
    val TtoBArc2:(colset_CASEID => BindTransInit) = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc2:(BindTransInit => colset_CASEID) = (b:BindTransInit) => { b.x.get }
    val arc2 = new Arc[colset_CASEID,BindTransInit]("arc2", pplace2, ttrans1, Direction.PtT, classOf[colset_CASEID])
    arc2.setArcExp(arcExpArc2)
    arc2.setTokenToBind(TtoBArc2)
    arc2.setBindToToken(BtoTArc2)
    
    // x (int) output arc for init to place3
    val arcExpArc3:(colset_CASEID => Option[colset_CASEID]) = (inp:Any) => inp match { case(x:colset_CASEID) => { Some(x) } } //arc3 exp
    val TtoBArc3:(colset_CASEID => BindTransInit) = (inp:Any) => BindTransInit(inp match { case x:Int => Some(x); case _ => None })
    val BtoTArc3:(BindTransInit => colset_CASEID) = (b:BindTransInit) => { b.x.get }
    val addTimeArc3:(BindTransInit => Long) = (b:BindTransInit) => { Math.round(Gaussian(100, 10).draw()) }
    val arc3 = new Arc[colset_CASEID,BindTransInit]("arc3", pplace3, ttrans1, Direction.TtP)
    arc3.setArcExp(arcExpArc3)
    arc3.setTokenToBind(TtoBArc3)
    arc3.setBindToToken(BtoTArc3)
    arc3.setAddTime(addTimeArc3)
    //End ENVIRONMENT
    
    //add transitions
    cgraph.addTransition(ttrans1)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    cgraph.addArc(arc3)
    
    /*
    val stopCrit = (p: Any) => p match { case p:Place[_] => { 
      	p.getcurrentMarking().multiset.keys.filter(_._1 > 2).size > 0
      } 
    }
    */
    val globtime = new GlobalTime(0)
    
    Simulator.run(cgraph, 4, globtime, subject)
    
    val stopCrit = (globtime: Any) => globtime match { case globtime:GlobalTime => { 
        globtime.time > 3000
      }
    }
    
    //Simulator.fastRun(cgraph, stopCrit, globtime, globtime, subject)
  }
}