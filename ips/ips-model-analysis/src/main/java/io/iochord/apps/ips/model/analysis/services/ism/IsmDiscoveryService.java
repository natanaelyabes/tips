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
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
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
		Map<String, Set<String>> snNodes = new LinkedHashMap<>();

		calculateDfMatrix(context, dfMatrix, config.getDatasetId(), config.getColCaseId(), config.getColEventActivity(), config.getColEventTimestamp(), config.getSkipRows());
		double fpFitness = calculateDpMatrix(config, dpMatrix, dfMatrix);
		calculateSNNodes(context, snNodes, config.getDatasetId(), config.getColCaseId(), config.getColEventActivity(), config.getColEventTimestamp(), config.getSkipRows());
		Map<String, Node> nodes = new LinkedHashMap<>();
		for (Entry<String, Map<String, Long>> fae : dfMatrix.entrySet()) {
			nodes.put(fae.getKey(), null);
			for (String ta : fae.getValue().keySet()) {
				nodes.put(ta, null);
			}
		}
		IsmGraph graph = createGraph(context, factory, nodes, snNodes, dfMatrix, dpMatrix);
		IsmReplayService replayer = new IsmReplayService();
		double trFitness = replayer.run(context, config, graph);
		System.out.println("TRFitness: " + trFitness);
		graph.getAttributes().put("fpFitness", String.valueOf(fpFitness));
		graph.getAttributes().put("trFitness", String.valueOf(trFitness));
		return graph;
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

	public IsmGraph createGraph(ServiceContext context, IsmFactory factory, Map<String, Node> nodes, Map<String, Set<String>> snNodes, Map<String, Map<String, Long>> dfMatrix, Map<String, Map<String, Double>> dpMatrix) {
		IsmGraph result = factory.create();
		Page p = result.getPages().values().iterator().next();
		int ni = 0;
		NodeImpl sn = (NodeImpl) factory.addStart(p);
		sn.setLabel("START");
		NodeImpl tn = (NodeImpl) factory.addStop(p);
		tn.setLabel("STOP");
		if (snNodes.containsKey("start") && snNodes.get("start").size() > 1) {
			BranchImpl ob = (BranchImpl) factory.addBranch(p);
			ob.setType(BranchType.SPLIT);
			//ob.setId("split-branch-start");
			ob.setLabel("sb-start");
			ob.setGate(BranchGate.XOR);
			factory.addConnector(p, sn, ob);
			sn = ob;
		}
		if (snNodes.containsKey("stop") && snNodes.get("stop").size() > 1) {
			BranchImpl ib = (BranchImpl) factory.addBranch(p);
			ib.setType(BranchType.JOIN);
			//ib.setId("join-branch-stop");
			ib.setLabel("jb-stop");
			ib.setGate(BranchGate.XOR);
			factory.addConnector(p, ib, tn);
			tn = ib;
		}
		for (String ea : nodes.keySet()) {
			ActivityImpl a = (ActivityImpl) factory.addActivity(p);
			a.setLabel(ea);
			//a.setId("ACTIVITY" + (ni++) + ea);
			nodes.put(ea, a);
		}
		createConnector(context, factory, nodes, sn, tn, snNodes, dfMatrix, dpMatrix, p, ni);
		return result;
	}

	private void createConnector(ServiceContext context, IsmFactory factory, Map<String, Node> nodes, NodeImpl sn, NodeImpl tn, Map<String, Set<String>> snNodes, Map<String, Map<String, Long>> dfMatrix, Map<String, Map<String, Double>> dpMatrix, Page p, int ni) {
		int ci = 0;

		Map<String, Set<Node>> inNodes = new LinkedHashMap<>();
		Map<String, Set<Node>> outNodes = new LinkedHashMap<>();

		for (String et : snNodes.keySet()) {
			for (String ea : snNodes.get(et)) {
				// Node an = nodes.get(ea);
				if (et.equals("start")) {
					if (!inNodes.containsKey(ea)) {
						inNodes.put(ea, new LinkedHashSet<>());
					}
					inNodes.get(ea).add(sn);
				} else {
					if (!outNodes.containsKey(ea)) {
						outNodes.put(ea, new LinkedHashSet<>());
					}
					outNodes.get(ea).add(tn);
				}
			}
		}
		
		for (Entry<String, Map<String, Double>> fae : dpMatrix.entrySet()) {
			String fa = fae.getKey();
			for (Entry<String, Double> tae : fae.getValue().entrySet()) {
				String ta = tae.getKey();
				if (nodes.containsKey(fa) && nodes.containsKey(ta)) {
					if (!inNodes.containsKey(ta)) {
						inNodes.put(ta, new LinkedHashSet<>());
					}
					inNodes.get(ta).add(nodes.get(fa));
					if (!outNodes.containsKey(fa)) {
						outNodes.put(fa, new LinkedHashSet<>());
					}
					outNodes.get(fa).add(nodes.get(ta));
				}
			}
		}

		Map<String, Node> inNode = new LinkedHashMap<>();
		Map<String, Node> outNode = new LinkedHashMap<>();
		Map<String, BranchImpl> bcs = new LinkedHashMap<>();
		
		for (Entry<String, Node> ea : nodes.entrySet()) {
			inNode.put(ea.getKey(), ea.getValue());
			outNode.put(ea.getKey(), ea.getValue());
		}
		
		int i = 0;
		for (Entry<String, Set<Node>> eae : inNodes.entrySet()) {
			String ea = eae.getKey();
			Node a = nodes.get(ea);
			Set<Node> rn = eae.getValue();
			if (rn.size() > 1) {
				BranchImpl ib = (BranchImpl) factory.addBranch(p);
				ib.setType(BranchType.JOIN);
				//ib.setId("JOIN-BRANCH-" + a.getId() + ea);
				ib.setLabel("jb" + i++);
				ib.setGate(BranchGate.XOR);
				bcs.put(ib.getId(), ib);
				//ConnectorImpl c = (ConnectorImpl)
				factory.addConnector(p, ib, a);
				inNode.put(ea, ib);
			}
		}
		int j = 0;
		for (Entry<String, Set<Node>> eae : outNodes.entrySet()) {
			String ea = eae.getKey();
			Node a = nodes.get(ea);
			Set<Node> rn = eae.getValue();
			if (rn.size() > 1) {
				BranchImpl ob = (BranchImpl) factory.addBranch(p);
				ob.setType(BranchType.SPLIT);
				//ob.setId("SPLIT-BRANCH-" + a.getId() + ea);
				ob.setLabel("sb" + j++);
				ob.setGate(BranchGate.XOR);
				bcs.put(ob.getId(), ob);
				//ConnectorImpl c = (ConnectorImpl) 
				factory.addConnector(p, a, ob);
				outNode.put(ea, ob);
			}
		}
		
		for (String et : snNodes.keySet()) {
			for (String ea : snNodes.get(et)) {
				if (et.equals("start")) {
					if (inNode.containsKey(ea)) {
						NodeImpl a = (NodeImpl) inNode.get(ea);
						ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, sn, a);
						c.setSourceIndex(sn.getOutputConnectors().size());
						c.setTargetIndex(a.getInputConnectors().size());
						a.getInputConnectors().add(c);
						sn.getOutputConnectors().add(c);
					}
				} else {
					if (outNode.containsKey(ea)) {
						NodeImpl a = (NodeImpl) outNode.get(ea);
						ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, a, tn);
						c.setSourceIndex(a.getOutputConnectors().size());
						c.setTargetIndex(tn.getInputConnectors().size());
						a.getOutputConnectors().add(c);
						tn.getInputConnectors().add(c);
					}
				}
			}
		}

		Map<String, Map<String, Long>> bcIf = new LinkedHashMap<>();
		Map<String, Map<String, Long>> bcOf = new LinkedHashMap<>();
		for (Entry<String, Map<String, Double>> fae : dpMatrix.entrySet()) {
			String fa = fae.getKey();
			for (Entry<String, Double> tae : fae.getValue().entrySet()) {
				String ta = tae.getKey();
				if (nodes.containsKey(fa) && nodes.containsKey(ta)) {
					NodeImpl on = (NodeImpl) outNode.get(fa);
					NodeImpl in = (NodeImpl) inNode.get(ta);
					long ff = dfMatrix.get(fa).get(ta);
					double dp = dpMatrix.get(fa).get(ta);
					ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, on, in);
					c.setSourceIndex(on.getOutputConnectors().size());
					c.setTargetIndex(in.getInputConnectors().size());
					on.getOutputConnectors().add(c);
					in.getInputConnectors().add(c);
					//c.setId("CONNECTOR" + ci++);
					c.setLabel(String.valueOf(ff) + " (" + String.valueOf(dp) + ")");
					if (on instanceof BranchImpl) {
						if (!bcIf.containsKey(on.getId())) {
							bcIf.put(on.getId(), new LinkedHashMap<>());
						}
						bcIf.get(on.getId()).put(c.getId(), ff);
//						System.out.println("IF " + on.getId() + " " + in.getId() + ": " + ff);
					}
					if (in instanceof BranchImpl) {
						if (!bcOf.containsKey(in.getId())) {
							bcOf.put(in.getId(), new LinkedHashMap<>());
						}
						bcOf.get(in.getId()).put(c.getId(), ff);
//						System.out.println("OF " + on.getId() + " " + in.getId() + ": " + ff);
					}
					if (++ci % 100 == 0) {
						context.updateProgress(75, ci + " connector found.");
					}
				}
			}
		}
		for (Entry<String, BranchImpl> bie : bcs.entrySet()) {
			String bi = bie.getKey();
			BranchImpl b = bie.getValue();
			long ic = 1;
			int icc = 1;
			if (bcIf.containsKey(bi)) {
				for (Entry<String, Long> bif : bcIf.get(bi).entrySet()) {
					ic += bif.getValue();
					icc++;
				}
			}
			long oc = 1;
			int occ = 1;
			if (bcOf.containsKey(bi)) {
				for (Entry<String, Long> bof : bcOf.get(bi).entrySet()) {
					oc += bof.getValue();
					occ++;
				}
			}
//			System.out.println(b.getId() + " " + ic + " " + icc + " - " + oc + " " + occ);
//			System.out.println((ic * occ == oc) + " " + (oc * icc == ic));
			if (ic * occ == oc || oc * icc == ic) {
				b.setGate(BranchGate.AND);
			}
		}
	}

	public Map<String, Map<String, Long>> calculateDfMatrix(ServiceContext context, Map<String, Map<String, Long>> dfMatrix, String tabName, String colCaseId, String colActivity, String colTs,  int skip) {
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ").append("\r\n")
				.append("	ce.a AS af, ").append("\r\n")
				.append("	ne.a AS at, ").append("\r\n")
				.append("	count(*) AS f ").append("\r\n")
				.append("FROM ( ").append("\r\n")
				.append("	SELECT  ").append("\r\n")
				.append("		row_number() over (ORDER BY ").append(colCaseId).append(", ").append(colTs).append(") AS ri,  ").append("\r\n")
				.append("		").append(colCaseId).append(" AS c,  ").append("\r\n")
				.append("		").append(colActivity).append(" AS a  ").append("\r\n")
				.append("	FROM ").append(tabName).append("\r\n")
				.append("   WHERE eid > ").append(skip).append("\r\n")
				.append("	ORDER BY ").append(colCaseId).append(", ").append(colTs).append(" ").append("\r\n")
				.append(") AS ce ").append("\r\n")
				.append("JOIN ").append("\r\n")
				.append("( ").append("\r\n")
				.append("	SELECT  ").append("\r\n")
				.append("		row_number() over (ORDER BY ").append(colCaseId).append(", ").append(colTs).append(") AS ri,  ").append("\r\n")
				.append("		").append(colCaseId).append(" AS c,  ").append("\r\n")
				.append("		").append(colActivity).append(" AS a  ").append("\r\n")
				.append("	FROM ").append(tabName).append("\r\n")
				.append("  WHERE eid > ").append(skip).append("\r\n")
				.append("	ORDER BY ").append(colCaseId).append(", ").append(colTs).append(" ").append("\r\n")
				.append(") AS ne ").append("\r\n")
				.append("ON ne.ri = ce.ri + 1 ").append("\r\n")
				.append("AND ne.c = ce.c ").append("\r\n")
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
	
	public Map<String, Set<String>> calculateSNNodes(ServiceContext context, Map<String, Set<String>> snNodes, String tabName, String colCaseId, String colActivity, String colTs,  int skip) {
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT 'start' AS t, ").append(colActivity).append(" ")
				.append("FROM ( ")
				.append("  SELECT row_number() over (ORDER BY ").append(colCaseId).append(", ").append(colTs).append(") AS ri, * ")
				.append("  FROM ").append(tabName).append(" AS LI1 ")
				.append("   WHERE eid > ").append(skip)
				.append(") AS L1 ")
				.append("WHERE L1.ri IN ( ")
				.append("  SELECT MIN(ri) ")
				.append("  FROM ( ")
				.append("    SELECT row_number() over (ORDER BY ").append(colCaseId).append(", ").append(colTs).append(") AS ri, ci ")
				.append("    FROM ").append(tabName).append(" AS LI2 ")
				.append("  ) AS L2 ")
				.append("  GROUP BY ci ")
				.append(") ")
				.append("UNION ")
				.append("SELECT 'stop' AS t, ").append(colActivity).append(" ")
				.append("FROM ( ")
				.append("  SELECT row_number() over (ORDER BY ").append(colCaseId).append(", ").append(colTs).append(") AS ri, * ")
				.append("  FROM ").append(tabName).append(" AS LI1 ")
				.append("   WHERE eid > ").append(skip)
				.append(") AS L1 ")
				.append("WHERE L1.ri IN ( ")
				.append("  SELECT MAX(ri) ")
				.append("  FROM ( ")
				.append("    SELECT row_number() over (ORDER BY ").append(colCaseId).append(", ").append(colTs).append(") AS ri, ci ")
				.append("    FROM ").append(tabName).append(" AS LI2 ")
				.append("  ) AS L2 ")
				.append("  GROUP BY ci ")
				.append(")");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String actFrom = rs.getString(1);
					String actTo = rs.getString(2);
					if (!snNodes.containsKey(actFrom)) {
						snNodes.put(actFrom, new LinkedHashSet<>());
					}
					snNodes.get(actFrom).add(actTo);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return snNodes;
	}
}
