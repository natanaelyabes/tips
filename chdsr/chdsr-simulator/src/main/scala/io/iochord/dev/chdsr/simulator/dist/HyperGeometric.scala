package io.iochord.dev.chdsr.simulator.dist

case class HyperGeometric(populationSize:Int,numberOfSuccess:Int,sampleSize:Int) extends breeze.stats.distributions.HypergeometricDistribution(populationSize,numberOfSuccess,sampleSize)