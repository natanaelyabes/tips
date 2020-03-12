package io.iochord.apps.ips.model.analysis.services.ism;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class IsmDiscoveryService extends AnIpsAsyncService<IsmDiscoveryConfiguration, IsmGraph> {

	@Override
	public IsmGraph run(ServiceContext context, IsmDiscoveryConfiguration config) {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		context.updateProgress(25, "Discovering DF matrix.");
		Map<String, Map<String, Long>> dfMatrix = new LinkedHashMap<>();
		Map<String, Map<String, Double>> dpMatrix = new LinkedHashMap<>();
		calculateDfMatrix(context, dfMatrix, config.getDatasetId(), config.getColCaseId(), config.getColEventActivity(), config.getColEventTimestamp(), config.getSkipRows());
		calculateDpMatrix(config, dpMatrix, dfMatrix);
		Map<String, Activity> nodes = new LinkedHashMap<>();
		for (Entry<String, Map<String, Long>> fae : dfMatrix.entrySet()) {
			nodes.put(fae.getKey(), null);
			for (String ta : fae.getValue().keySet()) {
				nodes.put(ta, null);
			}
		}
		return createGraph(context, factory, nodes, dfMatrix, dpMatrix);
	}
	
	private double calculateDpMatrix(IsmDiscoveryConfiguration config, Map<String, Map<String, Double>> dpMatrix, Map<String, Map<String, Long>> dfMatrix) {
		double incArcs = 0;
		double totalArcs = 0;
		for (Entry<String, Map<String, Long>> afe : dfMatrix.entrySet()) {
			String af = afe.getKey();
			for (Entry<String, Long> ate : afe.getValue().entrySet()) {
				totalArcs++;
				String at = ate.getKey();
				Long atp = ate.getValue();
				double ff = atp;
				double bf = (dfMatrix.containsKey(at) && dfMatrix.get(at).containsKey(af)) ? dfMatrix.get(at).get(af) : 0;
				double dep = 0d;
				if (af.equals(at)) {
					dep = 1;
				} else if (ff + bf > 0) {
					dep = (ff - bf) / (ff + bf);
				}
				if (ff > config.getPositiveObservationThreshold() && dep > config.getDependencyThreshold()) {
					incArcs++;
					if (!dpMatrix.containsKey(af)) {
						dpMatrix.put(af, new LinkedHashMap<>());
					}
					dpMatrix.get(af).put(at, dep);
				}
			}
		}
		double fitness = 0; 
		if (totalArcs > 0) {
			fitness = incArcs / totalArcs;
		}
		System.out.println("FPFitness: " + fitness);
		return fitness;
	}

	public IsmGraph createGraph(ServiceContext context, IsmFactory factory, Map<String, Activity> nodes, Map<String, Map<String, Long>> dfMatrix, Map<String, Map<String, Double>> dpMatrix) {
		IsmGraph result = factory.create();
		Page p = result.getPages().values().iterator().next();
		int ni = 0;
		for (String ea : nodes.keySet()) {
			ActivityImpl a = (ActivityImpl) factory.addActivity(p);
			a.setLabel(ea);
			a.setId("ACTIVITY" + ni);
			nodes.put(ea, a);
			ni++;
		}
		createConnector(context, factory, nodes, dfMatrix, dpMatrix, p, ni);
		return result;
	}

	private void createConnector(ServiceContext context, IsmFactory factory, Map<String, Activity> nodes, Map<String, Map<String, Long>> dfMatrix, Map<String, Map<String, Double>> dpMatrix, Page p, int ni) {
		int ci = 0;
		Map<String, Map<String, Long>> ins = new LinkedHashMap<>();
		Map<String, Map<String, Long>> outs = new LinkedHashMap<>();
		for (Entry<String, Map<String, Long>> afe : dfMatrix.entrySet()) {
			String af = afe.getKey();
			for (Entry<String, Long> ate : afe.getValue().entrySet()) {
				String at = ate.getKey();
				Long ff = ate.getValue();
				if (!outs.containsKey(af)) {
					outs.put(af, new LinkedHashMap<>());
				}
				outs.get(af).put(at, ff);
				if (!ins.containsKey(at)) {
					ins.put(at, new LinkedHashMap<>());
				}
				ins.get(at).put(af, ff);
			}
		}
		Map<String, NodeImpl> inNodes = new LinkedHashMap<>();
		Map<String, NodeImpl> outNodes = new LinkedHashMap<>();
		for (Entry<String, Activity> eae : nodes.entrySet()) {
			String ea = eae.getKey();
			ActivityImpl a = (ActivityImpl) eae.getValue();
			inNodes.put(ea, a);
			outNodes.put(ea, a);
			//*/
			double fi = 0;
			int cfi = 1;
			if (ins.containsKey(ea)) {
				for (Entry<String, Long> ina : ins.get(ea).entrySet()) {
					fi += ina.getValue();
					cfi++;
				}
			}
			double fo = 0;
			int cfo = 1;
			if (outs.containsKey(ea)) {
				for (Entry<String, Long> oua : outs.get(ea).entrySet()) {
					fo += oua.getValue();
				}
				cfo++;
			}
			BranchGate bg = BranchGate.XOR;
			if (fi * cfo == fo || fo * cfi == fi) {
				bg = BranchGate.AND;
			}
			if (ins.containsKey(ea) && ins.get(ea).size() > 1) {
				BranchImpl ib = (BranchImpl) factory.addBranch(p);
				ib.setType(BranchType.JOIN);
				ib.setId("JOIN-BRANCH-" + a.getId());
				ib.setGate(bg);
				ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, ib, a);
				c.setId("CONNECTOR" + ni);
				c.setLabel("");
				inNodes.put(ea, ib);
			}
			if (outs.containsKey(ea) && outs.get(ea).size() > 1) {
				BranchImpl ob = (BranchImpl) factory.addBranch(p);
				ob.setType(BranchType.SPLIT);
				ob.setId("SPLIT-BRANCH-" + a.getId());
				ob.setGate(bg);
				ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, a, ob);
				c.setId("CONNECTOR" + ni);
				c.setLabel("");
				outNodes.put(ea, ob);
			}
			//*/
		}
		for (Entry<String, Map<String, Double>> fae : dpMatrix.entrySet()) {
			String fa = fae.getKey();
			for (Entry<String, Double> tae : fae.getValue().entrySet()) {
				String ta = tae.getKey();
				if (nodes.containsKey(fa) && nodes.containsKey(ta)) {
					ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, outNodes.get(fa), inNodes.get(ta));
					c.setId("CONNECTOR" + ni);
					c.setLabel(String.valueOf(dfMatrix.get(fa).get(ta)) + " (" + String.valueOf(dpMatrix.get(fa).get(ta)) + ")");
					if (++ci % 100 == 0) {
						context.updateProgress(75, ci + " connector found.");
					}
				}
			}
		}
	}

	public Map<String, Map<String, Long>> calculateDfMatrix(ServiceContext context, Map<String, Map<String, Long>> dfMatrix, String tabName, String colCaseId, String colActivity, String colTs,  int skip) {
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
				.append("	CASE  ")
				.append("		WHEN ce.c = ne.c ")
				.append("			THEN ce.a  ")
				.append("		ELSE 'start' ")
				.append("	END AS af, ")
				.append("	ne.a AS at, ")
				.append("	count(*) AS f ")
				.append("FROM ( ")
				.append("	SELECT  ")
				.append("		row_number() over () AS ri,  ")
				.append("		").append(colCaseId).append(" AS c,  ")
				.append("		").append(colActivity).append(" AS a  ")
				.append("	FROM ").append(tabName)
				.append("   WHERE eid > ").append(skip)
				.append("	ORDER BY ").append(colCaseId).append(", ").append(colTs).append(" ")
				.append(") AS ce ")
				.append("JOIN ")
				.append("( ")
				.append("	SELECT  ")
				.append("		row_number() over () AS ri,  ")
				.append("		").append(colCaseId).append(" AS c,  ")
				.append("		").append(colActivity).append(" AS a  ")
				.append("	FROM ").append(tabName)
				.append("  WHERE eid > ").append(skip)
				.append("	ORDER BY ").append(colCaseId).append(", ").append(colTs).append(" ")
				.append(") AS ne ")
				.append("ON ne.ri = ce.ri + 1 ")
				.append("GROUP BY af, at");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String actFrom = rs.getString(1);
					String actTo = rs.getString(2);
					long actFrequency = rs.getLong(3);
					if (!dfMatrix.containsKey(actFrom)) {
						dfMatrix.put(actFrom, new LinkedHashMap<>());
					}
					dfMatrix.get(actFrom).put(actTo, actFrequency);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return dfMatrix;
	}
}
