package io.iochord.apps.ips.model.converter.sbp2cpn;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.converter.Converter;
import io.iochord.apps.ips.model.sbpnet.v1.Connector;
import io.iochord.apps.ips.model.sbpnet.v1.Data;
import io.iochord.apps.ips.model.sbpnet.v1.Sbpnet;
import io.iochord.apps.ips.model.sbpnet.v1.components.Activity;
import io.iochord.apps.ips.model.sbpnet.v1.components.Branch;
import io.iochord.apps.ips.model.sbpnet.v1.components.BranchGate;
import io.iochord.apps.ips.model.sbpnet.v1.components.DataTable;
import io.iochord.apps.ips.model.sbpnet.v1.components.Function;
import io.iochord.apps.ips.model.sbpnet.v1.components.Generator;
import io.iochord.apps.ips.model.sbpnet.v1.components.Monitor;
import io.iochord.apps.ips.model.sbpnet.v1.components.ObjectType;
import io.iochord.apps.ips.model.sbpnet.v1.components.Queue;
import io.iochord.apps.ips.model.sbpnet.v1.components.Resource;
import io.iochord.apps.ips.model.sbpnet.v1.components.Start;
import io.iochord.apps.ips.model.sbpnet.v1.components.Stop;

/**
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 *
 */
public class Sbpnet2CpnscalaBiConverter implements Converter<Sbpnet, String> {
	
	class KeyElement {
		final static String type = "Type";
		final static String place = "Place";
		final static String transition = "Transition";
		final static String guard = "Guard";
		final static String action = "Action";
		final static String actionfun = "ActionFun";
		final static String actionTtB = "ActionTtB";
		final static String actionBtT = "ActionBtT";
		final static String binding = "Binding";
		final static String eval = "Eval";
		final static String merge = "Merge";
		final static String arc = "Arc";
		final static String arcexp = "ArcExp";
		final static String TtB = "TtB";
		final static String BtT = "BtT";
		final static String addTime = "addTime";
	}
	
	StringBuilder factory = new StringBuilder();
	
	private Map<String, String> objecttypes = new LinkedHashMap<>();
	private Map<String, String> generators = new LinkedHashMap<>();
	private Map<String, String> resources = new LinkedHashMap<>();
	private Map<String, String> queues = new LinkedHashMap<>();
	private Map<String, String> placeshub = new LinkedHashMap<>();
	
	private Map<String, Integer> counters = new LinkedHashMap<>();
	
	private String getCounter(String clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return String.format("%8s", counters.get(clazz)).replace(' ', '0');
	}
	
	public String addPlace(String hubid, String name, String type, String initialMarking, String origin) {
		String counter = getCounter(KeyElement.place);
		String mapid = "map"+counter;
		String multisetid = "ms"+counter;
		String placeid = "place"+counter;
		
		if(hubid != null)
			placeshub.put(hubid, placeid);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val "+mapid+" = Map[("+type+",Long),Int]( "+initialMarking+" )\n" );
		placefactory.append( "val "+multisetid+" = new Multiset["+type+"]("+mapid+")\n" );
		placefactory.append( "val "+placeid+" = new Place(\""+placeid+"\",\""+name+"\","+multisetid+")\n" );
		placefactory.append( placeid+".setOrigin(Map[String,String]((\""+origin+"\",\""+name+"\")))\n" );
		placefactory.append( "cgraph.addPlace("+placeid+")\n" );
		placefactory.append("\n");
		
		factory.append(placefactory.toString());
		
		return placeid;
	}
	
	public String addTransition(String name, String guard, String action, String classbinding, String eval, String merge, String origin) {
		String counter = getCounter(KeyElement.transition);
		String transitionid = "trans"+counter;
		
		if(guard == null)
			guard = "null";
		if(action == null)
			action = "null";
		
		StringBuilder transfactory = new StringBuilder();
		transfactory.append( "val "+transitionid+" = new Transition["+classbinding+"](\""+transitionid+"\",\""+name+"\","+guard+","+action+")\n" );
		transfactory.append( transitionid+".setEval("+eval+")\n" );
		transfactory.append( transitionid+".setMerge("+merge+")\n" );
		transfactory.append( transitionid+".setOrigin(Map[String,String]((\""+origin+"\",\""+name+"\")))\n" );
		transfactory.append( "cgraph.addTransition("+transitionid+")\n" );
		transfactory.append("\n");
		
		factory.append(transfactory.toString());
		
		return transitionid;
	}
	
