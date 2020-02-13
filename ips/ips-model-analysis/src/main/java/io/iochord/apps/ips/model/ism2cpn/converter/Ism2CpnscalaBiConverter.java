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
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;

/**
*
* @package ips-model-analysis
* @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
* @since   2019
* 
* CPN Scala converter class
* Convert from IsmGraph to Ism2CpnscalaModel -> scala string
*
*/

public class Ism2CpnscalaBiConverter implements Converter<IsmGraph, Ism2CpnscalaModel> {
	
	StringBuilder factory = new StringBuilder();
	
	private Map<String, String> objecttypes = new LinkedHashMap<>();
	private Map<String, String> placeshub = new LinkedHashMap<>();
	private Map<String, Boolean> transIfExist = new HashMap<>();
	
	private Map<String, Integer> counters = new LinkedHashMap<>();
	
	/**
	 * @param clazz
	 * @return get counter of current value
	 * This function is used to generate unique value thats all
	 */
	private String getCounter(String clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return String.format("%2s", counters.get(clazz)).replace(' ', '0');
	}
	
	/**
	 * @param hubid : is used to keep specific var name of scala place that will be used in the connector part in this class
	 * @param name : name of this place
	 * @param type : type of the token multiset in this place
	 * @param initialMarking : define initial marking for this place
	 * @param origin : origin of this place (see trait Element in ips-model for detail explanation)
	 * @param placeId : id of this place if defined (if null it will used getCounter function to set automatic name for the variable) 
	 * @return the id of this place
	 */
	public String addPlace(String hubid, String name, String type, String initialMarking, String origin, String placeId) {
		String counter = getCounter(KeyElement.PLACE);
		String mapid = "map"+counter;
		String multisetid = "ms"+counter;
		String placeidClean = placeId == null ? "p_"+counter : cleanId("p_", placeId);
		
		if(hubid != null)
			placeshub.put(hubid, placeidClean);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val "+mapid+" = Map[("+type+",Long),Int]( "+initialMarking+" )\n" );
		placefactory.append( "val "+multisetid+" = new Multiset["+type+"]("+mapid+")\n" );
		placefactory.append( "val "+placeidClean+" = new Place(\""+placeidClean+"\",\""+name+"\","+multisetid+")\n" );
		placefactory.append( placeidClean+KeyElement.SETORIGINCONC+origin+KeyElement.ROLECONC+name+KeyElement.ENDCOLCONC );
		placefactory.append( "cgraph.addPlace("+placeidClean+")\n" );
		placefactory.append("\n");
		
		factory.append(placefactory.toString());
		
		return placeidClean;
	}
	
	/**
	 * @param name : name of this transition
	 * @param guard : guard variable of this transition (the definition of the guard is defined outside of this function)
	 * @param action : action variable of this transtion (the definition of the action is defined outside of this function)
	 * @param classbinding : classbinding name of this transition (the definition of the classbinding is defined outside of this function)
	 * @param eval : eval function of this transition (see Transition class in ips-model to more detail explanation)
	 * @param merge : merge function of this transition (see Transitio class in ips-model to more detail explanation)
	 * @param origin : origin of this transition (see trait Element in ips-model for detail explanation)
	 * @param transId : id of this transition if defined (if null it will used getCounter function to set automatic name for the variable)
	 * @return the id of this transition
	 */
	public String addTransition(String name, String guard, String action, String classbinding, String eval, String merge, String origin, String transId) {
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
		transfactory.append( transidClean+KeyElement.SETORIGINCONC+origin+KeyElement.ROLECONC+name+KeyElement.ENDCOLCONC );
		transfactory.append( "cgraph.addTransition("+transidClean+")\n" );
		transfactory.append("\n");
		
		factory.append(transfactory.toString());
		
		return transidClean;
	}
	
