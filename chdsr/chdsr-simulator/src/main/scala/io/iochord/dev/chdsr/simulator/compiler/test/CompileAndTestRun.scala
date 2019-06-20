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

//Example class to check binding using exhaustive search not recommended for var type with large combination
//In cpntools small colset s colset wth type with combination less than 10

object CompileAndTestRun {

  var x:Int = _
  var y:String = _
  
  def main(args: Array[String]) {
    
    val myclassSyntax = "new Simulation {\n"+
      "val test1 = \"test1\"\n"+
      "override def runSimulation(cpnGraph: CPNGraph):Unit ="+
        "{ println(\"Run Simulation\"); Simulator.run }\n"+ 
      "override def expState():Unit ="+
        "{ println(\"Explore State\") } \n"+ 
      "override def calcKPI[T](kpiFunc:T):Double ="+
        "{ println(\"Calculate KPI\"); testdist(); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()); return 5.0 }\n "+  
      "def test(var1:String):Unit = "+
        "{ println(var1) } \n"+
      "def testdist() { \n"+
        "val normal_val = Gaussian(100, 10).draw()+500 // mean (Double), sigma (Double) \n"+
        "println(\"Normal \"+normal_val) \n"+
        "val bern_val = Bernoulli(0.001).draw() // p (Double) \n"+
        "println(\"Bernoulli \"+bern_val) \n"+
        "val beta_val = Beta(10, 10).draw() // a (Double), b (Double) \n"+
        "println(\"Beta \"+beta_val) \n"+
        "val bin_val = Binomial(100, 10).draw() // n (Int), p(Double) \n"+
        "println(\"Binomial \"+bin_val) \n"+
        "val cauchy_val = Cauchy(100, 10).draw() // median (Double), scale (Double) \n"+
        "println(\"Cauchy \"+cauchy_val) \n"+
        "val chisqr_val = ChiSquared(100).draw() // x (Double) \n"+
        "println(\"Chisquared \"+chisqr_val) \n"+
        "val exp_val = Exponential(100).draw() // rate (Double) \n"+
        "println(\"Exponential \"+exp_val) \n"+
        "val gamma_val = Gamma(100,10).draw() // shape (Double), scale (Double) \n"+
        "println(\"Gamma \"+gamma_val) \n"+
        "val geo_val = Geometric(0.004).draw() // p (Double) \n"+
        "println(\"Geometric \"+geo_val) \n"+
        "val hgeo_val = HyperGeometric(10,2,5).draw() // populationSize (Int), numberOfSuccess (Int), sampleSize (Int) \n"+
        "println(\"HyperGeometric \"+hgeo_val) \n"+
        "val lap_val = Laplace(1,1).draw() // location (Double), scale (Double) \n"+
        "println(\"Laplace \"+lap_val) \n"+
        "val lognormal_val = LogNormal(100, 10).draw() // mean (Double), sigma (Double) \n"+
        "println(\"LogNormal \"+lognormal_val) \n"+
        "val negbin_val = NegativeBinomial(5, 0.5).draw() // r (Double), p (Double) \n"+
        "println(\"NegativeBinomial \"+negbin_val) \n"+
        "val studt_val = StudentsT(100).draw() // degreeOfFreedom (Double) \n"+
        "println(\"StudentsT \"+studt_val) \n"+
        "val unif_val = Uniform(10,100).draw() // low (Double), high (Double) \n"+
        "println(\"Uniform \"+unif_val) \n"+
        "val rayleigh_val = Rayleigh(100).draw() // scale (Double) \n"+
        "println(\"Rayleight \"+rayleigh_val) \n"+
        "val weibull_val = Weibull(100,200).draw() // alpha (Double), beta(Double) \n"+
        "println(\"Weibull \"+weibull_val) \n"+
      "} \n"+
      "class Coba1 { val varc1 = \"other test\" }\n"+
    "}";
    
    val cgraph = CPNGraph()
    
    type colset1 = (Int,String)
    
    val ms1 = new Multiset[colset1](LinkedHashMap[(colset1,Long),Int](), classOf[colset1])
    ms1 + (((1,"Bert"),2L))
    ms1 + (((2,"Test"),2L))
    ms1 + (((3,"Merdeka"),2L))
    
    val pplace1 = new Place("id1","woo1",ms1)
    
    val ms2 = new Multiset[colset1](LinkedHashMap[(colset1,Long),Int](), classOf[colset1])
    ms2 + (((1,"Amg"),2L))
    ms2 + (((3,"Test"),2L))
    ms2 + (((4,"Test"),2L))
    ms2 + (((5,"Ferari"),2L))
    ms2 + (((6,"Lambo"),2L))
    
    val pplace2 = new Place("id2","woo2",ms2)
    
    var globtime = 10
    
    case class Bind1(x:Option[Int],y:Option[String]) extends Bind
    
    val listtoken1 = pplace1.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset => Bind1(Some(token._1._1), Some(token._1._2))
      }
    ).toList
    
    val listtoken2 = pplace2.getcurrentMarking().multiset.keys.filter(_._2 < globtime).map(token => token match {      
        case colset => Bind1(Some(token._1._1), Some(token._1._2))
      }
    ).toList
    
    def exp_arc1(token: Any) = token match { case colset:colset1 => (2*colset._1, colset._2) }
    def exp_arc2(token: Any) = token match { case colset:colset1 => (3*colset._1, colset._2) }
    
    val ttrans1 = new Transition("tr1","transition1", new Guard())
    //guard is x >= 1
    def guard_exp_ttrans1(x:Int) = x >= 1
    
    def anyfunc(x:Int):Int = 2*x
    
    val gen_guard_exp_ttrans1 = (listb: List[Any]) => listb match { case list: List[Bind1] => { list.exists(be =>  be.x.map(x => guard_exp_ttrans1(anyfunc(x))).get) } case _ => false }
    ttrans1.getGuard().setGuardBind(gen_guard_exp_ttrans1)
    
    val arc1 = new Arc[colset1,Bind1]("arc1", pplace1, ttrans1, Direction.PtT)
    arc1.setArcExp(exp_arc1)
    
    val arc2 = new Arc[colset1,Bind1]("arc2", pplace2, ttrans1, Direction.PtT)
    arc2.setArcExp(exp_arc2)
    
    val TtoB = (token: Any) => token match { case colset: colset1 => { Bind1(Some(colset._1),Some(colset._2)) } }
    arc1.setTokenToBind(TtoB)
    arc2.setTokenToBind(TtoB)
    
    ttrans1.addIn(arc1)
    ttrans1.addIn(arc2)
    
    pplace1.addOut(arc1)
    pplace1.addOut(arc2)
    
    //add transitions
    cgraph.addTransition(ttrans1)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    
    Simulator.run(cgraph, 10)
    
    
    val normal_val = Gaussian(100, 10).draw() // mean (Double), sigma (Double)
    println("Normal "+normal_val)
    val bern_val = Bernoulli(1).draw() // p (Double)
    println("Bernoulli "+bern_val)
    val beta_val = Beta(10, 10).draw() // a (Double), b (Double)
    println("Beta "+beta_val)
    val bin_val = Binomial(100, 10).draw() // n (Int), p(Double)
    println("Binomial "+bin_val)
    val cauchy_val = Cauchy(100, 10).draw() // median (Double), scale (Double)
    println("Cauchy "+cauchy_val)
    val chisqr_val = ChiSquared(100).draw() // x (Double)
    println("Chisquared "+chisqr_val)
    val exp_val = Exponential(100).draw() // rate (Double)
    println("Exponential "+exp_val)
    val gamma_val = Gamma(100,10).draw() // shape (Double), scale (Double)
    println("Gamma "+gamma_val)
    val geo_val = Geometric(1).draw() // p (Double)
    println("Geometric "+geo_val)
    val hgeo_val = HyperGeometric(10,2,5).draw() // populationSize (Int), numberOfSuccess (Int), sampleSize (Int)
    println("HyperGeometric "+hgeo_val)
    val lap_val = Laplace(1,1).draw() // location (Double), scale (Double)
    println("Laplace "+lap_val)
    val lognormal_val = LogNormal(100, 10).draw() // mean (Double), sigma (Double)
    println("LogNormal "+lognormal_val)
    val negbin_val = NegativeBinomial(5, 0.5).draw() // r (Double), p (Double)
    println("NegativeBinomial "+negbin_val)
    val studt_val = StudentsT(100).draw() // degreeOfFreedom (Double)
    println("StudentsT "+studt_val)
    val unif_val = Uniform(10,100).draw() // low (Double), high (Double)
    println("Uniform "+unif_val)
    val rayleigh_val = Rayleigh(100).draw() // scale (Double)
    println("Rayleight "+rayleigh_val)
    val weibull_val = Weibull(100,200).draw() // alpha (Double), beta(Double)
    println("Weibull "+weibull_val)
    
    /*
    val (isArcEn, lbe) = ttrans1.isArcEnabled()
    for (be <- lbe) println(be)
    
    println(ttrans1.getGuard().evalGuard(lbe))
    */
  }
}