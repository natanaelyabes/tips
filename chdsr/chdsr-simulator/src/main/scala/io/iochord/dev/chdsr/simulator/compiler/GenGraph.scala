package io.iochord.dev.chdsr.simulator.compiler

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
}