package io.iochord.apps.ips.simulator.compiler

import java.sql.{Connection,DriverManager}
import java.util.Date
import java.text.SimpleDateFormat

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */
class GenGraph {
 
  def create(nTrans:Int):String = {
    
    val sb = new StringBuilder()
    
    sb.append("type colset00000001 = (Int,String)\n")
    sb.append("case class Binding00000001(entity:Option[colset00000001]) extends Bind\n")
    sb.append("val Eval00000001 = (b1:Binding00000001, b2:Binding00000001) => {\n")
    sb.append("\t(b1.entity == b2.entity || b1.entity == None || b2.entity == None)\n")
    sb.append("}\n")
    sb.append("val Merge00000001 = (b1:Binding00000001, b2:Binding00000001) => {\n")
    sb.append("\tval entity = if(b1.entity == None) b2.entity else b1.entity\n")
    sb.append("Binding00000001(entity)")
    sb.append("}\n\n")
      
    for(i <- 1 to nTrans) {
      sb.append("val map0000000" + i + "1 = Map[(colset00000001,Long),Int](  )\n")
      sb.append("val ms0000000" + i + "1 = new Multiset[colset00000001](map0000000" + i + "1)\n")
      sb.append("val place0000000" + i + "1 = new Place(\"place0000000" + i + "1\",\"Tr" + i + "_p1\",ms0000000" + i + "1)\n")
      sb.append("place0000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p1\")))\n")
      sb.append("cgraph.addPlace(place0000000" + i + "1)\n\n")
      sb.append("val map0000000" + i + "2 = Map[(colset00000001,Long),Int](  )\n")
      sb.append("val ms0000000" + i + "2 = new Multiset[colset00000001](map0000000" + i + "2)\n")
      sb.append("val place0000000" + i + "2 = new Place(\"place0000000" + i + "2\",\"Tr" + i + "_p2\",ms0000000" + i + "2)\n")
      sb.append("place0000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p2\")))\n")
      sb.append("cgraph.addPlace(place0000000" + i + "2)\n\n")
      sb.append("val map0000000" + i + "3 = Map[(colset00000001,Long),Int](  )\n")
      sb.append("val ms0000000" + i + "3 = new Multiset[colset00000001](map0000000" + i + "3)\n")
      sb.append("val place0000000" + i + "3 = new Place(\"place0000000" + i + "3\",\"Tr" + i + "_p3\",ms0000000" + i + "3)\n")
      sb.append("place0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p3\")))\n")
      sb.append("cgraph.addPlace(place0000000" + i + "3)\n\n")
      sb.append("val trans0000000" + i + " = new Transition[Binding00000001](\"trans0000000" + i + "\",\"Transition " + i + "\",null,null)\n")
      sb.append("trans0000000" + i + ".setEval(Eval00000001)\n")
      sb.append("trans0000000" + i + ".setMerge(Merge00000001)\n")
      sb.append("trans0000000" + i + ".setOrigin(Map[String,String]((\"Tr" + i + "\",\"Transition " + i + "\")))\n")
      sb.append("cgraph.addTransition(trans0000000" + i + ")\n\n")
      sb.append("val arcexp0000000" + i + "1 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb0000000" + i + "1 = (inp:Any) => Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None })\n")
      sb.append("val bTt0000000" + i + "1 = (b:Binding00000001) => {b.entity.get}\n")
      sb.append("val arc0000000" + i + "1 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "1\",place0000000" + i + "1,trans0000000" + i + ",Direction.PtT)\n")
      sb.append("arc0000000" + i + "1.setIsBase(true)\n")
      sb.append("arc0000000" + i + "1.setArcExp(arcexp0000000" + i + "1)\n")
      sb.append("arc0000000" + i + "1.setTokenToBind(tTb0000000" + i + "1)\n")
      sb.append("arc0000000" + i + "1.setBindToToken(bTt0000000" + i + "1)\n")
      sb.append("arc0000000" + i + "1.setBindToToken(bTt0000000" + i + "1)\n")
      sb.append("arc0000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "1\")))\n")
      sb.append("cgraph.addArc(arc0000000" + i + "1)\n\n")
      sb.append("val arcexp0000000" + i + "2 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb0000000" + i + "2 = (inp:Any) => Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None })\n")
      sb.append("val bTt0000000" + i + "2 = (b:Binding00000001) => {b.entity.get}\n")
      sb.append("val arc0000000" + i + "2 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "2\",place0000000" + i + "2,trans0000000" + i + ",Direction.PtT)\n")
      sb.append("arc0000000" + i + "2.setIsBase(true)\n")
      sb.append("arc0000000" + i + "2.setArcExp(arcexp0000000" + i + "2)\n")
      sb.append("arc0000000" + i + "2.setTokenToBind(tTb0000000" + i + "2)\n")
      sb.append("arc0000000" + i + "2.setBindToToken(bTt0000000" + i + "2)\n")
      sb.append("arc0000000" + i + "2.setBindToToken(bTt0000000" + i + "2)\n")
      sb.append("arc0000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "2\")))\n")
      sb.append("cgraph.addArc(arc0000000" + i + "2)\n\n")
      sb.append("val arcexp0000000" + i + "3 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
      sb.append("val tTb0000000" + i + "3 = (inp:Any) => Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None })\n")
      sb.append("val bTt0000000" + i + "3 = (b:Binding00000001) => {b.entity.get}\n")
      sb.append("val arc0000000" + i + "3 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "3\",place0000000" + i + "3,trans0000000" + i + ",Direction.TtP)\n")
      sb.append("arc0000000" + i + "3.setIsBase(true)\n")
      sb.append("arc0000000" + i + "3.setArcExp(arcexp0000000" + i + "3)\n")
      sb.append("arc0000000" + i + "3.setTokenToBind(tTb0000000" + i + "3)\n")
      sb.append("arc0000000" + i + "3.setBindToToken(bTt0000000" + i + "3)\n")
      sb.append("arc0000000" + i + "3.setBindToToken(bTt0000000" + i + "3)\n")
      sb.append("arc0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "3\")))\n")
      sb.append("cgraph.addArc(arc0000000" + i + "3)\n\n")
    }
    
    sb.toString()
  }
  
  def createPortModel():String = {
    
    val dS2018 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2018-11-01 00:00:00")
    val tS2018 = dS2018.getTime()/1000L;
    
    val sb = new StringBuilder()
    
    sb.append("type colsetVesInfo = Vcall\n")
    
    val url = "jdbc:mysql://localhost:3306/chdsr?verifyServerCertificate=false&useSSL=false&requireSSL=false&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    val username = "root"
    val password = "1234"
    var connection:Connection = null
    
    val sbvseq = new StringBuilder()
    val sbvess = new StringBuilder()
    
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT ship_seq, ship_cd, ata_dt, atd_dt, lod_cnt, dis_cnt, sft_cnt, sp_lt FROM vcall")
      var i = 0
      var bools = rs.next()
      while (bools) {
        val ship_seq = rs.getString("ship_seq")
        val ship_cd = rs.getString("ship_cd")
        val ata_dt = rs.getString("ata_dt")
        val atd_dt = rs.getString("atd_dt")
        val lod_cnt = rs.getString("lod_cnt")
        val dis_cnt = rs.getString("dis_cnt")
        val sft_cnt = rs.getString("sft_cnt")
        val sp_lt = rs.getString("sp_lt")
        
        val data_dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(ata_dt)
        val tata_dt = data_dt.getTime()/1000L
        
        val epochSec = tata_dt - tS2018
        
        if(i < 5) {
          sbvseq.append("((\""+ship_seq+"\","+epochSec+"),1),")
          sbvess.append("((Vcall(\""+ship_seq+"\",\""+ship_cd+"\","+lod_cnt+","+dis_cnt+","+sft_cnt+","+sp_lt+"),0),1),")
          bools = rs.next()
        }
        else {
          sbvseq.append("((\""+ship_seq+"\","+epochSec+"),1)")
          sbvess.append("((Vcall(\""+ship_seq+"\",\""+ship_cd+"\","+lod_cnt+","+dis_cnt+","+sft_cnt+","+sp_lt+"),0),1)")
          bools = false
        }
        //println("ship_seq = %s, ship_cd = %s, ata_dt = %s, atd_dt = %s, lod_cnt = %s, dis_cnt = %s, sft_cnt = %s, sp_lt = %s, timerel = %s".format(ship_seq, ship_cd, ata_dt, atd_dt, lod_cnt, dis_cnt, sft_cnt, sp_lt, epochSec))
        i = i+1
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    
    sb.append("val mapVesInfo = Map[(colsetVesInfo,Long),Int]( "+sbvess.toString()+" )\n")
    sb.append("val msVesInfo = new Multiset[colsetVesInfo](mapVesInfo)\n")
    sb.append("val placeVesInfo = new Place(\"placeVesInfo\",\"Place Vessel Info\",msVesInfo)\n")
    sb.append("placeVesInfo.setOrigin(Map[String,String]((\"Place Vessel Info\",\"placeVesInfo\")))\n")
    sb.append("\n")
    
    sb.append("type colset1 = String\n")
    sb.append("case class Binding00000001(entity:Option[colset1]) extends Bind\n")
    sb.append("val Eval1 = (b1:Binding1, b2:Binding1) => {\n")
    sb.append("\t(b1.entity == b2.entity || b1.entity == None || b2.entity == None)\n")
    sb.append("}\n")
    sb.append("val Merge00000001 = (b1:Binding1, b2:Binding1) => {\n")
    sb.append("\tval entity = if(b1.entity == None) b2.entity else b1.entity\n")
    sb.append("Binding1(entity)")
    sb.append("}\n\n")
    
    sb.append("val mapVes = Map[(colset_VES,Long),Int]( "+sbvseq.toString()+" )\n")
    sb.append("val msVes = new Multiset[colset_VES](mapVes)\n")
    sb.append("val placeVes = new Place(\"placeVes\",\"placeVes\",msVes)\n")
    sb.append("placeVes.setOrigin(Map[String,String]((\"placeVes\",\"pVes\")))\n")
    sb.append("cgraph.addPlace(placetr1_1)\n\n")
    /*
    sb.append("val map0000000" + i + "2 = Map[(colset00000001,Long),Int](  )\n")
    sb.append("val ms0000000" + i + "2 = new Multiset[colset00000001](map0000000" + i + "2)\n")
    sb.append("val place0000000" + i + "2 = new Place(\"place0000000" + i + "2\",\"Tr" + i + "_p2\",ms0000000" + i + "2)\n")
    sb.append("place0000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p2\")))\n")
    sb.append("cgraph.addPlace(place0000000" + i + "2)\n\n")
    sb.append("val map0000000" + i + "3 = Map[(colset00000001,Long),Int](  )\n")
    sb.append("val ms0000000" + i + "3 = new Multiset[colset00000001](map0000000" + i + "3)\n")
    sb.append("val place0000000" + i + "3 = new Place(\"place0000000" + i + "3\",\"Tr" + i + "_p3\",ms0000000" + i + "3)\n")
    sb.append("place0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"p3\")))\n")
    sb.append("cgraph.addPlace(place0000000" + i + "3)\n\n")
    sb.append("val trans0000000" + i + " = new Transition[Binding00000001](\"trans0000000" + i + "\",\"Transition " + i + "\",null,null)\n")
    sb.append("trans0000000" + i + ".setEval(Eval00000001)\n")
    sb.append("trans0000000" + i + ".setMerge(Merge00000001)\n")
    sb.append("trans0000000" + i + ".setOrigin(Map[String,String]((\"Tr" + i + "\",\"Transition " + i + "\")))\n")
    sb.append("cgraph.addTransition(trans0000000" + i + ")\n\n")
    sb.append("val arcexp0000000" + i + "1 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
    sb.append("val tTb0000000" + i + "1 = (inp:Any) => Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None })\n")
    sb.append("val bTt0000000" + i + "1 = (b:Binding00000001) => {b.entity.get}\n")
    sb.append("val arc0000000" + i + "1 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "1\",place0000000" + i + "1,trans0000000" + i + ",Direction.PtT)\n")
    sb.append("arc0000000" + i + "1.setIsBase(true)\n")
    sb.append("arc0000000" + i + "1.setArcExp(arcexp0000000" + i + "1)\n")
    sb.append("arc0000000" + i + "1.setTokenToBind(tTb0000000" + i + "1)\n")
    sb.append("arc0000000" + i + "1.setBindToToken(bTt0000000" + i + "1)\n")
    sb.append("arc0000000" + i + "1.setBindToToken(bTt0000000" + i + "1)\n")
    sb.append("arc0000000" + i + "1.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "1\")))\n")
    sb.append("cgraph.addArc(arc0000000" + i + "1)\n\n")
    sb.append("val arcexp0000000" + i + "2 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
    sb.append("val tTb0000000" + i + "2 = (inp:Any) => Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None })\n")
    sb.append("val bTt0000000" + i + "2 = (b:Binding00000001) => {b.entity.get}\n")
    sb.append("val arc0000000" + i + "2 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "2\",place0000000" + i + "2,trans0000000" + i + ",Direction.PtT)\n")
    sb.append("arc0000000" + i + "2.setIsBase(true)\n")
    sb.append("arc0000000" + i + "2.setArcExp(arcexp0000000" + i + "2)\n")
    sb.append("arc0000000" + i + "2.setTokenToBind(tTb0000000" + i + "2)\n")
    sb.append("arc0000000" + i + "2.setBindToToken(bTt0000000" + i + "2)\n")
    sb.append("arc0000000" + i + "2.setBindToToken(bTt0000000" + i + "2)\n")
    sb.append("arc0000000" + i + "2.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "2\")))\n")
    sb.append("cgraph.addArc(arc0000000" + i + "2)\n\n")
    sb.append("val arcexp0000000" + i + "3 = (inp:Any) => inp match { case entity:colset00000001 => { Some(entity) } }\n")
    sb.append("val tTb0000000" + i + "3 = (inp:Any) => Binding00000001(inp match { case entity:colset00000001 => Some(entity); case _ => None })\n")
    sb.append("val bTt0000000" + i + "3 = (b:Binding00000001) => {b.entity.get}\n")
    sb.append("val arc0000000" + i + "3 = new Arc[colset00000001,Binding00000001](\"arc0000000" + i + "3\",place0000000" + i + "3,trans0000000" + i + ",Direction.TtP)\n")
    sb.append("arc0000000" + i + "3.setIsBase(true)\n")
    sb.append("arc0000000" + i + "3.setArcExp(arcexp0000000" + i + "3)\n")
    sb.append("arc0000000" + i + "3.setTokenToBind(tTb0000000" + i + "3)\n")
    sb.append("arc0000000" + i + "3.setBindToToken(bTt0000000" + i + "3)\n")
    sb.append("arc0000000" + i + "3.setBindToToken(bTt0000000" + i + "3)\n")
    sb.append("arc0000000" + i + "3.setOrigin(Map[String,String]((\"Tr" + i + "\",\"arc0000000" + i + "3\")))\n")
    sb.append("cgraph.addArc(arc0000000" + i + "3)\n\n")
    */
    
    sb.toString()
  }
}