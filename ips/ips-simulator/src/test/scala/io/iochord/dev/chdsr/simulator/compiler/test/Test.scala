package io.iochord.dev.chdsr.simulator.compiler.test
import breeze.stats.distributions.Gaussian;
import breeze.stats.distributions.Binomial;
import breeze.stats.distributions.ChiSquared;
import breeze.stats.distributions.Exponential;
import breeze.stats.distributions.Gamma;
import breeze.stats.distributions.Geometric;
import breeze.stats.distributions.Laplace;
import breeze.stats.distributions.LogNormal;
import breeze.stats.distributions.NegativeBinomial;
import breeze.stats.distributions.StudentsT;
import breeze.stats.distributions.Uniform;
import breeze.stats.distributions.Rayleigh;
import breeze.stats.distributions.WeibullDistribution
import breeze.stats.distributions.Rand;

object Test {
  
  def main(args:Array[String]) {
    //Discrete
    val negbin = NegativeBinomial(1.0,0.5).get() //NegativeBinomial(r,probability)
    val gam = Gamma(1,2).get() //Gamma(shape,scale)
    val bin = Binomial(2,0.5).get() //Binomial(number,probability)
    val rn = Rand.randInt(5, 10).get()
    
    //Continous
    val gaus = Gaussian(0, 1).get() //Gaussian(mean,stddev)
    val lap = Laplace(5,2).get() //Laplace(location,scale)
    val lognorm = LogNormal(1,2).get() //LogNormal(mean,sigma)
    val stud = StudentsT(1).get() //StudentsT(degreeOfFreedom)
    val uniform = Uniform(1,5).get() //Uniform.(lowB,upB)
    val rayleigh = Rayleigh(2).get() //RayLeigh(scale)
    val weibull = new WeibullDistribution(1.0,1.0).get() //
    
    println(negbin)
    println(gam)
    println(bin)
    println(rn)
    
    println(gaus)
    println(lap)
  }
}