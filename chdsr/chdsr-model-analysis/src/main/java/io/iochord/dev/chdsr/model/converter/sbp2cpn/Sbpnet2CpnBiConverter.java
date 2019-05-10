package io.iochord.dev.chdsr.model.converter.sbp2cpn;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.iochord.dev.chdsr.model.converter.Converter;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Arc;
import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Direction;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Place;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition;
import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.Page;
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

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class Sbpnet2CpnBiConverter implements Converter<Sbpnet, CPNGraph> {
	@Override
	public CPNGraph convert(Sbpnet snet) {
		CPNScalaGraph cnet = new CPNScalaGraph();
		Sbpnet2CpnBiConverterModel model = new Sbpnet2CpnBiConverterModel(snet, cnet);
		// Convert Pages
		for (String pi : snet.getPages().keySet()) {
			Page p = snet.getPages().get(pi);
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				if (d instanceof ObjectType) {
					ObjectType ot = (ObjectType) d;
					cnet.getColsets().put(ot.getLabel(), ot.getFields().keySet().toString());
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					Place<String> dgP1 = new Place<>(dg.getId() + "_dgp1", dg.getLabel() + "_dgp1", null);
					cnet.addPlace(dgP1);
					Place<String> dgP2 = new Place<>(dg.getId() + "_dgp2", dg.getLabel() + "_dgp2", null);
					cnet.addPlace(dgP2);
					Place<String> dgP3 = new Place<>(dg.getId() + "_dgp3", dg.getLabel() + "_dgp3", null);
					cnet.addPlace(dgP3);
					Place<String> dgP4 = new Place<>(dg.getId() + "_dgp4", dg.getLabel() + "_dgp4", null);
					cnet.addPlace(dgP4);
					Transition dgT1 = new Transition(dg.getId() + "_dgt1", dg.getLabel() + "_dgt1", null);
					cnet.addTransition(dgT1);
					Transition dgT2 = new Transition(dg.getId() + "_dgt2", dg.getLabel() + "_dgt2", null);
					cnet.addTransition(dgT2);
					Arc<String> dga = new Arc<String>(dg.getId() + "_p1_t1", dgP1, dgT1, Direction.PtT());
				}
				if (d instanceof Function) {
					Function f = (Function) d;
					// TODO:
				}
				if (d instanceof Queue) {
					Queue q = (Queue) d;
					// TODO:
				}
				if (d instanceof Resource) {
					Resource r = (Resource) d;
					// TODO:
				}
				if (d instanceof DataTable) {
					DataTable dt = (DataTable) d;
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
				Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
					Place<String> naP1 = new Place<>(na.getId() + "_nap1", na.getLabel() + "_nap1", null);
					cnet.addPlace(naP1);
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					Place<String> noP1 = new Place<>(no.getId() + "_nop1", no.getLabel() + "_nop1", null);
					cnet.addPlace(noP1);
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					Place<String> naP1 = new Place<>(na.getId() + "_nap1", na.getLabel() + "_nap1", null);
					cnet.addPlace(naP1);
					Place<String> naP2 = new Place<>(na.getId() + "_nap2", na.getLabel() + "_nap2", null);
					cnet.addPlace(naP2);
					Place<String> naP3 = new Place<>(na.getId() + "_nap3", na.getLabel() + "_nap3", null);
					cnet.addPlace(naP3);
					Place<String> naP4 = new Place<>(na.getId() + "_nap4", na.getLabel() + "_nap4", null);
					cnet.addPlace(naP4);
					Place<String> naP5 = new Place<>(na.getId() + "_nap5", na.getLabel() + "_nap5", null);
					cnet.addPlace(naP5);
					Transition naTStart = new Transition(na.getId() + "_natstart", na.getLabel() + "_natstart", null);
					cnet.addTransition(naTStart);
					Transition naTEnd = new Transition(na.getId() + "_natend", na.getLabel() + "_natend", null);
					cnet.addTransition(naTEnd);
					Place<String> naqP1 = new Place<>(na.getId() + "_naqp1", na.getLabel() + "_naqp1", null);
					cnet.addPlace(naqP1);
					Place<String> naqP2 = new Place<>(na.getId() + "_naqp2", na.getLabel() + "_naqp2", null);
					cnet.addPlace(naqP2);
					Transition naqTPool = new Transition(na.getId() + "_naqtpool", na.getLabel() + "_naqtpool", null);
					cnet.addTransition(naqTPool);
				}
				if (n instanceof Branch) {
					Branch b = (Branch) n;
					b.toString();
					// TODO:
				}
				if (n instanceof Monitor) {
					Monitor m = (Monitor) n;
					// TODO:
				}
			}
			// Convert Arcs
			for (String s : p.getConnectors().keySet()) {
				Connector c = p.getConnectors().get(s);
			}
		}
		return model.getConvertedModel();
	}

	@Override
	public Sbpnet revert(CPNGraph cnet) {
		return null;
	}
}
