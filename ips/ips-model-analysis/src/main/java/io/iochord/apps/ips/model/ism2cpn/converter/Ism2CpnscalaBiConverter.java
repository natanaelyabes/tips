package io.iochord.apps.ips.model.ism2cpn.converter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.converter.Converter;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ActivityType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.nodes.Monitor;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;

/**
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 *
 */
public class Ism2CpnscalaBiConverter implements Converter<IsmGraph, Ism2CpnscalaModel> {
	
	class KeyElement {
		final static String type = "Type";
		final static String resource = "Resource";
		final static String queue = "Queue";
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
//	private Map<String, String> generators = new LinkedHashMap<>();
//	private Map<String, String> resources = new LinkedHashMap<>();
//	private Map<String, String> queues = new LinkedHashMap<>();
	private Map<String, String> placeshub = new LinkedHashMap<>();
	private Map<String, Boolean> transIfExist = new HashMap<>();
	
	private Map<String, Integer> counters = new LinkedHashMap<>();
	
	private String getCounter(String clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return String.format("%2s", counters.get(clazz)).replace(' ', '0');
	}
	
	public String addPlace(String hubid, String name, String type, String initialMarking, String origin, String placeId) {
		String counter = getCounter(KeyElement.place);
		String mapid = "map"+counter;
		String multisetid = "ms"+counter;
		String placeidClean = placeId == null ? "p_"+counter : cleanId("p_", placeId);
		
		if(hubid != null)
			placeshub.put(hubid, placeidClean);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val "+mapid+" = Map[("+type+",Long),Int]( "+initialMarking+" )\n" );
		placefactory.append( "val "+multisetid+" = new Multiset["+type+"]("+mapid+")\n" );
		placefactory.append( "val "+placeidClean+" = new Place(\""+placeidClean+"\",\""+name+"\","+multisetid+")\n" );
		placefactory.append( placeidClean+".setOrigin(Map[String,String]((\"origin\",\""+origin+"\"),(\"role\",\""+name+"\")))\n" );
		placefactory.append( "cgraph.addPlace("+placeidClean+")\n" );
		placefactory.append("\n");
		
		factory.append(placefactory.toString());
		
		return placeidClean;
	}
	