	/**
	 * @param classbinding
	 * @param guarddef
	 * @return
	 */
	public String addGuard(String classbinding, String guarddef) {
		String counter = getCounter(KeyElement.GUARD);
		String guardid = KeyElement.GUARD+counter;
		String bindguardexpid = "BindGuard"+counter;
		
		StringBuilder guardfactory = new StringBuilder();
		guardfactory.append( "val "+guardid+" = new Guard["+classbinding+"]()\n" );
		guardfactory.append( "val "+bindguardexpid+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+guarddef+"}\n" );
		guardfactory.append( guardid+".setGuardBind("+bindguardexpid+")\n" );
		factory.append(guardfactory.toString());
		
		return guardid;
	}
	
	/**
	 * @param classbinding
	 * @param actionfundef
	 * @return
	 */
	public String addAction(String classbinding, String actionfundef) {
		String counter = getCounter(KeyElement.ACTION);
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
	
	/**
	 * @param classdef
	 * @return
	 */
	public String addBindingClass(String classdef) {
		String counter = getCounter(KeyElement.BINDING);
		String bindingid = KeyElement.BINDING+counter;
		
		StringBuilder cBindingfactory = new StringBuilder();
		cBindingfactory.append( "case class "+bindingid+"(").append(classdef).append(")\n" );
		
		factory.append(cBindingfactory.toString());
		
		return bindingid;
	}
	
	/**
	 * @param evaldef
	 * @param classbinding
	 * @return
	 */
	public String addEval(String evaldef, String classbinding) {
		String counter = getCounter(KeyElement.EVAL);
		String evalid = "Eval"+counter;
		
		StringBuilder evalfactory = new StringBuilder();
		evalfactory.append( "val "+evalid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		evalfactory.append( "\t"+evaldef+"\n" );
		evalfactory.append( "}\n" );
		
		factory.append(evalfactory.toString());
		
		return evalid;
	}
	
	/**
	 * @param mergedef
	 * @param classbinding
	 * @param mergeassign
	 * @return
	 */
	public String addMerge(String mergedef, String classbinding, String mergeassign) {
		String counter = getCounter(KeyElement.MERGE);
		String mergeid = KeyElement.MERGE+counter;
		
		StringBuilder mergefactory = new StringBuilder();
		mergefactory.append( "val "+mergeid+" = (b1:"+classbinding+", b2:"+classbinding+") => {\n" );
		mergefactory.append( "\t"+mergedef+"\n" );
		mergefactory.append( "\t"+classbinding+"("+mergeassign+")\n" );
		mergefactory.append( "}\n" );
		
		factory.append(mergefactory.toString());
		
		return mergeid;
	}
	
	/**
	 * @param placeid
	 * @param transitionid
	 * @param direction
	 * @param type
	 * @param classbinding
	 * @param tTb
	 * @param bTt
	 * @param addTime
	 * @param noToken
	 * @param isBase
	 * @param origin
	 * @return
	 */
	public String addArc(String placeid, String transitionid, String direction, String type, String classbinding, String tTb, String bTt, String addTime, String noToken, boolean isBase, String origin) {
		String counter = getCounter(KeyElement.ARC);
		String arcid = "arc"+counter;
		
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
		
		factory.append(arcfactory.toString());
		
		return arcid;
	}
	
	/**
	 * @param classbinding
	 * @param type
	 * @param inverse
	 * @param tTbDef
	 * @return
	 */
	public String addTtB(String classbinding, String type, String inverse, String tTbDef) {
		String counter = getCounter(KeyElement.TTB);
		String tTbID = "tTb"+counter;
		
		StringBuilder tTbfactory = new StringBuilder();
		tTbfactory.append( "val "+tTbID+" = (t:"+type+") => { try { val "+inverse+" = t;Some("+classbinding+"("+tTbDef+")) } catch { case e: Exception => None } }\n" );
		
		factory.append(tTbfactory.toString());
		
		return tTbID;
	}
	
	/**
	 * @param classbinding
	 * @param type
	 * @param bTtDef
	 * @return
	 */
	public String addBtT(String classbinding, String type, String bTtDef) {
		String counter = getCounter(KeyElement.BTT);
		String bTtID = "bTt"+counter;
		
		StringBuilder bTtFactory = new StringBuilder();
		bTtFactory.append( "val "+bTtID+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+bTtDef+"}:"+type+"\n" );
		
		factory.append(bTtFactory.toString());
		
		return bTtID;
	}
	
	/**
	 * @param classbinding
	 * @param addedTimedef
	 * @return
	 */
	public String addAddedTime(String classbinding, String addedTimedef) {
		if(addedTimedef.length() <= 1)
			addedTimedef = "0L";
		
		String counter = getCounter(KeyElement.ADDTIME);
		String addTimeid = KeyElement.ADDTIME+counter;
		
		StringBuilder addTimefactory = new StringBuilder();
		addTimefactory.append( "val "+addTimeid+KeyElement.BPRECONC+classbinding+KeyElement.BENDCONC+addedTimedef+"}\n" );
		
		factory.append(addTimefactory.toString());
		
		return addTimeid;
	}
	
	/**
	 * @param resId
	 * @param typecaseId
	 * @return
	 */
	public String createResourcePlace(String resId, String typecaseId) {
		String residClean = cleanId("r_", resId);
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val map"+residClean+" = Map[(Resource["+typecaseId+"],Long),Int]()\n" );
		placefactory.append( "val ms"+residClean+" = new Multiset[Resource["+typecaseId+"]](map"+residClean+")\n" );
		placefactory.append( "val "+residClean+" = new Place(\""+residClean+"\",\"Resource Place\",ms"+residClean+")\n" );
		placefactory.append( residClean+KeyElement.SETORIGINCONC+resId+"\"),(\"role\",\"_resp\")))\n" );
		placefactory.append( "cgraph.addPlace("+residClean+")\n" );
		placefactory.append("\n");
		
		factory.append(placefactory.toString());
		
		return residClean;
	}
	
	/**
	 * @param resId
	 * @param name
	 * @param numbofresource
	 */
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
	
	/**
	 * @param typeid
	 * @param ltypeid
	 * @param cb
	 * @param ev
	 * @param mg
	 * @param queue
	 * @param origin
	 */
	public void addQueue(String typeid, String ltypeid, String cb, String ev, String mg, Queue queue, String origin) {
		String qidClean = cleanId("q_", queue.getId());
		String listid = "list_"+qidClean;
		
		factory.append("\n val "+listid+" = List["+typeid+"]() \n");
		String pendqueue = addPlace(null, KeyElement.QENDPSTRCONC, ltypeid, "(("+listid+",0),1)", origin, queue.getId()+KeyElement.QENDPSTRCONC);
		
		String guarddef = "b.queue.get.length < "+queue.getSize();
		String guard = queue.getSize() > 0 ? addGuard(cb, guarddef) : null;
		
		String tstartqueue = addTransition(KeyElement.QSTTSTRCONC, guard, null, cb, ev, mg, origin, queue.getId()+KeyElement.QSTTSTRCONC);
		
		addArc(pendqueue, tstartqueue, "PtT", ltypeid, cb, addTtB(cb, ltypeid, "queue", "None, Some(queue)"), addBtT(cb, ltypeid, KeyElement.BQGETCONC), null, null, true, origin);
		
		if(queue.getType() == Queue.QUEUE_TYPE.FIFO)
			addArc(pendqueue, tstartqueue, "TtP", ltypeid, cb, null, addBtT(cb, ltypeid, KeyElement.BENTTQGETCONC), null, null, false, origin);
		else
			addArc(pendqueue, tstartqueue, "TtP", ltypeid, cb, null, addBtT(cb, ltypeid, "b.queue.get:::List(b.entity.get)"), null, null, false, origin);
	}
	
	/* 
	 * @param snet : accept snet and convert to scala string
	 */
	public Ism2CpnscalaModel convert(IsmGraph snet) {
		Ism2CpnscalaModel result = new Ism2CpnscalaModel();
		result.setOriginalModel(snet);
		for (String pi : snet.getPages().keySet()) {
			io.iochord.apps.ips.model.ism.v1.Page p = snet.getPages().get(pi);
			
			String caseData = "case class CaseData(atr1:String,atr2:Int)";
			factory.append(caseData+"\n");
			factory.append("\n");
			
			String dataTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			factory.append(KeyElement.TYPECONC+dataTypeId+" = ((Int,String),CaseData)\n");
			factory.append("\n");
			
			String entTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			objecttypes.put("entityTypeId", entTypeId);
			factory.append(KeyElement.TYPECONC+entTypeId+" = (Int,String)\n");
			factory.append("\n");
			
			String bEntTypeId = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"]" );
			String eEntTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None)", bEntTypeId);
			String mEntTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;", bEntTypeId, KeyElement.ENTCONC);
			
			String entBrTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			objecttypes.put("entityBrTypeId", entBrTypeId);
			factory.append(KeyElement.TYPECONC+entBrTypeId+" = ((Int,String),Double)\n");
			factory.append("\n");
			
			String bEntBrTypeId = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"],cond:Option[Double]" );
			String eEntBrTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.cond == b2.cond || b1.cond == None || b2.cond == None)", bEntBrTypeId);
			String mEntBrTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val cond = if(b1.cond == None) b2.cond else b1.cond;", bEntBrTypeId, "entity,cond");
			