	public String addGuard(String classbinding, String guarddef) {
		String counter = getCounter(KeyElement.guard);
		String guardid = "Guard"+counter;
		String bindguardexpid = "BindGuard"+counter;
		
		StringBuilder guardfactory = new StringBuilder();
		guardfactory.append( "val "+guardid+" = new Guard["+classbinding+"]()\n" );
		guardfactory.append( "val "+bindguardexpid+" = (b:"+classbinding+") => {"+guarddef+"}\n" );
		guardfactory.append( guardid+".setGuardBind("+bindguardexpid+")\n" );
		factory.append(guardfactory.toString());
		
		return guardid;
	}
	
	public String addActionTtB(String classbinding, String TtBdef) {
		String counter = getCounter(KeyElement.actionTtB);
		String TtBid = "ActionTtB"+counter;
		
		StringBuilder TtBfactory = new StringBuilder();
		TtBfactory.append( "val "+TtBid+" = (inp:Any) => "+classbinding+"("+TtBdef+")\n" );
		
		factory.append(TtBfactory.toString());
		
		return TtBid;
	}
	
	public String addActionBtT(String classbinding, String BtTdef) {
		String counter = getCounter(KeyElement.actionBtT);
		String BtTid = "ActionBtT"+counter;
		
		StringBuilder BtTfactory = new StringBuilder();
		BtTfactory.append( "val "+BtTid+" = (b:"+classbinding+") => {"+BtTdef+"}\n" );
		
		factory.append(BtTfactory.toString());
		
		return BtTid;
	}
	
	public String addAction(String classbinding, String actionfundef) {
		String counter = getCounter(KeyElement.action);
		String actionid = "action"+counter;
		String actionfunid = "actionFun"+counter;
		String actionfun = "def "+actionfunid+"(b:"+classbinding+"):"+classbinding+" = { "+actionfundef+" }";
		factory.append( actionfun+"\n" );
		factory.append("\n");
		
		StringBuilder actionfactory = new StringBuilder();
		actionfactory.append( "val "+actionid+" = new Action["+classbinding+"]()\n" );
		actionfactory.append( actionid+".setActionFun("+actionfunid+")\n" );
		actionfactory.append("\n");
		
		factory.append(actionfactory.toString());
		
		return actionid;
	}
	
	public String addBindingClass(String classdef) {
		String counter = getCounter(KeyElement.binding);
		String bindingid = "Binding"+counter;
		
		StringBuilder cBindingfactory = new StringBuilder();
		cBindingfactory.append( "case class "+bindingid+"(").append(classdef).append(") extends Bind\n" );
		
		factory.append(cBindingfactory.toString());
		
		return bindingid;
	}
	
