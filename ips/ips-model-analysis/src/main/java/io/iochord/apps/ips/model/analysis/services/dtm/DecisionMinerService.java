package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ArrayUtils;
import org.deckfour.xes.model.XAttribute;

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
	
	@SuppressWarnings("unlikely-arg-type")
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
			System.out.println(branch.getLabel());
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
									  .map(c -> c.getTarget().getValue()) 
									  /* .filter(c -> !c.getElementType().equals(ElementType.NODE_BRANCH)) */ )
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
		
		try (Connection conn = context.getDataSource().getConnection();) {
			String datasetId = config.getDatasetId();
			StringBuilder sql = new StringBuilder();
			
			sql.append("DROP TABLE IF EXISTS ").append(datasetId).append("_branchpoint;");
			sql.append("CREATE TABLE IF NOT EXISTS ").append(datasetId).append("_branchpoint")
			   .append(" ( ")
			   .append("    eid SERIAL PRIMARY KEY, ")
			   .append("    page VARCHAR(100) NULL,")
			   .append("    branch VARCHAR(100) NULL, ")
			   .append("    input VARCHAR(100) NULL, ")
			   .append("    output VARCHAR(100) NULL ")
			   .append(" ); ");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				st.execute();
			}
			
			sql = new StringBuilder();
			sql.append("INSERT INTO ").append(datasetId).append("_branchpoint").append(" VALUES ")
			   .append("(DEFAULT, ?, ?, ?, ?);");
			
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				for (int i = 0; i < decision_point.size(); i++) {
					List<Map<BranchImpl, Map<List<Element>, List<Element>>>> list = decision_point.get(i);
					for (int j = 0; j < list.size(); j++) {
						Map<BranchImpl, Map<List<Element>, List<Element>>> map = list.get(j);
						map.entrySet().forEach(entry -> {
							entry.getValue().entrySet().forEach(e -> {
								e.getKey().forEach(input -> {
									e.getValue().forEach(output -> {
										try {
											st.setString(1, String.valueOf(0));
											st.setString(2, entry.getKey().getLabel());
											st.setString(3, input.getLabel());
											st.setString(4, output.getLabel());
											st.addBatch();
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									});
								});
							});
						});
					}
				}
				st.executeBatch();
			}
			
			List<List<String>> io = new ArrayList<>();
			sql = new StringBuilder();
			sql.append("SELECT page, input, output FROM ").append(datasetId).append("_branchpoint;");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					while (rs.next()) {
						List<String> bindings = new ArrayList<>();
						bindings.add(rs.getString(2));
						bindings.add(rs.getString(3));
						io.add(bindings);
					}
				}
			}
			
			Map<String, List<String>> iobindings = new LinkedHashMap<>();
			
			List<String> val = new ArrayList<>();
			for (int i = 0; i < io.size(); i++) {
				if (i == 0) {
					val.add(io.get(i).get(1));
				}
				
				if (i > 0) {
					if (io.get(i - 1).get(0).equals(io.get(i).get(0))) {
						val.add(io.get(i).get(1));
					} else {
						val = new ArrayList<>();
						val.add(io.get(i).get(1));
					}
				}
				iobindings.put(io.get(i).get(0), val);
			}
			
			sql = new StringBuilder();
			sql.append("SELECT * FROM ").append(datasetId).append(" OFFSET 1;");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					
					List<List<String>> act = new ArrayList<>();
					while (rs.next()) {
						String actname = rs.getString(sconfig.getColEventActivity());
						String caseid = rs.getString(sconfig.getColCaseId());
						List<String> a = new ArrayList<>();
						a.add(actname);
						a.add(caseid);
						act.add(a);						
					}
					
					sql = new StringBuilder();
					for (int i = 0; i < act.size(); i++) {
						if (i < act.size()) {
							if (iobindings.containsKey(act.get(i).get(0))) {
								List<String> obindings = iobindings.get(act.get(i).get(0));
								if (obindings.contains(act.get(i + 1).get(0))) {
									String label = obindings.get(obindings.indexOf(act.get(i + 1).get(0)));
									sql.append("UPDATE ").append(datasetId).append("_dataeventlog").append("_").append(act.get(i).get(0).toLowerCase().replace(" ", ""))
									   .append(" ")
									   .append("SET class = '").append(label).append("' ")
									   .append("WHERE case_id = '").append(act.get(i).get(1)).append("';");
									System.out.println(sql.toString());
								}
							}
						}
					}
					try (PreparedStatement ut = conn.prepareStatement(sql.toString());) {
						ut.execute();
					}
				}
			}
			
			sql = new StringBuilder();
			sql.append("SELECT DISTINCT table_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name LIKE '")
			   .append(datasetId).append("_dataeventlog%'");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					while (rs.next()) {
						StringBuilder dataeventlog_table = new StringBuilder();
						dataeventlog_table.append("SELECT * FROM ").append(rs.getString(1)).append(";");
						
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
}
