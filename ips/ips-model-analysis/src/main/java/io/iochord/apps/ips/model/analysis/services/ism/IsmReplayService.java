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
		double tProduced = 0;
		double tConsumed = 0;
		double tMissing = 0;
		double tRemaining = 0;
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
					"	SELECT " + config.getColCaseId() + ", STRING_AGG(" + config.getColEventActivity() + ", '|' ORDER BY " + config.getColCaseId() + ", " + config.getColEventTimestamp() + ", " + config.getColEventActivity() + ") AS t\r\n" + 
					"	FROM " + config.getDatasetId() + " \r\n" + 
					"	GROUP BY ci\r\n" + 
					") AS tv\r\n" + 
					"GROUP BY t\r\n");
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
					for (Entry<String, Node> ne : p.getNodes().entrySet()) {
						NodeImpl n = (NodeImpl) ne.getValue();
						tProduced += tMultiplier * n.getRProduced();
						tConsumed += tMultiplier * n.getRConsumed();
						tMissing += tMultiplier * n.getRMissing();
						tRemaining += tMultiplier * n.getRRemaining();
						System.out.println(n.getId() + " " + n.getLabel() + ": p=" + n.getRProduced() + ", c=" + n.getRConsumed() + ", m=" + n.getRMissing() + ", r=" + n.getRToken());
					}
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
		double fitness = (tConsumed == 0 ? 0 : (1 - tMissing / tConsumed) / 2) + (tProduced == 0 ? 0 : (1 - tRemaining / tProduced) / 2);
		System.out.println(fitness + ": p=" + tProduced + ", c=" + tConsumed + ", m=" + tMissing + ", r=" + tRemaining);
		return fitness;
	}
	
}
