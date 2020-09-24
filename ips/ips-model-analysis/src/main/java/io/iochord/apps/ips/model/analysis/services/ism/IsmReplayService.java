package io.iochord.apps.ips.model.analysis.services.ism;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StopImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class IsmReplayService extends AnIpsAsyncService<IsmDiscoveryConfiguration, Double> {

	@Getter
	@Setter
	private IsmDiscoveryService discoveryService = new IsmDiscoveryService();
	
	@Override
	public Double run(ServiceContext context, IsmDiscoveryConfiguration config) {
		IsmGraph graph = getDiscoveryService().run(context, config);
		return run(context, config, graph);
	}
	
	public Double run(ServiceContext context, IsmDiscoveryConfiguration config, IsmGraph graph) {
		int tProduced = 0;
		int tConsumed = 0;
		int tMissing = 0;
		int tRemaining = 0;
		Page p = graph.getPages().values().iterator().next();
		// Build node input and output sets
		for (Entry<String, Connector> ce : p.getConnectors().entrySet()) {
			Connector c = ce.getValue();
			if (c.getSource() != null && c.getSource().getValue() != null && c.getSource().getValue() instanceof NodeImpl
				&& c.getTarget() != null && c.getTarget().getValue() != null && c.getTarget().getValue() instanceof NodeImpl) {
				((NodeImpl) c.getSource().getValue()).getROutputNodes().add(((NodeImpl) c.getTarget().getValue()));
				((NodeImpl) c.getTarget().getValue()).getRInputNodes().add(((NodeImpl) c.getSource().getValue()));
			}
		}
		// Build activity nodes
		Map<String, NodeImpl> aNodes = new LinkedHashMap<>();
		StartImpl sNode = null;
		StopImpl tNode = null;
		for (Entry<String, Node> ne : p.getNodes().entrySet()) {
			NodeImpl n = (NodeImpl) ne.getValue();
			if (n instanceof ActivityImpl) {
				aNodes.put(n.getLabel(), n);
			}
			if (n instanceof StartImpl) {
				sNode = (StartImpl) n;
			}
			if (n instanceof StopImpl) {
				tNode = (StopImpl) n;
			}
		}
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT t, COUNT(*) AS m\r\n" + 
					"FROM (\r\n" + 
					"	SELECT " + config.getColCaseId() + " AS ci, STRING_AGG(" + config.getColEventActivity() + ", '|' ORDER BY " + config.getColCaseId() + ", " + config.getColEventTimestamp() + 
					// ", " + config.getColEventActivity() + 
					") AS t\r\n" + 
					"	FROM " + config.getDatasetId() + " \r\n" + 
					"	GROUP BY ci\r\n" + 
					") AS tv\r\n" + 
					"GROUP BY t\r\n");
			Map<NodeImpl, Integer[]> nTokens = new LinkedHashMap<>();
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String tActsStr = rs.getString("t");
					// Per Trace Replay
					String[] tActs = tActsStr.split("\\|");
					// String[] tActs = new String[] { "A", "B", "C", "D", "E", "F", "G"};
					int tMultiplier = rs.getInt("m");
					// Reset
					for (Entry<String, Node> ne : p.getNodes().entrySet()) {
						NodeImpl n = (NodeImpl) ne.getValue();
						n.rReset();
					}
					// Replay
					sNode.rFire(null);
					for (String a : tActs) {
						if (aNodes.containsKey(a)) {
							NodeImpl n = aNodes.get(a);
							n.rFire(null);
						}
					}
					tNode.rFire(null);
					// Calculate Fitness
					boolean tError = false;
					for (Entry<String, Node> ne : p.getNodes().entrySet()) {
						NodeImpl n = (NodeImpl) ne.getValue();
						Integer[] nToken = (nTokens.containsKey(n)) ? nTokens.get(n) : new Integer[] { 0, 0, 0, 0 };
						int mProduced = tMultiplier * n.getRProduced();
						int mConsumed = tMultiplier * n.getRConsumed();
						int mMissing = tMultiplier * n.getRMissing();
						int mRemaining = tMultiplier * n.getRRemaining();
						nToken[0] += mProduced;
						nToken[1] += mConsumed;
						nToken[2] += mMissing;
						nToken[3] += mRemaining;
						nTokens.put(n, nToken);
						tProduced += mProduced;
						tConsumed += mConsumed;
						tMissing += mMissing;
						tRemaining += mRemaining;
						if (n.getRMissing() > 0 || n.getRRemaining() > 0) {
							tError = true;
							if (n instanceof BranchImpl) {
								BranchImpl b = (BranchImpl) n;
								System.out.println(n.getId() + " " + n.getLabel() + " " + b.getGate() + " " + b.getType() + " : p=" + n.getRProduced() + ", c=" + n.getRConsumed() + ", m=" + n.getRMissing() + ", r=" + n.getRToken());
							} else {
								System.out.println(n.getId() + " " + n.getLabel() + ": p=" + n.getRProduced() + ", c=" + n.getRConsumed() + ", m=" + n.getRMissing() + ", r=" + n.getRToken());
							}
						}
					}
					if (tError) {
						System.out.println("MISSING/REMAINING: " + tActsStr + " (x " + tMultiplier + ")");
					}
				}
			}
			for (Entry<NodeImpl, Integer[]> nte : nTokens.entrySet()) {
				NodeImpl n = nte.getKey();
				Integer[] t = nte.getValue();
				graph.getAttributes().put("trToken-" + n.getLabel(), n.getLabel() + "|" + t[0] + "|" + t[1] + "|" + t[2] + "|" + t[3]);
				if (config.isTokenReplayDecorateLabel()) {
					n.setLabel(n.getLabel() + "\r\n(p=" + t[0] + ",c=" + t[1] + ",m=" + t[2] + ",r=" + t[3] + ")");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LoggerUtil.logError(ex);
		}
		// Cleanup replayers
		for (Entry<String, Node> ne : p.getNodes().entrySet()) {
			NodeImpl n = (NodeImpl) ne.getValue();
			n.rReset();
			n.getRInputNodes().clear();
			n.getROutputNodes().clear();
		}
		double fitness = (tConsumed == 0 ? 0 : (1 - ((double) tMissing) / ((double) tConsumed)) / 2) + (tProduced == 0 ? 0 : (1 - ((double) tRemaining) / ((double) tProduced)) / 2);
		System.out.println(fitness + ": p=" + tProduced + ", c=" + tConsumed + ", m=" + tMissing + ", r=" + tRemaining);
		graph.getAttributes().put("trTokenG", "GRAPH" + "|" + tProduced + "|" + tConsumed + "|" + tMissing + "|" + tRemaining);
		return fitness;
	}
	
}
