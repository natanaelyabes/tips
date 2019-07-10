package io.iochord.dev.chdsr.model.converter.sbp2cpn;

import java.util.LinkedHashMap;
import java.util.Map;

/*
import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.HLAnnotation;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.HLMarking;
import org.cpntools.accesscpn.model.Instance;
import org.cpntools.accesscpn.model.ModelFactory;
import org.cpntools.accesscpn.model.Name;
import org.cpntools.accesscpn.model.Node;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.ParameterAssignment;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.RefPlace;
import org.cpntools.accesscpn.model.Sort;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.cpntypes.CPNProduct;
import org.cpntools.accesscpn.model.cpntypes.CPNType;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;
import org.cpntools.accesscpn.model.declaration.DeclarationFactory;
import org.cpntools.accesscpn.model.declaration.TypeDeclaration;
import org.cpntools.accesscpn.model.declaration.VariableDeclaration;
*/

import io.iochord.dev.chdsr.model.converter.Converter;
import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Activity;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchGate;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Function;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Generator;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Monitor;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Resource;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Start;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Stop;
import lombok.Getter;

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
	
	private Map<String, Integer> counters = new LinkedHashMap<>();
	
	private String getCounter(String clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return String.format("%8s", counters.get(clazz)).replace(' ', '0');
	}
	
	public String addPlace(String name, String type, String initialMarking) {
		String counter = getCounter(KeyElement.place);
		String mapid = "map"+counter;
		String multisetid = "ms"+counter;
		String placeid = "place"+counter;
		
		StringBuilder placefactory = new StringBuilder();
		placefactory.append( "val "+mapid+" = Map[("+type+",Long),Int]( "+initialMarking+" )\n" );
		placefactory.append( "val "+multisetid+" = new Multiset["+type+"]("+mapid+")\n" );
		placefactory.append( "val "+placeid+" = new Place(\""+placeid+"\",\""+name+"\","+multisetid+")\n" );
		placefactory.append( "cgraph.addPlace("+placeid+")\n" );
		placefactory.append("\n");
		
		factory.append(placefactory.toString());
		
		return placeid;
	}
	
	public String addTransition(String name, String guard, String action, String classbinding, String eval, String merge) {
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
		guardfactory.append( "val "+bindguardexpid+" = (bind:"+classbinding+") => {"+guarddef+"}\n" );
		guardfactory.append( guardid+".setGuardBind()\n" );
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
	
	public String addArc(String placeid, String transitionid, String direction, String type, String classbinding, String arcexp, String TtB, String BtT, String addTime, String noToken) {
		String counter = getCounter(KeyElement.arc);
		String arcid = "arc"+counter;
		
		StringBuilder arcfactory = new StringBuilder();
		arcfactory.append( "val "+arcid+" = new Arc["+type+","+classbinding+"](\""+arcid+"\","+placeid+","+transitionid+",Direction."+direction+")\n" );
		arcfactory.append( arcid+".setArcExp("+arcexp+")\n" );
		arcfactory.append( arcid+".setTokenToBind("+TtB+")\n" );
		arcfactory.append( arcid+".setBindToToken("+BtT+")\n" );
		arcfactory.append( arcid+".setBindToToken("+BtT+")\n" );
		if(addTime != null)
			arcfactory.append( arcid+".setAddTime("+addTime+")\n" );
		if(noToken != null)
			arcfactory.append( arcid+".setNoTokArcExp("+noToken+")\n" );
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
	
	/*
	@Override
	public PetriNet convert(Sbpnet snet) {
		Sbpnet2CpnscalaBiConverter converter = this;
		PetriNet net = converter.createPetriNet(snet.getId());
		Page page =  converter.addPage(net, "SBPNET"); 
		converter.addTypeDeclaration(net, "INT", converter.getCfactory().createCPNInt(), true);
		CPNProduct cpnType = converter.getCfactory().createCPNProduct();
		cpnType.addSort("INT");
		cpnType.addSort("INT");
		converter.addTypeDeclaration(net, "ENTITY", cpnType, true);
		converter.addVariableDeclaration(net, "i", "INT");
		Map<Element, List<Node>> eInputSets = new LinkedHashMap<>();
		Map<Element, List<Node>> eOutputSets = new LinkedHashMap<>();
		
		// Convert Pages
		for (String pi : snet.getPages().keySet()) {
			io.iochord.dev.chdsr.model.sbpnet.v1.Page p = snet.getPages().get(pi);
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				if (d instanceof ObjectType) {
					ObjectType ot = (ObjectType) d;
					converter.addTypeDeclaration(net, ot.getLabel(), converter.getCfactory().createCPNInt(), true);
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					Page dgpage =  converter.addPage(net, "GENERATOR " + dg.getLabel()); 
					Instance dgi = converter.addInstance(page, "GENERATOR " + dg.getLabel(), dgpage.getId());
					Place dgp4s = converter.addPlace(page, dg.getLabel() + "_dgp4s", "INT", "");
					converter.addArc(page, dgi, dgp4s, "i");
					
					Place dgp1 = converter.addPlace(dgpage, dg.getLabel() + "_dgp1", "INT", "1`1");
					Place dgp2 = converter.addPlace(dgpage, dg.getLabel() + "_dgp2", "INT", "1`1");
					Place dgp3 = converter.addPlace(dgpage, dg.getLabel() + "_dgp3", "INT", "");
					RefPlace dgp4 = converter.addRefPlace(dgpage, dg.getLabel() + "_dgp4", "INT", "");
					converter.addPortSocketAssignment(dgi, dgp4, dgp4s);
					
					Transition dgt1 = converter.addTransition(dgpage, dg.getLabel() + "_dgt1");
					Transition dgt2 = converter.addTransition(dgpage, dg.getLabel() + "_dgt2");
					converter.addArc(dgpage, dgp1, dgt1, "i");
					converter.addArc(dgpage, dgt1, dgp1, "i");
					converter.addArc(dgpage, dgt1, dgp2, "i");
					converter.addArc(dgpage, dgp2, dgt2, "i");
					converter.addArc(dgpage, dgt2, dgp2, "i");
					converter.addArc(dgpage, dgt2, dgp4, "i");
					converter.addArc(dgpage, dgt2, dgp3, "i");
					
					eOutputSets.put(d, Arrays.asList(new Node[] { dgp4s }));
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
				io.iochord.dev.chdsr.model.sbpnet.v1.Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
//					Page napage = converter.addPage(net, "START " + na.getLabel()); 
//					Place nap = converter.addPlace(napage, na.getLabel() + "_nap1", "INT", "");
					if (na.getGenerator() == null) {
						Place nap = converter.addPlace(page, na.getLabel() + "_nap1", "INT", "");
						eOutputSets.put(n, Arrays.asList(new Node[] { nap }));
					} else {
						List<Node> nap = eOutputSets.get(na.getGenerator());
						if (nap != null && nap.size() > 0) {
							eOutputSets.put(n, Arrays.asList(new Node[] { nap.get(0) }));
						}
					}
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
//					Page nopage =  converter.addPage(net, "STOP " + no.getLabel()); 
//					Place nop = converter.addPlace(nopage, no.getLabel() + "_nop1", "INT", "");
					Place nop = converter.addPlace(page, no.getLabel() + "_nop1", "INT", "");
					eInputSets.put(n, Arrays.asList(new Node[] { nop }));
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					Page napage =  converter.addPage(net, "ACTIVITY " + na.getLabel()); 
					Instance nai = converter.addInstance(page, "ACTIVITY " + na.getLabel(), napage.getId());
					Place nap1s = converter.addPlace(page, na.getLabel() + "_nap1", "INT", "");
					Place nap3s = converter.addPlace(page, na.getLabel() + "_nap3", "INT", "");
					converter.addArc(page, nap1s, nai, "i");
					converter.addArc(page, nai, nap3s, "i");
					
					RefPlace nap1 = converter.addRefPlace(napage, na.getLabel() + "_nap1", "INT", "");
					Place nap2 = converter.addPlace(napage, na.getLabel() + "_nap2", "INT", "");
					RefPlace nap3 = converter.addRefPlace(napage, na.getLabel() + "_nap3", "INT", "");
					Transition natstart = converter.addTransition(napage, na.getLabel() + "_natstart");
					Transition natend = converter.addTransition(napage, na.getLabel() + "_natend");
					converter.addPortSocketAssignment(nai, nap1, nap1s);
					converter.addPortSocketAssignment(nai, nap3, nap3s);
					converter.addArc(napage, nap1, natstart, "i");
					converter.addArc(napage, natstart, nap2, "i");
					converter.addArc(napage, nap2, natend, "i");
					converter.addArc(napage, natend, nap3, "i");
					
					eInputSets.put(n, Arrays.asList(new Node[] { nap1s }));
					eOutputSets.put(n, Arrays.asList(new Node[] { nap3s }));
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					List<Node> binputs = new ArrayList<>();
					List<Node> boutputs = new ArrayList<>();
					Page bpage =  converter.addPage(net,"BRANCH " + b.getGate() + " " + b.getType() + " " + b.getLabel()); 
					Instance bi = converter.addInstance(page, "BRANCH " + b.getGate() + " " + b.getType() + " " + b.getLabel(), bpage.getId());
					Transition bt = null;
					Place bp = null;
//					if (b.getGate() == BranchGate.AND) { 
						bt = converter.addTransition(bpage, b.getLabel() + "_bt");
//					} else if (b.getGate() == BranchGate.XOR) {
						bp = converter.addPlace(page, b.getLabel() + "_bp", "INT", "");
//					}
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getTarget() != b) continue;
						Place bpis = converter.addPlace(page, b.getLabel() + "_bpi" + i + "s", "INT", "");
						converter.addArc(page, bpis, bi, "i");
						RefPlace bpi = converter.addRefPlace(bpage, b.getLabel() + "_bpi" + i, "INT", "");
						converter.addPortSocketAssignment(bi, bpi, bpis);
//						if (b.getGate() == BranchGate.AND) { 
							converter.addArc(bpage, bpi, bt, "i");
//						} else if (b.getGate() == BranchGate.XOR) {
//							Transition bti = converter.addTransition(bpage, b.getLabel() + "_bti" + i);
//							converter.addArc(bpage, bpi, bti, "i");
//							converter.addArc(bpage, bti, bp, "i");
//						}
						binputs.add(bpis);
						i++;
					}
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getSource() != b) continue;
						Place bpos = converter.addPlace(page, b.getLabel() + "_bpo" + i + "s", "INT", "");
						converter.addArc(page, bi, bpos, "i");
						RefPlace bpo = converter.addRefPlace(bpage, b.getLabel() + "_bpo" + i, "INT", "");
						converter.addPortSocketAssignment(bi, bpo, bpos);
//						if (b.getGate() == BranchGate.AND) { 
							converter.addArc(bpage, bt, bpo, "i");
//						} else if (b.getGate() == BranchGate.XOR) {
//							Transition bto = converter.addTransition(bpage, b.getLabel() + "_bto" + i);
//							converter.addArc(bpage, bp, bto, "i");
//							converter.addArc(bpage, bto, bpo, "i");
//						}
						boutputs.add(bpos);
						i++;
					}
					eInputSets.put(n, binputs);
					eOutputSets.put(n, boutputs);
					// TODO:
					// Temporary use activity definition
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
				List<Node> sourcePlaces = eOutputSets.get(c.getSource());
				List<Node> targetPlaces = eInputSets.get(c.getTarget());
				if (sourcePlaces != null && targetPlaces != null) {
					Transition silent = converter.addTransition(page, "");
					converter.addArc(page, sourcePlaces.get(c.getSourceIndex()), silent, "i");
					converter.addArc(page, silent, targetPlaces.get(c.getTargetIndex()), "i");
				}
			}
		}
		return net;
	}
	*/
	
	public String convert(Sbpnet snet) {
		for (String pi : snet.getPages().keySet()) {
			io.iochord.dev.chdsr.model.sbpnet.v1.Page p = snet.getPages().get(pi);
			
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
					
					String p_dgNextCaseId = addPlace(dg.getLabel()+"_dgNextCaseId", typeId, "((1,0),1)");
					String p_dgStart = addPlace(dg.getLabel() + "_dgStart", entTypeId, "");
					String p_dgData = addPlace(dg.getLabel() + "_dgData", dataTypeId, "");
					
					String b_dgt1 = addBindingClass( "tid:Option[Int],gid:Option[String],data:Option[CaseData]" );
					String e_dgt1 = addEval("(b1.tid == b2.tid || b1.tid == None || b2.tid == None) && (b1.gid == b2.gid || b1.tid == None || b2.tid == None) && (b1.gid == b2.data || b1.data == None || b2.data == None)", b_dgt1);
					String m_dgt1 = addMerge("val tid = if(b1.tid == None) b2.tid else b1.tid;val gid = if(b1.gid == None) b2.gid else b1.gid;val data = if(b1.data == None) b2.data else b1.data;", b_dgt1, "tid,gid,data");
					
					String guard = null;
					String action = addAction(b_dgt1,"val r = new java.util.Random();val rint = r.nextInt();val gid = \""+dg.getId()+"\";val data = CaseData(\"nama\"+rint,rint);"+b_dgt1+"(b.tid,Some(gid),Some(data))");
					
					String dgt1 = addTransition(dg.getLabel() + "_dgt1", guard, action, b_dgt1, e_dgt1, m_dgt1);
					
					addArc(p_dgNextCaseId, dgt1, "PtT", typeId, b_dgt1, addArcExp("case tid:"+typeId+" => { Some(tid) }"), addTtB(b_dgt1,"inp match { case tid:"+typeId+" => Some(tid); case _ => None }, None, None"), addBtT(b_dgt1,"b.tid.get"), null, null);
					addArc(p_dgNextCaseId, dgt1, "TtP", typeId, b_dgt1, addArcExp("case tid:"+typeId+" => { Some(tid+1) }"), addTtB(b_dgt1,"inp match { case tid:"+typeId+" => Some(tid); case _ => None }, None, None"), addBtT(b_dgt1,"b.tid.get"), addAddedTime(b_dgt1,"Math.round(Gaussian(100, 10).draw())"), null);
					addArc(p_dgStart, dgt1, "TtP", entTypeId, b_dgt1, addArcExp("case (tid:"+typeId+",gid:String) => { Some(tid,gid) }"), addTtB(b_dgt1,"inp match { case (tid:"+typeId+",gid:Any) => Some(tid); case _ => None }, inp match { case (tid:Any,gid:String) => Some(gid); case _ => None }, None"), addBtT(b_dgt1,"(b.tid.get,b.gid.get)"), null, "3");
					addArc(p_dgData, dgt1, "TtP", dataTypeId, b_dgt1, addArcExp("case (tid:"+typeId+",gid:String,data:CaseData) => { Some(tid,gid,data) }"), addTtB(b_dgt1,"inp match { case (tid:"+typeId+",gid:Any,data:Any) => Some(tid); case _ => None }, inp match { case (tid:Any,gid:String,data:Any) => Some(gid); case _ => None }, inp match { case (tid:Any,gid:Any,data:CaseData) => Some(data); case _ => None }"), addBtT(b_dgt1,"(b.tid.get,b.gid.get,b.data.get)"), null, "3");
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
				io.iochord.dev.chdsr.model.sbpnet.v1.Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
//								Page napage =  converter.addPage(net, "START " + na.getLabel()); 
//								Place nap = converter.addPlace(napage, na.getLabel() + "_nap1", "INT", "");
					if (na.getGenerator() == null) {
						String p_nap1 = addPlace(na.getLabel() + "_nap1", entTypeId, "");
					} else {
						//if not null
					}
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					String p_nop1 = addPlace(no.getLabel() + "_nop1", entTypeId, "");
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					String p_nap1 = addPlace(na.getLabel() + "_nap1", entTypeId, "");
					String p_nap2 = addPlace(na.getLabel() + "_nap2", entTypeId, "");
					String p_nap3 = addPlace(na.getLabel() + "_nap3", entTypeId, "");
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					String p_bp = addPlace(b.getLabel() + "_bp", entTypeId, "");
					
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getTarget() != b) continue;
						String bpis = addPlace(b.getLabel() + "_bpi" + i + "s", entTypeId, "");
						
						i++;
					}
					i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getSource() != b) continue;
						String bpos = addPlace(b.getLabel() + "_bpo" + i + "s", entTypeId, "");
						
						i++;
					}
					// TODO:
					// Temporary use activity definition
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
				
			}
		}
	
		return factory.toString();
	}
	
	public Sbpnet revert(String scalacode) {
		return null;
	}
	
}
