package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class DecisionMinerService extends AnIpsAsyncService<DecisionMinerConfig, DecisionMinerResult> {

	@Getter
	@Setter
	private IsmDiscoveryService discoveryService = new IsmDiscoveryService();
	
	@Override
	public DecisionMinerResult run(ServiceContext context, DecisionMinerConfig config) {
		IsmDiscoveryConfiguration sconfig = new IsmDiscoveryConfiguration();
		// This is example to get model from some dataset
		// Parameter tuning should be set from UI
		// If graph discovery is necessary then DecisionMinerConfig should inherit IsmDiscoveryConfiguration
		sconfig.setDatasetId(config.getDatasetId());
		// Perform case mapping against selected event log.
		// Case mapping should be performed before decision point analysis.
		Map<String, String> mappings = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder mappingsettings = new StringBuilder();
			mappingsettings.append("SELECT technical_names, mappings FROM ").append(config.getDatasetId()).append("_mappings;");
			try (PreparedStatement st = conn.prepareStatement(mappingsettings.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					while (rs.next()) {
						mappings.put(rs.getString(1), rs.getString(2));
					}
				}
			}
		} catch (SQLException e) {
			LoggerUtil.logError(e);
		}
		
		String caseid_col = mappings.entrySet().stream()
				.filter(set -> set.getValue().equals("case_id"))
				.map(set -> set.getKey()).collect(Collectors.toList()).get(0);
		String evact_col = mappings.entrySet().stream()
				.filter(set -> set.getValue().equals("concept:name"))
				.map(set -> set.getKey()).collect(Collectors.toList()).get(0);
		String tsmp_col = mappings.entrySet().stream()
				.filter(set -> set.getValue().equals("time:timestamp"))
				.map(set -> set.getKey()).collect(Collectors.toList()).get(0);
		sconfig.setColCaseId(caseid_col);
		sconfig.setColEventActivity(evact_col);
		sconfig.setColEventTimestamp(tsmp_col);
		sconfig.setDependencyThreshold(0.9f);
		sconfig.setPositiveObservationThreshold(0);
		
		IsmGraph ismGraph = getDiscoveryService().run(context, sconfig);
		
		List<List<Node>> branches = ismGraph.getPages().entrySet().stream()
				.map(page -> page.getValue())
				.map(page -> page.getNodes())
				.map(nodes -> nodes.entrySet().stream()
						.map(nds -> nds.getValue())
						.filter(node -> node.getElementType().equals(ElementType.NODE_BRANCH))
						.collect(Collectors.toList())).collect(Collectors.toList());
		System.out.println("==============================");
		System.out.println("Branches:");
		System.out.println("==============================");
		branches.get(0).forEach(branch -> {
			System.out.println(branch.getId());
		});
		
		List<List<Connector>> connectors = ismGraph.getPages().entrySet().stream()
				.map(page -> page.getValue())
				.map(page -> page.getConnectors())
				.map(css -> css.entrySet().stream()
						.map(cs -> cs.getValue()).collect(Collectors.toList()))
						.collect(Collectors.toList());
		System.out.println("==============================");
		System.out.println("Connectors:");
		System.out.println("==============================");
		connectors.get(0).forEach(connector -> {
			System.out.println(connector.getId());
			System.out.print(connector.getSource().getId() + "-");
			System.out.println(connector.getTarget().getId());
			System.out.println("");
		});
		
		List<List<Connector>> branching_connector = connectors.stream().map(cs -> cs.stream()
			.filter(connector -> 
				connector.getSource().getValue().getElementType()
					.equals(ElementType.NODE_BRANCH) 
			 || connector.getTarget().getValue().getElementType()
			 	.equals(ElementType.NODE_BRANCH))
			.collect(Collectors.toList()))
		.collect(Collectors.toList());
		
		List<List<Map<BranchImpl, Map<List<Element>, List<Element>>>>> decision_point = branches.stream().map(pages -> {
			return pages.stream().filter(br -> (Boolean) ((BranchImpl) br).getType().equals(BranchType.SPLIT)).map(branch -> {
						Map<BranchImpl, Map<List<Element>, List<Element>>> branch_point = new LinkedHashMap<>();
						List<Element> out = branching_connector.stream().flatMap(p -> 
							p.stream().filter(cs -> cs.getSource().getValue().equals(branch))
									  .map(c -> c.getTarget().getValue()).filter(c -> !c.getElementType().equals(ElementType.NODE_BRANCH)))
								.collect(Collectors.toList());
						List<Element> in = branching_connector.stream().flatMap(p -> 
							p.stream().filter(cs -> cs.getTarget().getValue().equals(branch))
									  .map(c -> c.getSource().getValue()))
								.collect(Collectors.toList());
						Map<List<Element>, List<Element>> io = new LinkedHashMap<>();
						io.put(in, out);
						branch_point.put((BranchImpl) branch, io);
						return branch_point;
				}).collect(Collectors.toList());
		}).collect(Collectors.toList());
		for (List<Map<BranchImpl, Map<List<Element>, List<Element>>>> list : decision_point) {
			for (Map<BranchImpl, Map<List<Element>, List<Element>>> map : list) {
				map.entrySet().forEach(entry -> {
					System.out.println("Branch: " + entry.getKey().getId());
					entry.getValue().entrySet().forEach(e -> {
						e.getKey().forEach(i -> System.out.println("In: " + i.getId()));
						e.getValue().forEach(o -> System.out.println("Out: " + o.getId()));
					});
				});
			}
		}
		
		try (Connection conn = context.getDataSource().getConnection();) {
			String datasetId = config.getDatasetId();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT table_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name LIKE '")
			   .append(datasetId).append("_dataeventlog%'");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					while (rs.next()) {
						StringBuilder dataeventlog_table = new StringBuilder();
						dataeventlog_table.append("SELECT * FROM ").append(rs.getString(1)).append(";");
						 performClassLabeling(ismGraph, conn, dataeventlog_table);
					}
				}
			}
			
			// Build the result
			DecisionMinerResult result = new DecisionMinerResult();
			result.setConfig(config);
			return result;
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return null;
	}

	private void performClassLabeling(IsmGraph ismGraph, Connection conn, StringBuilder dataeventlog_table) throws SQLException {
		try (PreparedStatement st = conn.prepareStatement(dataeventlog_table.toString());) {
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					IsmGraph g = ismGraph;
					ResultSetMetaData mt = rs.getMetaData();
					for (int i = 1; i < mt.getColumnCount(); i++) {
						System.out.println(rs.getString(i));
					}
				}
			}
		}
	}
}
