package io.iochord.dev.chdsr.simulator.dist


case class Cauchy(median:Double,scale:Double) extends breeze.stats.distributions.CauchyDistribution(median,scale)