			String qentTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			objecttypes.put("qentityTypeId", qentTypeId);
			factory.append(KeyElement.TYPECONC+qentTypeId+" = List["+entTypeId+"]\n");
			factory.append("\n");
			
			String bQentTypeId = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"], queue:Option["+qentTypeId+"]" );
			String eQentTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", bQentTypeId);
			String mQentTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity;val queue = if(b1.queue == None) b2.queue else b1.queue;", bQentTypeId, "entity,queue");
			
			String entResTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			objecttypes.put("entityResTypeId", entResTypeId);
			factory.append(KeyElement.TYPECONC+entResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"])\n");
			factory.append("\n");
			
			String bEntResTypeId = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"], resource:Option[Resource["+entTypeId+"]]" );
			String eEntResTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None)", bEntResTypeId);
			String mEntResTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource;", bEntResTypeId, "entity,resource");
			
			String qentResTypeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
			objecttypes.put("entityResTypeId", qentResTypeId);
			factory.append(KeyElement.TYPECONC+qentResTypeId+" = ("+entTypeId+",Resource["+entTypeId+"], List["+entTypeId+"])\n");
			factory.append("\n");
			
			String bQentResTypeId = addBindingClass( KeyElement.ENTOPTCONC+entTypeId+"], resource:Option[Resource["+entTypeId+"]], queue:Option[List["+entTypeId+"]]" );
			String eQentResTypeId = addEval("(b1.entity == b2.entity || b1.entity == None || b2.entity == None) && (b1.resource == b2.resource || b1.resource == None || b2.resource == None) && (b1.queue == b2.queue || b1.queue == None || b2.queue == None)", bQentResTypeId);
			String mQentResTypeId = addMerge("val entity = if(b1.entity == None) b2.entity else b1.entity; val resource = if(b1.resource == None) b2.resource else b1.resource; val queue = if(b1.queue == None) b2.queue else b1.queue;", bQentResTypeId, "entity,resource,queue");
			
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				result.getConversionMap().put(d.getId(), d);
				if (d instanceof ObjectType) {
					ObjectType ot = (ObjectType) d;
					String typeId = KeyElement.COLSETCONC+getCounter(KeyElement.TYPE);
					objecttypes.put(ot.getId(), typeId);
					factory.append(KeyElement.TYPECONC+typeId+" = Int\n");
					factory.append("\n");
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					
					String typeId = objecttypes.get(dg.getObjectType().getId());
					
					String pDgp1 = addPlace(null, "_dgp1", typeId, "((1,0),1)", dg.getId(), null);
					String pDgp2 = addPlace(dg.getId()+ KeyElement.START0STRCONC, "_dgp2", entTypeId, "", dg.getId(), null);
					String pDgpData = addPlace(null, "_dgpData", dataTypeId, "", dg.getId(), null);
					
					String bDgt1 = addBindingClass( "tid:Option[Int],gid:Option[String],data:Option[CaseData]" );
					String eDgt1 = addEval("(b1.tid == b2.tid || b1.tid == None || b2.tid == None) && (b1.gid == b2.gid || b1.gid == None || b2.gid == None) && (b1.data == b2.data || b1.data == None || b2.data == None)", bDgt1);
					String mDgt1 = addMerge("val tid = if(b1.tid == None) b2.tid else b1.tid;val gid = if(b1.gid == None) b2.gid else b1.gid;val data = if(b1.data == None) b2.data else b1.data;", bDgt1, "tid,gid,data");
					
					String guard = addGuard(bDgt1, "b.tid.get <= "+dg.getMaxArrival());
					String action = addAction(bDgt1,
							"val r = new java.util.Random()\n"
							+ "val rint = r.nextInt();val gid = \""+dg.getId()+"\"\n"
							+ "val data = CaseData(\"atr\"+rint,rint)\n"
							+ bDgt1+"(b.tid,Some(gid),Some(data))");
					
					String tDgt1 = addTransition("_dgt1", guard, action, bDgt1, eDgt1, mDgt1, dg.getId(), null);
					
					addArc(pDgp1, tDgt1, "PtT", typeId, bDgt1, addTtB(bDgt1, typeId, "tid", "Some(tid), None, None"), addBtT(bDgt1, typeId, "b.tid.get"), null, null, true, dg.getId());
					addArc(pDgp1, tDgt1, "TtP", typeId, bDgt1, null, addBtT(bDgt1, typeId, "b.tid.get + 1"), addAddedTime(bDgt1,dg.getExpression()), null, false, dg.getId());
					addArc(pDgp2, tDgt1, "TtP", entTypeId, bDgt1, null, addBtT(bDgt1, entTypeId, "(b.tid.get,b.gid.get)"), null, null, false, dg.getId());
					addArc(pDgpData, tDgt1, "TtP", dataTypeId, bDgt1, null, addBtT(bDgt1, dataTypeId, "((b.tid.get,b.gid.get),b.data.get)"), null, null, false, dg.getId());
					
				}
				if (d instanceof Function) {
					// TODO:
				}
				if (d instanceof Queue) {
					Queue q = (Queue) d;
					addQueue(entTypeId, qentTypeId, bQentTypeId, eQentTypeId, mQentTypeId, q, "");
				}
				if (d instanceof Resource) {
					Resource r = (Resource) d;
					createResourcePlace(r.getId(), entTypeId);
					addResource(r.getId(), r.getLabel(), r.getNumberOfResource());
				}
				if (d instanceof DataTable) {
					// TODO:
				}
			}
			
			// Convert Nodes
			for (String ni : p.getNodes().keySet()) {
				io.iochord.apps.ips.model.ism.v1.Node n = p.getNodes().get(ni);
				result.getConversionMap().put(n.getId(), n);
				if (n instanceof Start) {
					Start na = (Start) n;
					String pstart = addPlace(na.getId()+KeyElement.START0STRCONC, "_nap1", entTypeId, "", na.getId(), null);
					if (na.getGenerator() != null) {
						String tGen = cleanId("t_",na.getGenerator().getValue().getId()+"_mergegen");
						String tSilent = transIfExist.containsKey(tGen) ? tGen : addTransition("_start", null, null, bEntTypeId, eEntTypeId, mEntTypeId, na.getId(), na.getGenerator().getValue().getId()+"_mergegen");
						if(!transIfExist.containsKey(tGen))
							addArc(placeshub.get(na.getGenerator().getId()+KeyElement.START0STRCONC), tSilent, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getGenerator().getValue().getId());
						transIfExist.put(tGen, true);
						addArc(pstart, tSilent, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, na.getGenerator().getValue().getId());
					}
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					
					addPlace(no.getId()+"_end_0", "_nop1", entTypeId, "", no.getId(), null);
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					
					String pNap1 = addPlace(na.getId()+"_end_0", "_nap1", entTypeId, "", na.getId(), null);
					
					if(na.getQueue() != null && na.getResource() != null) {
						String qidClean = cleanId(null, na.getQueue().getValue().getId());
						String tQstt = "t_"+qidClean+KeyElement.QSTTSTRCONC;
						String pQendp = "p_"+qidClean+KeyElement.QENDPSTRCONC;
						
						addArc(pNap1, tQstt, "PtT", entTypeId, bQentTypeId, addTtB(bQentTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMENONECONC), addBtT(bQentTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getId());
						String tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bQentResTypeId, eQentResTypeId, mQentResTypeId, na.getId(), null);
						addArc(pQendp, tNatstart, "PtT", qentTypeId, bQentResTypeId, addTtB(bQentResTypeId, qentTypeId, "entity::queue", "Some(entity), None, Some(queue)"), addBtT(bQentResTypeId, qentTypeId, KeyElement.BENTTQGETCONC), null, null, true, na.getId());
						addArc(pQendp, tNatstart, "TtP", qentTypeId, bQentResTypeId, null, addBtT(bQentResTypeId, qentTypeId, KeyElement.BQGETCONC), null, null, false, na.getId());
						String pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entResTypeId, "", na.getId(), null);
					    addArc(pNap2, tNatstart, "TtP", entResTypeId, bQentResTypeId, null, addBtT(bQentResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(bQentResTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
					    String tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntResTypeId, eEntResTypeId, mEntResTypeId, na.getId(), null);
					    addArc(pNap2, tNatend, "PtT", entResTypeId, bEntResTypeId, addTtB(bEntResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(bEntResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
					    String pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
					    addArc(pNap3, tNatend, "TtP", entTypeId, bEntResTypeId, null, addBtT(bEntResTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, na.getId());
					   
					    addArc(cleanId("r_", na.getResource().getId()), tNatstart, "PtT", KeyElement.RESCONC+entTypeId+"]", bQentResTypeId, addTtB(bQentResTypeId, KeyElement.RESCONC+entTypeId+"]", "resource", "None, Some(resource), None"), addBtT(bQentResTypeId, KeyElement.RESCONC+entTypeId+"]", KeyElement.BRESGETCONC), null, null, true, na.getId());
					    addArc(cleanId("r_", na.getResource().getId()), tNatend, "TtP", KeyElement.RESCONC+entTypeId+"]", bEntResTypeId, null, addBtT(bEntResTypeId, KeyElement.RESCONC+entTypeId+"]", KeyElement.BRESGETCONC), null, null, false, na.getId());
					}
					else if(na.getQueue() != null) {
						String qidClean = cleanId(null, na.getQueue().getValue().getId());
						String tQstt = "t_"+qidClean+KeyElement.QSTTSTRCONC;
						String pQendp = "p_"+qidClean+KeyElement.QENDPSTRCONC;
						
						addArc(pNap1, tQstt, "PtT", entTypeId, bQentTypeId, addTtB(bQentTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMENONECONC), addBtT(bQentTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getId());
						String tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bQentTypeId, eQentTypeId, mQentTypeId, na.getId(), null);
						addArc(pQendp, tNatstart, "PtT", qentTypeId, bQentTypeId, addTtB(bQentTypeId, qentTypeId, "entity::queue", "Some(entity), Some(queue)"), addBtT(bQentTypeId, qentTypeId, KeyElement.BENTTQGETCONC), null, null, true, na.getId());
						addArc(pQendp, tNatstart, "TtP", qentTypeId, bQentTypeId, null, addBtT(bQentTypeId, qentTypeId, KeyElement.BQGETCONC), null, null, false, na.getId());
						String pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entTypeId, "", na.getId(), null);
						addArc(pNap2, tNatstart, "TtP", entTypeId, bQentTypeId, null, addBtT(bQentTypeId, entTypeId, KeyElement.BENTTGETCONC), addAddedTime(bEntTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
						String tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntTypeId, eEntTypeId, mEntTypeId, na.getId(), null);
						addArc(pNap2, tNatend, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getId());
						String pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
						addArc(pNap3, tNatend, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, na.getId());  
					}
					else if(na.getResource() != null) {
					   String tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bEntResTypeId, eEntResTypeId, mEntResTypeId, na.getId(), null);
					   addArc(pNap1, tNatstart, "PtT", entTypeId, bEntResTypeId, addTtB(bEntResTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMENONECONC), addBtT(bEntResTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getId());
					   String pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entResTypeId, "", na.getId(), null);
					   addArc(pNap2, tNatstart, "TtP", entResTypeId, bEntResTypeId, null, addBtT(bEntResTypeId, entResTypeId, "(b.entity.get,b.resource.get)"), addAddedTime(bEntResTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
					   String tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntResTypeId, eEntResTypeId, mEntResTypeId, na.getId(), null);
					   addArc(pNap2, tNatend, "PtT", entResTypeId, bEntResTypeId, addTtB(bEntResTypeId, entResTypeId, "(entity, resource)", "Some(entity), Some(resource)"), addBtT(bEntResTypeId, entResTypeId, "(b.entity.get, b.resource.get)"), null, null, true, na.getId());
					   String pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
					   addArc(pNap3, tNatend, "TtP", entTypeId, bEntResTypeId, null, addBtT(bEntResTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, na.getId());
					   
					   addArc(cleanId("r_", na.getResource().getId()), tNatstart, "PtT", KeyElement.RESCONC+entTypeId+"]", bEntResTypeId, addTtB(bEntResTypeId, KeyElement.RESCONC+entTypeId+"]", "resource", "None, Some(resource)"), addBtT(bEntResTypeId, KeyElement.RESCONC+entTypeId+"]", KeyElement.BRESGETCONC), null, null, true, na.getId());
					   addArc(cleanId("r_", na.getResource().getId()), tNatend, "TtP", KeyElement.RESCONC+entTypeId+"]", bEntResTypeId, null, addBtT(bEntResTypeId, KeyElement.RESCONC+entTypeId+"]", KeyElement.BRESGETCONC), null, null, false, na.getId());
					}
					else {
					   String tNatstart = addTransition(KeyElement.NATSTARTSTRCONC, null, null, bEntTypeId, eEntTypeId, mEntTypeId, na.getId(), null);
					   addArc(pNap1, tNatstart, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getId());
					   String pNap2 = addPlace(null, KeyElement.NAP2STRCONC, entTypeId, "", na.getId(), null);
					   addArc(pNap2, tNatstart, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), addAddedTime(bEntTypeId,na.getProcessingTimeExpression()), null, false, na.getId());
					   String tNatend = addTransition(KeyElement.NATENDSTRCONC, null, null, bEntTypeId, eEntTypeId, mEntTypeId, na.getId(), null);
					   addArc(pNap2, tNatend, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, na.getId());
					   String pNap3 = addPlace(na.getId()+KeyElement.START0STRCONC, KeyElement.NAP3STRCONC, entTypeId, "", na.getId(), null);
					   addArc(pNap3, tNatend, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, na.getId());
					}
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					
					String bt = null;
					String bp = null;
					
					if (b.getGate() == BranchGate.AND) { 
						bt = addTransition("_bt", null, null, bEntTypeId, eEntTypeId, mEntTypeId, b.getId(), null);
					}
					
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getTarget().getValue() != b) continue;
							String pBpis = addPlace(b.getId()+KeyElement.ENDSTRCONC+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
							addArc(pBpis, bt, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, "entitiy", KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, b.getId());
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getTarget().getValue() != b) continue;
								String pBpis = addPlace(b.getId()+KeyElement.ENDSTRCONC+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								
								String actiondef = "val newb = b.copy(cond = Some(scala.util.Random.nextDouble()));newb";
								String action = addAction(bEntBrTypeId, actiondef);
								
								String tCondGen = addTransition("_condGen", null, action, bEntBrTypeId, eEntBrTypeId, mEntBrTypeId, b.getId(), null);
								String pBpt = addPlace(null, "_bpt", entBrTypeId, "", b.getId(), null);
								bp = pBpt;
								addArc(pBpis, tCondGen, "PtT", entTypeId, bEntBrTypeId, addTtB(bEntBrTypeId, entTypeId, KeyElement.ENTCONC, "Some(entity),None"), addBtT(bEntBrTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, b.getId());
								addArc(pBpt, tCondGen, "TtP", entBrTypeId, bEntBrTypeId, addTtB(bEntBrTypeId, entBrTypeId, "(entity,cond)", "Some(entity),Some(cond)"), addBtT(bEntBrTypeId, entBrTypeId, "(b.entity.get,b.cond.get)"), null, null, false, b.getId());
							}
							else {
								if (c.getSource().getValue() != b) continue;
								String pBpos = addPlace(b.getId()+KeyElement.STARTSTRCONC+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								bp = pBpos;
							}
						}
						
						i++;
					}
					
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if(b.getGate() == BranchGate.AND) {
							if (c.getSource().getValue() != b) continue;
							String pBpos = addPlace(b.getId()+KeyElement.STARTSTRCONC+c.getSourceIndex(), "_bpo_" + i + "s", entTypeId, "", b.getId(), null);
							addArc(pBpos, bt, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, b.getId());
						}
						else if(b.getGate() == BranchGate.XOR) {
							if(b.getType() == BranchType.SPLIT) {
								if (c.getSource().getValue() != b) continue;
								String guard = null;
								
								if (b.getRule() == BranchRule.CONDITION) {
									String guarddef = "b.cond.get "+c.getAttributes().get("condition");
									guard = addGuard(bEntBrTypeId, guarddef);
								}
								String pBpos = addPlace(b.getId()+KeyElement.STARTSTRCONC+c.getSourceIndex(), "_bpo" + i + "s", entTypeId, "", b.getId(), null);
								String tSilent = addTransition("_temps_"+i, guard, null, bEntBrTypeId, eEntBrTypeId, mEntBrTypeId, b.getId(), null);
								addArc(bp, tSilent, "PtT", entBrTypeId, bEntBrTypeId, addTtB(bEntBrTypeId, entBrTypeId, "(entity,cond)", "Some(entity),Some(cond)"), addBtT(bEntBrTypeId, entBrTypeId, "(b.entity.get,b.cond.get)"), null, null, true, b.getId());
								addArc(pBpos, tSilent, "TtP", entTypeId, bEntBrTypeId, null, addBtT(bEntBrTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, b.getId());
							}
							else {
								if (c.getTarget().getValue() != b) continue;
								String pBpis = addPlace(b.getId()+KeyElement.ENDSTRCONC+c.getTargetIndex(), "_bpi" + i + "s", entTypeId, "", b.getId(), null);
								String tSilent = addTransition("_temps_"+i, null, null, bEntTypeId, eEntTypeId, mEntTypeId, b.getId(), null);
								addArc(pBpis, tSilent, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, b.getId());
								addArc(bp, tSilent, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, b.getId());
							}
						}
						
						i++;
					}
				}
			}
			// Convert Arcs
			for (String s : p.getConnectors().keySet()) {
				Connector c = p.getConnectors().get(s);
				
				String source = placeshub.get(c.getSource().getId()+KeyElement.STARTSTRCONC+c.getSourceIndex());
				String target = placeshub.get(c.getTarget().getId()+KeyElement.ENDSTRCONC+c.getTargetIndex());
				
				String[] oriRole = target.contains("-stop-") ? new String[]{target,"_stop"} : new String[]{c.getId(), "silent"};
				String tSilent = addTransition(oriRole[1], null, null, bEntTypeId, eEntTypeId, mEntTypeId, oriRole[0], null);
				addArc(source, tSilent, "PtT", entTypeId, bEntTypeId, addTtB(bEntTypeId, entTypeId, KeyElement.ENTCONC, KeyElement.SOMECONC), addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, true, c.getId());
				addArc(target, tSilent, "TtP", entTypeId, bEntTypeId, null, addBtT(bEntTypeId, entTypeId, KeyElement.BENTTGETCONC), null, null, false, c.getId());
			}
		}
		
		result.setConvertedModel(factory.toString());
		
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
	
	public IsmGraph revert(Ism2CpnscalaModel cgraph) {
		return null;
	}
	
}