	public String addTransition(String name, String guard, String action, String classbinding, String eval, String merge, String origin, String transId) {
		String counter = getCounter(KeyElement.transition);
		String transidClean = transId == null ? "t_"+counter : cleanId("t_", transId);
		
		if(guard == null)
			guard = "null";
		if(action == null)
			action = "null";
		
		StringBuilder transfactory = new StringBuilder();
		transfactory.append( "val "+transidClean+" = new Transition["+classbinding+"](\""+transidClean+"\",\""+name+"\","+guard+","+action+")\n" );
		transfactory.append( transidClean+".setEval("+eval+")\n" );
		transfactory.append( transidClean+".setMerge("+merge+")\n" );
		transfactory.append( transidClean+".setOrigin(Map[String,String]((\"origin\",\""+origin+"\"),(\"role\",\""+name+"\")))\n" );
		transfactory.append( "cgraph.addTransition("+transidClean+")\n" );
		transfactory.append("\n");
		
		factory.append(transfactory.toString());
		
		return transidClean;
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
		cBindingfactory.append( "case class "+bindingid+"(").append(classdef).append(")\n" );
		
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
	
	public String addArc(String placeid, String transitionid, String direction, String type, String classbinding, String TtB, String BtT, String addTime, String noToken, boolean isBase, String origin) {
		String counter = getCounter(KeyElement.arc);
		String arcid = "arc"+counter;
		
		StringBuilder arcfactory = new StringBuilder();
		arcfactory.append( "val "+arcid+" = new Arc["+type+","+classbinding+"](\""+arcid+"\","+placeid+","+transitionid+",Direction."+direction+")\n" );
		arcfactory.append( arcid+".setIsBase("+isBase+")\n" );
		if(isBase && TtB != null)
			arcfactory.append( arcid+".setTokenToBind("+TtB+")\n" );
		arcfactory.append( arcid+".setBindToToken("+BtT+")\n" );
		if(addTime != null)
			arcfactory.append( arcid+".setAddTime("+addTime+")\n" );
		if(noToken != null)
			arcfactory.append( arcid+".setNoTokArcExp("+noToken+")\n" );
		arcfactory.append( arcid+".setOrigin(Map[String,String]((\"origin\",\""+origin+"\"),(\"role\",\""+arcid+"\")))\n" );
		arcfactory.append( "cgraph.addArc("+arcid+")\n" );
		arcfactory.append("\n");
		
		factory.append(arcfactory.toString());
		
		return arcid;
	}
	
	public String addTtB(String classbinding, String type, String inverse, String TtBdef) {
		String counter = getCounter(KeyElement.TtB);
		String TtBid = "tTb"+counter;
		
		StringBuilder TtBfactory = new StringBuilder();
		TtBfactory.append( "val "+TtBid+" = (t:"+type+") => { try { val "+inverse+" = t;Some("+classbinding+"("+TtBdef+")) } catch { case e: Exception => None } }\n" );
		
		factory.append(TtBfactory.toString());
		
		return TtBid;
	}
	
	public String addBtT(String classbinding, String type, String BtTdef) {
		String counter = getCounter(KeyElement.BtT);
		String BtTid = "bTt"+counter;
		
		StringBuilder BtTfactory = new StringBuilder();
		BtTfactory.append( "val "+BtTid+" = (b:"+classbinding+") => {"+BtTdef+"}:"+type+"\n" );
		
		factory.append(BtTfactory.toString());
		
		return BtTid;
	}
	
	public String addAddedTime(String classbinding, String addedTimedef) {
		if(addedTimedef.length() <= 1)
			addedTimedef = "0L";
		
		String counter = getCounter(KeyElement.addTime);
		String addTimeid = "addTime"+counter;
		
		StringBuilder addTimefactory = new StringBuilder();
		addTimefactory.append( "val "+addTimeid+" = (b:"+classbinding+") => {"+addedTimedef+"}\n" );
		
		factory.append(addTimefactory.toString());
		
		return addTimeid;
	}
	
	public String createResourcePlace(String resId, String typecaseId) {
		String residClean = cleanId("r_", resId);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val map"+residClean+" = Map[(Resource["+typecaseId+"],Long),Int]()\n" );
		placefactory.append( "val ms"+residClean+" = new Multiset[Resource["+typecaseId+"]](map"+residClean+")\n" );
		placefactory.append( "val "+residClean+" = new Place(\""+residClean+"\",\"Resource Place\",ms"+residClean+")\n" );
		placefactory.append( residClean+".setOrigin(Map[String,String]((\"origin\",\""+resId+"\"),(\"role\",\"_resp\")))\n" );
		placefactory.append( "cgraph.addPlace("+residClean+")\n" );
		placefactory.append("\n");
		
		factory.append(placefactory.toString());
		
		return residClean;
	}
	
	public void addResource(String resId, String name, int numbofresource) {
		String residClean = cleanId("r_", resId);
		
		StringBuilder resfactory = new StringBuilder();
		for(int i=1; i<=numbofresource; i++) {
			String residInScala = residClean+"_"+i;
			resfactory.append( "val "+residInScala+" = new Resource(\""+residInScala+"\", \""+name+"\", 0L)\n" );
			resfactory.append( residClean+".addTokenWithTime( ("+residInScala+",0L), 1)\n" );
		}
		
		factory.append(resfactory.toString());
	}
	
	public void addQueue(String typeid, String ltypeid, String cb, String ev, String mg, Queue queue, String origin) {
		String qidClean = cleanId("q_", queue.getId());
		String listid = "list_"+qidClean;
		
		factory.append("\n val "+listid+" = List["+typeid+"]() \n");
		String pendqueue = addPlace(null, "_qendp", ltypeid, "(("+listid+",0),1)", origin, queue.getId()+"_qendp");
		
		String guarddef = "b.queue.get.length < "+queue.getSize();
		String guard = queue.getSize() > 0 ? addGuard(cb, guarddef) : null;
		
		String tstartqueue = addTransition("_qstt", guard, null, cb, ev, mg, origin, queue.getId()+"_qstt");
		
		addArc(pendqueue, tstartqueue, "PtT", ltypeid, cb, addTtB(cb, ltypeid, "queue", "None, Some(queue)"), addBtT(cb, ltypeid, "b.queue.get"), null, null, true, origin);
		
		if(queue.getType() == Queue.QUEUE_TYPE.FIFO)
			addArc(pendqueue, tstartqueue, "TtP", ltypeid, cb, null, addBtT(cb, ltypeid, "b.entity.get::b.queue.get"), null, null, false, origin);
		else
			addArc(pendqueue, tstartqueue, "TtP", ltypeid, cb, null, addBtT(cb, ltypeid, "b.queue.get:::List(b.entity.get)"), null, null, false, origin);
	}
	
	public Ism2CpnscalaModel convert(IsmGraph snet) {
		Ism2CpnscalaModel result = new Ism2CpnscalaModel();
		result.setOriginalModel(snet);
		for (String pi : snet.getPages().keySet()) {
			io.iochord.apps.ips.model.ism.v1.Page p = snet.getPages().get(pi);
			
			String CaseData = "case class CaseData(atr1:String,atr2:Int)";
			factory.append(CaseData+"\n");
			factory.append("\n");
			
			String dataTypeId = "colset"+getCounter(KeyElement.type);
			factory.append("type "+dataTypeId+" = ((Int,String),CaseData)\n");
			factory.append("\n");
			
			String entTypeId = "colset"+getCounter(KeyElement.type);
			objecttypes.put("entityTypeId", entTypeId);
			factory.append("type "+entTypeId+" = (Int,String)\n");
			factory.append("\n");
			
			String b_entTypeId = addBindingClass( "entity:Option["+entTypeId+"]" );
			String e_entTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None)", b_entTypeId);
			String m_entTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;", b_entTypeId, "entity");
			
			String entBrTypeId = "colset"+getCounter(KeyElement.type);
			objecttypes.put("entityBrTypeId", entBrTypeId);
			factory.append("type "+entBrTypeId+" = ((Int,String),Double)\n");
			factory.append("\n");
			
			String b_entBrTypeId = addBindingClass( "entity:Option["+entTypeId+"],cond:Option[Double]" );
			String e_entBrTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.cond == b2.cond || b1.cond == None || b2.cond == None)", b_entBrTypeId);
			String m_entBrTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val cond = if(b1.cond == None) b2.cond else b1.cond;", b_entBrTypeId, "entity,cond");
			
			String qentTypeId = "colset"+getCounter(KeyElement.type);
			objecttypes.put("qentityTypeId", qentTypeId);
			factory.append("type "+qentTypeId+" = List["+entTypeId+"]\n");
			factory.append("\n");
			
			String b_qentTypeId = addBindingClass( "entity:Option["+entTypeId+"], queue:Option["+qentTypeId+"]" );
			String e_qentTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", b_qentTypeId);
			String m_qentTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;val queue = if(b1.queue == None) b2.queue else b1.queue;", b_qentTypeId, "entity,queue");
			
			String entResTypeId = "colset"+getCounter(KeyElement.type);
			objecttypes.put("entityResTypeId", entResTypeId);
			factory.append("type "+entResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"])\n");
			factory.append("\n");
			
			String b_entResTypeId = addBindingClass( "entity:Option["+entTypeId+"], resource:Option[Resource["+entTypeId+"]]" );
			String e_entResTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None)", b_entResTypeId);
			String m_entResTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource;", b_entResTypeId, "entity,resource");
			
