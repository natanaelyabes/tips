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
		String[] idDef = new String[3];
		
		String counter = getCounter(KeyElement.PLACE);
		String mapid = "map"+counter;
		String multisetid = "ms"+counter;
		String placeidClean = placeId == null ? "p_"+counter : cleanId("p_", placeId);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val "+mapid+" = Map[("+type+",Long),Int]( "+initialMarking+" )\n" );
		placefactory.append( "val "+multisetid+" = new Multiset["+type+"]("+mapid+")\n" );
		placefactory.append( "val "+placeidClean+" = new Place(\""+placeidClean+"\",\""+name+"\","+multisetid+","+mapid+".clone())\n" );
		placefactory.append( placeidClean+KeyElement.SETORIGINCONC+origin+KeyElement.ROLECONC+name+KeyElement.ENDCOLCONC );
		placefactory.append( "cgraph.addPlace("+placeidClean+")\n" );
		placefactory.append("\n");
		
		idDef[0] = placeidClean;
		idDef[1] = placefactory.toString();
		idDef[2] = origin;
		
		if(hubid != null)
			placeshub.put(hubid, idDef);
		
		return idDef;
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
	public String[] addTransition(String name, String guard, String action, String classbinding, String eval, String merge, String origin, String transId, String isPartOfLot, String isWaitOfLot, String compName) {
		String[] idDef = new String[3];
		
		String counter = getCounter(KeyElement.TRANSITION);
		String transidClean = transId == null ? "t_"+counter : cleanId("t_", transId);
		
		if(guard == null)
			guard = "null";
		if(action == null)
			action = "null";
		
		StringBuilder transfactory = new StringBuilder();
		transfactory.append( "val "+transidClean+" = new Transition["+classbinding+"](\""+transidClean+"\",\""+name+"\","+guard+","+action+")\n" );
		transfactory.append( transidClean+".setEval("+eval+")\n" );
		transfactory.append( transidClean+".setMerge("+merge+")\n" );
		if(isPartOfLot != null) {
			transfactory.append( transidClean+".setIsPartOfLot("+isPartOfLot+")\n" );
		}
		if(isWaitOfLot != null) {
			transfactory.append( transidClean+".setIsWaitOfLot("+isWaitOfLot+")\n" );
		}
		transfactory.append( transidClean+".setOrigin(Map[String,String]( (\"origin\",\""+origin+"\"),(\"role\",\""+name+"\"),(\"compName\",\""+compName+"\") ))\n");
		transfactory.append( "cgraph.addTransition("+transidClean+")\n" );
		transfactory.append("\n");
		
		idDef[0] = transidClean;
		idDef[1] = transfactory.toString();
		idDef[2] = origin;
		
		return idDef;
	}
	
	/**
	 * @param classbinding
	 * @param guarddef
	 * @return
	 */
	public String[] addGuard(String classbinding, String guarddef, String guarddefFin) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.GUARD);
		String guardid = KeyElement.GUARD+counter;
		String bindguardexpid = "BindGuard"+counter;
		String bindguardexpidFin = "BindGuardFin"+counter;
		
		StringBuilder guardfactory = new StringBuilder();
		guardfactory.append( "val "+guardid+" = new Guard["+classbinding+"]()\n" );
		guardfactory.append( "val "+bindguardexpid+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+guarddef+"}\n" );
		guardfactory.append( guardid+".setGuardBind("+bindguardexpid+")\n" );
		if(guarddefFin != null) {
			guardfactory.append( "val "+bindguardexpidFin+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+guarddefFin+"}\n" );
			guardfactory.append( guardid+".setGuardBindFinish("+bindguardexpidFin+")\n" );
		}
		
		idDef[0] = guardid;
		idDef[1] = guardfactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param classbinding
	 * @param actionfundef
	 * @return
	 */
	public String[] addAction(String classbinding, String actionfundef) {
		String[] idDef = new String[2];
		
		StringBuilder actionfactory = new StringBuilder();
		
		String counter = getCounter(KeyElement.ACTION);
		String actionid = "action"+counter;
		String actionfunid = "actionFun"+counter;
		String actionfun = "def "+actionfunid+"(b:"+classbinding+"):"+classbinding+" = { "+actionfundef+" }";
		
		actionfactory.append( actionfun+"\n" );
		actionfactory.append("\n");
		
		actionfactory.append( "val "+actionid+" = new Action["+classbinding+"]()\n" );
		actionfactory.append( actionid+".setActionFun("+actionfunid+")\n" );
		actionfactory.append("\n");
		
		idDef[0] = actionid;
		idDef[1] = actionfactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param classdef
	 * @return
	 */
	public String[] addBindingClass(String classdef) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.BINDING);
		String bindingid = KeyElement.BINDING+counter;
		
		StringBuilder cBindingfactory = new StringBuilder();
		cBindingfactory.append( "case class "+bindingid+"(").append(classdef).append(")\n" );
		
		idDef[0] = bindingid;
		idDef[1] = cBindingfactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param evaldef
	 * @param classbinding
	 * @return
	 */
	public String[] addEval(String evaldef, String classbinding) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.EVAL);
		String evalid = "Eval"+counter;
		
		StringBuilder evalfactory = new StringBuilder();
		evalfactory.append( "val "+evalid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		evalfactory.append( "\t"+evaldef+"\n" );
		evalfactory.append( "}\n" );
		
		idDef[0] = evalid;
		idDef[1] = evalfactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param mergedef
	 * @param classbinding
	 * @param mergeassign
	 * @return
	 */
	public String[] addMerge(String mergedef, String classbinding, String mergeassign) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.MERGE);
		String mergeid = KeyElement.MERGE+counter;
		
		StringBuilder mergefactory = new StringBuilder();
		mergefactory.append( "val "+mergeid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		mergefactory.append( "\t"+mergedef+"\n" );
		mergefactory.append( "\t"+classbinding+"("+mergeassign+")\n" );
		mergefactory.append( "}\n" );
		
		idDef[0] = mergeid;
		idDef[1] = mergefactory.toString();
		
		return idDef;
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
	public String[] addArc(String placeid, String transitionid, String direction, String type, String classbinding, String tTb, String bTt, String addTime, String noToken, boolean isBase, String origin) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.ARC);
		String arcid = KeyElement.ARC+counter;
		
		StringBuilder arcfactory = new StringBuilder();
		arcfactory.append( "val "+arcid+" = new Arc["+type+","+classbinding+"](\""+arcid+"\","+placeid+","+transitionid+",Direction."+direction+")\n" );
		arcfactory.append( arcid+".setIsBase("+isBase+")\n" );
		if(isBase && tTb != null)
			arcfactory.append( arcid+".setTokenToBind("+tTb+")\n" );
		arcfactory.append( arcid+".setBindToToken("+bTt+")\n" );
		if(addTime != null)
			arcfactory.append( arcid+".setAddTime("+addTime+")\n" );
		if(noToken != null)
			arcfactory.append( arcid+".setNoTokArcExp("+noToken+")\n" );
		arcfactory.append( arcid+KeyElement.SETORIGINCONC+origin+KeyElement.ROLECONC+arcid+KeyElement.ENDCOLCONC );
		arcfactory.append( "cgraph.addArc("+arcid+")\n" );
		arcfactory.append("\n");
		
		idDef[0] = arcid;
		idDef[1] = arcfactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param classbinding
	 * @param type
	 * @param inverse
	 * @param tTbDef
	 * @return
	 */
	public String[] addTtB(String classbinding, String type, String inverse, String tTbDef) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.TTB);
		String tTbID = KeyElement.TTB+counter;
		
		StringBuilder tTbFactory = new StringBuilder();
		tTbFactory.append( "val "+tTbID+" = (t:"+type+") => { try { val "+inverse+" = t;Some("+classbinding+"("+tTbDef+")) } catch { case e: Exception => { e.printStackTrace();None } } }\n" );
		
		idDef[0] = tTbID;
		idDef[1] = tTbFactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param classbinding
	 * @param type
	 * @param BtTdef
	 * @return
	 */
	public String[] addBtT(String classbinding, String type, String bTtDef) {
		String[] idDef = new String[2];
		
		String counter = getCounter(KeyElement.BTT);
		String bTtID = KeyElement.BTT+counter;
		
		StringBuilder bTtFactory = new StringBuilder();
		bTtFactory.append( "val "+bTtID+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+bTtDef+"}:"+type+"\n" );
		
		idDef[0] = bTtID;
		idDef[1] = bTtFactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param classbinding
	 * @param addedTimedef
	 * @return
	 */
	public String[] addAddedTime(String classbinding, String addedTimedef) {
		String[] idDef = new String[2];
		
		if(addedTimedef == null || addedTimedef.length() <= 1)
			addedTimedef = "0L";
		
		String counter = getCounter(KeyElement.ADDTIME);
		String addTimeid = KeyElement.ADDTIME+counter;
		
		StringBuilder addTimefactory = new StringBuilder();
		addTimefactory.append( "val "+addTimeid+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+addedTimedef+"}\n" );
		
		idDef[0] = addTimeid;
		idDef[1] = addTimefactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param resId
	 * @param typecaseId
	 * @return
	 */
	public String[] createResourcePlace(String presidClean, String resId, String typecaseId) {
		String[] idDef = new String[2];
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val ms"+presidClean+" = new Multiset[Resource["+typecaseId+"]](map"+presidClean+",Resource.getClass)\n" );
		placefactory.append( "val "+presidClean+" = new Place(\""+presidClean+"\",\"Resource Place\",ms"+presidClean+",map"+presidClean+".clone())\n" );
		placefactory.append( presidClean+KeyElement.SETORIGINCONC+resId+"\"),(\"role\",\"_resp\")))\n" );
		placefactory.append( "cgraph.addPlace("+presidClean+")\n" );
		placefactory.append("\n");
		
		idDef[0] = presidClean;
		idDef[1] = placefactory.toString();
		
		return idDef;
	}
	
	/**
	 * @param resId
	 * @param name
	 * @param numbofresource
	 * @return
	 */
	public String addResource(String residClean, String name, int numbofresource, int capacity, String entTypeId) {
		StringBuilder resfactory = new StringBuilder();
		for(int i=1; i<=numbofresource; i++) {
			String residInScala = residClean+"_"+i;
			resfactory.append( "val "+residInScala+" = new Resource["+entTypeId+"](\""+residInScala+"\", \""+name+"\", 0L, List(), "+capacity+")\n" );
			resfactory.append( "map"+residClean+".put( ("+residInScala+",0L), 1)\n" );
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
		
		String[] pendqueue = addPlace(null, KeyElement.QENDPSTRCONC, ltypeid, "(("+listid+",0),1)", origin, queue.getId()+KeyElement.QENDPSTRCONC);
		queuefactory.append(pendqueue[1]);
		
		String guarddef = "b.queue.get.length < "+queue.getSize();
		
		String guardid = null;
		
		if(queue.getSize() > 0) {
			String[] guard = addGuard(cb, guarddef, null);
			queuefactory.append(guard[1]);
			guardid = guard[0];
		}
		
		String[] tstartqueue = addTransition(KeyElement.QSTTSTRCONC, guardid, null, cb, ev, mg, origin, queue.getId()+KeyElement.QSTTSTRCONC, null, null, origin);
		queuefactory.append(tstartqueue[1]);
		
		String[] tTb1 = addTtB(cb, ltypeid, "queue", "None, Some(queue)");
		queuefactory.append(tTb1[1]);
		
		String[] bTt1 = addBtT(cb, ltypeid, KeyElement.BQGETCONC);
		queuefactory.append(bTt1[1]);
		
		String[] arc1 = addArc(pendqueue[0], tstartqueue[0], "PtT", ltypeid, cb, tTb1[0], bTt1[0], null, null, true, origin);
		queuefactory.append(arc1[1]);
		
		if(queue.getType() == Queue.QUEUE_TYPE.FIFO) {
			String[] btT2 = addBtT(cb, ltypeid, KeyElement.BENTTQGETCONC);
			queuefactory.append(btT2[1]);
			String[] arc2 = addArc(pendqueue[0], tstartqueue[0], "TtP", ltypeid, cb, null, btT2[0], null, null, false, origin);
			queuefactory.append(arc2[1]);
		}
		else {
			String[] btT2 = addBtT(cb, ltypeid, "b.queue.get:::List(b.entity.get)");
			queuefactory.append(btT2[1]);
			String[] arc2 = addArc(pendqueue[0], tstartqueue[0], "TtP", ltypeid, cb, null, btT2[0], null, null, false, origin);
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
			
			String pCaseData = "pCaseData";
			
			StringBuilder strGen = new StringBuilder();
			
			String typeData = "type TypeData = {val inc:Int}\n";
			strGen.append(typeData);
			
			String caseData = "case class CaseData(inc:Int)\n";
			strGen.append(caseData);
			
			String entTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			String entType = KeyElement.TYPECONC+entTypeId+" = (Int,String)\n";
			strGen.append(entType);
			
			String[] bEntType = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"]" );
			String[] eEntType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None)", bEntType[0]);
			String[] mEntType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;", bEntType[0], KeyElement.ENTCONC);
			strGen.append(bEntType[1]);
			strGen.append(eEntType[1]);
			strGen.append(mEntType[1]);
			
			String dataTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			String dataType = KeyElement.TYPECONC+dataTypeId+" = ((Int,String),TypeData)\n";
			strGen.append(dataType);
			
			String resTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			String resType = KeyElement.TYPECONC+resTypeId+" = Resource["+entTypeId+"]\n";
			strGen.append(resType);
			
			String[] bResType = addBindingClass( "resource:Option["+resTypeId+"]" );
			String[] eResType = addEval("(b1.resource == b2.resource || b1.resource == None || b2.resource == None)", bResType[0]);
			String[] mResType = addMerge("val resource = if(b1.resource == None) b2.resource else b1.resource;", bResType[0], "resource");
			strGen.append(bResType[1]);
			strGen.append(eResType[1]);
			strGen.append(mResType[1]);
			
			String[] bResStrType = addBindingClass( "resource:Option["+resTypeId+"], id:Option[String]" );
			String[] eResStrType = addEval("(b1.resource == b2.resource || b1.resource == None || b2.resource == None) && (b1.id == b2.id || b1.id == None || b2.id == None)", bResStrType[0]);
			String[] mResStrType = addMerge("val resource = if(b1.resource == None) b2.resource else b1.resource; val id = if(b1.id == None) b2.id else b1.id;", bResStrType[0], "resource,id");
			strGen.append(bResStrType[1]);
			strGen.append(eResStrType[1]);
			strGen.append(mResStrType[1]);
			
			String qentTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			String qentType = KeyElement.TYPECONC+qentTypeId+" = List["+entTypeId+"]\n";
			strGen.append(qentType);
			
			String[] bQentType = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"], queue:Option["+qentTypeId+"]" );
			String[] eQentType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", bQentType[0]);
			String[] mQentType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;val queue = if(b1.queue == None) b2.queue else b1.queue;", bQentType[0], "entity,queue");
			strGen.append(bQentType[1]);
			strGen.append(eQentType[1]);
			strGen.append(mQentType[1]);
			
			String entResTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			String entResType = KeyElement.TYPECONC+entResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"])\n";
			strGen.append(entResType);
			
			String[] bEntResType = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"], resource:Option[Resource["+entTypeId+"]]" );
			String[] eEntResType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None)", bEntResType[0]);
			String[] mEntResType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource;", bEntResType[0], "entity,resource");
			strGen.append(bEntResType[1]);
			strGen.append(eEntResType[1]);
			strGen.append(mEntResType[1]);
			
			String[] bDataResType = addBindingClass( "entity:Option["+entTypeId+"], data:Option[TypeData], resource:Option[Resource["+entTypeId+"]]" );
			String[] eDataResType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.data == b2.data || b1.data == None || b2.data == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None)", bDataResType[0]);
			String[] mDataResType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val data = if(b1.data == None) b2.data else b1.data; val resource = if(b1.resource == None) b2.resource else b1.resource;", bDataResType[0], "entity,data,resource");
			strGen.append(bDataResType[1]);
			strGen.append(eDataResType[1]);
			strGen.append(mDataResType[1]);
			
			String qentResTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			String qentResType = KeyElement.TYPECONC+qentResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"], List["+entTypeId+"])\n";
			strGen.append(qentResType);
			
			String[] bQentResType = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"], resource:Option[Resource["+entTypeId+"]], queue:Option[List["+entTypeId+"]]" );
			String[] eQentResType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", bQentResType[0]);
			String[] mQentResType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource; val queue = if(b1.queue == None) b2.queue else b1.queue;", bQentResType[0], "entity,resource,queue");
			strGen.append(bQentResType[1]);
			strGen.append(eQentResType[1]);
			strGen.append(mQentResType[1]);
			
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				result.getConversionMap().put(d.getId(), d);
				if (d instanceof ObjectType) {
					// TODO:
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					
					StringBuilder strGenerator = new StringBuilder();
					strGenerator.append(strGen.toString());
					
					String typeId = "Int";
					
					String[] pDgp1 = addPlace(null, "_dgp1", typeId, "((1,0),1)", dg.getId(), null);
					String[] pDgp2 = addPlace(dg.getId()+ KeyElement.START0STRCONC, "_dgp2", entTypeId, "", dg.getId(), null);
					String[] pDgpData = addPlace(pCaseData, "_dgpData", dataTypeId, "", dg.getId(), pCaseData);
					strGenerator.append(pDgp1[1]);
					strGenerator.append(pDgp2[1]);
					strGenerator.append(pDgpData[1]);
					
					String[] bDgt1 = addBindingClass( "tid:Option[Int],gid:Option[String],data:Option[TypeData]" );
					String[] eDgt1 = addEval("(b1.tid == b2.tid || b1.tid == None || b2.tid == None) && (b1.gid == b2.gid || b1.gid == None || b2.gid == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", bDgt1[0]);
					String[] mDgt1 = addMerge("val tid = if(b1.tid == None) b2.tid else b1.tid;val gid = if(b1.gid == None) b2.gid else b1.gid;val data = if(b1.data == None) b2.data else b1.data;", bDgt1[0], "tid,gid,data");
					strGenerator.append(bDgt1[1]);
					strGenerator.append(eDgt1[1]);
					strGenerator.append(mDgt1[1]);
					
					String[] guard = dg.getMaxArrival() > 0 ? addGuard(bDgt1[0], "b.tid.get <= "+dg.getMaxArrival(), null) : new String[]{null, null};
					String[] action = addAction(bDgt1[0],
							//"val r = new java.util.Random()\n"
							//+ "val rint = r.nextInt();val gid = \""+dg.getId()+"\"\n"
							//+ "val data = CaseData(\"atr\"+rint,rint)\n"
							"val gid = \""+dg.getId()+"\";val data = CaseData(0)\n"
							+ bDgt1[0]+"(b.tid,Some(gid),Some(data))");
					
					String[] tDgt1 = addTransition("_dgt1", guard[0], action[0], bDgt1[0], eDgt1[0], mDgt1[0], dg.getId(), null, null, null, dg.getLabel());
					if(guard[1] != null)
						strGenerator.append(guard[1]);
					strGenerator.append(action[1]);
					strGenerator.append(tDgt1[1]);
					
					String[] tTb1 = addTtB(bDgt1[0], typeId, "tid", "Some(tid), None, None");
					String[] bTt1 = addBtT(bDgt1[0], typeId, "b.tid.get");
					strGenerator.append(tTb1[1]);
					strGenerator.append(bTt1[1]);	
					String[] arc1 = addArc(pDgp1[0], tDgt1[0], "PtT", typeId, bDgt1[0], tTb1[0], bTt1[0], null, null, true, dg.getId());
					strGenerator.append(arc1[1]);
				
					String[] btT2 = addBtT(bDgt1[0], typeId, "b.tid.get + 1");
					String[] time2 = addAddedTime(bDgt1[0],dg.getExpression());
					strGenerator.append(btT2[1]);
					strGenerator.append(time2[1]);
					String[] arc2 = addArc(pDgp1[0], tDgt1[0], "TtP", typeId, bDgt1[0], null, btT2[0], time2[0], null, false, dg.getId());
					strGenerator.append(arc2[1]);
					
					String[] btT3 = addBtT(bDgt1[0], entTypeId, "(b.tid.get,b.gid.get)");
					strGenerator.append(btT3[1]);
					String[] arc3 = addArc(pDgp2[0], tDgt1[0], "TtP", entTypeId, bDgt1[0], null, btT3[0], null, null, false, dg.getId());
					strGenerator.append(arc3[1]);
					
					String[] btT4 = addBtT(bDgt1[0], dataTypeId, "((b.tid.get,b.gid.get),b.data.get)");
					strGenerator.append(btT4[1]);
					String[] arc4 = addArc(pDgpData[0], tDgt1[0], "TtP", dataTypeId, bDgt1[0], null, btT4[0], null, null, false, dg.getId());
					strGenerator.append(arc4[1]);
					
					strGenerator.append(KeyElement.GRAPHENDCONC);
					convm.put(dg.getId(), strGenerator.toString());
				}
				if (d instanceof Function) {
					// TODO:
				}
				if (d instanceof Queue) {
					Queue q = (Queue) d;
					queues.put(q.getId(),addQueue(entTypeId, qentTypeId, bQentType[0], eQentType[0], mQentType[0], q, ""));
				}
				if (d instanceof Resource) {
					Resource r = (Resource) d;
					
					StringBuilder strRes = new StringBuilder();
					String presidClean = cleanId("r_", r.getId());
					strRes.append( "val map"+presidClean+" = Map[(Resource["+entTypeId+"],Long),Int]()\n" );
					strRes.append(addResource(presidClean, r.getLabel(), r.getNumberOfResource(), 1, entTypeId));
					strRes.append(createResourcePlace(presidClean, r.getId(), entTypeId)[1]);
					resources.put(r.getId(), strRes.toString());
				}
				if (d instanceof DataTable) {
//					// TODO:
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
					
					String[] pstart = addPlace(na.getId()+KeyElement.START0STRCONC, "_nap1", entTypeId, "", na.getId(), null);
					strStart.append(pstart[1]);
					
					if (na.getGenerator() != null) {
						String[] tSilent = addTransition("_start", null, null, bEntType[0], eEntType[0], mEntType[0], na.getId(), na.getGenerator().getValue().getId()+"_mergegen", null, null, na.getLabel());
						strStart.append(tSilent[1]);
						
						String[] pGenStart = placeshub.get(na.getGenerator().getId()+KeyElement.START0STRCONC);
						strStart.append(pGenStart[1]);
						
						String[] tTb1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						strStart.append(tTb1[1]);
						String[] bTt1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strStart.append(bTt1[1]);
						String[] arc1 = addArc(pGenStart[0], tSilent[0], "PtT", entTypeId, bEntType[0], tTb1[0], bTt1[0], null, null, true, na.getGenerator().getValue().getId());
						strStart.append(arc1[1]);
						
						String[] btT2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strStart.append(btT2[1]);
						String[] arc2 = addArc(pstart[0], tSilent[0], "TtP", entTypeId, bEntType[0], null, btT2[0], null, null, false, na.getGenerator().getValue().getId());
						strStart.append(arc2[1]);
					}
					
					strStart.append(KeyElement.GRAPHENDCONC);
					convm.put(na.getId(), strStart.toString());
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					
					StringBuilder strStop = new StringBuilder();
					strStop.append(strGen.toString());
					
					String[] pstop = addPlace(no.getId()+"_end_0", "_nop1", entTypeId, "", no.getId(), no.getId());
					strStop.append(pstop[1]);
					
					strStop.append(KeyElement.GRAPHENDCONC);
					convm.put(no.getId(), strStop.toString());
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					
					StringBuilder strAct = new StringBuilder();
					strAct.append(strGen.toString());
					
					String[] pNap1 = addPlace(na.getId()+"_end_0", "_nap1", entTypeId, "", na.getId(), null);
					strAct.append(pNap1[1]);
					
					if(na.getType() == ActivityType.CONCURRENT_BATCH && na.getResource() != null) {
						String guarddefWait = "b.resource.get.capacity > b.resource.get.jobList.size";
						String[] guardWait = addGuard(bEntResType[0], guarddefWait, null);
						strAct.append(guardWait[1]);
						
						String[] tTmpWait = addTransition("tTmpWait", guardWait[0], null, bEntResType[0], eEntResType[0], mEntResType[0], "tmpWait", null, null, "true", na.getLabel());
						strAct.append(tTmpWait[1]);
						
						String[] tTbTmpWait1 = addTtB(bEntResType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMENONECONC);
						String[] bTtTmpWait1 = addBtT(bEntResType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTbTmpWait1[1]);
						strAct.append(bTtTmpWait1[1]);
						String[] arcTmpWait1 = addArc(pNap1[0], tTmpWait[0], "PtT", entTypeId, bEntResType[0], tTbTmpWait1[0], bTtTmpWait1[0], null, null, true, "tmpWait");
						strAct.append(arcTmpWait1[1]);
						
						strAct.append(resources.get(na.getResource().getValue().getId()));
						
						String[] tTbTmpWait2 = addTtB(bEntResType[0], resTypeId, "resource", "None, Some(resource)");
						String[] bTtTmpWait2 = addBtT(bEntResType[0], resTypeId, "b.resource.get");
						strAct.append(tTbTmpWait2[1]);
						strAct.append(bTtTmpWait2[1]);
						String[] arcTmpWait2 = addArc(cleanId("r_", na.getResource().getId()), tTmpWait[0], "PtT", resTypeId, bEntResType[0], tTbTmpWait2[0], bTtTmpWait2[0], null, null, true, "tmpWait");
						strAct.append(arcTmpWait2[1]);
						
						String[] pHold = addPlace(null, "_hold", "String", "", "hold", null);
						strAct.append(pHold[1]);
						
						String[] bHold = addBtT(bEntResType[0], "String", "if(b.resource.get.jobList.size == 0) b.resource.get.id else null");
						String[] timeHold = addAddedTime(bEntResType[0], "0L");
						strAct.append(bHold[1]);
						strAct.append(timeHold[1]);
						String[] arcHold = addArc(pHold[0], tTmpWait[0], "TtP", "String", bEntResType[0], null, bHold[0], timeHold[0], null, false, "tmpHold");
						strAct.append(arcHold[1]);
						
						String[] bTtTmpWait3 = addBtT(bEntResType[0], resTypeId, "new Resource(b.resource.get.id,b.resource.get.name,b.resource.get.setupTime,b.entity.get :: b.resource.get.jobList,b.resource.get.capacity)");
						strAct.append(bTtTmpWait3[1]);
						String[] arcTmpWait3 = addArc(cleanId("r_", na.getResource().getId()), tTmpWait[0], "TtP", resTypeId, bEntResType[0], null, bTtTmpWait3[0], null, null, false, "tmpWait");
						strAct.append(arcTmpWait3[1]);
						
						//String guarddef = "b.resource.get.jobList.size > 0 && b.resource.get.id == b.id.get";
						String guarddef = "b.resource.get.jobList.size == b.resource.get.capacity  && b.resource.get.id == b.id.get";
						String guarddefFin = "b.resource.get.id == b.id.get";
						String[] guard = addGuard(bResStrType[0], guarddef, guarddefFin);
						strAct.append(guard[1]);
						String[] tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, guard[0], null, bResStrType[0], eResStrType[0], mResStrType[0], na.getId(), null, "true", null, na.getLabel());
						strAct.append(tNatstart[1]);
						
						String[] tTb1 = addTtB(bResStrType[0], resTypeId, "resource", "Some(resource),None");
						String[] bTt1 = addBtT(bResStrType[0], resTypeId, "b.resource.get");
						strAct.append(tTb1[1]);
						strAct.append(bTt1[1]);
						String[] arc1 = addArc(cleanId("r_", na.getResource().getId()), tNatstart[0], "PtT", resTypeId, bResStrType[0], tTb1[0], bTt1[0], null, null, true, na.getId());
						strAct.append(arc1[1]);
						
						String[] tTb1x = addTtB(bResStrType[0], "String", "id", "None,Some(id)");
						String[] bTt1x = addBtT(bResStrType[0], "String", "b.id.get");
						strAct.append(tTb1x[1]);
						strAct.append(bTt1x[1]);
						String[] arc1x = addArc(pHold[0], tNatstart[0], "PtT", "String", bResStrType[0], tTb1x[0], bTt1x[0], null, null, true, na.getId());
						strAct.append(arc1x[1]);
					   
						String[] pNap2 = addPlace(null, KeyElement.NAP2STRCONC, resTypeId, "", na.getId(), null);
						strAct.append(pNap2[1]);
					   
						//System.out.println(na.getId()+" - UI : "+na.getProcessingTimeExpression());
						//System.out.println(na.getId()+" - UI : "+na.getWaitingTimeExpression());
						
						String[] btT2 = addBtT(bResStrType[0], resTypeId, "b.resource.get");
						String[] time2 = addAddedTime(bResStrType[0],na.getProcessingTimeExpression());
						strAct.append(btT2[1]);
						strAct.append(time2[1]);
						String[] arc2 = addArc(pNap2[0], tNatstart[0], "TtP", resTypeId, bResStrType[0], null, btT2[0], time2[0], null, false, na.getId());
						strAct.append(arc2[1]);
						
						String[] tNatend = addTransition("_natendfirst", null, null, bResType[0], eResType[0], mResType[0], "natendfirst", null, null, null, na.getLabel());
						strAct.append(tNatend[1]);
					   
						String[] tTb3 = addTtB(bResType[0], resTypeId, "resource", "Some(resource)");
						String[] btT3 = addBtT(bResType[0], resTypeId, "b.resource.get");
						strAct.append(tTb3[1]);
						strAct.append(btT3[1]);
						String[] arc3 = addArc(pNap2[0], tNatend[0], "PtT", resTypeId, bResType[0], tTb3[0], btT3[0], null, null, true, na.getId());
						strAct.append(arc3[1]);
						
						String[] pPool = addPlace(null, "pool entity", qentTypeId, "", na.getId(), null);
						strAct.append(pPool[1]);
						
						String[] btT4 = addBtT(bResType[0], qentTypeId, "b.resource.get.jobList");
						strAct.append(btT4[1]);
						String[] arc4 = addArc(pPool[0], tNatend[0], "TtP", qentTypeId, bResType[0], null, btT4[0], null, null, false, na.getId());
						strAct.append(arc4[1]);
					    
						String[] btT5 = addBtT(bResType[0], resTypeId, "new Resource(b.resource.get.id, b.resource.get.name, b.resource.get.setupTime, List(), b.resource.get.capacity)");
						strAct.append(btT5[1]);
						String[] arc5 = addArc(cleanId("r_", na.getResource().getId()), tNatend[0], "TtP", resTypeId, bResType[0], null, btT5[0], null, null, false, na.getId());
						strAct.append(arc5[1]);
						
						String guarddef2 = "b.queue.get.size > 0";
						String[] guard2 = addGuard(bQentType[0], guarddef2, null);
						strAct.append(guard2[1]);
						
						String[] tNatendbr = addTransition(KeyElement.NATENDSTRCONC, guard2[0], null, bQentType[0], eQentType[0], mQentType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatendbr[1]);
						
						String[] tTb6 = addTtB(bQentType[0], qentTypeId, "resource", "None, Some(resource)");
						String[] btT6 = addBtT(bQentType[0], qentTypeId, "b.queue.get");
						strAct.append(tTb6[1]);
						strAct.append(btT6[1]);
						String[] arc6 = addArc(pPool[0], tNatendbr[0], "PtT", qentTypeId, bQentType[0], tTb6[0], btT6[0], null, null, true, na.getId());
						strAct.append(arc6[1]);
					    
						String[] btT7 = addBtT(bQentType[0], qentTypeId, "val eti::rlist = b.queue.get; if(rlist.size > 0) rlist else null");
						strAct.append(btT7[1]);
						String[] arc7 = addArc(pPool[0], tNatendbr[0], "TtP", qentTypeId, bQentType[0], null, btT7[0], null, null, false, na.getId());
						strAct.append(arc7[1]);
						
						String[] pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
						strAct.append(pNap3[1]);
					   
						String[] btT8 = addBtT(bQentType[0], entTypeId, "b.queue.get.head");
						strAct.append(btT8[1]);
						String[] arc8 = addArc(pNap3[0], tNatendbr[0], "TtP", entTypeId, bQentType[0], null, btT8[0], null, null, false, na.getId());
						strAct.append(arc8[1]);
					}
					else if(na.getQueue() != null && na.getResource() != null) {
						String[] tTmpWait = addTransition("tTmpWait", null, null, bEntType[0], eEntType[0], mEntType[0], "tmpWait", null, null, null, na.getLabel());
						strAct.append(tTmpWait[1]);
						
						String[] tTbTmpWait1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] bTtTmpWait1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTbTmpWait1[1]);
						strAct.append(bTtTmpWait1[1]);
						String[] arcTmpWait1 = addArc(pNap1[0], tTmpWait[0], "PtT", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], null, null, true, "tmpWait");
						strAct.append(arcTmpWait1[1]);
						
						String[] pTmpWait = addPlace("pTmpWait", "_ptmpwait", entTypeId, "", "tmpWait", null);
						strAct.append(pTmpWait[1]);
						
						String[] bTtTmpWait2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						String[] timeWait2 = addAddedTime(bEntType[0], "0L");
						strAct.append(bTtTmpWait2[1]);
						strAct.append(timeWait2[1]);
						String[] arcTmpWait2 = addArc(pTmpWait[0], tTmpWait[0], "TtP", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], timeWait2[0], null, true, "tmpWait");
						strAct.append(arcTmpWait2[1]);
						
						strAct.append(queues.get(na.getQueue().getValue().getId()));
						strAct.append(resources.get(na.getResource().getValue().getId()));
						
						String qidClean = cleanId(null, na.getQueue().getValue().getId());
						String tQsttId = "t_"+qidClean+KeyElement.QSTTSTRCONC;
						String pQendpId = "p_"+qidClean+KeyElement.QENDPSTRCONC;
						
						String[] tTb1 = addTtB(bQentType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMENONECONC);
						String[] bTt1 = addBtT(bQentType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTb1[1]);
						strAct.append(bTt1[1]);
						String[] arc1 = addArc(pTmpWait[0], tQsttId, "PtT", entTypeId, bQentType[0], tTb1[0], bTt1[0], null, null, true, na.getId());
						strAct.append(arc1[1]);
						
						String[] tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bQentResType[0], eQentResType[0], mQentResType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatstart[1]);
						
						String[] tTb2 = addTtB(bQentResType[0], qentTypeId, "entity::queue", "Some(entity), None, Some(queue)");
						String[] btT2 = addBtT(bQentResType[0], qentTypeId, KeyElement.BENTTQGETCONC);
						strAct.append(tTb2[1]);
						strAct.append(btT2[1]);
						String[] arc2 = addArc(pQendpId, tNatstart[0], "PtT", qentTypeId, bQentResType[0], tTb2[0], btT2[0], null, null, true, na.getId());
						strAct.append(arc2[1]);
						
						String[] btT3 = addBtT(bQentResType[0], qentTypeId, KeyElement.BQGETCONC);
						strAct.append(btT3[1]);
						String[] arc3 = addArc(pQendpId, tNatstart[0], "TtP", qentTypeId, bQentResType[0], null, btT3[0], null, null, false, na.getId());
						strAct.append(arc3[1]);
						
						String[] pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entResTypeId, "", na.getId(), null);
						strAct.append(pNap2[1]);
						
						String[] btT4 = addBtT(bQentResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
						String[] time4 = addAddedTime(bQentResType[0], na.getProcessingTimeExpression());
						strAct.append(btT4[1]);
						strAct.append(time4[1]);
					    String[] arc4 = addArc(pNap2[0], tNatstart[0], "TtP", entResTypeId, bQentResType[0], null, btT4[0], time4[0], null, false, na.getId());
					    strAct.append(arc4[1]);
					    
					    String[] tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntResType[0], eEntResType[0], mEntResType[0], na.getId(), null, null, null, na.getLabel());
					    strAct.append(tNatend[1]);
					    
					    String[] tTb5 = addTtB(bEntResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
					    String[] btT5 = addBtT(bEntResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
					    strAct.append(tTb5[1]);
						strAct.append(btT5[1]);
						String[] arc5 = addArc(pNap2[0], tNatend[0], "PtT", entResTypeId, bEntResType[0], tTb5[0], btT5[0], null, null, true, na.getId());
					    strAct.append(arc5[1]);
					    
						String[] pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
					    strAct.append(pNap3[1]);
					    
					    String[] btT6 = addBtT(bEntResType[0], entTypeId, KeyElement.BENTTGETCONC);
					    strAct.append(btT6[1]);
						String[] arc6 = addArc(pNap3[0], tNatend[0], "TtP", entTypeId, bEntResType[0], null, btT6[0], null, null, false, na.getId());
						strAct.append(arc6[1]);
						
						String[] tTb7 = addTtB(bQentResType[0], resTypeId, "resource", "None, Some(resource), None");
						String[] btT7 = addBtT(bQentResType[0], resTypeId, KeyElement.BRESGETCONC);
						strAct.append(tTb7[1]);
						strAct.append(btT7[1]);
					    String[] arc7 = addArc(cleanId("r_", na.getResource().getId()), tNatstart[0], "PtT", resTypeId, bQentResType[0], tTb7[0], btT7[0], null, null, true, na.getId());
					    strAct.append(arc7[1]);
					    
					    String[] btT8 = addBtT(bEntResType[0], resTypeId, KeyElement.BRESGETCONC);
					    strAct.append(btT8[1]);
					    String[] arc8 = addArc(cleanId("r_", na.getResource().getId()), tNatend[0], "TtP", resTypeId, bEntResType[0], null, btT8[0], null, null, false, na.getId());
					    strAct.append(arc8[1]);
					}
					else if(na.getQueue() != null) {
						String[] tTmpWait = addTransition("tTmpWait", null, null, bEntType[0], eEntType[0], mEntType[0], "tmpWait", null, null, null, na.getLabel());
						strAct.append(tTmpWait[1]);
						
						String[] tTbTmpWait1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] bTtTmpWait1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTbTmpWait1[1]);
						strAct.append(bTtTmpWait1[1]);
						String[] arcTmpWait1 = addArc(pNap1[0], tTmpWait[0], "PtT", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], null, null, true, "tmpWait");
						strAct.append(arcTmpWait1[1]);
						
						String[] pTmpWait = addPlace("pTmpWait", "_ptmpwait", entTypeId, "", "tmpWait", null);
						strAct.append(pTmpWait[1]);
						
						String[] bTtTmpWait2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						String[] timeWait2 = addAddedTime(bEntType[0], "0L");
						strAct.append(bTtTmpWait2[1]);
						strAct.append(timeWait2[1]);
						String[] arcTmpWait2 = addArc(pTmpWait[0], tTmpWait[0], "TtP", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], timeWait2[0], null, true, "tmpWait");
						strAct.append(arcTmpWait2[1]);
						
						strAct.append(queues.get(na.getQueue().getValue().getId()));
						
						String qidClean = cleanId(null, na.getQueue().getValue().getId());
						String tQsttId = "t_"+qidClean+KeyElement.QSTTSTRCONC;
						String pQendpId = "p_"+qidClean+KeyElement.QENDPSTRCONC;
						
						String[] tTb1 = addTtB(bQentType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMENONECONC);
						String[] bTt1 = addBtT(bQentType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTb1[0]);
						strAct.append(bTt1[0]);
						String[] arc1 = addArc(pTmpWait[0], tQsttId, "PtT", entTypeId, bQentType[0], tTb1[0], bTt1[0], null, null, true, na.getId());
						strAct.append(arc1[1]);
						
						String[] tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bQentType[0], eQentType[0], mQentType[0], na.getId(), null, null, null, na.getLabel());
						
						String[] tTb2 = addTtB(bQentType[0], qentTypeId, "entity::queue", "Some(entity), Some(queue)");
						String[] btT2 = addBtT(bQentType[0], qentTypeId, KeyElement.BENTTQGETCONC);
						strAct.append(tTb2[0]);
						strAct.append(btT2[0]);
						String[] arc2 = addArc(pQendpId, tNatstart[0], "PtT", qentTypeId, bQentType[0], tTb2[0], btT2[0], null, null, true, na.getId());
						strAct.append(arc2[1]);
						
						String[] btT3 = addBtT(bQentType[0], qentTypeId, KeyElement.BQGETCONC);
						strAct.append(btT3[1]);
						String[] arc3 = addArc(pQendpId, tNatstart[0], "TtP", qentTypeId, bQentType[0], null, btT3[0], null, null, false, na.getId());
						strAct.append(arc3[1]);
						
						String[] pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entTypeId, "", na.getId(), null);
						
						String[] btT4 = addBtT(bQentType[0], entTypeId, KeyElement.BENTTGETCONC);
						String[] time4 = addAddedTime(bEntType[0],na.getProcessingTimeExpression());
						strAct.append(btT4[1]);
						strAct.append(time4[1]);
						String[] arc4 = addArc(pNap2[0], tNatstart[0], "TtP", entTypeId, bQentType[0], null, btT4[0], time4[0], null, false, na.getId());
						strAct.append(arc4[1]);
						
						String[] tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntType[0], eEntType[0], mEntType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatend[1]);
						
						String[] tTb5 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] btT5 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTb5[1]);
						strAct.append(btT5[1]);
						String[] arc5 = addArc(pNap2[0], tNatend[0], "PtT", entTypeId, bEntType[0], tTb5[0], btT5[0], null, null, true, na.getId());
						strAct.append(arc5[1]);
						
						String[] pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
						strAct.append(pNap3[1]);
						
						String[] btT6 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(btT6[1]);
						String[] arc6 = addArc(pNap3[0], tNatend[0], "TtP", entTypeId, bEntType[0], null, btT6[0], null, null, false, na.getId());  
						strAct.append(arc6[1]);
					}
					else if(na.getResource() != null) {
						String[] tTmpWait = addTransition("tTmpWait", null, null, bEntType[0], eEntType[0], mEntType[0], "tmpWait", null, null, null, na.getLabel());
						strAct.append(tTmpWait[1]);
						
						String[] tTbTmpWait1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] bTtTmpWait1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTbTmpWait1[1]);
						strAct.append(bTtTmpWait1[1]);
						String[] arcTmpWait1 = addArc(pNap1[0], tTmpWait[0], "PtT", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], null, null, true, "tmpWait");
						strAct.append(arcTmpWait1[1]);
						
						String[] pTmpWait = addPlace("pTmpWait", "_ptmpwait", entTypeId, "", "tmpWait", null);
						strAct.append(pTmpWait[1]);
						
						String[] bTtTmpWait2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						String[] timeWait2 = addAddedTime(bEntType[0], "0L");
						strAct.append(bTtTmpWait2[1]);
						strAct.append(timeWait2[1]);
						String[] arcTmpWait2 = addArc(pTmpWait[0], tTmpWait[0], "TtP", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], timeWait2[0], null, true, "tmpWait");
						strAct.append(arcTmpWait2[1]);
						
						strAct.append(resources.get(na.getResource().getValue().getId()));
						
						String[] tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bDataResType[0], eDataResType[0], mDataResType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatstart[1]);
					   
						// ---------- added to get case data from activity
						String[] pDgpData = placeshub.get(pCaseData);
						strAct.append(pDgpData[1]);
						
						String[] tTbCsdt1 = addTtB(bDataResType[0], dataTypeId, "(entity,data)", "Some(entity),Some(data),None");
						String[] bTtCsdt1 = addBtT(bDataResType[0], dataTypeId, "(b.entity.get,b.data.get)");
						strAct.append(tTbCsdt1[1]);
						strAct.append(bTtCsdt1[1]);
						String[] arcCsdt1 = addArc(pDgpData[0], tNatstart[0], "PtT", dataTypeId, bDataResType[0], tTbCsdt1[0], bTtCsdt1[0], null, null, true, na.getId());
						strAct.append(arcCsdt1[1]);
						
						String[] bTtCsdt2 = addBtT(bDataResType[0], dataTypeId, "(b.entity.get,CaseData(b.data.get.inc+1))");
						strAct.append(bTtCsdt2[1]);
						String[] arcCsdt2 = addArc(pDgpData[0], tNatstart[0], "TtP", dataTypeId, bDataResType[0], null, bTtCsdt2[0], null, null, false, na.getId());
						strAct.append(arcCsdt2[1]);
						// ---------- added to get case data from activity
						
						String[] tTb1 = addTtB(bDataResType[0], entTypeId, KeyElement.ENTCONC, "Some(entity),None,None");
						String[] bTt1 = addBtT(bDataResType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTb1[1]);
						strAct.append(bTt1[1]);
						String[] arc1 = addArc(pTmpWait[0], tNatstart[0], "PtT", entTypeId, bDataResType[0], tTb1[0], bTt1[0], null, null, true, na.getId());
						strAct.append(arc1[1]);
						
						String[] tTb5 = addTtB(bDataResType[0], resTypeId, "resource", "None, None, Some(resource)");
						String[] btT5 = addBtT(bDataResType[0], resTypeId, KeyElement.BRESGETCONC);
						strAct.append(tTb5[1]);
						strAct.append(btT5[1]);
						String[] arc5 = addArc(cleanId("r_", na.getResource().getId()), tNatstart[0], "PtT", resTypeId, bDataResType[0], tTb5[0], btT5[0], null, null, true, na.getId());
						strAct.append(arc5[1]);
						
						String[] pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entResTypeId, "", na.getId(), null);
						strAct.append(pNap2[1]);
					   
						String[] btT2 = addBtT(bDataResType[0], entResTypeId, "(b.entity.get,b.resource.get)");
						String[] time2 = addAddedTime(bDataResType[0],na.getProcessingTimeExpression());
						strAct.append(btT2[1]);
						strAct.append(time2[1]);
						String[] arc2 = addArc(pNap2[0], tNatstart[0], "TtP", entResTypeId, bDataResType[0], null, btT2[0], time2[0], null, false, na.getId());
						strAct.append(arc2[1]);
					   
						String[] tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntResType[0], eEntResType[0], mEntResType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatend[1]);
					   
						String[] tTb3 = addTtB(bEntResType[0], entResTypeId, "(entity, resource)", "Some(entity), Some(resource)");
						String[] btT3 = addBtT(bEntResType[0], entResTypeId, "(b.entity.get, b.resource.get)");
						strAct.append(tTb3[1]);
						strAct.append(btT3[1]);
						String[] arc3 = addArc(pNap2[0], tNatend[0], "PtT", entResTypeId, bEntResType[0], tTb3[0], btT3[0], null, null, true, na.getId());
						strAct.append(arc3[1]);
					   
						String[] pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
						strAct.append(pNap3[1]);
					   
						String[] btT4 = addBtT(bEntResType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(btT4[1]);
						String[] arc4 = addArc(pNap3[0], tNatend[0], "TtP", entTypeId, bEntResType[0], null, btT4[0], null, null, false, na.getId());
						strAct.append(arc4[1]);
					   
						String[] btT6 = addBtT(bEntResType[0], resTypeId, KeyElement.BRESGETCONC);
						strAct.append(btT6[1]);
						String[] arc6 = addArc(cleanId("r_", na.getResource().getId()), tNatend[0], "TtP", resTypeId, bEntResType[0], null, btT6[0], null, null, false, na.getId());
						strAct.append(arc6[1]);
					}
					else {
						String[] tTmpWait = addTransition("tTmpWait", null, null, bEntType[0], eEntType[0], mEntType[0], "tmpWait", null, null, null, na.getLabel());
						strAct.append(tTmpWait[1]);
						
						String[] tTbTmpWait1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] bTtTmpWait1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTbTmpWait1[1]);
						strAct.append(bTtTmpWait1[1]);
						String[] arcTmpWait1 = addArc(pNap1[0], tTmpWait[0], "PtT", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], null, null, true, "tmpWait");
						strAct.append(arcTmpWait1[1]);
						
						String[] pTmpWait = addPlace("pTmpWait", "_ptmpwait", entTypeId, "", "tmpWait", null);
						strAct.append(pTmpWait[1]);
						
						String[] bTtTmpWait2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						String[] timeWait2 = addAddedTime(bEntType[0], "0L");
						strAct.append(bTtTmpWait2[1]);
						strAct.append(timeWait2[1]);
						String[] arcTmpWait2 = addArc(pTmpWait[0], tTmpWait[0], "TtP", entTypeId, bEntType[0], tTbTmpWait1[0], bTtTmpWait1[0], timeWait2[0], null, true, "tmpWait");
						strAct.append(arcTmpWait2[1]);
						
						String[] tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bEntType[0], eEntType[0], mEntType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatstart[1]);
						
						String[] tTb1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] bTt1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTb1[1]);
						strAct.append(bTt1[1]);
						String[] arc1 = addArc(pTmpWait[0], tNatstart[0], "PtT", entTypeId, bEntType[0], tTb1[0], bTt1[0], null, null, true, na.getId());
						strAct.append(arc1[1]);
						
						String[] pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entTypeId, "", na.getId(), null);
						strAct.append(pNap2[1]);
						
						String[] btT2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						String[] time2 = addAddedTime(bEntType[0],na.getProcessingTimeExpression());
						strAct.append(btT2[1]);
						strAct.append(time2[1]);
						String[] arc2 = addArc(pNap2[0], tNatstart[0], "TtP", entTypeId, bEntType[0], null, btT2[0], time2[0], null, false, na.getId());
						strAct.append(arc2[1]);
						
						String[] tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntType[0], eEntType[0], mEntType[0], na.getId(), null, null, null, na.getLabel());
						strAct.append(tNatend[1]);
						
						String[] tTb3 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
						String[] btT3 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(tTb3[1]);
						strAct.append(btT3[1]);
						String[] arc3 = addArc(pNap2[0], tNatend[0], "PtT", entTypeId, bEntType[0], tTb3[0], btT3[0], null, null, true, na.getId());
						strAct.append(arc3[1]);
						
						String[] pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
						strAct.append(pNap3[1]);
						
						String[] btT4 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
						strAct.append(btT4[1]);
						String[] arc4 = addArc(pNap3[0], tNatend[0], "TtP", entTypeId, bEntType[0], null, btT4[0], null, null, false, na.getId());
						strAct.append(arc4[1]);
					}
					
					strAct.append(KeyElement.GRAPHENDCONC);
					convm.put(na.getId(), strAct.toString());
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					
					StringBuilder strBr = new StringBuilder();
					strBr.append(strGen.toString());
					
					String entBrTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
					String entBrType = KeyElement.TYPECONC+entBrTypeId+" = ((Int,String),Double,TypeData)\n";
					strBr.append(entBrType);
					
					String[] bEntBrType = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"],cond:Option[Double],data:Option[TypeData]" );
					String[] eEntBrType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.cond == b2.cond || b1.cond == None || b2.cond == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", bEntBrType[0]);
					String[] mEntBrType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val cond = if(b1.cond == None) b2.cond else b1.cond; val data = if(b1.data == None) b2.data else b1.data;", bEntBrType[0], "entity,cond,data");
					strBr.append(bEntBrType[1]);
					strBr.append(eEntBrType[1]);
					strBr.append(mEntBrType[1]);
					
					String btid = null;
					String bpid = null;
					
					if (b.getGate() == BranchGate.AND) { 
						String[] bt = addTransition("_bt", null, null, bEntType[0], eEntType[0], mEntType[0], b.getId(), null, null, null, b.getLabel());
						strBr.append(bt[1]);
						btid = bt[0];
					}
					
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getTarget().getValue() != b) continue;
							String[] pBpis = addPlace(b.getId()+KeyElement.ENDSTRCONC+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
							strBr.append(pBpis[1]);
							
							String[] tTb1 = addTtB(bEntType[0], entTypeId, "entity", KeyElement.SOMECONC);
							String[] bTt1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
							strBr.append(tTb1[1]);
							strBr.append(bTt1[1]);
							String[] arc1 = addArc(pBpis[0], btid, "PtT", entTypeId, bEntType[0], tTb1[0], bTt1[0], null, null, true, b.getId());
							strBr.append(arc1[1]);
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getTarget().getValue() != b) continue;
								String[] pBpis = addPlace(b.getId()+KeyElement.ENDSTRCONC+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(pBpis[1]);
								
								String actiondef = "val newb = b.copy(cond = Some(scala.util.Random.nextDouble()));newb";
								String[] action = addAction(bEntBrType[0], actiondef);
								strBr.append(action[1]);
								
								String[] tCondGen = addTransition("_condGen", null, action[0], bEntBrType[0], eEntBrType[0], mEntBrType[0], b.getId(), null, null, null, b.getLabel());
								strBr.append(tCondGen[1]);
								String[] pBpt = addPlace(null, "_bpt", entBrTypeId, "", b.getId(), null);
								strBr.append(pBpt[1]);
								
								bpid = pBpt[0];
								
								String[] pDgpData = placeshub.get(pCaseData);
								strBr.append(pDgpData[1]);
								
								String[] tTbCsdt1 = addTtB(bEntBrType[0], dataTypeId, "(entity,data)", "Some(entity),None,Some(data)");
								String[] bTtCsdt1 = addBtT(bEntBrType[0], dataTypeId, "(b.entity.get,b.data.get)");
								strBr.append(tTbCsdt1[1]);
								strBr.append(bTtCsdt1[1]);
								String[] arcCsdt1 = addArc(pDgpData[0], tCondGen[0], "PtT", dataTypeId, bEntBrType[0], tTbCsdt1[0], bTtCsdt1[0], null, null, true, b.getId());
								strBr.append(arcCsdt1[1]);
								
								String[] bTtCsdt2 = addBtT(bEntBrType[0], dataTypeId, "(b.entity.get,CaseData(b.data.get.inc))");
								strBr.append(bTtCsdt2[1]);
								String[] arcCsdt2 = addArc(pDgpData[0], tCondGen[0], "TtP", dataTypeId, bEntBrType[0], null, bTtCsdt2[0], null, null, false, b.getId());
								strBr.append(arcCsdt2[1]);
								
								String[] tTb1 = addTtB(bEntBrType[0], entTypeId, "entity", "Some(entity),None,None");
								String[] bTt1 = addBtT(bEntBrType[0], entTypeId, "b.entity.get");
								strBr.append(tTb1[1]);
								strBr.append(bTt1[1]);
								String[] arc1 = addArc(pBpis[0], tCondGen[0], "PtT", entTypeId, bEntBrType[0], tTb1[0], bTt1[0], null, null, true, b.getId());
								strBr.append(arc1[1]);
								
								String[] tTb2 = addTtB(bEntBrType[0], entBrTypeId, "(entity,cond,data)", "Some(entity),Some(cond),Some(data)");
								String[] btT2 = addBtT(bEntBrType[0], entBrTypeId, "(b.entity.get,b.cond.get,b.data.get)");
								strBr.append(tTb2[1]);
								strBr.append(btT2[1]);
								String[] arc2 = addArc(pBpt[0], tCondGen[0], "TtP", entBrTypeId, bEntBrType[0], tTb2[0], btT2[0], null, null, false, b.getId());
								strBr.append(arc2[1]);
							}
							else {
								if (c.getSource().getValue() != b) continue;
								String[] pBpos = addPlace(b.getId()+KeyElement.STARTSTRCONC+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(pBpos[1]);
								
								bpid = pBpos[0];
							}
						}

						i++;
					}
					
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getSource().getValue() != b) continue;
							String[] pBpos = addPlace(b.getId()+KeyElement.STARTSTRCONC+c.getSourceIndex(), "_bpo_" + i + "s", entTypeId, "", b.getId(), null);
							strBr.append(pBpos[1]);
							String[] bTt1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
							strBr.append(bTt1[1]);
							String[] arc1 = addArc(pBpos[0], btid, "TtP", entTypeId, bEntType[0], null, bTt1[0], null, null, false, b.getId());
							strBr.append(arc1[1]);
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getSource().getValue() != b) continue;
								String guardid = null;
								if(b.getRule() == BranchRule.DATA)
								{
									String guarddef = c.getAttributes().get("data");
									String[] guard = addGuard(bEntBrType[0], guarddef, null);
									guardid = guard[0];
									strBr.append(guard[1]);// TODO:
								}
								else if (b.getRule() == BranchRule.CONDITION) {
									String guarddef = "b.cond.get "+c.getAttributes().get("condition");
									String[] guard = addGuard(bEntBrType[0], guarddef, null);
									guardid = guard[0];
									strBr.append(guard[1]);
								}
								String[] pBpos = addPlace(b.getId()+KeyElement.STARTSTRCONC+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(pBpos[1]);
								String[] tSilent = addTransition("_temps_"+i, guardid, null, bEntBrType[0], eEntBrType[0], mEntBrType[0], b.getId(), null, null, null, b.getLabel());
								strBr.append(tSilent[1]);
								
								String[] tTb1 = addTtB(bEntBrType[0], entBrTypeId, "(entity,cond,data)", "Some(entity),Some(cond),Some(data)");
								String[] bTt1 = addBtT(bEntBrType[0], entBrTypeId, "(b.entity.get,b.cond.get,b.data.get)");
								strBr.append(tTb1[1]);
								strBr.append(bTt1[1]);
								String[] arc1 = addArc(bpid, tSilent[0], "PtT", entBrTypeId, bEntBrType[0], tTb1[0], bTt1[0], null, null, true, b.getId());
								strBr.append(arc1[1]);
								
								String[] btT2 = addBtT(bEntBrType[0], entTypeId, KeyElement.BENTTGETCONC);
								strBr.append(btT2[1]);
								String[] arc2 = addArc(pBpos[0], tSilent[0], "TtP", entTypeId, bEntBrType[0], null, btT2[0], null, null, false, b.getId());
								strBr.append(arc2[1]);
							}
							else {
								if (c.getTarget().getValue() != b) continue;
								String[] pBpis = addPlace(b.getId()+KeyElement.ENDSTRCONC+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								strBr.append(pBpis[1]);
								String[] tSilent = addTransition("_temps_"+i, null, null, bEntType[0], eEntType[0], mEntType[0], b.getId(), null, null, null, b.getLabel());
								strBr.append(tSilent[1]);
								
								String[] tTb1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
								String[] bTt1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
								strBr.append(tTb1[1]);
								strBr.append(bTt1[1]);
								String[] arc1 = addArc(pBpis[0], tSilent[0], "PtT", entTypeId, bEntType[0], tTb1[0], bTt1[0], null, null, true, b.getId());
								strBr.append(arc1[1]);
								
								String[] btT2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
								strBr.append(btT2[1]);
								String[] arc2 = addArc(bpid, tSilent[0], "TtP", entTypeId, bEntType[0], null, btT2[0], null, null, false, b.getId());
								strBr.append(arc2[1]);
							}
						}
						i++;
					}
					
					strBr.append(KeyElement.GRAPHENDCONC);
					convm.put(b.getId(), strBr.toString());
				}
				if (n instanceof Monitor) {
					// TODO:
				}
			}
			// Convert Arcs
			for (String s : p.getConnectors().keySet()) {
				Connector c = p.getConnectors().get(s);
				
				StringBuilder strCon = new StringBuilder();
				strCon.append(strGen.toString());
				
				String[] source = placeshub.get(c.getSource().getId()+KeyElement.STARTSTRCONC+c.getSourceIndex());
				String[] target = placeshub.get(c.getTarget().getId()+KeyElement.ENDSTRCONC+c.getTargetIndex());
				
				strCon.append(source[1]);
				strCon.append(target[1]);
				
				String[] tSilent = null;
				if(target[2].contains("-stop-")) {
					String[] bDataEntType = addBindingClass( "entity:Option["+entTypeId+"], data:Option[TypeData]" );
					String[] eDataEntType = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", bDataEntType[0]);
					String[] mDataEntType = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val data = if(b1.data == None) b2.data else b1.data;", bDataEntType[0], "entity,data");
					strCon.append(bDataEntType[1]);
					strCon.append(eDataEntType[1]);
					strCon.append(mDataEntType[1]);
					
					String[] oriRole = new String[]{target[2],"_stop"};
					tSilent = addTransition(oriRole[1], null, null, bDataEntType[0], eDataEntType[0], mDataEntType[0], oriRole[0], null, null, null, oriRole[0]);
					strCon.append(tSilent[1]);
					
					String[] tTb1 = addTtB(bDataEntType[0], entTypeId, KeyElement.ENTCONC, "Some(entity),None");
					String[] bTt1 = addBtT(bDataEntType[0], entTypeId, KeyElement.BENTTGETCONC);
					strCon.append(tTb1[1]);
					strCon.append(bTt1[1]);
					String[] arc1 = addArc(source[0], tSilent[0], "PtT", entTypeId, bDataEntType[0], tTb1[0], bTt1[0], null, null, true, c.getId());
					strCon.append(arc1[1]);
					
					String[] pDgpData = placeshub.get(pCaseData);
					strCon.append(pDgpData[1]);
					
					String[] tTbCsdt1 = addTtB(bDataEntType[0], dataTypeId, "(entity,data)", "Some(entity),Some(data)");
					String[] bTtCsdt1 = addBtT(bDataEntType[0], dataTypeId, "(b.entity.get,b.data.get)");
					strCon.append(tTbCsdt1[1]);
					strCon.append(bTtCsdt1[1]);
					String[] arcCsdt1 = addArc(pDgpData[0], tSilent[0], "PtT", dataTypeId, bDataEntType[0], tTbCsdt1[0], bTtCsdt1[0], null, null, true, c.getId());
					strCon.append(arcCsdt1[1]);
					
					String[] btT2 = addBtT(bDataEntType[0], entTypeId, KeyElement.BENTTGETCONC);
					strCon.append(btT2[1]);
					String[] arc2 = addArc(target[0], tSilent[0], "TtP", entTypeId, bDataEntType[0], null, btT2[0], null, null, false, c.getId());
					strCon.append(arc2[1]);
				}
				else {
					String[] oriRole = new String[]{c.getId(), "silent"};
					tSilent = addTransition(oriRole[1], null, null, bEntType[0], eEntType[0], mEntType[0], oriRole[0], null, null, null, oriRole[0]);
					strCon.append(tSilent[1]);
					
					String[] tTb1 = addTtB(bEntType[0], entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC);
					String[] bTt1 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
					strCon.append(tTb1[1]);
					strCon.append(bTt1[1]);
					String[] arc1 = addArc(source[0], tSilent[0], "PtT", entTypeId, bEntType[0], tTb1[0], bTt1[0], null, null, true, c.getId());
					strCon.append(arc1[1]);
					
					String[] btT2 = addBtT(bEntType[0], entTypeId, KeyElement.BENTTGETCONC);
					strCon.append(btT2[1]);
					String[] arc2 = addArc(target[0], tSilent[0], "TtP", entTypeId, bEntType[0], null, btT2[0], null, null, false, c.getId());
					strCon.append(arc2[1]);
				}
				
				strCon.append(KeyElement.GRAPHENDCONC);
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
