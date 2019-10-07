package io.iochord.apps.ips.model.converter.sbp2cpn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import io.iochord.apps.ips.model.converter.Converter;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.components.Activity;
import io.iochord.apps.ips.model.ism.v1.components.Branch;
import io.iochord.apps.ips.model.ism.v1.components.BranchGate;
import io.iochord.apps.ips.model.ism.v1.components.DataTable;
import io.iochord.apps.ips.model.ism.v1.components.Function;
import io.iochord.apps.ips.model.ism.v1.components.Generator;
import io.iochord.apps.ips.model.ism.v1.components.Monitor;
import io.iochord.apps.ips.model.ism.v1.components.ObjectType;
import io.iochord.apps.ips.model.ism.v1.components.Queue;
import io.iochord.apps.ips.model.ism.v1.components.Resource;
import io.iochord.apps.ips.model.ism.v1.components.Start;
import io.iochord.apps.ips.model.ism.v1.components.Stop;
import lombok.Getter;

/**
 *
 * @package chdsr-model-analysis
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class Sbpnet2CpnmlBiConverter implements Converter<IsmGraph, PetriNet> {
	
	@Getter
	protected ModelFactory factory = ModelFactory.INSTANCE;

	@Getter
	protected DeclarationFactory dfactory = DeclarationFactory.INSTANCE;
	
	@Getter
	protected CpntypesFactory cfactory = CpntypesFactory.INSTANCE;
	
	private Map<Class<?>, Integer> counters = new LinkedHashMap<>();
	
	private String getCounter(Class<?> clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return String.format("%8s", counters.get(clazz)).replace(' ', '0');
	}
	
	public PetriNet createPetriNet(String name) {
		PetriNet net = factory.createPetriNet();
		net.setId("NET" + getCounter(PetriNet.class));
		Name n = factory.createName();
		n.setText(name);
		net.setName(n);
		return net;
	}
	
	public HLDeclaration addTypeDeclaration(PetriNet net, String name, CPNType value, boolean timed) {
		HLDeclaration hl = factory.createHLDeclaration();
		TypeDeclaration type;
		type = dfactory.createTypeDeclaration();
		type.setTypeName(name);
		type.setSort(value);
		value.setTimed(timed);
		hl.setStructure(type);
		hl.setId("HL" + getCounter(HLDeclaration.class));
		net.getLabel().add(hl);
		return hl;
	}

	public HLDeclaration addVariableDeclaration(PetriNet net, String name, String tname) {
		HLDeclaration hl = factory.createHLDeclaration();
		VariableDeclaration type;
		type = dfactory.createVariableDeclaration();
		type.setTypeName(tname);
		type.getVariables().add(name);
		hl.setStructure(type);
		hl.setId("HL" + getCounter(HLDeclaration.class));
		net.getLabel().add(hl);
		return hl;
	}
	
	public Page addPage(PetriNet net, String name) {
		Page page = factory.createPage();
		page.setId("PAGE" + getCounter(Page.class));
		Name n = factory.createName();
		n.setText(name);
		page.setName(n);
		net.getPage().add(page);
		return page;
	}
	
	public Place addPlace(Page page, String name, String type, String initialMarking) {
		Place place = factory.createPlace();
		place.setId("ID" + getCounter(Place.class));
		Name n = factory.createName();
		n.setText(name);
		place.setName(n);
		if (initialMarking != null) {
			HLMarking ml = factory.createHLMarking();
			ml.setText(initialMarking);
			place.setInitialMarking(ml);
		}
		Sort sort = factory.createSort();
		sort.setText(type);
		place.setSort(sort);
		page.getObject().add(place);
		return place;
	}
	
	public RefPlace addRefPlace(Page page, String name, String type, String initialMarking) {
		RefPlace rplace = factory.createRefPlace();
		rplace.setId("ID" + getCounter(Place.class));
		Name n = factory.createName();
		n.setText(name);
		rplace.setName(n);
		if (initialMarking != null) {
			HLMarking ml = factory.createHLMarking();
			ml.setText(initialMarking);
			rplace.setInitialMarking(ml);
		}
		Sort sort = factory.createSort();
		sort.setText(type);
		rplace.setSort(sort);
		page.getObject().add(rplace);
		return rplace;
	}

	public Instance addInstance(Page page, String name, String subpageId) {
		Instance subst = factory.createInstance();
		subst.setId("SUBST" + getCounter(Transition.class));
		subst.setSubPageID(subpageId);
		Name n = factory.createName();
		n.setText(name);
		subst.setName(n);
		page.getObject().add(subst);
		return subst;
	}
	
	public ParameterAssignment addPortSocketAssignment(Instance instance, RefPlace source, Place target) {
		ParameterAssignment pa = factory.createParameterAssignment();
		pa.setInstance(instance);
		pa.setValue(source.getId());
		pa.setParameter(target.getId());
		source.setRef(target);
		instance.getParameterAssignment().add(pa);
		return pa;
	}
	
	public Transition addTransition(Page page, String name) {
		Transition trans = factory.createTransition();
		trans.setId("TRANS" + getCounter(Transition.class));
		Name n = factory.createName();
		n.setText(name);
		trans.setName(n);
		page.getObject().add(trans);
		return trans;
	}
	
	public Arc addArc(Page page, Node source, Node target, String inscription) {
		Arc arc = factory.createArc();
		arc.setId("ARC" + getCounter(Arc.class));
		arc.setSource(source);
		arc.setTarget(target);
		HLAnnotation ann = factory.createHLAnnotation();
		ann.setText(inscription);
		arc.setHlinscription(ann);
		page.getArc().add(arc);
		return arc;
	}
	
	@Override
	public PetriNet convert(IsmGraph snet) {
		Sbpnet2CpnmlBiConverter converter = this;
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
			io.iochord.apps.ips.model.ism.v1.Page p = snet.getPages().get(pi);
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
					Place dgp4s = converter.addPlace(page, dg.getLabel() + "_dgp1", "INT", "");
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
				io.iochord.apps.ips.model.ism.v1.Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
//					Page napage =  converter.addPage(net, "START " + na.getLabel()); 
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
					if (b.getGate() == BranchGate.AND) { 
						bt = converter.addTransition(bpage, b.getLabel() + "_bt");
					} else if (b.getGate() == BranchGate.XOR) {
						bp = converter.addPlace(bpage, b.getLabel() + "_bp", "INT", "");
					}
					int i = 0;
					for (Connector c : p.getConnectors().values()) {
						if (c.getTarget() != b) continue;
						Place bpis = converter.addPlace(page, b.getLabel() + "_bpi" + i + "s", "INT", "");
						converter.addArc(page, bpis, bi, "i");
						RefPlace bpi = converter.addRefPlace(bpage, b.getLabel() + "_bpi" + i, "INT", "");
						converter.addPortSocketAssignment(bi, bpi, bpis);
						if (b.getGate() == BranchGate.AND) { 
							converter.addArc(bpage, bpi, bt, "i");
						} else if (b.getGate() == BranchGate.XOR) {
							Transition bti = converter.addTransition(bpage, b.getLabel() + "_bti" + i);
							converter.addArc(bpage, bpi, bti, "i");
							converter.addArc(bpage, bti, bp, "i");
						}
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
						if (b.getGate() == BranchGate.AND) { 
							converter.addArc(bpage, bt, bpo, "i");
						} else if (b.getGate() == BranchGate.XOR) {
							Transition bto = converter.addTransition(bpage, b.getLabel() + "_bto" + i);
							converter.addArc(bpage, bp, bto, "i");
							converter.addArc(bpage, bto, bpo, "i");
						}
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

	@Override
	public IsmGraph revert(PetriNet o) {
		// TODO Auto-generated method stub
		return null;
	}
}
