package io.iochord.dev.chdsr.simulator.dist

case class Weibull(alpha:Double,beta:Double) extends breeze.stats.distributions.WeibullDistribution(alpha,beta)