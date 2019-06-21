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

object PortHanjinSimulationComplete {
  
  def main(args: Array[String]) {
    case class CaseData(name:String)
    
    val filterResGroup1 = Set("GC102", "GC101", "GC107", "GC106", "GC105", "GC104", "GC103")
    val filterResGroup2 = Set("TC272", "TC273", "TC271", "TC225", "TC226", "TC223", "TC224", "TC221", "TC265", "TC222", "TC266", "TC263", "TC264", "TC261", "TC262", "RS309", "TC216", "TC214", "RS301", "TC215", "TC212", "TC256", "TC213", "RS304", "TC254", "TC211", "TC255", "RS302", "TC252", "RS303", "TC253", "TC251", "TC245", "TC246", "TC243", "TC244", "TC241", "TH311", "TC242", "TH313", "TH312", "TC236", "TC234", "TC235", "TC232", "TC276", "TC233", "TC274", "TC231", "TC275")
    val filterResGroup3 = Set("YT507", "YT506", "YT508", "YT514", "YT558", "YT513", "YT557", "YT516", "YT515", "YT559", "YT510", "YT554", "YT553", "YT512", "YT556", "YT511", "YT555", "YT550", "YT594", "YT593", "YT552", "YT596", "YT551", "YT595", "YT590", "YT592", "YT518", "YT517", "YT519", "YT525", "YT569", "YT524", "YT527", "YT526", "YT521", "YT565", "YT520", "YT564", "YT523", "YT567", "YT522", "YT566", "YT561", "YT560", "YT563", "YT562", "YT529", "YT528", "YT536", "YT535", "YT579", "YT538", "YT537", "YT532", "YT576", "YT531", "YT575", "YT534", "YT578", "YT533", "YT577", "YT572", "YT571", "YT530", "YT574", "YT573", "YT570", "YT539", "YT503", "YT547", "YT502", "YT546", "YT505", "YT549", "YT504", "YT548", "YT543", "YT587", "YT542", "YT586", "YT501", "YT545", "YT589", "YT500", "YT544", "YT588", "YT583", "YT582", "YT541", "YT585", "YT540", "YT584", "YT581", "YT580")
    
    case class BindTransInit(x:Option[Int]) extends Bind
    case class BindTransProcess(x:Option[Int], y:Option[String]) extends Bind
    
    val cgraph = CPNGraph()
    
    type colset_CASEID = Int
    type colset_RES = String
    type colset_CASEIDxRES = (Int,String)
    
    //ENVIRONMENT
    //---------------------- place 1 ------------------
    val ms1 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    ms1 + ((1,0L))
    val pplace1 = new Place("p1","next_case_id",ms1)
    
    //---------------------- place 2 ------------------
    val ms2 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace2 = new Place("p2","place2",ms2)
    
    //---------------------- resource ------------------
    val ms_res = new Multiset[colset_RES](LinkedHashMap[(colset_RES,Long),Int](), classOf[colset_RES])
    ms_res + (("TC272",0)) + (("TC273",0)) + (("TC271",0)) + (("YT507",0)) + (("YT506",0)) + (("YT508",0)) + (("YT514",0)) + (("YT513",0)) + (("YT516",0)) + (("YT515",0)) + (("YT510",0)) + (("YT512",0)) + (("YT511",0)) + (("YT594",0)) + (("YT593",0)) + (("YT596",0)) + (("YT595",0)) + (("TC265",0)) + (("YT590",0)) + (("TC266",0)) + (("TC263",0)) + (("YT592",0)) + (("TC264",0)) + (("TC261",0)) + (("TC262",0)) + (("YT518",0)) + (("YT517",0)) + (("YT519",0)) + (("YT525",0)) + (("RS309",0)) + (("YT524",0)) + (("YT527",0)) + (("YT526",0)) + (("YT521",0)) + (("YT520",0)) + (("YT523",0)) + (("YT522",0)) + (("RS301",0)) + (("TC256",0)) + (("RS304",0)) + (("TC254",0)) + (("TC255",0)) + (("RS302",0)) + (("TC252",0)) + (("RS303",0)) + (("TC253",0)) + (("YT529",0)) + (("YT528",0)) + (("YT536",0)) + (("YT535",0)) + (("YT538",0)) + (("YT537",0)) + (("YT532",0)) + (("YT531",0)) + (("YT534",0)) + (("YT533",0)) + (("YT530",0)) + (("TH311",0)) + (("TH313",0)) + (("TH312",0)) + (("YT539",0)) + (("YT547",0)) + (("YT546",0)) + (("YT549",0)) + (("YT548",0)) + (("YT543",0)) + (("YT542",0)) + (("YT545",0)) + (("YT544",0)) + (("YT541",0)) + (("YT540",0)) + (("TC276",0)) + (("TC274",0)) + (("TC275",0)) + (("GC102",0)) + (("YT558",0)) + (("GC101",0)) + (("YT557",0)) + (("YT559",0)) + (("YT554",0)) + (("YT553",0)) + (("YT556",0)) + (("YT555",0)) + (("TC225",0)) + (("YT550",0)) + (("TC226",0)) + (("TC223",0)) + (("YT552",0)) + (("GC107",0)) + (("TC224",0)) + (("YT551",0)) + (("GC106",0)) + (("TC221",0)) + (("GC105",0)) + (("TC222",0)) + (("GC104",0)) + (("GC103",0)) + (("YT569",0)) + (("YT565",0)) + (("YT564",0)) + (("TC216",0)) + (("YT567",0)) + (("YT566",0)) + (("TC214",0)) + (("YT561",0)) + (("TC215",0)) + (("YT560",0)) + (("TC212",0)) + (("YT563",0)) + (("TC213",0)) + (("YT562",0)) + (("TC211",0)) + (("TC251",0)) + (("YT579",0)) + (("YT576",0)) + (("YT575",0)) + (("YT578",0)) + (("YT577",0)) + (("YT572",0)) + (("YT571",0)) + (("TC245",0)) + (("YT574",0)) + (("TC246",0)) + (("YT573",0)) + (("TC243",0)) + (("TC244",0)) + (("TC241",0)) + (("YT570",0)) + (("TC242",0)) + (("YT503",0)) + (("YT502",0)) + (("YT505",0)) + (("YT504",0)) + (("YT587",0)) + (("YT586",0)) + (("YT501",0)) + (("YT589",0)) + (("YT500",0)) + (("YT588",0)) + (("TC236",0)) + (("YT583",0)) + (("YT582",0)) + (("TC234",0)) + (("YT585",0)) + (("TC235",0)) + (("YT584",0)) + (("TC232",0)) + (("TC233",0)) + (("YT581",0)) + (("TC231",0)) + (("YT580"))
      
    val pplace_res = new Place("p_res","place_res",ms_res)
    
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
    //End ENVIRONMENT
    
    //PROCESS
    //---------------------- place 2 tmp1 ------------------
    val ms2tmp1 = new Multiset[colset_CASEIDxRES](LinkedHashMap[(colset_CASEIDxRES,Long),Int](), classOf[colset_CASEIDxRES])
    val pplace2tmp1 = new Place("p2tmp1","place 2 tmp1",ms2tmp1)
    
    //---------------------- place 2 tmp2 ------------------
    val ms2tmp2 = new Multiset[colset_CASEIDxRES](LinkedHashMap[(colset_CASEIDxRES,Long),Int](), classOf[colset_CASEIDxRES])
    val pplace2tmp2 = new Place("p2tmp2","place 2 tmp2",ms2tmp2)
    
    //---------------------- place 3 ------------------
    val ms3 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace3 = new Place("p3","place 3",ms3)
    
    //---------------------- place 3 tmp ------------------
    val ms3tmp = new Multiset[colset_CASEIDxRES](LinkedHashMap[(colset_CASEIDxRES,Long),Int](), classOf[colset_CASEIDxRES])
    val pplace3tmp = new Place("p3tmp","place 3 tmp",ms3tmp)
    
    //---------------------- place 4 ------------------
    val ms4 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace4 = new Place("p4","place 4",ms4)
    
    //---------------------- place 4 tmp ------------------
    val ms4tmp = new Multiset[colset_CASEIDxRES](LinkedHashMap[(colset_CASEIDxRES,Long),Int](), classOf[colset_CASEIDxRES])
    val pplace4tmp = new Place("p4tmp","place 4 tmp",ms4tmp)
    
    //---------------------- place 5 ------------------
    val ms5 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace5 = new Place("p5","place 5",ms5)
    
    //---------------------- place 5 tmp ------------------
    val ms5tmp = new Multiset[colset_CASEIDxRES](LinkedHashMap[(colset_CASEIDxRES,Long),Int](), classOf[colset_CASEIDxRES])
    val pplace5tmp = new Place("p5tmp","place 5 tmp",ms5tmp)
    
    //---------------------- place 6 ------------------
    val ms6 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace6 = new Place("p6","place 6",ms6)
    
    //---------------------- place 6 tmp ------------------
    val ms6tmp = new Multiset[colset_CASEIDxRES](LinkedHashMap[(colset_CASEIDxRES,Long),Int](), classOf[colset_CASEIDxRES])
    val pplace6tmp = new Place("p6tmp","place 6 tmp",ms6tmp)
    
    //---------------------- place 7 ------------------
    val ms7 = new Multiset[colset_CASEID](LinkedHashMap[(colset_CASEID,Long),Int](), classOf[colset_CASEID])
    val pplace7 = new Place("p7","place 7",ms7)
    
    val eval_trans2_start = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans2_start = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans2_start = (b: BindTransProcess) => { b.x != None && b.y != None }
    
    val ttrans2_start = new Transition[BindTransProcess]("tr2_start","DS_QUAYSIDE START")
    ttrans2_start.setEval(eval_trans2_start)
    ttrans2_start.setMerge(merge_trans2_start)
    ttrans2_start.setEvalLast(eval_last_trans2_start)
    
    val eval_trans2_comp = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans2_comp = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans2_comp = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans2_comp = new Transition[BindTransProcess]("tr2_complete","DS_QUAYSIDE COMPLETE")
    ttrans2_comp.setEval(eval_trans2_comp)
    ttrans2_comp.setMerge(merge_trans2_comp)
    ttrans2_comp.setEvalLast(eval_last_trans2_comp)
    
    val eval_trans3_start = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans3_start = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans3_start = (b: BindTransProcess) => { b.x != None && b.y != None }
    
    val ttrans3_start = new Transition[BindTransProcess]("tr3_start","LD_YARDSIDE START")
    ttrans3_start.setEval(eval_trans3_start)
    ttrans3_start.setMerge(merge_trans3_start)
    ttrans3_start.setEvalLast(eval_last_trans3_start)
    
    val eval_trans3_comp = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans3_comp = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans3_comp = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans3_comp = new Transition[BindTransProcess]("tr3_complete","LD_YARDSIDE COMPLETE")
    ttrans3_comp.setEval(eval_trans3_comp)
    ttrans3_comp.setMerge(merge_trans3_comp)
    ttrans3_comp.setEvalLast(eval_last_trans3_comp)
    
    val eval_trans4_start = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans4_start = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans4_start = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans4_start = new Transition[BindTransProcess]("tr4_start","DS_MOVE START")
    ttrans4_start.setEval(eval_trans4_start)
    ttrans4_start.setMerge(merge_trans4_start)
    ttrans4_start.setEvalLast(eval_last_trans4_start)
    
    val eval_trans4_comp = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans4_comp = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans4_comp = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans4_comp = new Transition[BindTransProcess]("tr4_complete","DS_MOVE COMPLETE")
    ttrans4_comp.setEval(eval_trans4_comp)
    ttrans4_comp.setMerge(merge_trans4_comp)
    ttrans4_comp.setEvalLast(eval_last_trans4_comp)
    
    val eval_trans5_start = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans5_start = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans5_start = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans5_start = new Transition[BindTransProcess]("tr5_start","LD_MOVE START")
    ttrans5_start.setEval(eval_trans5_start)
    ttrans5_start.setMerge(merge_trans5_start)
    ttrans5_start.setEvalLast(eval_last_trans5_start)
    
    val eval_trans5_comp = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans5_comp = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans5_comp = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans5_comp = new Transition[BindTransProcess]("tr5_complete","LD_MOVE COMPLETE")
    ttrans5_comp.setEval(eval_trans5_comp)
    ttrans5_comp.setMerge(merge_trans5_comp)
    ttrans5_comp.setEvalLast(eval_last_trans5_comp)
    
    val eval_trans6_start = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans6_start = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans6_start = (b: BindTransProcess) => { b.x != None && b.y != None }
    
    val ttrans6_start = new Transition[BindTransProcess]("tr6_start","DS_YARDSIDE START")
    ttrans6_start.setEval(eval_trans6_start)
    ttrans6_start.setMerge(merge_trans6_start)
    ttrans6_start.setEvalLast(eval_last_trans6_start)
    
    val eval_trans6_comp = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans6_comp = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans6_comp = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans6_comp = new Transition[BindTransProcess]("tr6_complete","DS_YARDSIDE COMPLETE")
    ttrans6_comp.setEval(eval_trans6_comp)
    ttrans6_comp.setMerge(merge_trans6_comp)
    ttrans6_comp.setEvalLast(eval_last_trans6_comp)
    
    val eval_trans7_start = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans7_start = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans7_start = (b: BindTransProcess) => { b.x != None && b.y != None }
    
    val ttrans7_start = new Transition[BindTransProcess]("tr7_start","LD_QUAYSIDE START")
    ttrans7_start.setEval(eval_trans7_start)
    ttrans7_start.setMerge(merge_trans7_start)
    ttrans7_start.setEvalLast(eval_last_trans7_start)
    
    val eval_trans7_comp = (b1: BindTransProcess, b2: BindTransProcess) => {
      (b1.x == b2.x || b1.x == None || b2.x == None) && (b1.y == b2.y || b1.y == None || b2.y == None)
    }
    
    val merge_trans7_comp = (b1: BindTransProcess, b2: BindTransProcess) => { 
      val x = if (b1.x == None) b2.x else b1.x
      val y = if (b1.y == None) b2.y else b1.y
      BindTransProcess(x,y)
    }
    
    val eval_last_trans7_comp = (b: BindTransProcess) => { b.x != None && b.y != None  }
    
    val ttrans7_comp = new Transition[BindTransProcess]("tr7_complete","LD_QUAYSIDE COMPLETE")
    ttrans7_comp.setEval(eval_trans7_comp)
    ttrans7_comp.setMerge(merge_trans7_comp)
    ttrans7_comp.setEvalLast(eval_last_trans7_comp)
    
    def anyfunc(x:Int):Int = { 2*x }
    
    //========================== arc 4 ==========================
    // x (int) input arc from start to ds_quayside_start
    val arcExpArc4_st = (inp:Any) => inp match { case x:Int => { anyfunc(x) } } //arc1 exp
    val TtoBArc4_st = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc4_st = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc4_st = new Arc[colset_CASEID,BindTransProcess]("arc4st", pplace2, ttrans2_start, Direction.PtT)
    arc4_st.setIsBase(true)
    arc4_st.setArcExp(arcExpArc4_st)
    arc4_st.setTokenToBind(TtoBArc4_st)
    arc4_st.setBindToToken(BtoTArc4_st)
    
    // (x,y) (int,string) output arc from ds_quayside_start to place2tmp1
    val arcExpArc4_cptmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc4_cptmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc4_cptmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc4_cptmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc4cptmp", pplace2tmp1, ttrans2_start, Direction.TtP)
    val addTimeArc4_cptmp = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    arc4_cptmp.setArcExp(arcExpArc4_cptmp)
    arc4_cptmp.setTokenToBind(TtoBArc4_cptmp)
    arc4_cptmp.setBindToToken(BtoTArc4_cptmp)
    arc4_cptmp.setAddTime(addTimeArc4_cptmp)
    
    // (x,y) (int,string) output arc from place2tmp1 to ds_quayside_complete
    val arcExpArc4_sttmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc4_sttmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc4_sttmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc4_sttmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc4sttmp", pplace2tmp1, ttrans2_comp, Direction.PtT)
    arc4_sttmp.setArcExp(arcExpArc4_sttmp)
    arc4_sttmp.setTokenToBind(TtoBArc4_sttmp)
    arc4_sttmp.setBindToToken(BtoTArc4_sttmp)
    
    // y (string) input arc from resource to ds_quayside_start
    val arcExpArc4_stres = (inp:Any) => inp match { case y:String => { if(filterResGroup1(y)) y else None  } } //arc2 exp
    val TtoBArc4_stres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc4_stres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc4_stres = new Arc[colset_RES,BindTransProcess]("arc4stres", pplace_res, ttrans2_start, Direction.PtT)
    arc4_stres.setIsBase(true)
    arc4_stres.setArcExp(arcExpArc4_stres)
    arc4_stres.setTokenToBind(TtoBArc4_stres)
    arc4_stres.setBindToToken(BtoTArc4_stres)
    
    // y (string) input arc from ds_quayside_complete to resource
    val arcExpArc4_cpres = (inp:Any) => inp match { case y:String => { if(filterResGroup1(y)) y else None  } } //arc2 exp
    val TtoBArc4_cpres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc4_cpres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc4_cpres = new Arc[colset_RES,BindTransProcess]("arc4cpres", pplace_res, ttrans2_comp, Direction.TtP)
    arc4_cpres.setArcExp(arcExpArc4_cpres)
    arc4_cpres.setTokenToBind(TtoBArc4_cpres)
    arc4_cpres.setBindToToken(BtoTArc4_cpres)
    
    // x (int) output arc from ds_quayside_comp to place3
    val arcExpArc4_cp = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc4_cp = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc4_cp = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc4_cp = new Arc[colset_CASEID,BindTransProcess]("arc4cp", pplace3, ttrans2_comp, Direction.TtP)
    arc4_cp.setArcExp(arcExpArc4_cp)
    arc4_cp.setTokenToBind(TtoBArc4_cp)
    arc4_cp.setBindToToken(BtoTArc4_cp)
    
    //========================== arc 5 ==========================
    // x (int) input arc from start to ld_yardside_start
    val arcExpArc5_st = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc5_st = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc5_st = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc5_st = new Arc[colset_CASEID,BindTransProcess]("arc5st", pplace2, ttrans3_start, Direction.PtT)
    arc5_st.setIsBase(true)
    arc5_st.setArcExp(arcExpArc5_st)
    arc5_st.setTokenToBind(TtoBArc5_st)
    arc5_st.setBindToToken(BtoTArc5_st)
    
    // (x,y) (int,string) output arc from ld_yardside_start to place2tmp1
    val arcExpArc5_cptmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc5_cptmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc5_cptmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc5_cptmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc5cptmp", pplace2tmp2, ttrans3_start, Direction.TtP)
    val addTimeArc5_cptmp = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    arc5_cptmp.setArcExp(arcExpArc5_cptmp)
    arc5_cptmp.setTokenToBind(TtoBArc5_cptmp)
    arc5_cptmp.setBindToToken(BtoTArc5_cptmp)
    arc5_cptmp.setAddTime(addTimeArc5_cptmp)
    
    // (x,y) (int,string) output arc from place2tmp1 to ld_yardside_complete
    val arcExpArc5_sttmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc5_sttmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc5_sttmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc5_sttmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc5sttmp", pplace2tmp2, ttrans3_comp, Direction.PtT)
    arc5_sttmp.setArcExp(arcExpArc5_sttmp)
    arc5_sttmp.setTokenToBind(TtoBArc5_sttmp)
    arc5_sttmp.setBindToToken(BtoTArc5_sttmp)
    
    // y (string) input arc from resource to ld_yardside_start
    val arcExpArc5_stres = (inp:Any) => inp match { case y:String => { if(filterResGroup2(y)) y else None  } } //arc2 exp
    val TtoBArc5_stres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc5_stres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc5_stres = new Arc[colset_RES,BindTransProcess]("arc5stres", pplace_res, ttrans3_start, Direction.PtT)
    arc5_stres.setIsBase(true)
    arc5_stres.setArcExp(arcExpArc5_stres)
    arc5_stres.setTokenToBind(TtoBArc5_stres)
    arc5_stres.setBindToToken(BtoTArc5_stres)
    
    // y (string) input arc from ld_yardside_complete to resource
    val arcExpArc5_cpres = (inp:Any) => inp match { case y:String => { if(filterResGroup2(y)) y else None  } } //arc2 exp
    val TtoBArc5_cpres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc5_cpres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc5_cpres = new Arc[colset_RES,BindTransProcess]("arc5cpres", pplace_res, ttrans3_comp, Direction.TtP)
    arc5_cpres.setArcExp(arcExpArc5_cpres)
    arc5_cpres.setTokenToBind(TtoBArc5_cpres)
    arc5_cpres.setBindToToken(BtoTArc5_cpres)
    
    // x (int) output arc from ld_yardside_comp to place3
    val arcExpArc5_cp = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc5_cp = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc5_cp = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc5_cp = new Arc[colset_CASEID,BindTransProcess]("arc5cp", pplace4, ttrans3_comp, Direction.TtP)
    arc5_cp.setArcExp(arcExpArc5_cp)
    arc5_cp.setTokenToBind(TtoBArc5_cp)
    arc5_cp.setBindToToken(BtoTArc5_cp)
    
    //========================== arc 6 ==========================
    // x (int) input arc from start to ds_move
    val arcExpArc6_st = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc6_st = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc6_st = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc6_st = new Arc[colset_CASEID,BindTransProcess]("arc6st", pplace3, ttrans4_start, Direction.PtT)
    arc6_st.setIsBase(true)
    arc6_st.setArcExp(arcExpArc6_st)
    arc6_st.setTokenToBind(TtoBArc6_st)
    arc6_st.setBindToToken(BtoTArc6_st)
    
    // (x,y) (int,string) output arc from ds_move_start to place2tmp1
    val arcExpArc6_cptmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc6_cptmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc6_cptmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc6_cptmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc6cptmp", pplace3tmp, ttrans4_start, Direction.TtP)
    val addTimeArc6_cptmp = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    arc6_cptmp.setArcExp(arcExpArc6_cptmp)
    arc6_cptmp.setTokenToBind(TtoBArc6_cptmp)
    arc6_cptmp.setBindToToken(BtoTArc6_cptmp)
    arc6_cptmp.setAddTime(addTimeArc6_cptmp)
    
    // (x,y) (int,string) output arc from place2tmp1 to ds_move_complete
    val arcExpArc6_sttmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc6_sttmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc6_sttmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc6_sttmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc6sttmp", pplace3tmp, ttrans4_comp, Direction.PtT)
    arc6_sttmp.setArcExp(arcExpArc6_sttmp)
    arc6_sttmp.setTokenToBind(TtoBArc6_sttmp)
    arc6_sttmp.setBindToToken(BtoTArc6_sttmp)
    
    // y (string) input arc from resource to ds_move_start
    val arcExpArc6_stres = (inp:Any) => inp match { case y:String => { if(filterResGroup3(y)) y else None  } } //arc2 exp
    val TtoBArc6_stres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc6_stres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc6_stres = new Arc[colset_RES,BindTransProcess]("arc6stres", pplace_res, ttrans4_start, Direction.PtT)
    arc6_stres.setIsBase(true)
    arc6_stres.setArcExp(arcExpArc6_stres)
    arc6_stres.setTokenToBind(TtoBArc6_stres)
    arc6_stres.setBindToToken(BtoTArc6_stres)
    
    // y (string) input arc from ds_move_complete to resource
    val arcExpArc6_cpres = (inp:Any) => inp match { case y:String => { if(filterResGroup3(y)) y else None  } } //arc2 exp
    val TtoBArc6_cpres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc6_cpres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc6_cpres = new Arc[colset_RES,BindTransProcess]("arc6cpres", pplace_res, ttrans4_comp, Direction.TtP)
    arc6_cpres.setArcExp(arcExpArc6_cpres)
    arc6_cpres.setTokenToBind(TtoBArc6_cpres)
    arc6_cpres.setBindToToken(BtoTArc6_cpres)
    
    // x (int) output arc from ds_move_comp to place3
    val arcExpArc6_cp = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc6_cp = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc6_cp = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc6_cp = new Arc[colset_CASEID,BindTransProcess]("arc6cp", pplace5, ttrans4_comp, Direction.TtP)
    arc6_cp.setArcExp(arcExpArc6_cp)
    arc6_cp.setTokenToBind(TtoBArc6_cp)
    arc6_cp.setBindToToken(BtoTArc6_cp)
    
    //========================== arc 7 ==========================
    // x (int) input arc from start to ld_move_start
    val arcExpArc7_st = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc7_st = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc7_st = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc7_st = new Arc[colset_CASEID,BindTransProcess]("arc7st", pplace4, ttrans5_start, Direction.PtT)
    arc7_st.setIsBase(true)
    arc7_st.setArcExp(arcExpArc7_st)
    arc7_st.setTokenToBind(TtoBArc7_st)
    arc7_st.setBindToToken(BtoTArc7_st)
    
    // (x,y) (int,string) output arc from ds_quayside_start to place2tmp1
    val arcExpArc7_cptmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc7_cptmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc7_cptmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc7_cptmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc7cptmp", pplace4tmp, ttrans5_start, Direction.TtP)
    val addTimeArc7_cptmp = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    arc7_cptmp.setArcExp(arcExpArc7_cptmp)
    arc7_cptmp.setTokenToBind(TtoBArc7_cptmp)
    arc7_cptmp.setBindToToken(BtoTArc7_cptmp)
    arc7_cptmp.setAddTime(addTimeArc7_cptmp)
    
    // (x,y) (int,string) output arc from place2tmp1 to ds_quayside_complete
    val arcExpArc7_sttmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc7_sttmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc7_sttmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc7_sttmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc7sttmp", pplace4tmp, ttrans5_comp, Direction.PtT)
    arc7_sttmp.setArcExp(arcExpArc7_sttmp)
    arc7_sttmp.setTokenToBind(TtoBArc7_sttmp)
    arc7_sttmp.setBindToToken(BtoTArc7_sttmp)
    
    // y (string) input arc from resource to ds_quayside_start
    val arcExpArc7_stres = (inp:Any) => inp match { case y:String => { if(filterResGroup3(y)) y else None  } } //arc2 exp
    val TtoBArc7_stres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc7_stres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc7_stres = new Arc[colset_RES,BindTransProcess]("arc7stres", pplace_res, ttrans5_start, Direction.PtT)
    arc7_stres.setIsBase(true)
    arc7_stres.setArcExp(arcExpArc7_stres)
    arc7_stres.setTokenToBind(TtoBArc7_stres)
    arc7_stres.setBindToToken(BtoTArc7_stres)
    
    // y (string) input arc from ds_quayside_complete to resource
    val arcExpArc7_cpres = (inp:Any) => inp match { case y:String => { if(filterResGroup3(y)) y else None  } } //arc2 exp
    val TtoBArc7_cpres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc7_cpres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc7_cpres = new Arc[colset_RES,BindTransProcess]("arc7cpres", pplace_res, ttrans5_comp, Direction.TtP)
    arc7_cpres.setArcExp(arcExpArc7_cpres)
    arc7_cpres.setTokenToBind(TtoBArc7_cpres)
    arc7_cpres.setBindToToken(BtoTArc7_cpres)
    
    // x (int) output arc from ds_quayside_comp to place3
    val arcExpArc7_cp = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc7_cp = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc7_cp = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc7_cp = new Arc[colset_CASEID,BindTransProcess]("arc7cp", pplace6, ttrans5_comp, Direction.TtP)
    arc7_cp.setArcExp(arcExpArc7_cp)
    arc7_cp.setTokenToBind(TtoBArc7_cp)
    arc7_cp.setBindToToken(BtoTArc7_cp)
    
    //========================== arc 8 ==========================
    // x (int) input arc from start to ds_yardside_start
    val arcExpArc8_st = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc8_st = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc8_st = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc8_st = new Arc[colset_CASEID,BindTransProcess]("arc8st", pplace5, ttrans6_start, Direction.PtT)
    arc8_st.setIsBase(true)
    arc8_st.setArcExp(arcExpArc8_st)
    arc8_st.setTokenToBind(TtoBArc8_st)
    arc8_st.setBindToToken(BtoTArc8_st)
    
    // (x,y) (int,string) output arc from ds_yardside_start to place2tmp1
    val arcExpArc8_cptmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc8_cptmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc8_cptmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc8_cptmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc8cptmp", pplace5tmp, ttrans6_start, Direction.TtP)
    val addTimeArc8_cptmp = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    arc8_cptmp.setArcExp(arcExpArc8_cptmp)
    arc8_cptmp.setTokenToBind(TtoBArc8_cptmp)
    arc8_cptmp.setBindToToken(BtoTArc8_cptmp)
    arc8_cptmp.setAddTime(addTimeArc8_cptmp)
    
    // (x,y) (int,string) output arc from place2tmp1 to ds_yardside_complete
    val arcExpArc8_sttmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc8_sttmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc8_sttmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc8_sttmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc8sttmp", pplace5tmp, ttrans6_comp, Direction.PtT)
    arc8_sttmp.setArcExp(arcExpArc8_sttmp)
    arc8_sttmp.setTokenToBind(TtoBArc8_sttmp)
    arc8_sttmp.setBindToToken(BtoTArc8_sttmp)
    
    // y (string) input arc from resource to ds_yardside_start
    val arcExpArc8_stres = (inp:Any) => inp match { case y:String => { if(filterResGroup2(y)) y else None  } } //arc2 exp
    val TtoBArc8_stres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc8_stres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc8_stres = new Arc[colset_RES,BindTransProcess]("arc8stres", pplace_res, ttrans6_start, Direction.PtT)
    arc8_stres.setIsBase(true)
    arc8_stres.setArcExp(arcExpArc8_stres)
    arc8_stres.setTokenToBind(TtoBArc8_stres)
    arc8_stres.setBindToToken(BtoTArc8_stres)
    
    // y (string) input arc from ds_yardside_complete to resource
    val arcExpArc8_cpres = (inp:Any) => inp match { case y:String => { if(filterResGroup2(y)) y else None  } } //arc2 exp
    val TtoBArc8_cpres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc8_cpres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc8_cpres = new Arc[colset_RES,BindTransProcess]("arc8cpres", pplace_res, ttrans6_comp, Direction.TtP)
    arc8_cpres.setArcExp(arcExpArc8_cpres)
    arc8_cpres.setTokenToBind(TtoBArc8_cpres)
    arc8_cpres.setBindToToken(BtoTArc8_cpres)
    
    // x (int) output arc from ds_yardside_comp to place3
    val arcExpArc8_cp = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc8_cp = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc8_cp = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc8_cp = new Arc[colset_CASEID,BindTransProcess]("arc8cp", pplace7, ttrans6_comp, Direction.TtP)
    arc8_cp.setArcExp(arcExpArc8_cp)
    arc8_cp.setTokenToBind(TtoBArc8_cp)
    arc8_cp.setBindToToken(BtoTArc8_cp)
    
    //========================== arc 9 ==========================
    // x (int) input arc from start to ld_quayside_start
    val arcExpArc9_st = (inp:Any) => inp match { case x:Int => { x } } //arc1 exp
    val TtoBArc9_st = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc9_st = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc9_st = new Arc[colset_CASEID,BindTransProcess]("arc9st", pplace6, ttrans7_start, Direction.PtT)
    arc9_st.setIsBase(true)
    arc9_st.setArcExp(arcExpArc9_st)
    arc9_st.setTokenToBind(TtoBArc9_st)
    arc9_st.setBindToToken(BtoTArc9_st)
    
    // (x,y) (int,string) output arc from ds_quayside_start to place2tmp1
    val arcExpArc9_cptmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc9_cptmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc9_cptmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc9_cptmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc9cptmp", pplace6tmp, ttrans7_start, Direction.TtP)
    val addTimeArc9_cptmp = (inp:Any) => Math.round(Gaussian(100, 10).draw())
    arc9_cptmp.setArcExp(arcExpArc9_cptmp)
    arc9_cptmp.setTokenToBind(TtoBArc9_cptmp)
    arc9_cptmp.setBindToToken(BtoTArc9_cptmp)
    arc9_cptmp.setAddTime(addTimeArc9_cptmp)
    
    // (x,y) (int,string) output arc from place2tmp1 to ds_quayside_complete
    val arcExpArc9_sttmp = (inp:Any) => inp match { case (x:Int,y:String) => { (x,y)  } } //arc2 exp
    val TtoBArc9_sttmp = (inp:Any) => BindTransProcess(inp match { case (x:Int,y:Any) => Some(x); case _ => None }, inp match { case (x:Any,y:String) => Some(y); case _ => None })
    val BtoTArc9_sttmp = (inp:Any) => inp match { case inp:BindTransProcess => { (inp.x.get,inp.y.get) } }
    val arc9_sttmp = new Arc[colset_CASEIDxRES,BindTransProcess]("arc9sttmp", pplace6tmp, ttrans7_comp, Direction.PtT)
    arc9_sttmp.setArcExp(arcExpArc9_sttmp)
    arc9_sttmp.setTokenToBind(TtoBArc9_sttmp)
    arc9_sttmp.setBindToToken(BtoTArc9_sttmp)
    
    // y (string) input arc from resource to ds_quayside_start
    val arcExpArc9_stres = (inp:Any) => inp match { case y:String => { if(filterResGroup1(y)) y else None  } } //arc2 exp
    val TtoBArc9_stres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc9_stres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc9_stres = new Arc[colset_RES,BindTransProcess]("arc9stres", pplace_res, ttrans7_start, Direction.PtT)
    arc9_stres.setIsBase(true)
    arc9_stres.setArcExp(arcExpArc9_stres)
    arc9_stres.setTokenToBind(TtoBArc9_stres)
    arc9_stres.setBindToToken(BtoTArc9_stres)
    
    // y (string) input arc from ds_quayside_complete to resource
    val arcExpArc9_cpres = (inp:Any) => inp match { case y:String => { if(filterResGroup1(y)) y else None  } } //arc2 exp
    val TtoBArc9_cpres = (inp:Any) => BindTransProcess(None, inp match { case y:String => Some(y); case _ => None })
    val BtoTArc9_cpres = (inp:Any) => inp match { case inp:BindTransProcess => { inp.y.get } }
    val arc9_cpres = new Arc[colset_RES,BindTransProcess]("arc9cpres", pplace_res, ttrans7_comp, Direction.TtP)
    arc9_cpres.setArcExp(arcExpArc9_cpres)
    arc9_cpres.setTokenToBind(TtoBArc9_cpres)
    arc9_cpres.setBindToToken(BtoTArc9_cpres)
    
    // x (int) output arc from ds_quayside_comp to place3
    val arcExpArc9_cp = (inp:Any) => inp match { case x:Int => { x  } } //arc2 exp
    val TtoBArc9_cp = (inp:Any) => BindTransProcess(inp match { case x:Int => Some(x); case _ => None }, None)
    val BtoTArc9_cp = (inp:Any) => inp match { case inp:BindTransProcess => { inp.x.get } }
    val arc9_cp = new Arc[colset_CASEID,BindTransProcess]("arc9cp", pplace7, ttrans7_comp, Direction.TtP)
    arc9_cp.setArcExp(arcExpArc9_cp)
    arc9_cp.setTokenToBind(TtoBArc9_cp)
    arc9_cp.setBindToToken(BtoTArc9_cp)
    ///End PROCESS
    
    //add transitions
    cgraph.addTransition(ttrans1)
    cgraph.addTransition(ttrans2_start)
    cgraph.addTransition(ttrans2_comp)
    cgraph.addTransition(ttrans3_start)
    cgraph.addTransition(ttrans3_comp)
    cgraph.addTransition(ttrans4_start)
    cgraph.addTransition(ttrans4_comp)
    cgraph.addTransition(ttrans5_start)
    cgraph.addTransition(ttrans5_comp)
    cgraph.addTransition(ttrans6_start)
    cgraph.addTransition(ttrans6_comp)
    cgraph.addTransition(ttrans7_start)
    cgraph.addTransition(ttrans7_comp)
    
    //add places
    cgraph.addPlace(pplace1)
    cgraph.addPlace(pplace2)
    cgraph.addPlace(pplace_res)
    cgraph.addPlace(pplace2tmp1)
    cgraph.addPlace(pplace2tmp2)
    cgraph.addPlace(pplace3)
    cgraph.addPlace(pplace3tmp)
    cgraph.addPlace(pplace4)
    cgraph.addPlace(pplace4tmp)
    cgraph.addPlace(pplace5)
    cgraph.addPlace(pplace5tmp)
    cgraph.addPlace(pplace6)
    cgraph.addPlace(pplace6tmp)
    cgraph.addPlace(pplace7)
    
    //add arcs
    cgraph.addArc(arc1)
    cgraph.addArc(arc2)
    cgraph.addArc(arc3)
    cgraph.addArc(arc4_st)
    cgraph.addArc(arc4_cptmp)
    cgraph.addArc(arc4_sttmp)
    cgraph.addArc(arc4_stres)
    cgraph.addArc(arc4_cpres)
    cgraph.addArc(arc4_cp)
    cgraph.addArc(arc5_st)
    cgraph.addArc(arc5_cptmp)
    cgraph.addArc(arc5_sttmp)
    cgraph.addArc(arc5_stres)
    cgraph.addArc(arc5_cpres)
    cgraph.addArc(arc5_cp)
    cgraph.addArc(arc6_st)
    cgraph.addArc(arc6_cptmp)
    cgraph.addArc(arc6_sttmp)
    cgraph.addArc(arc6_stres)
    cgraph.addArc(arc6_cpres)
    cgraph.addArc(arc6_cp)
    cgraph.addArc(arc7_st)
    cgraph.addArc(arc7_cptmp)
    cgraph.addArc(arc7_sttmp)
    cgraph.addArc(arc7_stres)
    cgraph.addArc(arc7_cpres)
    cgraph.addArc(arc7_cp)
    cgraph.addArc(arc8_st)
    cgraph.addArc(arc8_cptmp)
    cgraph.addArc(arc8_sttmp)
    cgraph.addArc(arc8_stres)
    cgraph.addArc(arc8_cpres)
    cgraph.addArc(arc8_cp)
    cgraph.addArc(arc9_st)
    cgraph.addArc(arc9_cptmp)
    cgraph.addArc(arc9_sttmp)
    cgraph.addArc(arc9_stres)
    cgraph.addArc(arc9_cpres)
    cgraph.addArc(arc9_cp)
    
    /*
    val stopCrit = (p: Any) => p match { case p:Place[_] => { 
      	p.getcurrentMarking().multiset.keys.filter(_._1 > 2).size > 0
      } 
    }
    */
    val globtime = GlobalTime(0)
    
    val stopCrit = (globtime: Any) => globtime match { case globtime:GlobalTime => { 
        globtime.time > 3000
      }
    }
    
    Simulator.fastRun(cgraph, stopCrit, globtime, globtime)
  }
}