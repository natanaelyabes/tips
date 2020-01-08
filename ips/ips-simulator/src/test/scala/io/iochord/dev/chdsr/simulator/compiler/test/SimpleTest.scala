package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.apps.ips.model.cpn.v1.impl._
import io.iochord.apps.ips.model.cpn.v1._
import io.iochord.apps.ips.simulator.engine.Simulator
import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver
import io.iochord.apps.ips.simulator.engine.subject.MarkingObservable

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

/**
*
* @package ips-simulator
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
object SimpleTest {
  
  def main(args: Array[String]) {
    val subject = new MarkingObservable()
    subject.addObserver(new MarkingObserver())
    
    case class BindTransInit(x:Option[Int])
    
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
    val BtoTArc1 = (b:BindTransInit) => { b.x.get }:colset_CASEID
    val TtoBArc1:(colset_CASEID => Option[BindTransInit]) = (t:colset_CASEID) => {
      try {
        val x = t
        Some(BindTransInit(Some(x)))
      } catch {
        case e:Exception => {
          None
        }
      }
    }
    val arc1 = new Arc[colset_CASEID,BindTransInit]("arc1", pplace1, ttrans1, Direction.PtT)
    arc1.setBindToToken(BtoTArc1)
    arc1.setTokenToBind(TtoBArc1)
    
    // x (int) input arc for init from place2_id
    val BtoTArc2:(BindTransInit => colset_CASEID) = (b:BindTransInit) => { b.x.get }
    val addTimeArc2:(BindTransInit => Long) = (b:BindTransInit) => { Math.round(Gaussian(100, 10).draw()) }
    val arc2 = new Arc[colset_CASEID,BindTransInit]("arc2", pplace2, ttrans1, Direction.TtP)
    arc2.setIsBase(false)
    arc2.setBindToToken(BtoTArc2)
    arc2.setAddTime(addTimeArc2)
    
    // x (int) output arc for init to place3
    val BtoTArc3:(BindTransInit => colset_CASEID) = (b:BindTransInit) => { b.x.get + 1}
    val arc3 = new Arc[colset_CASEID,BindTransInit]("arc3", pplace1, ttrans1, Direction.TtP)
    arc3.setIsBase(false)
    arc3.setBindToToken(BtoTArc3)
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
    
    val globtime = new GlobalTime(0)
    
    val stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    val inpStopCrit = false
    
    new Simulator().run(cgraph, stopCrit, inpStopCrit, 4, globtime, subject)
  }
}