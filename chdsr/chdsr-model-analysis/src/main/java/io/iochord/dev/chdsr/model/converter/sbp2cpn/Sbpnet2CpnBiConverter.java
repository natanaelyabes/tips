package io.iochord.dev.chdsr.model.converter.sbp2cpn;

import io.iochord.dev.chdsr.model.converter.Converter;
import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Place;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
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
		CPNGraph cnet = new CPNGraph();
		Sbpnet2CpnBiConverterModel model = new Sbpnet2CpnBiConverterModel(snet, cnet);
		// Convert Pages
		for (String pi : snet.getPages().keySet()) {
			Page p = snet.getPages().get(pi);
			// Convert Data Nodes
			for (String di : p.getData().keySet()) {
				Data d = p.getData().get(di);
				if (d instanceof ObjectType) {
					
				}
				if (d instanceof Generator) {
					Generator dg = (Generator) d;
					Place<?> dgP1 = new Place<String>(dg.getId() + "_dgp1", dg.getLabel() + "_dgp1", null);
					cnet.addPlace(dgP1);
					Place<?> dgP2 = new Place<String>(dg.getId() + "_dgp2", dg.getLabel() + "_dgp2", null);
					cnet.addPlace(dgP2);
					Place<?> dgP3 = new Place<String>(dg.getId() + "_dgp3", dg.getLabel() + "_dgp3", null);
					cnet.addPlace(dgP3);
					Place<?> dgP4 = new Place<String>(dg.getId() + "_dgp4", dg.getLabel() + "_dgp4", null);
					cnet.addPlace(dgP4);
					Transition dgT1 = new Transition(dg.getId() + "_dgt1", dg.getLabel() + "_dgt1", null);
					cnet.addTransition(dgT1);
					Transition dgT2 = new Transition(dg.getId() + "_dgt2", dg.getLabel() + "_dgt2", null);
					cnet.addTransition(dgT2);
					// Arc<?> dgaT1P1 = new Arc<String>(dg.getId() + "_t1_p1", dgP1, dgT1,
					// Direction.TtP());
				}
				if (d instanceof Function) {
					// TODO:
				}
				if (d instanceof Queue) {
					// TODO:
				}
				if (d instanceof Resource) {
					// TODO:
				}
				if (d instanceof DataTable) {
					// TODO:
				}
			}
			// Convert Nodes
			for (String ni : p.getData().keySet()) {
				Node n = p.getNodes().get(ni);
				if (n instanceof Start) {
					Start na = (Start) n;
					Place<?> naP1 = new Place<String>(na.getId() + "_nap1", na.getLabel() + "_nap1", null);
					cnet.addPlace(naP1);
				}
				if (n instanceof Stop) {
					Stop no = (Stop) n;
					Place<?> noP1 = new Place<String>(no.getId() + "_nop1", no.getLabel() + "_nop1", null);
					cnet.addPlace(noP1);
				}
				if (n instanceof Activity) {
					Activity na = (Activity) n;
					Place<?> naP1 = new Place<String>(na.getId() + "_nap1", na.getLabel() + "_nap1", null);
					cnet.addPlace(naP1);
					Place<?> naP2 = new Place<String>(na.getId() + "_nap2", na.getLabel() + "_nap2", null);
					cnet.addPlace(naP2);
					Place<?> naP3 = new Place<String>(na.getId() + "_nap3", na.getLabel() + "_nap3", null);
					cnet.addPlace(naP3);
					Place<?> naP4 = new Place<String>(na.getId() + "_nap4", na.getLabel() + "_nap4", null);
					cnet.addPlace(naP4);
					Place<?> naP5 = new Place<String>(na.getId() + "_nap5", na.getLabel() + "_nap5", null);
					cnet.addPlace(naP5);
					Transition naTStart = new Transition(na.getId() + "_natstart", na.getLabel() + "_natstart", null);
					cnet.addTransition(naTStart);
					Transition naTEnd = new Transition(na.getId() + "_natend", na.getLabel() + "_natend", null);
					cnet.addTransition(naTEnd);
					Place<?> naqP1 = new Place<String>(na.getId() + "_naqp1", na.getLabel() + "_naqp1", null);
					cnet.addPlace(naqP1);
					Place<?> naqP2 = new Place<String>(na.getId() + "_naqp2", na.getLabel() + "_naqp2", null);
					cnet.addPlace(naqP2);
					Transition naqTPool = new Transition(na.getId() + "_naqtpool", na.getLabel() + "_naqtpool", null);
					cnet.addTransition(naqTPool);
				}
				if (n instanceof Branch) {
					// TODO:
				}
				if (n instanceof Monitor) {
					// TODO:
				}
			}
		}
		return model.getConvertedModel();
	}

	@Override
	public Sbpnet revert(CPNGraph cnet) {
		return null;
	}
}
