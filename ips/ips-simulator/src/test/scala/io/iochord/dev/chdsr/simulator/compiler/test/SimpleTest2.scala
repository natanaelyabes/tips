package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.apps.ips.model.cpn.v1.impl._
import io.iochord.apps.ips.model.cpn.v1.impl.token._
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
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

object SimpleTest2 {
  
  def main(args: Array[String]) {
    val subject = new MarkingObservable()
    subject.addObserver(new MarkingObserver())
    val cgraph = new CPNGraph()
    
    

    val globtime = new GlobalTime(0)
    
    val stopCrit = (stop:Any) => stop match { case stop:Boolean => stop }
    val inpStopCrit = false
    
    new Simulator().run(cgraph, stopCrit, inpStopCrit, 1000, globtime, subject)
  }
}