	public String addEval(String evaldef, String classbinding) {
		String counter = getCounter(KeyElement.eval);
		String evalid = "Eval"+counter;
		
		StringBuilder evalfactory = new StringBuilder();
		evalfactory.append( "val "+evalid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		evalfactory.append( "\t"+evaldef+"\n" );
		evalfactory.append( "}\n" );
		
		factory.append(evalfactory.toString());
		
		return evalid;
	}
	
	public String addMerge(String mergedef, String classbinding, String mergeassign) {
		String counter = getCounter(KeyElement.merge);
		String mergeid = "Merge"+counter;
		
		StringBuilder mergefactory = new StringBuilder();
		mergefactory.append( "val "+mergeid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		mergefactory.append( "\t"+mergedef+"\n" );
		mergefactory.append( "\t"+classbinding+"("+mergeassign+")\n" );
		mergefactory.append( "}\n" );
		
		factory.append(mergefactory.toString());
		
		return mergeid;
	}
	
	public String addArc(String placeid, String transitionid, String direction, String type, String classbinding, String arcexp, String TtB, String BtT, String addTime, String noToken, boolean isBase, String origin) {
		String counter = getCounter(KeyElement.arc);
		String arcid = "arc"+counter;
		
		StringBuilder arcfactory = new StringBuilder();
		arcfactory.append( "val "+arcid+" = new Arc["+type+","+classbinding+"](\""+arcid+"\","+placeid+","+transitionid+",Direction."+direction+")\n" );
		arcfactory.append( arcid+".setIsBase("+isBase+")\n" );
		arcfactory.append( arcid+".setArcExp("+arcexp+")\n" );
		arcfactory.append( arcid+".setTokenToBind("+TtB+")\n" );
		arcfactory.append( arcid+".setBindToToken("+BtT+")\n" );
		arcfactory.append( arcid+".setBindToToken("+BtT+")\n" );
		if(addTime != null)
			arcfactory.append( arcid+".setAddTime("+addTime+")\n" );
		if(noToken != null)
			arcfactory.append( arcid+".setNoTokArcExp("+noToken+")\n" );
		arcfactory.append( arcid+".setOrigin(Map[String,String]((\""+origin+"\",\""+arcid+"\")))\n" );
		arcfactory.append( "cgraph.addArc("+arcid+")\n" );
		arcfactory.append("\n");
		
		factory.append(arcfactory.toString());
		
		return arcid;
	}
	
	public String addArcExp(String arcexpdef) {
		String counter = getCounter(KeyElement.arcexp);
		String arcexpid = "arcexp"+counter;
		
		StringBuilder arcexpfactory = new StringBuilder();
		arcexpfactory.append( "val "+arcexpid+" = (inp:Any) => inp match { "+arcexpdef+" }\n" );
		
		factory.append(arcexpfactory.toString());
		
		return arcexpid;
	}
	
	public String addTtB(String classbinding, String TtBdef) {
		String counter = getCounter(KeyElement.TtB);
		String TtBid = "tTb"+counter;
		
		StringBuilder TtBfactory = new StringBuilder();
		TtBfactory.append( "val "+TtBid+" = (inp:Any) => "+classbinding+"("+TtBdef+")\n" );
		
		factory.append(TtBfactory.toString());
		
		return TtBid;
	}
	
	public String addBtT(String classbinding, String BtTdef) {
		String counter = getCounter(KeyElement.BtT);
		String BtTid = "bTt"+counter;
		
		StringBuilder BtTfactory = new StringBuilder();
		BtTfactory.append( "val "+BtTid+" = (b:"+classbinding+") => {"+BtTdef+"}\n" );
		
		factory.append(BtTfactory.toString());
		
		return BtTid;
	}
	
	public String addAddedTime(String classbinding, String addedTimedef) {
		String counter = getCounter(KeyElement.addTime);
		String addTimeid = "addTime"+counter;
		
		StringBuilder addTimefactory = new StringBuilder();
		addTimefactory.append( "val "+addTimeid+" = (b:"+classbinding+") => {"+addedTimedef+"}\n" );
		
		factory.append(addTimefactory.toString());
		
		return addTimeid;
	}
	
	public String convert(Sbpnet snet) {
		for (String pi : snet.getPages().keySet()) {
			io.iochord.apps.ips.model.sbpnet.v1.Page p = snet.getPages().get(pi);
			
			String CaseData = "case class CaseData(name:String,age:Int)";
			factory.append(CaseData+"\n");
			factory.append("\n");
			
			String entTypeId = "colset"+getCounter(KeyElement.type);
			objecttypes.put("entityTypeId", entTypeId);
			factory.append("type "+entTypeId+" = (Int,String)\n");
			factory.append("\n");
			
			String dataTypeId = "colset"+getCounter(KeyElement.type);
			factory.append("type "+dataTypeId+" = (Int,String,CaseData)\n");
			factory.append("\n");
			
			String b_entTypeId = addBindingClass( "entity:Option["+entTypeId+"]" );
			String e_entTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None)", b_entTypeId);
			String m_entTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;", b_entTypeId, "entity");
			
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				if (d instanceof ObjectType) {
					ObjectType ot = (ObjectType) d;
					String typeId = "colset"+getCounter(KeyElement.type);
					objecttypes.put(ot.getId(), typeId);
					factory.append("type "+typeId+" = Int\n");
					factory.append("\n");
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					
					String typeId = objecttypes.get(dg.getObjectType().getId());
					
					String p_dgp1 = addPlace(null, dg.getLabel()+"_dgp1", typeId, "((1,0),1)", dg.getId());
					String p_dgp2 = addPlace(dg.getId()+ "_start_0",dg.getLabel()+"_dgp2", entTypeId, "", dg.getId());
					String p_dgpData = addPlace(null,dg.getLabel()+"_dgpData", dataTypeId, "", dg.getId());
					
					String b_dgt1 = addBindingClass( "tid:Option[Int],gid:Option[String],data:Option[CaseData]" );
					String e_dgt1 = addEval("(b1.tid == b2.tid || b1.tid == None || b2.tid == None) && (b1.gid == b2.gid || b1.gid == None || b2.gid == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", b_dgt1);
					String m_dgt1 = addMerge("val tid = if(b1.tid == None) b2.tid else b1.tid;val gid = if(b1.gid == None) b2.gid else b1.gid;val data = if(b1.data == None) b2.data else b1.data;", b_dgt1, "tid,gid,data");
					
					String guard = addGuard(b_dgt1, "b.tid.get < "+dg.getMaxArrival());
					String action = addAction(b_dgt1,
							"val r = new java.util.Random()\n"
							+ "val rint = r.nextInt();val gid = \""+dg.getId()+"\"\n"
							+ "val data = CaseData(\"nama\"+rint,rint)\n"
							+ b_dgt1+"(b.tid,Some(gid),Some(data))");
					
					String t_dgt1 = addTransition(dg.getLabel()+"_dgt1", guard, action, b_dgt1, e_dgt1, m_dgt1, "Map[String,String]("+dg.getId()+","+dg.getLabel()+"_dgt1)");
					
					addArc(p_dgp1, t_dgt1, "PtT", typeId, b_dgt1, addArcExp("case tid:"+typeId+" => { Some(tid) }"), addTtB(b_dgt1,"inp match { case tid:"+typeId+" => Some(tid); case _ => None }, None, None"), addBtT(b_dgt1,"b.tid.get"), null, null, true, dg.getId());
					addArc(p_dgp1, t_dgt1, "TtP", typeId, b_dgt1, addArcExp("case tid:"+typeId+" => { Some(tid+1) }"), addTtB(b_dgt1,"inp match { case tid:"+typeId+" => Some(tid); case _ => None }, None, None"), addBtT(b_dgt1,"b.tid.get"), addAddedTime(b_dgt1,dg.getExpression()), null, false, dg.getId());
					addArc(p_dgp2, t_dgt1, "TtP", entTypeId, b_dgt1, addArcExp("case (tid:"+typeId+",gid:String) => { Some(tid,gid) }"), addTtB(b_dgt1,"inp match { case (tid:"+typeId+",gid:Any) => Some(tid); case _ => None }, inp match { case (tid:Any,gid:String) => Some(gid); case _ => None }, None"), addBtT(b_dgt1,"(b.tid.get,b.gid.get)"), null, null, false, dg.getId());
					addArc(p_dgpData, t_dgt1, "TtP", dataTypeId, b_dgt1, addArcExp("case (tid:"+typeId+",gid:String,data:CaseData) => { Some(tid,gid,data) }"), addTtB(b_dgt1,"inp match { case (tid:"+typeId+",gid:Any,data:Any) => Some(tid); case _ => None }, inp match { case (tid:Any,gid:String,data:Any) => Some(gid); case _ => None }, inp match { case (tid:Any,gid:Any,data:CaseData) => Some(data); case _ => None }"), addBtT(b_dgt1,"(b.tid.get,b.gid.get,b.data.get)"), null, null, false, dg.getId());
				}
				if (d instanceof Function) {
					Function f = (Function) d;
					//Page fpage =  converter.addPage(net, "FUNCTION " + f.getLabel()); 
					// TODO:
				}
				if (d instanceof Queue) {
					Queue q = (Queue) d;
					//Page qpage =  converter.addPage(net, "QUEUE " + q.getLabel()); 
					// TODO:
				}
				if (d instanceof Resource) {
					Resource r = (Resource) d;
					//Page rpage =  converter.addPage(net, "RESOURCE " + r.getLabel()); 
					// TODO:
				}
				if (d instanceof DataTable) {
					DataTable dt = (DataTable) d;
					//Page dtpage =  converter.addPage(net, "DATATABLE " + dt.getLabel()); 
					// TODO:
				}
			}
			
			// Convert Nodes
			for (String ni : p.getNodes().keySet()) {
				io.iochord.apps.ips.model.sbpnet.v1.Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
					if (na.getGenerator() == null) {
						addPlace(na.getId()+"_start_0", na.getLabel()+"_nap1", entTypeId, "", na.getId());
					} else {
						placeshub.put(na.getId()+"_start_0",placeshub.get(na.getGenerator().getId()+"_start_0"));
					}
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					
					addPlace(no.getId()+"_end_0", no.getLabel() + "_nop1", entTypeId, "", no.getId());
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					String p_nap1 = addPlace(na.getId()+"_end_0", na.getLabel() + "_nap1", entTypeId, "", na.getId());
					String t_natstart = addTransition(na.getLabel()+"_natstart", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId());
					addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, na.getId());
					String p_nap2 = addPlace(null, na.getLabel() + "_nap2", entTypeId, "", na.getId());
					addArc(p_nap2, t_natstart, "TtP", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeParameter()), null, false, na.getId());
					String t_natend = addTransition(na.getLabel()+"_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId());
					addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, na.getId());
					String p_nap3 = addPlace(na.getId()+"_start_0", na.getLabel() + "_nap3", entTypeId, "", na.getId());
					addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, false, na.getId());
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					//System.out.println("Branch "+b.getId());
					
