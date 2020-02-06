package io.iochord.apps.ips.model.ism2cpn.converter;

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
*
* @package ips-model-analysis
* @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
* @since   2020
* 
* CPN Scala converter class per module
* Convert from IsmGraph to Ism2CpnscalaModelPerModule -> (scala string for each module)
* 
*/
public class Ism2CpnscalaPerModuleBiConverter implements Converter<IsmGraph, Ism2CpnscalaModelPerModule> {
	
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
	
	private Map<String, String> resources = new LinkedHashMap<>();
	private Map<String, String> queues = new LinkedHashMap<>();
	private Map<String, String[]> placeshub = new LinkedHashMap<>();
	
	private Map<String, Integer> counters = new LinkedHashMap<>();
	
	/**
	 * @param clazz
	 * @return
	 */
	private String getCounter(String clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return String.format("%2s", counters.get(clazz)).replace(' ', '0');
	}
	
	/**
	 * @param hubid
	 * @param name
	 * @param type
	 * @param initialMarking
	 * @param origin
	 * @param placeId
	 * @return array of string consist of place id and place definition
	 * 
	 * We don't directly write to string because we want to package the string per its module (per node of high level representation)
	 */
	public String[] addPlace(String hubid, String name, String type, String initialMarking, String origin, String placeId) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.place);
		String mapid = "map"+counter;
		String multisetid = "ms"+counter;
		String placeidClean = placeId == null ? "p_"+counter : cleanId("p_", placeId);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val "+mapid+" = Map[("+type+",Long),Int]( "+initialMarking+" )\n" );
		placefactory.append( "val "+multisetid+" = new Multiset["+type+"]("+mapid+")\n" );
		placefactory.append( "val "+placeidClean+" = new Place(\""+placeidClean+"\",\""+name+"\","+multisetid+")\n" );
		placefactory.append( placeidClean+".setOrigin(Map[String,String]((\"origin\",\""+origin+"\"),(\"role\",\""+name+"\")))\n" );
		placefactory.append( "cgraph.addPlace("+placeidClean+")\n" );
		placefactory.append("\n");
		
		id_def[0] = placeidClean;
		id_def[1] = placefactory.toString();
		
		if(hubid != null)
			placeshub.put(hubid, id_def);
		
		return id_def;
	}
	
	/**
	 * @param name
	 * @param guard
	 * @param action
	 * @param classbinding
	 * @param eval
	 * @param merge
	 * @param origin
	 * @param transId
	 * @return
	 */
	public String[] addTransition(String name, String guard, String action, String classbinding, String eval, String merge, String origin, String transId) {
		String[] id_def = new String[2];
		
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
		
		id_def[0] = transidClean;
		id_def[1] = transfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param classbinding
	 * @param guarddef
	 * @return
	 */
	public String[] addGuard(String classbinding, String guarddef) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.guard);
		String guardid = "Guard"+counter;
		String bindguardexpid = "BindGuard"+counter;
		
		StringBuilder guardfactory = new StringBuilder();
		guardfactory.append( "val "+guardid+" = new Guard["+classbinding+"]()\n" );
		guardfactory.append( "val "+bindguardexpid+" = (b:"+classbinding+") => {"+guarddef+"}\n" );
		guardfactory.append( guardid+".setGuardBind("+bindguardexpid+")\n" );
		
		id_def[0] = guardid;
		id_def[1] = guardfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param classbinding
	 * @param actionfundef
	 * @return
	 */
	public String[] addAction(String classbinding, String actionfundef) {
		String[] id_def = new String[2];
		
		StringBuilder actionfactory = new StringBuilder();
		
		String counter = getCounter(KeyElement.action);
		String actionid = "action"+counter;
		String actionfunid = "actionFun"+counter;
		String actionfun = "def "+actionfunid+"(b:"+classbinding+"):"+classbinding+" = { "+actionfundef+" }";
		
		actionfactory.append( actionfun+"\n" );
		actionfactory.append("\n");
		
		actionfactory.append( "val "+actionid+" = new Action["+classbinding+"]()\n" );
		actionfactory.append( actionid+".setActionFun("+actionfunid+")\n" );
		actionfactory.append("\n");
		
		id_def[0] = actionid;
		id_def[1] = actionfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param classdef
	 * @return
	 */
	public String[] addBindingClass(String classdef) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.binding);
		String bindingid = "Binding"+counter;
		
		StringBuilder cBindingfactory = new StringBuilder();
		cBindingfactory.append( "case class "+bindingid+"(").append(classdef).append(")\n" );
		
		id_def[0] = bindingid;
		id_def[1] = cBindingfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param evaldef
	 * @param classbinding
	 * @return
	 */
	public String[] addEval(String evaldef, String classbinding) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.eval);
		String evalid = "Eval"+counter;
		
		StringBuilder evalfactory = new StringBuilder();
		evalfactory.append( "val "+evalid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		evalfactory.append( "\t"+evaldef+"\n" );
		evalfactory.append( "}\n" );
		
		id_def[0] = evalid;
		id_def[1] = evalfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param mergedef
	 * @param classbinding
	 * @param mergeassign
	 * @return
	 */
	public String[] addMerge(String mergedef, String classbinding, String mergeassign) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.merge);
		String mergeid = "Merge"+counter;
		
		StringBuilder mergefactory = new StringBuilder();
		mergefactory.append( "val "+mergeid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		mergefactory.append( "\t"+mergedef+"\n" );
		mergefactory.append( "\t"+classbinding+"("+mergeassign+")\n" );
		mergefactory.append( "}\n" );
		
		id_def[0] = mergeid;
		id_def[1] = mergefactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param placeid
	 * @param transitionid
	 * @param direction
	 * @param type
	 * @param classbinding
	 * @param TtB
	 * @param BtT
	 * @param addTime
	 * @param noToken
	 * @param isBase
	 * @param origin
	 * @return
	 */
	public String[] addArc(String placeid, String transitionid, String direction, String type, String classbinding, String TtB, String BtT, String addTime, String noToken, boolean isBase, String origin) {
		String[] id_def = new String[2];
		
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
		
		id_def[0] = arcid;
		id_def[1] = arcfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param classbinding
	 * @param type
	 * @param inverse
	 * @param TtBdef
	 * @return
	 */
	public String[] addTtB(String classbinding, String type, String inverse, String TtBdef) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.TtB);
		String TtBid = "tTb"+counter;
		
		StringBuilder TtBfactory = new StringBuilder();
		TtBfactory.append( "val "+TtBid+" = (t:"+type+") => { try { val "+inverse+" = t;Some("+classbinding+"("+TtBdef+")) } catch { case e: Exception => None } }\n" );
		
		id_def[0] = TtBid;
		id_def[1] = TtBfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param classbinding
	 * @param type
	 * @param BtTdef
	 * @return
	 */
	public String[] addBtT(String classbinding, String type, String BtTdef) {
		String[] id_def = new String[2];
		
		String counter = getCounter(KeyElement.BtT);
		String BtTid = "bTt"+counter;
		
		StringBuilder BtTfactory = new StringBuilder();
		BtTfactory.append( "val "+BtTid+" = (b:"+classbinding+") => {"+BtTdef+"}:"+type+"\n" );
		
		id_def[0] = BtTid;
		id_def[1] = BtTfactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param classbinding
	 * @param addedTimedef
	 * @return
	 */
	public String[] addAddedTime(String classbinding, String addedTimedef) {
		String[] id_def = new String[2];
		
		if(addedTimedef.length() <= 1)
			addedTimedef = "0L";
		
		String counter = getCounter(KeyElement.addTime);
		String addTimeid = "addTime"+counter;
		
		StringBuilder addTimefactory = new StringBuilder();
		addTimefactory.append( "val "+addTimeid+" = (b:"+classbinding+") => {"+addedTimedef+"}\n" );
		
		id_def[0] = addTimeid;
		id_def[1] = addTimefactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param resId
	 * @param typecaseId
	 * @return
	 */
	public String[] createResourcePlace(String resId, String typecaseId) {
		String[] id_def = new String[2];
		
		String presidClean = cleanId("r_", resId);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val map"+presidClean+" = Map[(Resource["+typecaseId+"],Long),Int]()\n" );
		placefactory.append( "val ms"+presidClean+" = new Multiset[Resource["+typecaseId+"]](map"+presidClean+")\n" );
		placefactory.append( "val "+presidClean+" = new Place(\""+presidClean+"\",\"Resource Place\",ms"+presidClean+")\n" );
		placefactory.append( presidClean+".setOrigin(Map[String,String]((\"origin\",\""+resId+"\"),(\"role\",\"_resp\")))\n" );
		placefactory.append( "cgraph.addPlace("+presidClean+")\n" );
		placefactory.append("\n");
		
		id_def[0] = presidClean;
		id_def[1] = placefactory.toString();
		
		return id_def;
	}
	
	/**
	 * @param resId
	 * @param name
	 * @param numbofresource
	 * @return
	 */
	public String addResource(String resId, String name, int numbofresource) {
		String residClean = cleanId("r_", resId);
		
		StringBuilder resfactory = new StringBuilder();
		for(int i=1; i<=numbofresource; i++) {
			String residInScala = residClean+"_"+i;
			resfactory.append( "val "+residInScala+" = new Resource(\""+residInScala+"\", \""+name+"\", 0L)\n" );
			resfactory.append( residClean+".addTokenWithTime( ("+residInScala+",0L), 1)\n" );
		}
		
		return resfactory.toString();
	}
	
	/**
	 * @param typeid
	 * @param ltypeid
	 * @param cb
	 * @param ev
	 * @param mg
	 * @param queue
	 * @param origin
	 * @return
	 */
	public String addQueue(String typeid, String ltypeid, String cb, String ev, String mg, Queue queue, String origin) {
		String qidClean = cleanId("q_", queue.getId());
		String listid = "list_"+qidClean;
		
		StringBuilder queuefactory = new StringBuilder();
		queuefactory.append("\n val "+listid+" = List["+typeid+"]() \n");
		
		String[] pendqueue = addPlace(null, "_qendp", ltypeid, "(("+listid+",0),1)", origin, queue.getId()+"_qendp");
		queuefactory.append(pendqueue[1]);
		
		String guarddef = "b.queue.get.length < "+queue.getSize();
		
		String guardid = null;
		
		if(queue.getSize() > 0) {
			String[] guard = addGuard(cb, guarddef);
			queuefactory.append(guard[1]);
			guardid = guard[0];
		}
		
		String[] tstartqueue = addTransition("_qstt", guardid, null, cb, ev, mg, origin, queue.getId()+"_qstt");
		queuefactory.append(tstartqueue[1]);
		
		String[] TtB1 = addTtB(cb, ltypeid, "queue", "None, Some(queue)");
		queuefactory.append(TtB1[1]);
		
		String[] BtT1 = addBtT(cb, ltypeid, "b.queue.get");
		queuefactory.append(BtT1[1]);
		
		String[] arc1 = addArc(pendqueue[0], tstartqueue[0], "PtT", ltypeid, cb, TtB1[0], BtT1[0], null, null, true, origin);
		queuefactory.append(arc1[1]);
		
		if(queue.getType() == Queue.QUEUE_TYPE.FIFO) {
			String[] BtT2 = addBtT(cb, ltypeid, "b.entity.get::b.queue.get");
			queuefactory.append(BtT2[1]);
			String[] arc2 = addArc(pendqueue[0], tstartqueue[0], "TtP", ltypeid, cb, null, BtT2[0], null, null, false, origin);
			queuefactory.append(arc2[1]);
		}
		else {
			String[] BtT2 = addBtT(cb, ltypeid, "b.queue.get:::List(b.entity.get)");
			queuefactory.append(BtT2[1]);
			String[] arc2 = addArc(pendqueue[0], tstartqueue[0], "TtP", ltypeid, cb, null, BtT2[0], null, null, false, origin);
			queuefactory.append(arc2[1]);
		}
		
		return queuefactory.toString();
	}
	
	/* 
	 * @param snet : accept snet and convert to scala string per module
	 */
	public Ism2CpnscalaModelPerModule convert(IsmGraph snet) {
		Ism2CpnscalaModelPerModule result = new Ism2CpnscalaModelPerModule();
		result.setOriginalModel(snet);
		
		LinkedHashMap<String,String> convm = new LinkedHashMap<String,String>();
		
		for (String pi : snet.getPages().keySet()) {
			io.iochord.apps.ips.model.ism.v1.Page p = snet.getPages().get(pi);
			
			StringBuilder strGen = new StringBuilder();
			
			String CaseData = "case class CaseData(atr1:String,atr2:Int)\n";
			strGen.append(CaseData);
			
			String dataTypeId = "colset"+getCounter(KeyElement.type);
			String dataType = "type "+dataTypeId+" = ((Int,String),CaseData)\n";
			strGen.append(dataType);
			
			String entTypeId = "colset"+getCounter(KeyElement.type);
			String entType = "type "+entTypeId+" = (Int,String)\n";
			strGen.append(entType);
			
			String[] b_entType = addBindingClass( "entity:Option["+entTypeId+"]" );
			String[] e_entType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None)", b_entType[0]);
			String[] m_entType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;", b_entType[0], "entity");
			strGen.append(b_entType[1]);
			strGen.append(e_entType[1]);
			strGen.append(m_entType[1]);
			
			String entBrTypeId = "colset"+getCounter(KeyElement.type);
			String entBrType = "type "+entBrTypeId+" = ((Int,String),Double)\n";
			strGen.append(entBrType);
			
			String[] b_entBrType = addBindingClass( "entity:Option["+entTypeId+"],cond:Option[Double]" );
			String[] e_entBrType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.cond == b2.cond || b1.cond == None || b2.cond == None)", b_entBrType[0]);
			String[] m_entBrType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val cond = if(b1.cond == None) b2.cond else b1.cond;", b_entBrType[0], "entity,cond");
			strGen.append(b_entBrType[1]);
			strGen.append(e_entBrType[1]);
			strGen.append(m_entBrType[1]);
			
			String qentTypeId = "colset"+getCounter(KeyElement.type);
			String qentType = "type "+qentTypeId+" = List["+entTypeId+"]\n";
			strGen.append(qentType);
			
			String[] b_qentType = addBindingClass( "entity:Option["+entTypeId+"], queue:Option["+qentTypeId+"]" );
			String[] e_qentType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", b_qentType[0]);
			String[] m_qentType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;val queue = if(b1.queue == None) b2.queue else b1.queue;", b_qentType[0], "entity,queue");
			strGen.append(b_qentType[1]);
			strGen.append(e_qentType[1]);
			strGen.append(m_qentType[1]);
			
			String entResTypeId = "colset"+getCounter(KeyElement.type);
			String entResType = "type "+entResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"])\n";
			strGen.append(entResType);
			
			String[] b_entResType = addBindingClass( "entity:Option["+entTypeId+"], resource:Option[Resource["+entTypeId+"]]" );
			String[] e_entResType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None)", b_entResType[0]);
			String[] m_entResType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource;", b_entResType[0], "entity,resource");
			strGen.append(b_entResType[1]);
			strGen.append(e_entResType[1]);
			strGen.append(m_entResType[1]);
			
			String qentResTypeId = "colset"+getCounter(KeyElement.type);
			String qentResType = "type "+qentResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"], List["+entTypeId+"])\n";
			strGen.append(qentResType);
			
			String[] b_qentResType = addBindingClass( "entity:Option["+entTypeId+"], resource:Option[Resource["+entTypeId+"]], queue:Option[List["+entTypeId+"]]" );
			String[] e_qentResType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", b_qentResType[0]);
			String[] m_qentResType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource; val queue = if(b1.queue == None) b2.queue else b1.queue;", b_qentResType[0], "entity,resource,queue");
			strGen.append(b_qentResType[1]);
			strGen.append(e_qentResType[1]);
			strGen.append(m_qentResType[1]);
			
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				result.getConversionMap().put(d.getId(), d);
				if (d instanceof ObjectType) {
//					ObjectType ot = (ObjectType) d;
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					
					StringBuilder strGenerator = new StringBuilder();
					strGenerator.append(strGen.toString());
					
					String typeId = "Int";
					
					String[] p_dgp1 = addPlace(null, "_dgp1", typeId, "((1,0),1)", dg.getId(), null);
					String[] p_dgp2 = addPlace(dg.getId()+ "_start_0", "_dgp2", entTypeId, "", dg.getId(), null);
					String[] p_dgpData = addPlace(null, "_dgpData", dataTypeId, "", dg.getId(), null);
					strGenerator.append(p_dgp1[1]);
					strGenerator.append(p_dgp2[1]);
					strGenerator.append(p_dgpData[1]);
					
					String[] b_dgt1 = addBindingClass( "tid:Option[Int],gid:Option[String],data:Option[CaseData]" );
					String[] e_dgt1 = addEval("(b1.tid == b2.tid || b1.tid == None || b2.tid == None) && (b1.gid == b2.gid || b1.gid == None || b2.gid == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", b_dgt1[0]);
					String[] m_dgt1 = addMerge("val tid = if(b1.tid == None) b2.tid else b1.tid;val gid = if(b1.gid == None) b2.gid else b1.gid;val data = if(b1.data == None) b2.data else b1.data;", b_dgt1[0], "tid,gid,data");
					strGenerator.append(b_dgt1[1]);
					strGenerator.append(e_dgt1[1]);
					strGenerator.append(m_dgt1[1]);
					
					String[] guard = addGuard(b_dgt1[0], "b.tid.get <= "+dg.getMaxArrival());
					String[] action = addAction(b_dgt1[0],
							"val r = new java.util.Random()\n"
							+ "val rint = r.nextInt();val gid = \""+dg.getId()+"\"\n"
							+ "val data = CaseData(\"atr\"+rint,rint)\n"
							+ b_dgt1[0]+"(b.tid,Some(gid),Some(data))");
					
					String[] t_dgt1 = addTransition("_dgt1", guard[0], action[0], b_dgt1[0], e_dgt1[0], m_dgt1[0], dg.getId(), null);
					strGenerator.append(guard[1]);
					strGenerator.append(action[1]);
					strGenerator.append(t_dgt1[1]);
					
					String[] TtB1 = addTtB(b_dgt1[0], typeId, "tid", "Some(tid), None, None");
					String[] BtT1 = addBtT(b_dgt1[0], typeId, "b.tid.get");
					strGenerator.append(TtB1[1]);
					strGenerator.append(BtT1[1]);	
					String[] arc1 = addArc(p_dgp1[0], t_dgt1[0], "PtT", typeId, b_dgt1[0], TtB1[0], BtT1[0], null, null, true, dg.getId());
					strGenerator.append(arc1[1]);
				
					String[] BtT2 = addBtT(b_dgt1[0], typeId, "b.tid.get + 1");
					String[] time2 = addAddedTime(b_dgt1[0],dg.getExpression());
					strGenerator.append(BtT2[1]);
					strGenerator.append(time2[1]);
					String[] arc2 = addArc(p_dgp1[0], t_dgt1[0], "TtP", typeId, b_dgt1[0], null, BtT2[0], time2[0], null, false, dg.getId());
					strGenerator.append(arc2[1]);
					
					String[] BtT3 = addBtT(b_dgt1[0], entTypeId, "(b.tid.get,b.gid.get)");
					strGenerator.append(BtT3[1]);
					String[] arc3 = addArc(p_dgp2[0], t_dgt1[0], "TtP", entTypeId, b_dgt1[0], null, BtT3[0], null, null, false, dg.getId());
					strGenerator.append(arc3[1]);
					
					String[] BtT4 = addBtT(b_dgt1[0], dataTypeId, "((b.tid.get,b.gid.get),b.data.get)");
					strGenerator.append(BtT4[1]);
					String[] arc4 = addArc(p_dgpData[0], t_dgt1[0], "TtP", dataTypeId, b_dgt1[0], null, BtT4[0], null, null, false, dg.getId());
					strGenerator.append(arc4[1]);
					
					strGenerator.append("\ncgraph");
					convm.put(dg.getId(), strGenerator.toString());
				}
				if (d instanceof Function) {
//					Function f = (Function) d;
					//Page fpage =  converter.addPage(net, "FUNCTION " + f.getLabel()); 
					// TODO:
				}
				if (d instanceof Queue) {
					Queue q = (Queue) d;
					queues.put(q.getId(),addQueue(entTypeId, qentTypeId, b_qentType[0], e_qentType[0], m_qentType[0], q, ""));
				}
				if (d instanceof Resource) {
					Resource r = (Resource) d;
					
					StringBuilder strRes = new StringBuilder();
					strRes.append(createResourcePlace(r.getId(), entTypeId)[1]);
					strRes.append(addResource(r.getId(), r.getLabel(), r.getNumberOfResource()));
					resources.put(r.getId(), strRes.toString());
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
					
					StringBuilder strStart = new StringBuilder();
					strStart.append(strGen.toString());
					
					String[] pstart = addPlace(na.getId()+"_start_0", "_nap1", entTypeId, "", na.getId(), null);
					strStart.append(pstart[1]);
					
					if (na.getGenerator() != null) {
//						String t_genid = cleanId("t_",na.getGenerator().getValue().getId()+"_mergegen");
						
						String[] t_silent = addTransition("_start", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), na.getGenerator().getValue().getId()+"_mergegen");
						strStart.append(t_silent[1]);
						
						String[] p_genStart = placeshub.get(na.getGenerator().getId()+"_start_0");
						strStart.append(p_genStart[1]);
						
						String[] TtB1 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
						strStart.append(TtB1[1]);
						String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
						strStart.append(BtT1[1]);
						String[] arc1 = addArc(p_genStart[0], t_silent[0], "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, na.getGenerator().getValue().getId());
						strStart.append(arc1[1]);
						
						String[] BtT2 = addBtT(b_entType[0], entTypeId, "b.entity.get");
						strStart.append(BtT2[1]);
						String[] arc2 = addArc(pstart[0], t_silent[0], "TtP", entTypeId, b_entType[0], null, BtT2[0], null, null, false, na.getGenerator().getValue().getId());
						strStart.append(arc2[1]);
					}
					
					strStart.append("\ncgraph");
					convm.put(na.getId(), strStart.toString());
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					
					StringBuilder strStop = new StringBuilder();
					strStop.append(strGen.toString());
					
					String[] pstop = addPlace(no.getId()+"_end_0", "_nop1", entTypeId, "", no.getId(), null);
					strStop.append(pstop[1]);
					
					strStop.append("\ncgraph");
					convm.put(no.getId(), strStop.toString());
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					
					StringBuilder strAct = new StringBuilder();
					strAct.append(strGen.toString());
					
					String[] p_nap1 = addPlace(na.getId()+"_end_0", "_nap1", entTypeId, "", na.getId(), null);
					strAct.append(p_nap1[1]);
					
					if(na.getType() == ActivityType.CONCURRENT_BATCH) {
						if(na.getQueue() != null && na.getResource() != null) {
							strAct.append(queues.get(na.getQueue().getValue().getId()));
							strAct.append(resources.get(na.getResource().getValue().getId()));
							
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qsttId = "t_"+qidClean+"_qstt";
							String p_qendpId = "p_"+qidClean+"_qendp";
							
							String[] TtB1 = addTtB(b_qentType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_qsttId, "PtT", entTypeId, b_qentType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] t_natstart = addTransition("_natstart", null, null, b_qentResType[0], e_qentResType[0], m_qentResType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
							
							String[] TtB2 = addTtB(b_qentResType[0], qentTypeId, "entity::queue", "Some(entity), None, Some(queue)");
							String[] BtT2 = addBtT(b_qentResType[0], qentTypeId, "b.entity.get::b.queue.get");
							strAct.append(TtB2[1]);
							strAct.append(BtT2[1]);
							String[] arc2 = addArc(p_qendpId, t_natstart[0], "PtT", qentTypeId, b_qentResType[0], TtB2[0], BtT2[0], null, null, true, na.getId());
							strAct.append(arc2[1]);
							
							String[] BtT3 = addBtT(b_qentResType[0], qentTypeId, "b.queue.get");
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_qendpId, t_natstart[0], "TtP", qentTypeId, b_qentResType[0], null, BtT3[0], null, null, false, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
							
							String[] BtT4 = addBtT(b_qentResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
							String[] time4 = addAddedTime(b_qentResType[0], na.getProcessingTimeExpression());
							strAct.append(BtT4[1]);
							strAct.append(time4[1]);
						    String[] arc4 = addArc(p_nap2[0], t_natstart[0], "TtP", entResTypeId, b_qentResType[0], null, BtT4[0], time4[0], null, false, na.getId());
						    strAct.append(arc4[1]);
						    
						    String[] t_natend = addTransition("_natend", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
						    strAct.append(t_natend[1]);
						    
						    String[] TtB5 = addTtB(b_entResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
						    String[] BtT5 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
						    strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(p_nap2[0], t_natend[0], "PtT", entResTypeId, b_entResType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
						    strAct.append(arc5[1]);
						    
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						    strAct.append(p_nap3[1]);
						    
						    String[] BtT6 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
						    strAct.append(BtT6[1]);
							String[] arc6 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entResType[0], null, BtT6[0], null, null, false, na.getId());
							strAct.append(arc6[1]);
							
							String[] TtB7 = addTtB(b_qentResType[0], "Resource["+entTypeId+"]", "resource", "None, Some(resource), None");
							String[] BtT7 = addBtT(b_qentResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(TtB7[1]);
							strAct.append(BtT7[1]);
						    String[] arc7 = addArc(cleanId("r_", na.getResource().getId()), t_natstart[0], "PtT", "Resource["+entTypeId+"]", b_qentResType[0], TtB7[0], BtT7[0], null, null, true, na.getId());
						    strAct.append(arc7[1]);
						    
						    String[] BtT8 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
						    strAct.append(BtT8[1]);
						    String[] arc8 = addArc(cleanId("r_", na.getResource().getId()), t_natend[0], "TtP", "Resource["+entTypeId+"]", b_entResType[0], null, BtT8[0], null, null, false, na.getId());
						    strAct.append(arc8[1]);
						}
						else if(na.getQueue() != null) {
							strAct.append(queues.get(na.getQueue().getValue().getId()));
							
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qsttId = "t_"+qidClean+"_qstt";
							String p_qendpId = "p_"+qidClean+"_qendp";
							
							String[] TtB1 = addTtB(b_qentType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[0]);
							strAct.append(BtT1[0]);
							String[] arc1 = addArc(p_nap1[0], t_qsttId, "PtT", entTypeId, b_qentType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] t_natstart = addTransition("_natstart", null, null, b_qentType[0], e_qentType[0], m_qentType[0], na.getId(), null);
							
							String[] TtB2 = addTtB(b_qentType[0], qentTypeId, "entity::queue", "Some(entity), Some(queue)");
							String[] BtT2 = addBtT(b_qentType[0], qentTypeId, "b.entity.get::b.queue.get");
							strAct.append(TtB2[0]);
							strAct.append(BtT2[0]);
							String[] arc2 = addArc(p_qendpId, t_natstart[0], "PtT", qentTypeId, b_qentType[0], TtB2[0], BtT2[0], null, null, true, na.getId());
							strAct.append(arc2[1]);
							
							String[] BtT3 = addBtT(b_qentType[0], qentTypeId, "b.queue.get");
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_qendpId, t_natstart[0], "TtP", qentTypeId, b_qentType[0], null, BtT3[0], null, null, false, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							
							String[] BtT4 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							String[] time4 = addAddedTime(b_entType[0],na.getProcessingTimeExpression());
							strAct.append(BtT4[1]);
							strAct.append(time4[1]);
							String[] arc4 = addArc(p_nap2[0], t_natstart[0], "TtP", entTypeId, b_qentType[0], null, BtT4[0], time4[0], null, false, na.getId());
							strAct.append(arc4[1]);
							
							String[] t_natend = addTransition("_natend", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natend[1]);
							
							String[] TtB5 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT5 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(p_nap2[0], t_natend[0], "PtT", entTypeId, b_entType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
							strAct.append(arc5[1]);
							
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
							
							String[] BtT6 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(BtT6[1]);
							String[] arc6 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entType[0], null, BtT6[0], null, null, false, na.getId());  
							strAct.append(arc6[1]);
						}
						else if(na.getResource() != null) {
							strAct.append(resources.get(na.getResource().getValue().getId()));
							
							String[] t_natstart = addTransition("_natstart", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
						   
							String[] TtB1 = addTtB(b_entResType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_natstart[0], "PtT", entTypeId, b_entResType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
						   
							String[] p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
						   
							String[] BtT2 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
							String[] time2 = addAddedTime(b_entResType[0],na.getProcessingTimeExpression());
							strAct.append(BtT2[1]);
							strAct.append(time2[1]);
							String[] arc2 = addArc(p_nap2[0], t_natstart[0], "TtP", entResTypeId, b_entResType[0], null, BtT2[0], time2[0], null, false, na.getId());
							strAct.append(arc2[1]);
						   
							String[] t_natend = addTransition("_natend", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
							strAct.append(t_natend[1]);
						   
							String[] TtB3 = addTtB(b_entResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
							String[] BtT3 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
							String[] arc3 = addArc(p_nap2[0], t_natend[0], "PtT", entResTypeId, b_entResType[0], TtB3[0], BtT3[0], null, null, true, na.getId());
							strAct.append(arc3[1]);
						   
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
						   
							String[] BtT4 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
							strAct.append(BtT4[1]);
							String[] arc4 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entResType[0], null, BtT4[0], null, null, false, na.getId());
							strAct.append(arc4[1]);
						   
							String[] TtB5 = addTtB(b_entResType[0], "Resource["+entTypeId+"]", "resource", "None, Some(resource)");
							String[] BtT5 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(cleanId("r_", na.getResource().getId()), t_natstart[0], "PtT", "Resource["+entTypeId+"]", b_entResType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
							strAct.append(arc5[1]);
						   
							String[] BtT6 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(BtT6[1]);
							String[] arc6 = addArc(cleanId("r_", na.getResource().getId()), t_natend[0], "TtP", "Resource["+entTypeId+"]", b_entResType[0], null, BtT6[0], null, null, false, na.getId());
							strAct.append(arc6[1]);
						}
						else {
							String[] t_natstart = addTransition("_natstart", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
							
							String[] TtB1 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_natstart[0], "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
							
							String[] BtT2 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							String[] time2 = addAddedTime(b_entType[0],na.getProcessingTimeExpression());
							strAct.append(BtT2[1]);
							strAct.append(time2[1]);
							String[] arc2 = addArc(p_nap2[0], t_natstart[0], "TtP", entTypeId, b_entType[0], null, BtT2[0], time2[0], null, false, na.getId());
							strAct.append(arc2[1]);
							
							String[] t_natend = addTransition("_natend", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natend[1]);
							
							String[] TtB3 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT3 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB3[1]);
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_nap2[0], t_natend[0], "PtT", entTypeId, b_entType[0], TtB3[0], BtT3[0], null, null, true, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
							
							String[] BtT4 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(BtT4[1]);
							String[] arc4 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entType[0], null, BtT4[0], null, null, false, na.getId());
							strAct.append(arc4[1]);
						}
					}
					else if(na.getType() == ActivityType.SPLIT_MODULE) {
						if(na.getQueue() != null && na.getResource() != null) {
							strAct.append(queues.get(na.getQueue().getValue().getId()));
							strAct.append(resources.get(na.getResource().getValue().getId()));
							
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qsttId = "t_"+qidClean+"_qstt";
							String p_qendpId = "p_"+qidClean+"_qendp";
							
							String[] TtB1 = addTtB(b_qentType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_qsttId, "PtT", entTypeId, b_qentType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] t_natstart = addTransition("_natstart", null, null, b_qentResType[0], e_qentResType[0], m_qentResType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
							
							String[] TtB2 = addTtB(b_qentResType[0], qentTypeId, "entity::queue", "Some(entity), None, Some(queue)");
							String[] BtT2 = addBtT(b_qentResType[0], qentTypeId, "b.entity.get::b.queue.get");
							strAct.append(TtB2[1]);
							strAct.append(BtT2[1]);
							String[] arc2 = addArc(p_qendpId, t_natstart[0], "PtT", qentTypeId, b_qentResType[0], TtB2[0], BtT2[0], null, null, true, na.getId());
							strAct.append(arc2[1]);
							
							String[] BtT3 = addBtT(b_qentResType[0], qentTypeId, "b.queue.get");
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_qendpId, t_natstart[0], "TtP", qentTypeId, b_qentResType[0], null, BtT3[0], null, null, false, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
							
							String[] BtT4 = addBtT(b_qentResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
							String[] time4 = addAddedTime(b_qentResType[0], na.getProcessingTimeExpression());
							strAct.append(BtT4[1]);
							strAct.append(time4[1]);
						    String[] arc4 = addArc(p_nap2[0], t_natstart[0], "TtP", entResTypeId, b_qentResType[0], null, BtT4[0], time4[0], null, false, na.getId());
						    strAct.append(arc4[1]);
						    
						    String[] t_natend = addTransition("_natend", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
						    strAct.append(t_natend[1]);
						    
						    String[] TtB5 = addTtB(b_entResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
						    String[] BtT5 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
						    strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(p_nap2[0], t_natend[0], "PtT", entResTypeId, b_entResType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
						    strAct.append(arc5[1]);
						    
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						    strAct.append(p_nap3[1]);
						    
						    String[] BtT6 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
						    strAct.append(BtT6[1]);
							String[] arc6 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entResType[0], null, BtT6[0], null, null, false, na.getId());
							strAct.append(arc6[1]);
							
							String[] TtB7 = addTtB(b_qentResType[0], "Resource["+entTypeId+"]", "resource", "None, Some(resource), None");
							String[] BtT7 = addBtT(b_qentResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(TtB7[1]);
							strAct.append(BtT7[1]);
						    String[] arc7 = addArc(cleanId("r_", na.getResource().getId()), t_natstart[0], "PtT", "Resource["+entTypeId+"]", b_qentResType[0], TtB7[0], BtT7[0], null, null, true, na.getId());
						    strAct.append(arc7[1]);
						    
						    String[] BtT8 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
						    strAct.append(BtT8[1]);
						    String[] arc8 = addArc(cleanId("r_", na.getResource().getId()), t_natend[0], "TtP", "Resource["+entTypeId+"]", b_entResType[0], null, BtT8[0], null, null, false, na.getId());
						    strAct.append(arc8[1]);
						}
						else if(na.getQueue() != null) {
							strAct.append(queues.get(na.getQueue().getValue().getId()));
							
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qsttId = "t_"+qidClean+"_qstt";
							String p_qendpId = "p_"+qidClean+"_qendp";
							
							String[] TtB1 = addTtB(b_qentType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[0]);
							strAct.append(BtT1[0]);
							String[] arc1 = addArc(p_nap1[0], t_qsttId, "PtT", entTypeId, b_qentType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] t_natstart = addTransition("_natstart", null, null, b_qentType[0], e_qentType[0], m_qentType[0], na.getId(), null);
							
							String[] TtB2 = addTtB(b_qentType[0], qentTypeId, "entity::queue", "Some(entity), Some(queue)");
							String[] BtT2 = addBtT(b_qentType[0], qentTypeId, "b.entity.get::b.queue.get");
							strAct.append(TtB2[0]);
							strAct.append(BtT2[0]);
							String[] arc2 = addArc(p_qendpId, t_natstart[0], "PtT", qentTypeId, b_qentType[0], TtB2[0], BtT2[0], null, null, true, na.getId());
							strAct.append(arc2[1]);
							
							String[] BtT3 = addBtT(b_qentType[0], qentTypeId, "b.queue.get");
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_qendpId, t_natstart[0], "TtP", qentTypeId, b_qentType[0], null, BtT3[0], null, null, false, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							
							String[] BtT4 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							String[] time4 = addAddedTime(b_entType[0],na.getProcessingTimeExpression());
							strAct.append(BtT4[1]);
							strAct.append(time4[1]);
							String[] arc4 = addArc(p_nap2[0], t_natstart[0], "TtP", entTypeId, b_qentType[0], null, BtT4[0], time4[0], null, false, na.getId());
							strAct.append(arc4[1]);
							
							String[] t_natend = addTransition("_natend", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natend[1]);
							
							String[] TtB5 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT5 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(p_nap2[0], t_natend[0], "PtT", entTypeId, b_entType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
							strAct.append(arc5[1]);
							
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
							
							String[] BtT6 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(BtT6[1]);
							String[] arc6 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entType[0], null, BtT6[0], null, null, false, na.getId());  
							strAct.append(arc6[1]);
						}
						else if(na.getResource() != null) {
							strAct.append(resources.get(na.getResource().getValue().getId()));
							
							String[] t_natstart = addTransition("_natstart", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
						   
							String[] TtB1 = addTtB(b_entResType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_natstart[0], "PtT", entTypeId, b_entResType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
						   
							String[] p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
						   
							String[] BtT2 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
							String[] time2 = addAddedTime(b_entResType[0],na.getProcessingTimeExpression());
							strAct.append(BtT2[1]);
							strAct.append(time2[1]);
							String[] arc2 = addArc(p_nap2[0], t_natstart[0], "TtP", entResTypeId, b_entResType[0], null, BtT2[0], time2[0], null, false, na.getId());
							strAct.append(arc2[1]);
						   
							String[] t_natend = addTransition("_natend", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
							strAct.append(t_natend[1]);
						   
							String[] TtB3 = addTtB(b_entResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
							String[] BtT3 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
							String[] arc3 = addArc(p_nap2[0], t_natend[0], "PtT", entResTypeId, b_entResType[0], TtB3[0], BtT3[0], null, null, true, na.getId());
							strAct.append(arc3[1]);
						   
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
						   
							String[] BtT4 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
							strAct.append(BtT4[1]);
							String[] arc4 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entResType[0], null, BtT4[0], null, null, false, na.getId());
							strAct.append(arc4[1]);
						   
							String[] TtB5 = addTtB(b_entResType[0], "Resource["+entTypeId+"]", "resource", "None, Some(resource)");
							String[] BtT5 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(cleanId("r_", na.getResource().getId()), t_natstart[0], "PtT", "Resource["+entTypeId+"]", b_entResType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
							strAct.append(arc5[1]);
						   
							String[] BtT6 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(BtT6[1]);
							String[] arc6 = addArc(cleanId("r_", na.getResource().getId()), t_natend[0], "TtP", "Resource["+entTypeId+"]", b_entResType[0], null, BtT6[0], null, null, false, na.getId());
							strAct.append(arc6[1]);
						}
						else {
							String[] t_natstart = addTransition("_natstart", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
							
							String[] TtB1 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_natstart[0], "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
							
							String[] BtT2 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							String[] time2 = addAddedTime(b_entType[0],na.getProcessingTimeExpression());
							strAct.append(BtT2[1]);
							strAct.append(time2[1]);
							String[] arc2 = addArc(p_nap2[0], t_natstart[0], "TtP", entTypeId, b_entType[0], null, BtT2[0], time2[0], null, false, na.getId());
							strAct.append(arc2[1]);
							
							String[] t_natend = addTransition("_natend", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natend[1]);
							
							String[] TtB3 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT3 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB3[1]);
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_nap2[0], t_natend[0], "PtT", entTypeId, b_entType[0], TtB3[0], BtT3[0], null, null, true, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
							
							String[] BtT4 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(BtT4[1]);
							String[] arc4 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entType[0], null, BtT4[0], null, null, false, na.getId());
							strAct.append(arc4[1]);
						}
					}
					else {
						if(na.getQueue() != null && na.getResource() != null) {
							strAct.append(queues.get(na.getQueue().getValue().getId()));
							strAct.append(resources.get(na.getResource().getValue().getId()));
							
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qsttId = "t_"+qidClean+"_qstt";
							String p_qendpId = "p_"+qidClean+"_qendp";
							
							String[] TtB1 = addTtB(b_qentType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_qsttId, "PtT", entTypeId, b_qentType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] t_natstart = addTransition("_natstart", null, null, b_qentResType[0], e_qentResType[0], m_qentResType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
							
							String[] TtB2 = addTtB(b_qentResType[0], qentTypeId, "entity::queue", "Some(entity), None, Some(queue)");
							String[] BtT2 = addBtT(b_qentResType[0], qentTypeId, "b.entity.get::b.queue.get");
							strAct.append(TtB2[1]);
							strAct.append(BtT2[1]);
							String[] arc2 = addArc(p_qendpId, t_natstart[0], "PtT", qentTypeId, b_qentResType[0], TtB2[0], BtT2[0], null, null, true, na.getId());
							strAct.append(arc2[1]);
							
							String[] BtT3 = addBtT(b_qentResType[0], qentTypeId, "b.queue.get");
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_qendpId, t_natstart[0], "TtP", qentTypeId, b_qentResType[0], null, BtT3[0], null, null, false, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
							
							String[] BtT4 = addBtT(b_qentResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
							String[] time4 = addAddedTime(b_qentResType[0], na.getProcessingTimeExpression());
							strAct.append(BtT4[1]);
							strAct.append(time4[1]);
						    String[] arc4 = addArc(p_nap2[0], t_natstart[0], "TtP", entResTypeId, b_qentResType[0], null, BtT4[0], time4[0], null, false, na.getId());
						    strAct.append(arc4[1]);
						    
						    String[] t_natend = addTransition("_natend", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
						    strAct.append(t_natend[1]);
						    
						    String[] TtB5 = addTtB(b_entResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
						    String[] BtT5 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
						    strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(p_nap2[0], t_natend[0], "PtT", entResTypeId, b_entResType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
						    strAct.append(arc5[1]);
						    
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
						    strAct.append(p_nap3[1]);
						    
						    String[] BtT6 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
						    strAct.append(BtT6[1]);
							String[] arc6 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entResType[0], null, BtT6[0], null, null, false, na.getId());
							strAct.append(arc6[1]);
							
							String[] TtB7 = addTtB(b_qentResType[0], "Resource["+entTypeId+"]", "resource", "None, Some(resource), None");
							String[] BtT7 = addBtT(b_qentResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(TtB7[1]);
							strAct.append(BtT7[1]);
						    String[] arc7 = addArc(cleanId("r_", na.getResource().getId()), t_natstart[0], "PtT", "Resource["+entTypeId+"]", b_qentResType[0], TtB7[0], BtT7[0], null, null, true, na.getId());
						    strAct.append(arc7[1]);
						    
						    String[] BtT8 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
						    strAct.append(BtT8[1]);
						    String[] arc8 = addArc(cleanId("r_", na.getResource().getId()), t_natend[0], "TtP", "Resource["+entTypeId+"]", b_entResType[0], null, BtT8[0], null, null, false, na.getId());
						    strAct.append(arc8[1]);
						}
						else if(na.getQueue() != null) {
							strAct.append(queues.get(na.getQueue().getValue().getId()));
							
							String qidClean = cleanId(null, na.getQueue().getValue().getId());
							String t_qsttId = "t_"+qidClean+"_qstt";
							String p_qendpId = "p_"+qidClean+"_qendp";
							
							String[] TtB1 = addTtB(b_qentType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[0]);
							strAct.append(BtT1[0]);
							String[] arc1 = addArc(p_nap1[0], t_qsttId, "PtT", entTypeId, b_qentType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] t_natstart = addTransition("_natstart", null, null, b_qentType[0], e_qentType[0], m_qentType[0], na.getId(), null);
							
							String[] TtB2 = addTtB(b_qentType[0], qentTypeId, "entity::queue", "Some(entity), Some(queue)");
							String[] BtT2 = addBtT(b_qentType[0], qentTypeId, "b.entity.get::b.queue.get");
							strAct.append(TtB2[0]);
							strAct.append(BtT2[0]);
							String[] arc2 = addArc(p_qendpId, t_natstart[0], "PtT", qentTypeId, b_qentType[0], TtB2[0], BtT2[0], null, null, true, na.getId());
							strAct.append(arc2[1]);
							
							String[] BtT3 = addBtT(b_qentType[0], qentTypeId, "b.queue.get");
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_qendpId, t_natstart[0], "TtP", qentTypeId, b_qentType[0], null, BtT3[0], null, null, false, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							
							String[] BtT4 = addBtT(b_qentType[0], entTypeId, "b.entity.get");
							String[] time4 = addAddedTime(b_entType[0],na.getProcessingTimeExpression());
							strAct.append(BtT4[1]);
							strAct.append(time4[1]);
							String[] arc4 = addArc(p_nap2[0], t_natstart[0], "TtP", entTypeId, b_qentType[0], null, BtT4[0], time4[0], null, false, na.getId());
							strAct.append(arc4[1]);
							
							String[] t_natend = addTransition("_natend", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natend[1]);
							
							String[] TtB5 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT5 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(p_nap2[0], t_natend[0], "PtT", entTypeId, b_entType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
							strAct.append(arc5[1]);
							
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
							
							String[] BtT6 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(BtT6[1]);
							String[] arc6 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entType[0], null, BtT6[0], null, null, false, na.getId());  
							strAct.append(arc6[1]);
						}
						else if(na.getResource() != null) {
							strAct.append(resources.get(na.getResource().getValue().getId()));
							
							String[] t_natstart = addTransition("_natstart", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
						   
							String[] TtB1 = addTtB(b_entResType[0], entTypeId, "entity", "Some(entity), None");
							String[] BtT1 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_natstart[0], "PtT", entTypeId, b_entResType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
						   
							String[] p_nap2 = addPlace(null, "_nap2", entResTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
						   
							String[] BtT2 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
							String[] time2 = addAddedTime(b_entResType[0],na.getProcessingTimeExpression());
							strAct.append(BtT2[1]);
							strAct.append(time2[1]);
							String[] arc2 = addArc(p_nap2[0], t_natstart[0], "TtP", entResTypeId, b_entResType[0], null, BtT2[0], time2[0], null, false, na.getId());
							strAct.append(arc2[1]);
						   
							String[] t_natend = addTransition("_natend", null, null, b_entResType[0], e_entResType[0], m_entResType[0], na.getId(), null);
							strAct.append(t_natend[1]);
						   
							String[] TtB3 = addTtB(b_entResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
							String[] BtT3 = addBtT(b_entResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
							String[] arc3 = addArc(p_nap2[0], t_natend[0], "PtT", entResTypeId, b_entResType[0], TtB3[0], BtT3[0], null, null, true, na.getId());
							strAct.append(arc3[1]);
						   
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
						   
							String[] BtT4 = addBtT(b_entResType[0], entTypeId, "b.entity.get");
							strAct.append(BtT4[1]);
							String[] arc4 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entResType[0], null, BtT4[0], null, null, false, na.getId());
							strAct.append(arc4[1]);
						   
							String[] TtB5 = addTtB(b_entResType[0], "Resource["+entTypeId+"]", "resource", "None, Some(resource)");
							String[] BtT5 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(TtB5[1]);
							strAct.append(BtT5[1]);
							String[] arc5 = addArc(cleanId("r_", na.getResource().getId()), t_natstart[0], "PtT", "Resource["+entTypeId+"]", b_entResType[0], TtB5[0], BtT5[0], null, null, true, na.getId());
							strAct.append(arc5[1]);
						   
							String[] BtT6 = addBtT(b_entResType[0], "Resource["+entTypeId+"]", "b.resource.get");
							strAct.append(BtT6[1]);
							String[] arc6 = addArc(cleanId("r_", na.getResource().getId()), t_natend[0], "TtP", "Resource["+entTypeId+"]", b_entResType[0], null, BtT6[0], null, null, false, na.getId());
							strAct.append(arc6[1]);
						}
						else {
							String[] t_natstart = addTransition("_natstart", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natstart[1]);
							
							String[] TtB1 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB1[1]);
							strAct.append(BtT1[1]);
							String[] arc1 = addArc(p_nap1[0], t_natstart[0], "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, na.getId());
							strAct.append(arc1[1]);
							
							String[] p_nap2 = addPlace(null, "_nap2", entTypeId, "", na.getId(), null);
							strAct.append(p_nap2[1]);
							
							String[] BtT2 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							String[] time2 = addAddedTime(b_entType[0],na.getProcessingTimeExpression());
							strAct.append(BtT2[1]);
							strAct.append(time2[1]);
							String[] arc2 = addArc(p_nap2[0], t_natstart[0], "TtP", entTypeId, b_entType[0], null, BtT2[0], time2[0], null, false, na.getId());
							strAct.append(arc2[1]);
							
							String[] t_natend = addTransition("_natend", null, null, b_entType[0], e_entType[0], m_entType[0], na.getId(), null);
							strAct.append(t_natend[1]);
							
							String[] TtB3 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
							String[] BtT3 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(TtB3[1]);
							strAct.append(BtT3[1]);
							String[] arc3 = addArc(p_nap2[0], t_natend[0], "PtT", entTypeId, b_entType[0], TtB3[0], BtT3[0], null, null, true, na.getId());
							strAct.append(arc3[1]);
							
							String[] p_nap3 = addPlace(na.getId()+"_start_0", "_nap3", entTypeId, "", na.getId(), null);
							strAct.append(p_nap3[1]);
							
							String[] BtT4 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strAct.append(BtT4[1]);
							String[] arc4 = addArc(p_nap3[0], t_natend[0], "TtP", entTypeId, b_entType[0], null, BtT4[0], null, null, false, na.getId());
							strAct.append(arc4[1]);
						}
					}
					
					strAct.append("\ncgraph");
					convm.put(na.getId(), strAct.toString());
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					//System.out.println("Branch "+b.getId());
					
					StringBuilder strBr = new StringBuilder();
					strBr.append(strGen.toString());
					
					String btid = null;
					String bpid = null;
					
					if (b.getGate() == BranchGate.AND) { 
						String[] bt = addTransition("_bt", null, null, b_entType[0], e_entType[0], m_entType[0], b.getId(), null);
						strBr.append(bt[1]);
						btid = bt[0];
					}
					
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getTarget().getValue() != b) continue;
							String[] p_bpis = addPlace(b.getId()+"_end_"+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
							strBr.append(p_bpis[1]);
							
							String[] TtB1 = addTtB(b_entType[0], entTypeId, "entitiy", "Some(entity)");
							String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strBr.append(TtB1[1]);
							strBr.append(BtT1[1]);
							String[] arc1 = addArc(p_bpis[0], btid, "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, b.getId());
							strBr.append(arc1[1]);
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getTarget().getValue() != b) continue;
								String[] p_bpis = addPlace(b.getId()+"_end_"+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(p_bpis[1]);
								
								String actiondef = "val newb = b.copy(cond = Some(scala.util.Random.nextDouble()));newb";
								String[] action = addAction(b_entBrType[0], actiondef);
								strBr.append(action[1]);
								
								String[] t_condGen = addTransition("_condGen", null, action[0], b_entBrType[0], e_entBrType[0], m_entBrType[0], b.getId(), null);
								strBr.append(t_condGen[1]);
								String[] p_bpt = addPlace(null, "_bpt", entBrTypeId, "", b.getId(), null);
								strBr.append(p_bpt[1]);
								
								bpid = p_bpt[0];
								
								String[] TtB1 = addTtB(b_entBrType[0], entTypeId, "entity", "Some(entity),None");
								String[] BtT1 = addBtT(b_entBrType[0], entTypeId, "b.entity.get");
								strBr.append(TtB1[1]);
								strBr.append(BtT1[1]);
								String[] arc1 = addArc(p_bpis[0], t_condGen[0], "PtT", entTypeId, b_entBrType[0], TtB1[0], BtT1[0], null, null, true, b.getId());
								strBr.append(arc1[1]);
								
								String[] TtB2 = addTtB(b_entBrType[0], entBrTypeId, "(entity,cond)", "Some(entity),Some(cond)");
								String[] BtT2 = addBtT(b_entBrType[0], entBrTypeId, "(b.entity.get,b.cond.get)");
								strBr.append(TtB2[1]);
								strBr.append(BtT2[1]);
								String[] arc2 = addArc(p_bpt[0], t_condGen[0], "TtP", entBrTypeId, b_entBrType[0], TtB2[0], BtT2[0], null, null, false, b.getId());
								strBr.append(arc2[1]);
							}
							else {
								if (c.getSource().getValue() != b) continue;
								String[] p_bpos = addPlace(b.getId()+"_start_"+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(p_bpos[1]);
								
								bpid = p_bpos[0];
							}
						}
						//System.out.println("Source "+c.getSource().getId());
						i++;
					}
					
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getSource().getValue() != b) continue;
							String[] p_bpos = addPlace(b.getId()+"_start_"+c.getSourceIndex(), "_bpo_" + i + "s", entTypeId, "", b.getId(), null);
							strBr.append(p_bpos[1]);
							String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
							strBr.append(BtT1[1]);
							String[] arc1 = addArc(p_bpos[0], btid, "TtP", entTypeId, b_entType[0], null, BtT1[0], null, null, false, b.getId());
							strBr.append(arc1[1]);
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getSource().getValue() != b) continue;
								String guardid = null;
								if(b.getRule() == BranchRule.DATA)
								{
									//not yet defined in high level
								}
								else if (b.getRule() == BranchRule.CONDITION) {
									String guarddef = "b.cond.get "+c.getAttributes().get("condition");
									String[] guard = addGuard(b_entBrType[0], guarddef);
									guardid = guard[0];
									strBr.append(guard[1]);
								}
								String[] p_bpos = addPlace(b.getId()+"_start_"+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(p_bpos[1]);
								String[] t_silent = addTransition("_temps_"+i, guardid, null, b_entBrType[0], e_entBrType[0], m_entBrType[0], b.getId(), null);
								strBr.append(t_silent[1]);
								
								String[] TtB1 = addTtB(b_entBrType[0], entBrTypeId, "(entity,cond)", "Some(entity),Some(cond)");
								String[] BtT1 = addBtT(b_entBrType[0], entBrTypeId, "(b.entity.get,b.cond.get)");
								strBr.append(TtB1[1]);
								strBr.append(BtT1[1]);
								String[] arc1 = addArc(bpid, t_silent[0], "PtT", entBrTypeId, b_entBrType[0], TtB1[0], BtT1[0], null, null, true, b.getId());
								strBr.append(arc1[1]);
								
								String[] BtT2 = addBtT(b_entBrType[0], entTypeId, "b.entity.get");
								strBr.append(BtT2[1]);
								String[] arc2 = addArc(p_bpos[0], t_silent[0], "TtP", entTypeId, b_entBrType[0], null, BtT2[0], null, null, false, b.getId());
								strBr.append(arc2[1]);
							}
							else {
								if (c.getTarget().getValue() != b) continue;
								String[] p_bpis = addPlace(b.getId()+"_end_"+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(p_bpis[1]);
								String[] t_silent = addTransition("_temps_"+i, null, null, b_entType[0], e_entType[0], m_entType[0], b.getId(), null);
								strBr.append(t_silent[1]);
								
								String[] TtB1 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
								String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
								strBr.append(TtB1[1]);
								strBr.append(BtT1[1]);
								String[] arc1 = addArc(p_bpis[0], t_silent[0], "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, b.getId());
								strBr.append(arc1[1]);
								
								String[] BtT2 = addBtT(b_entType[0], entTypeId, "b.entity.get");
								strBr.append(BtT2[1]);
								String[] arc2 = addArc(bpid, t_silent[0], "TtP", entTypeId, b_entType[0], null, BtT2[0], null, null, false, b.getId());
								strBr.append(arc2[1]);
							}
						}
						//System.out.println("Target "+c.getTarget().getId());
						i++;
					}
					
					strBr.append("\ncgraph");
					convm.put(b.getId(), strBr.toString());
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
				
				StringBuilder strCon = new StringBuilder();
				strCon.append(strGen.toString());
				
				//System.out.println("Source HL : "+c.getSource().getId());
				//System.out.println("Target HL : "+c.getTarget().getId());
				
				String[] source = placeshub.get(c.getSource().getId()+"_start_"+c.getSourceIndex());
				String[] target = placeshub.get(c.getTarget().getId()+"_end_"+c.getTargetIndex());
				
				strCon.append(source[1]);
				strCon.append(target[1]);
				
				//System.out.println(source[0]+" - "+target[0]);
				
				
				String[] oriRole = target[0].contains("-stop-") ? new String[]{target[0],"_stop"} : new String[]{c.getId(), "silent"};
				String[] t_silent = addTransition(oriRole[1], null, null, b_entType[0], e_entType[0], m_entType[0], oriRole[0], null);
				strCon.append(t_silent[1]);
				
				String[] TtB1 = addTtB(b_entType[0], entTypeId, "entity", "Some(entity)");
				String[] BtT1 = addBtT(b_entType[0], entTypeId, "b.entity.get");
				strCon.append(TtB1[1]);
				strCon.append(BtT1[1]);
				String[] arc1 = addArc(source[0], t_silent[0], "PtT", entTypeId, b_entType[0], TtB1[0], BtT1[0], null, null, true, c.getId());
				strCon.append(arc1[1]);
				
				String[] BtT2 = addBtT(b_entType[0], entTypeId, "b.entity.get");
				strCon.append(BtT2[1]);
				String[] arc2 = addArc(target[0], t_silent[0], "TtP", entTypeId, b_entType[0], null, BtT2[0], null, null, false, c.getId());
				strCon.append(arc2[1]);
				
				strCon.append("\ncgraph");
				convm.put(c.getId(), strCon.toString());
			}
		}
		
		result.setConvertedModel(convm);
		
		return result;
	}
	
	/**
	 * @param prefix
	 * @param oriId
	 * @return
	 */
	public String cleanId(String prefix, String oriId) {
		String modId = oriId.replace("-", "");
		
		if(prefix != null) 
			modId = prefix+modId;
		
		return modId;
	}
	
	public IsmGraph revert(Ism2CpnscalaModelPerModule cgraph) {
		return null;
	}
	
}
