package io.iochord.dev.chdsr.model.converter.sbp2cpn;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.HLAnnotation;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.HLMarking;
import org.cpntools.accesscpn.model.ModelFactory;
import org.cpntools.accesscpn.model.Name;
import org.cpntools.accesscpn.model.Node;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.Sort;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.cpntypes.CPNInt;
import org.cpntools.accesscpn.model.cpntypes.CPNType;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;
import org.cpntools.accesscpn.model.declaration.DeclarationFactory;
import org.cpntools.accesscpn.model.declaration.TypeDeclaration;
import org.cpntools.accesscpn.model.declaration.VariableDeclaration;
import org.cpntools.accesscpn.model.impl.PetriNetImpl;

import io.iochord.dev.chdsr.model.converter.Converter;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Direction;
import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Activity;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch;
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
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class Sbpnet2CpnmlBiConverter implements Converter<Sbpnet, PetriNet> {
	
	@Getter
	protected ModelFactory factory = ModelFactory.INSTANCE;

	@Getter
	protected DeclarationFactory dfactory = DeclarationFactory.INSTANCE;
	
	@Getter
	protected CpntypesFactory cfactory = CpntypesFactory.INSTANCE;
	
	private Map<Class<?>, Integer> counters = new LinkedHashMap<>();
	
	private int getCounter(Class<?> clazz) {
		if (!counters.containsKey(clazz)) {
			counters.put(clazz, 0);
		}
		counters.put(clazz, counters.get(clazz) + 1);
		return counters.get(clazz);
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
		place.setId("PLACE" + getCounter(Place.class));
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
	public PetriNet convert(Sbpnet snet) {
		Sbpnet2CpnmlBiConverter converter = new Sbpnet2CpnmlBiConverter();
		PetriNet net = converter.createPetriNet(snet.getId());
		Page page =  converter.addPage(net, "SBPNET"); 
		converter.addTypeDeclaration(net, "INT", converter.getCfactory().createCPNInt(), true);
		converter.addVariableDeclaration(net, "i", "INT");
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
					Place dgp1 = converter.addPlace(dgpage, dg.getLabel() + "_dgp1", "INT", "1`1");
					Place dgp2 = converter.addPlace(dgpage, dg.getLabel() + "_dgp2", "INT", "1`1");
					Place dgp3 = converter.addPlace(dgpage, dg.getLabel() + "_dgp3", "INT", "");
					Place dgp4 = converter.addPlace(dgpage, dg.getLabel() + "_dgp4", "INT", "");
					Transition dgt1 = converter.addTransition(dgpage, dg.getLabel() + "_dgt1");
					Transition dgt2 = converter.addTransition(dgpage, dg.getLabel() + "_dgt2");
					converter.addArc(dgpage, dgp1, dgt1, "i");
					converter.addArc(dgpage, dgt1, dgp1, "i");
					converter.addArc(dgpage, dgt1, dgp2, "i");
					converter.addArc(dgpage, dgp2, dgt2, "i");
					converter.addArc(dgpage, dgt2, dgp2, "i");
					converter.addArc(dgpage, dgt2, dgp4, "i");
					converter.addArc(dgpage, dgt2, dgp3, "i");
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
			// Collect Connector Info
			Map<Element, List<Element>> inputSets = new LinkedHashMap<>();
			Map<Element, List<Element>> outputSets = new LinkedHashMap<>();
			for (String s : p.getConnectors().keySet()) {
				Connector c = p.getConnectors().get(s);
				
			}
			// Convert Nodes
			for (String ni : p.getNodes().keySet()) {
				io.iochord.dev.chdsr.model.sbpnet.v1.Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
					Page napage =  converter.addPage(net, "START " + na.getLabel()); 
					converter.addPlace(napage, na.getLabel() + "_nap1", "INT", "");
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					Page nopage =  converter.addPage(net, "STOP " + no.getLabel()); 
					converter.addPlace(nopage, no.getLabel() + "_nop1", "INT", "");
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					Page napage =  converter.addPage(net, "ACTIVITY " + na.getLabel()); 
					converter.addPlace(napage, na.getLabel() + "_nap1", "INT", "");
					converter.addPlace(napage, na.getLabel() + "_nap2", "INT", "");
					converter.addPlace(napage, na.getLabel() + "_nap3", "INT", "");
					converter.addPlace(napage, na.getLabel() + "_nap4", "INT", "");
					converter.addPlace(napage, na.getLabel() + "_nap5", "INT", "");
					converter.addTransition(napage, na.getLabel() + "_natstart");
					converter.addTransition(napage, na.getLabel() + "_natend");
					converter.addPlace(napage, na.getLabel() + "_naqp1", "INT", "");
					converter.addPlace(napage, na.getLabel() + "_naqp2", "INT", "");
					converter.addTransition(napage, na.getLabel() + "_naqtpool");
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					//Page bpage =  converter.addPage(net, "BRANCH " + b.getLabel()); 
					// TODO:
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
		return net;
	}

	@Override
	public Sbpnet revert(PetriNet o) {
		// TODO Auto-generated method stub
		return null;
	}
}