					String bt = null, bp = null;
					
					if (b.getGate() == BranchGate.AND) { 
						bt = addTransition(b.getLabel()+"_bt", null, null, b_entTypeId, e_entTypeId, m_entTypeId, b.getId());
					} else if (b.getGate() == BranchGate.XOR) {
						bp = addPlace(null, b.getLabel() + "_bp", entTypeId, "", b.getId());
					}
						
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getTarget() != b) continue;
						String p_bpis = addPlace(b.getId()+"_end_"+i, b.getLabel() + "_bpi" + i + "s", entTypeId, "", b.getId());
						
						if (b.getGate() == BranchGate.AND) {
							addArc(p_bpis, bt, "PtT", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, b.getId());
						}
						else {
							String t_silent = addTransition(b.getLabel()+"_temps_"+i, null, null, b_entTypeId, e_entTypeId, m_entTypeId, b.getId());
							addArc(p_bpis, t_silent, "PtT", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, b.getId());
							addArc(bp, t_silent, "TtP", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, false, b.getId());
						}
						
						//System.out.println("Source "+c.getSource().getId());
						i++;
					}
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getSource() != b) continue;
						String p_bpos = addPlace(b.getId()+"_start_"+i, b.getLabel() + "_bpo" + i + "s", entTypeId, "", b.getId());
						
						if (b.getGate() == BranchGate.AND) {
							addArc(p_bpos, bt, "TtP", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, false, b.getId());
						}
						else {
							String t_silent = addTransition(b.getLabel()+"_temps_"+i, null, null, b_entTypeId, e_entTypeId, m_entTypeId, b.getId());
							addArc(bp, t_silent, "PtT", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, b.getId());
							addArc(p_bpos, t_silent, "TtP", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, false, b.getId());
							
						}
						
						//System.out.println("Target "+c.getTarget().getId());
						i++;
					}
				}
				if (n instanceof Monitor) {
					Monitor m = (Monitor) n;
					//Page mpage =  converter.addPage(net, "MONITOR " + m.getLabel()); 
					// TODO:
				}
			}
			// Convert Arcs
			for (String s : p.getConnectors().keySet()) {
				Connector c = p.getConnectors().get(s);
				
				String source = placeshub.get(c.getSource().getId()+"_start_"+c.getSourceIndex());
				String target = placeshub.get(c.getTarget().getId()+"_end_"+c.getTargetIndex());
				
				String t_silent = addTransition("", null, null, b_entTypeId, e_entTypeId, m_entTypeId, "Connector");
				addArc(source, t_silent, "PtT", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, "Connector");
				addArc(target, t_silent, "TtP", entTypeId, b_entTypeId, addArcExp("case entity:"+entTypeId+" => { Some(entity) }"), addTtB(b_entTypeId,"inp match { case entity:"+entTypeId+" => Some(entity); case _ => None }"), addBtT(b_entTypeId,"b.entity.get"), null, null, true, "Connector");
			}
		}
		
		return factory.toString();
	}
	
	public Sbpnet revert(String cgraph) {
		return null;
	}
	
}