			String qentResTypeId = "colset"+getCounter(KeyElement.type);
			objecttypes.put("entityResTypeId", qentResTypeId);
			factory.append("type "+qentResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"], List["+entTypeId+"])\n");
			factory.append("\n");
			
			String b_qentResTypeId = addBindingClass( "entity:Option["+entTypeId+"], resource:Option[Resource["+entTypeId+"]], queue:Option[List["+entTypeId+"]]" );
			String e_qentResTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", b_qentResTypeId);
			String m_qentResTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource; val queue = if(b1.queue == None) b2.queue else b1.queue;", b_qentResTypeId, "entity,resource,queue");
			
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				result.getConversionMap().put(d.getId(), d);
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
					
					String p_dgp1 = addPlace(null, "_dgp1", typeId, "((1,0),1)", dg.getId(), null);
					String p_dgp2 = addPlace(dg.getId()+ "_start_0", "_dgp2", entTypeId, "", dg.getId(), null);
					String p_dgpData = addPlace(null, "_dgpData", dataTypeId, "", dg.getId(), null);
					
					String b_dgt1 = addBindingClass( "tid:Option[Int],gid:Option[String],data:Option[CaseData]" );
					String e_dgt1 = addEval("(b1.tid == b2.tid || b1.tid == None || b2.tid == None) && (b1.gid == b2.gid || b1.gid == None || b2.gid == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", b_dgt1);
					String m_dgt1 = addMerge("val tid = if(b1.tid == None) b2.tid else b1.tid;val gid = if(b1.gid == None) b2.gid else b1.gid;val data = if(b1.data == None) b2.data else b1.data;", b_dgt1, "tid,gid,data");
					
					String guard = addGuard(b_dgt1, "b.tid.get <= "+dg.getMaxArrival());
					String action = addAction(b_dgt1,
							"val r = new java.util.Random()\n"
							+ "val rint = r.nextInt();val gid = \""+dg.getId()+"\"\n"
							+ "val data = CaseData(\"atr\"+rint,rint)\n"
							+ b_dgt1+"(b.tid,Some(gid),Some(data))");
					
