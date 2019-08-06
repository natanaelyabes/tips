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

object SimpleTest3 {
  
  def main(args: Array[String]) {
    val spa = new SimulatorPerformAnalysis();
		println(spa.doATMTestWithManyToken(200, "[{\"id\": \"place00000002\", \"numInitToken\": 100}]"));
  }
}