					String t_dgt1 = addTransition("_dgt1", guard, action, b_dgt1, e_dgt1, m_dgt1, dg.getId(), null);
					
					addArc(p_dgp1, t_dgt1, "PtT", typeId, b_dgt1, addTtB(b_dgt1, typeId, "tid", "Some(tid), None, None"), addBtT(b_dgt1, typeId, "b.tid.get"), null, null, true, dg.getId());
					addArc(p_dgp1, t_dgt1, "TtP", typeId, b_dgt1, null, addBtT(b_dgt1, typeId, "b.tid.get + 1"), addAddedTime(b_dgt1,dg.getExpression()), null, false, dg.getId());
					addArc(p_dgp2, t_dgt1, "TtP", entTypeId, b_dgt1, null, addBtT(b_dgt1, entTypeId, "(b.tid.get,b.gid.get)"), null, null, false, dg.getId());
					addArc(p_dgpData, t_dgt1, "TtP", dataTypeId, b_dgt1, null, addBtT(b_dgt1, dataTypeId, "((b.tid.get,b.gid.get),b.data.get)"), null, null, false, dg.getId());
					
				}
				if (d instanceof Function) {
//					Function f = (Function) d;
					//Page fpage =  converter.addPage(net, "FUNCTION " + f.getLabel()); 
					// TODO:
				}
				if (d instanceof Queue) {
					Queue q = (Queue) d;
					addQueue(entTypeId, qentTypeId, b_qentTypeId, e_qentTypeId, m_qentTypeId, q, "");
				}
				if (d instanceof Resource) {
					Resource r = (Resource) d;
					createResourcePlace(r.getId(), entTypeId);
					addResource(r.getId(), r.getLabel(), r.getNumberOfResource());
				}
				if (d instanceof DataTable) {
//					DataTable dt = (DataTable) d;
					//Page dtpage =  converter.addPage(net, "DATATABLE " + dt.getLabel()); 
					// TODO:
				}
			}
			
			// Convert Nodes
			for (String ni : p.getNodes().keySet()) {
				io.iochord.apps.ips.model.ism.v1.Node n = p.getNodes().get(ni);
				result.getConversionMap().put(n.getId(), n);
				if (n instanceof Start) {
					Start na = (Start) n;
					String pstart = addPlace(na.getId()+"_start_0", "_nap1", entTypeId, "", na.getId(), null);
					if (na.getGenerator() != null) {
						String t_gen = cleanId("t_",na.getGenerator().getValue().getId()+"_mergegen");
						String t_silent = transIfExist.containsKey(t_gen) ? t_gen : addTransition("_start", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), na.getGenerator().getValue().getId()+"_mergegen");
						if(!transIfExist.containsKey(t_gen))
							addArc(placeshub.get(na.getGenerator().getId()+"_start_0"), t_silent, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getGenerator().getValue().getId());
						transIfExist.put(t_gen, true);
						addArc(pstart, t_silent, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getGenerator().getValue().getId());
					}
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					
					addPlace(no.getId()+"_end_0", "_nop1", entTypeId, "", no.getId(), null);
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					
					String p_nap1 = addPlace(na.getId()+"_end_0", "_nap1", entTypeId, "", na.getId(), null);
					
					if(na.getType() == ActivityType.CONCURRENT_BATCH) {
						if(na.getQueue() != null && na.getResource() != null) {
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qstt = "t_"+qidClean+"_qstt";
							String p_qendp = "p_"+qidClean+"_qendp";
							
							addArc(p_nap1, t_qstt, "PtT", entTypeId, b_qentTypeId, addTtB(b_qentTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_qentTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String t_natstart = addTransition("_natstart", null, null, b_qentResTypeId, e_qentResTypeId, m_qentResTypeId, na.getId(), null);
							addArc(p_qendp, t_natstart, "PtT", qentTypeId, b_qentResTypeId, addTtB(b_qentResTypeId, qentTypeId, "entity::queue", "Some(entity), None, Some(queue)"), addBtT(b_qentResTypeId, qentTypeId, "b.entity.get::b.queue.get"), null, null, true, na.getId());
							addArc(p_qendp, t_natstart, "TtP", qentTypeId, b_qentResTypeId, null, addBtT(b_qentResTypeId, qentTypeId, "b.queue.get"), null, null, false, na.getId());
							String p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
						    addArc(p_nap2, t_natstart, "TtP", entResTypeId, b_qentResTypeId, null, addBtT(b_qentResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(b_qentResTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						    String t_natend = addTransition("_natend", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						    addArc(p_nap2, t_natend, "PtT", entResTypeId, b_entResTypeId, addTtB(b_entResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(b_entResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
						    String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						    addArc(p_nap3, t_natend, "TtP", entTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						   
						    addArc(cleanId("r_", na.getResource().getId()), t_natstart, "PtT", "Resource["+entTypeId+"]", b_qentResTypeId, addTtB(b_qentResTypeId, "Resource["+entTypeId+"]", "resource", "None, Some(resource), None"), addBtT(b_qentResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, true, na.getId());
						    addArc(cleanId("r_", na.getResource().getId()), t_natend, "TtP", "Resource["+entTypeId+"]", b_entResTypeId, null, addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, false, na.getId());
						}
						else if(na.getQueue() != null) {
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qstt = "t_"+qidClean+"_qstt";
							String p_qendp = "p_"+qidClean+"_qendp";
							
							addArc(p_nap1, t_qstt, "PtT", entTypeId, b_qentTypeId, addTtB(b_qentTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_qentTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String t_natstart = addTransition("_natstart", null, null, b_qentTypeId, e_qentTypeId, m_qentTypeId, na.getId(), null);
							addArc(p_qendp, t_natstart, "PtT", qentTypeId, b_qentTypeId, addTtB(b_qentTypeId, qentTypeId, "entity::queue", "Some(entity), Some(queue)"), addBtT(b_qentTypeId, qentTypeId, "b.entity.get::b.queue.get"), null, null, true, na.getId());
							addArc(p_qendp, t_natstart, "TtP", qentTypeId, b_qentTypeId, null, addBtT(b_qentTypeId, qentTypeId, "b.queue.get"), null, null, false, na.getId());
							String p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							addArc(p_nap2, t_natstart, "TtP", entTypeId, b_qentTypeId, null, addBtT(b_qentTypeId, entTypeId, "b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
							String t_natend = addTransition("_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
							addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());  
						}
						else if(na.getResource() != null) {
						   String t_natstart = addTransition("_natstart", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						   addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entResTypeId, addTtB(b_entResTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
						   addArc(p_nap2, t_natstart, "TtP", entResTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(b_entResTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						   String t_natend = addTransition("_natend", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						   addArc(p_nap2, t_natend, "PtT", entResTypeId, b_entResTypeId, addTtB(b_entResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(b_entResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
						   String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						   addArc(p_nap3, t_natend, "TtP", entTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						   
						   addArc(cleanId("r_", na.getResource().getId()), t_natstart, "PtT", "Resource["+entTypeId+"]", b_entResTypeId, addTtB(b_entResTypeId, "Resource["+entTypeId+"]", "resource", "None, Some(resource)"), addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, true, na.getId());
						   addArc(cleanId("r_", na.getResource().getId()), t_natend, "TtP", "Resource["+entTypeId+"]", b_entResTypeId, null, addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, false, na.getId());
						}
						else {
						   String t_natstart = addTransition("_natstart", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
						   addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
						   addArc(p_nap2, t_natstart, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						   String t_natend = addTransition("_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
						   addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						   addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						}
					}
					else if(na.getType() == ActivityType.SPLIT_MODULE) {
						if(na.getQueue() != null && na.getResource() != null) {
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qstt = "t_"+qidClean+"_qstt";
							String p_qendp = "p_"+qidClean+"_qendp";
							
							addArc(p_nap1, t_qstt, "PtT", entTypeId, b_qentTypeId, addTtB(b_qentTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_qentTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String t_natstart = addTransition("_natstart", null, null, b_qentResTypeId, e_qentResTypeId, m_qentResTypeId, na.getId(), null);
							addArc(p_qendp, t_natstart, "PtT", qentTypeId, b_qentResTypeId, addTtB(b_qentResTypeId, qentTypeId, "entity::queue", "Some(entity), None, Some(queue)"), addBtT(b_qentResTypeId, qentTypeId, "b.entity.get::b.queue.get"), null, null, true, na.getId());
							addArc(p_qendp, t_natstart, "TtP", qentTypeId, b_qentResTypeId, null, addBtT(b_qentResTypeId, qentTypeId, "b.queue.get"), null, null, false, na.getId());
							String p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
						    addArc(p_nap2, t_natstart, "TtP", entResTypeId, b_qentResTypeId, null, addBtT(b_qentResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(b_qentResTypeId,na.getProcessingTimeExpression()), "5", false, na.getId());
						    String t_natend = addTransition("_natend", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						    addArc(p_nap2, t_natend, "PtT", entResTypeId, b_entResTypeId, addTtB(b_entResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(b_entResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
						    String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						    addArc(p_nap3, t_natend, "TtP", entTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						   
						    addArc(cleanId("r_", na.getResource().getId()), t_natstart, "PtT", "Resource["+entTypeId+"]", b_qentResTypeId, addTtB(b_qentResTypeId, "Resource["+entTypeId+"]", "resource", "None, Some(resource), None"), addBtT(b_qentResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, true, na.getId());
						    addArc(cleanId("r_", na.getResource().getId()), t_natend, "TtP", "Resource["+entTypeId+"]", b_entResTypeId, null, addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, false, na.getId());
						}
						else if(na.getQueue() != null) {
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qstt = "t_"+qidClean+"_qstt";
							String p_qendp = "p_"+qidClean+"_qendp";
							
							addArc(p_nap1, t_qstt, "PtT", entTypeId, b_qentTypeId, addTtB(b_qentTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_qentTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String t_natstart = addTransition("_natstart", null, null, b_qentTypeId, e_qentTypeId, m_qentTypeId, na.getId(), null);
							addArc(p_qendp, t_natstart, "PtT", qentTypeId, b_qentTypeId, addTtB(b_qentTypeId, qentTypeId, "entity::queue", "Some(entity), Some(queue)"), addBtT(b_qentTypeId, qentTypeId, "b.entity.get::b.queue.get"), null, null, true, na.getId());
							addArc(p_qendp, t_natstart, "TtP", qentTypeId, b_qentTypeId, null, addBtT(b_qentTypeId, qentTypeId, "b.queue.get"), null, null, false, na.getId());
							String p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							addArc(p_nap2, t_natstart, "TtP", entTypeId, b_qentTypeId, null, addBtT(b_qentTypeId, entTypeId, "b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
							String t_natend = addTransition("_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
							addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());  
						}
						else if(na.getResource() != null) {
						   String t_natstart = addTransition("_natstart", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						   addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entResTypeId, addTtB(b_entResTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
						   addArc(p_nap2, t_natstart, "TtP", entResTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(b_entResTypeId,na.getProcessingTimeExpression()), "5", false, na.getId());
						   String t_natend = addTransition("_natend", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						   addArc(p_nap2, t_natend, "PtT", entResTypeId, b_entResTypeId, addTtB(b_entResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(b_entResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
						   String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						   addArc(p_nap3, t_natend, "TtP", entTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						   
						   addArc(cleanId("r_", na.getResource().getId()), t_natstart, "PtT", "Resource["+entTypeId+"]", b_entResTypeId, addTtB(b_entResTypeId, "Resource["+entTypeId+"]", "resource", "None, Some(resource)"), addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, true, na.getId());
						   addArc(cleanId("r_", na.getResource().getId()), t_natend, "TtP", "Resource["+entTypeId+"]", b_entResTypeId, null, addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, false, na.getId());
						}
						else {
						   String t_natstart = addTransition("_natstart", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
						   addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
						   addArc(p_nap2, t_natstart, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeExpression()), "5", false, na.getId());
						   String t_natend = addTransition("_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
						   addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						   addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						}
					}
					else {
						if(na.getQueue() != null && na.getResource() != null) {
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qstt = "t_"+qidClean+"_qstt";
							String p_qendp = "p_"+qidClean+"_qendp";
							
							addArc(p_nap1, t_qstt, "PtT", entTypeId, b_qentTypeId, addTtB(b_qentTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_qentTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String t_natstart = addTransition("_natstart", null, null, b_qentResTypeId, e_qentResTypeId, m_qentResTypeId, na.getId(), null);
							addArc(p_qendp, t_natstart, "PtT", qentTypeId, b_qentResTypeId, addTtB(b_qentResTypeId, qentTypeId, "entity::queue", "Some(entity), None, Some(queue)"), addBtT(b_qentResTypeId, qentTypeId, "b.entity.get::b.queue.get"), null, null, true, na.getId());
							addArc(p_qendp, t_natstart, "TtP", qentTypeId, b_qentResTypeId, null, addBtT(b_qentResTypeId, qentTypeId, "b.queue.get"), null, null, false, na.getId());
							String p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
						    addArc(p_nap2, t_natstart, "TtP", entResTypeId, b_qentResTypeId, null, addBtT(b_qentResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(b_qentResTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						    String t_natend = addTransition("_natend", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						    addArc(p_nap2, t_natend, "PtT", entResTypeId, b_entResTypeId, addTtB(b_entResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(b_entResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
						    String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						    addArc(p_nap3, t_natend, "TtP", entTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						   
						    addArc(cleanId("r_", na.getResource().getId()), t_natstart, "PtT", "Resource["+entTypeId+"]", b_qentResTypeId, addTtB(b_qentResTypeId, "Resource["+entTypeId+"]", "resource", "None, Some(resource), None"), addBtT(b_qentResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, true, na.getId());
						    addArc(cleanId("r_", na.getResource().getId()), t_natend, "TtP", "Resource["+entTypeId+"]", b_entResTypeId, null, addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, false, na.getId());
						}
						else if(na.getQueue() != null) {
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qstt = "t_"+qidClean+"_qstt";
							String p_qendp = "p_"+qidClean+"_qendp";
							
							addArc(p_nap1, t_qstt, "PtT", entTypeId, b_qentTypeId, addTtB(b_qentTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_qentTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String t_natstart = addTransition("_natstart", null, null, b_qentTypeId, e_qentTypeId, m_qentTypeId, na.getId(), null);
							addArc(p_qendp, t_natstart, "PtT", qentTypeId, b_qentTypeId, addTtB(b_qentTypeId, qentTypeId, "entity::queue", "Some(entity), Some(queue)"), addBtT(b_qentTypeId, qentTypeId, "b.entity.get::b.queue.get"), null, null, true, na.getId());
							addArc(p_qendp, t_natstart, "TtP", qentTypeId, b_qentTypeId, null, addBtT(b_qentTypeId, qentTypeId, "b.queue.get"), null, null, false, na.getId());
							String p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							addArc(p_nap2, t_natstart, "TtP", entTypeId, b_qentTypeId, null, addBtT(b_qentTypeId, entTypeId, "b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
							String t_natend = addTransition("_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
							addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
							String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());  
						}
						else if(na.getResource() != null) {
						   String t_natstart = addTransition("_natstart", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						   addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entResTypeId, addTtB(b_entResTypeId, entTypeId, "entity", "Some(entity), None"), addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
						   addArc(p_nap2, t_natstart, "TtP", entResTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(b_entResTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						   String t_natend = addTransition("_natend", null, null, b_entResTypeId, e_entResTypeId, m_entResTypeId, na.getId(), null);
						   addArc(p_nap2, t_natend, "PtT", entResTypeId, b_entResTypeId, addTtB(b_entResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(b_entResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
						   String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						   addArc(p_nap3, t_natend, "TtP", entTypeId, b_entResTypeId, null, addBtT(b_entResTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						   
						   addArc(cleanId("r_", na.getResource().getId()), t_natstart, "PtT", "Resource["+entTypeId+"]", b_entResTypeId, addTtB(b_entResTypeId, "Resource["+entTypeId+"]", "resource", "None, Some(resource)"), addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, true, na.getId());
						   addArc(cleanId("r_", na.getResource().getId()), t_natend, "TtP", "Resource["+entTypeId+"]", b_entResTypeId, null, addBtT(b_entResTypeId, "Resource["+entTypeId+"]", "b.resource.get"), null, null, false, na.getId());
						}
						else {
						   String t_natstart = addTransition("_natstart", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
						   addArc(p_nap1, t_natstart, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
						   addArc(p_nap2, t_natstart, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), addAddedTime(b_entTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						   String t_natend = addTransition("_natend", null, null, b_entTypeId, e_entTypeId, m_entTypeId, na.getId(), null);
						   addArc(p_nap2, t_natend, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, na.getId());
						   String p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						   addArc(p_nap3, t_natend, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, na.getId());
						}
					}
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					//System.out.println("Branch "+b.getId());
					
					String bt = null;
					String bp = null;
					
					if (b.getGate() == BranchGate.AND) { 
						bt = addTransition("_bt", null, null, b_entTypeId, e_entTypeId, m_entTypeId, b.getId(), null);
					}
					
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getTarget().getValue() != b) continue;
							String p_bpis = addPlace(b.getId()+"_end_"+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
							addArc(p_bpis, bt, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entitiy", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, b.getId());
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getTarget().getValue() != b) continue;
								String p_bpis = addPlace(b.getId()+"_end_"+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								
								String actiondef = "val newb = b.copy(cond = Some(scala.util.Random.nextDouble()));newb";
								String action = addAction(b_entBrTypeId, actiondef);
								
								String t_condGen = addTransition("_condGen", null, action, b_entBrTypeId, e_entBrTypeId, m_entBrTypeId, b.getId(), null);
								String p_bpt = addPlace(null, "_bpt", entBrTypeId, "", b.getId(), null);
								bp = p_bpt;
								addArc(p_bpis, t_condGen, "PtT", entTypeId, b_entBrTypeId, addTtB(b_entBrTypeId, entTypeId, "entity", "Some(entity),None"), addBtT(b_entBrTypeId, entTypeId, "b.entity.get"), null, null, true, b.getId());
								addArc(p_bpt, t_condGen, "TtP", entBrTypeId, b_entBrTypeId, addTtB(b_entBrTypeId, entBrTypeId, "(entity,cond)", "Some(entity),Some(cond)"), addBtT(b_entBrTypeId, entBrTypeId, "(b.entity.get,b.cond.get)"), null, null, false, b.getId());
							}
							else {
								if (c.getSource().getValue() != b) continue;
								String p_bpos = addPlace(b.getId()+"_start_"+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								bp = p_bpos;
							}
						}
						//System.out.println("Source "+c.getSource().getId());
						i++;
					}
					
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getSource().getValue() != b) continue;
							String p_bpos = addPlace(b.getId()+"_start_"+c.getSourceIndex(), "_bpo_" + i + "s", entTypeId, "", b.getId(), null);
							addArc(p_bpos, bt, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, b.getId());
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getSource().getValue() != b) continue;
								String guard = null;
								if(b.getRule() == BranchRule.DATA)
								{
										
								}
								else if (b.getRule() == BranchRule.CONDITION) {
									String guarddef = "b.cond.get "+c.getAttributes().get("condition");
									guard = addGuard(b_entBrTypeId, guarddef);
								}
								String p_bpos = addPlace(b.getId()+"_start_"+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								String t_silent = addTransition("_temps_"+i, guard, null, b_entBrTypeId, e_entBrTypeId, m_entBrTypeId, b.getId(), null);
								addArc(bp, t_silent, "PtT", entBrTypeId, b_entBrTypeId, addTtB(b_entBrTypeId, entBrTypeId, "(entity,cond)", "Some(entity),Some(cond)"), addBtT(b_entBrTypeId, entBrTypeId, "(b.entity.get,b.cond.get)"), null, null, true, b.getId());
								addArc(p_bpos, t_silent, "TtP", entTypeId, b_entBrTypeId, null, addBtT(b_entBrTypeId, entTypeId, "b.entity.get"), null, null, false, b.getId());
							}
							else {
								if (c.getTarget().getValue() != b) continue;
								String p_bpis = addPlace(b.getId()+"_end_"+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								String t_silent = addTransition("_temps_"+i, null, null, b_entTypeId, e_entTypeId, m_entTypeId, b.getId(), null);
								addArc(p_bpis, t_silent, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, b.getId());
								addArc(bp, t_silent, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, b.getId());
							}
						}
						//System.out.println("Target "+c.getTarget().getId());
						i++;
					}
				}
				if (n instanceof Monitor) {
//					Monitor m = (Monitor) n;
					//Page mpage =  converter.addPage(net, "MONITOR " + m.getLabel()); 
					// TODO:
				}
			}
			// Convert Arcs
			for (String s : p.getConnectors().keySet()) {
				Connector c = p.getConnectors().get(s);
				//System.out.println("Source HL : "+c.getSource().getId());
				//System.out.println("Target HL : "+c.getTarget().getId());
				
				String source = placeshub.get(c.getSource().getId()+"_start_"+c.getSourceIndex());
				String target = placeshub.get(c.getTarget().getId()+"_end_"+c.getTargetIndex());
				
				//System.out.println(source+" - "+target);
				
				String[] oriRole = target.contains("-stop-") ? new String[]{target,"_stop"} : new String[]{c.getId(), "silent"};
				String t_silent = addTransition(oriRole[1], null, null, b_entTypeId, e_entTypeId, m_entTypeId, oriRole[0], null);
				addArc(source, t_silent, "PtT", entTypeId, b_entTypeId, addTtB(b_entTypeId, entTypeId, "entity", "Some(entity)"), addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, true, c.getId());
				addArc(target, t_silent, "TtP", entTypeId, b_entTypeId, null, addBtT(b_entTypeId, entTypeId, "b.entity.get"), null, null, false, c.getId());
			}
		}
		
		result.setConvertedModel(factory.toString());
		
		return result;
	}
	
	public String cleanId(String prefix, String oriId) {
		String modId = oriId.replace("-", "");
		
		if(prefix != null) 
			modId = prefix+modId;
		
		return modId;
	}
	
	public IsmGraph revert(Ism2CpnscalaModel cgraph) {
		return null;
	}
	
}
