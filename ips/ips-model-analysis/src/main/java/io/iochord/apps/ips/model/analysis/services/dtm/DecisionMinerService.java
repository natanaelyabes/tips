package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


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
									   .append("WHERE case_id = '").append(act.get(i).get(1)).append("'; ");
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
						alterColumnType(conn, sql, rs);
						inferDecisionTree(conn, config, rs);
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

	private void inferDecisionTree(Connection conn, DecisionMinerConfig config, ResultSet rs) throws SQLException {
		config.setStrategy(DecisionTreeStrategy.ENTROPY);
		if (config.getStrategy().equals(DecisionTreeStrategy.ENTROPY))
			inferUsingEntropyMeasure(conn, rs);
<<<<<<< HEAD
=======
		} else if (config.getStrategy().equals(DecisionTreeStrategy.ENTROPY_RATIO)) {
			inferUsingEntropyRatioMeasure(conn, rs);
		} else if (config.getStrategy().equals(DecisionTreeStrategy.GINI)) {
			inferUsingGiniMeasure(conn, rs);
		}
>>>>>>> b03d0e59db3a76d6f1b7f16b9391580d67cdfc5a
	}

	private void inferUsingEntropyMeasure(Connection conn, ResultSet rs) throws SQLException {
		String tablename = rs.getString(1);
		double E_INFO = computeExpectedInformation(conn, tablename);
		StringBuilder colname = new StringBuilder();
		colname.append("SELECT column_name, data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '")
		       .append(rs.getString(1)).append("' AND column_name != 'eid' AND column_name != 'case_id';");
		try (PreparedStatement st1 = conn.prepareStatement(colname.toString());) {
			try (ResultSet rs1 = st1.executeQuery();) {
				Map<String, Map<String, List<String>>> colcandidate = new LinkedHashMap<>();
				while (rs1.next()) {
					StringBuilder sql = new StringBuilder();
					if (!rs1.getString(1).equals("class")) {
						if (rs1.getString(2).equals("character varying")) {
							sql.append("SELECT COUNT(DISTINCT \"").append(rs1.getString(1)).append("\") * 1.0 FROM ").append(tablename).append(";");
							try (PreparedStatement st2 = conn.prepareStatement(sql.toString());) {
								try (ResultSet rs2 = st2.executeQuery();) {
									while (rs2.next()) {
										sql = new StringBuilder();
										sql.append("SELECT (COUNT(DISTINCT  eid) / 2) * 1.0 FROM ").append(tablename).append(";");
										try (PreparedStatement st3 = conn.prepareStatement(sql.toString());) {
											try (ResultSet rs3 = st3.executeQuery();) {
												while (rs3.next()) {
													if (rs2.getDouble(1) <= rs3.getDouble(1)) {
														Map<String, List<String>> candidate_value = new LinkedHashMap<>();
														List<String> v = new ArrayList<>();
														sql = new StringBuilder();
														sql.append("SELECT DISTINCT \"").append(rs1.getString(1)).append("\" FROM ").append(tablename).append(";");
														try (PreparedStatement st4 = conn.prepareStatement(sql.toString())) {
															try (ResultSet rs4 = st4.executeQuery()) {
																while (rs4.next()) {
																	v.add(rs4.getString(1));
																}
															}
														}
														candidate_value.put(rs1.getString(2), v);
														colcandidate.put(rs1.getString(1), candidate_value);
													}
												}
											}
										}
									}
								}
							}
						} else {
							Map<String, List<String>> candidate_value = new LinkedHashMap<>();
							List<String> v = new ArrayList<>();
							sql = new StringBuilder();
							sql.append("SELECT (x + y) / 2 as midpoint, COUNT(x + y) OVER() + 1 AS tot FROM (")
							   .append("    WITH candidate_pair AS (")
							   .append("        SELECT X.eid, X.\"").append(rs1.getString(1)).append("\" AS x, Y.\"").append(rs1.getString(1)).append("\" AS y")
							   .append("            FROM ").append(tablename).append(" AS X,")
							   .append("                 ").append(tablename).append(" AS Y")
							   .append("        WHERE X.\"").append(rs1.getString(1)).append("\" > ").append("Y.\"").append(rs1.getString(1)).append("\" ")
							   .append("        ORDER BY X.\"").append(rs1.getString(1)).append("\" DESC, Y.\"").append(rs1.getString(1)).append("\" DESC")
							   .append("    ) SELECT eid, MAX(x) AS x, MAX(y) AS y FROM candidate_pair GROUP BY eid ORDER BY eid")
							   .append(") AS midpoint");
							try (PreparedStatement st2 = conn.prepareStatement(sql.toString())) {
								try (ResultSet rs2 = st2.executeQuery()) {
									List<String> flags = new ArrayList<>();
									flags.add("<=");
									flags.add(">");
									while (rs2.next()) {
										for (String flag : flags) {
											v.add(flag + " " + rs2.getDouble(1));
										}
									}
								}
							}
							candidate_value.put(rs1.getString(2), v);
							colcandidate.put(rs1.getString(1), candidate_value);
						}
					}
				}
				Map<String, Map<String, List<String>>> node = new LinkedHashMap<>();
				String conditions = "";
				String curr = null;
				traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
			}
		}
	}

	@SuppressWarnings("unchecked")
<<<<<<< HEAD
	private void traverseTree(Connection conn, String tablename,
			                  double E_INFO, Map<String, Map<String, List<String>>> colcandidate, 
			                  Map<String, Map<String, Map<String, String>>> node, String curr, String conditions)
=======
	private void traverseTree(Connection conn, String tablename, double E_INFO, Map<String, Map<String, List<String>>> colcandidate, Map<String, Map<String, List<String>>> node, String curr, String conditions)
>>>>>>> b03d0e59db3a76d6f1b7f16b9391580d67cdfc5a
			throws SQLException {
		if (colcandidate.isEmpty()) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT \"class\" FROM ").append(tablename).append(" AS r WHERE 1 = 1 ");
			System.out.println(sql);
			try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
				try (ResultSet rs = st.executeQuery()) {
					while (rs.next()) {
						Map<String, List<String>> ac = new LinkedHashMap<>();
						ac.put(curr, null);
						node.put(rs.getString(1), ac);
					}
				}
			}
			System.out.println(node);
		} else {
			Map<String, Double> node_candidate = new TreeMap<>();
			Set<Entry<String, Map<String, List<String>>>> entries = colcandidate.entrySet();
			for (Entry<String, Map<String, List<String>>> col : entries) {
				String colname = col.getKey();
				String coltype = col.getValue().entrySet().stream()
						.map(entry -> entry.getKey())
						.collect(Collectors.toList()).get(0);
				StringBuilder sql = new StringBuilder();
				if (!colname.equals("class")) {
					if (coltype.equals("character varying")) {
						sql.append("SELECT SUM((ca / tot) * ((-1.0 * prob) * LOG(2, prob))) AS gain ")
						   .append("FROM (")
						   .append("    WITH computed_gain AS (")
						   .append("        SELECT \"").append(colname).append("\",")
						   .append("               \"class\",")
						   .append("               COUNT(\"").append(colname).append("\") OVER(PARTITION BY \"")
						                                     .append(colname).append("\") * 1.0 AS ca,")
						   .append("               COUNT(\"class\") OVER(PARTITION BY (\"class\")) * 1.0 AS cc,")
						   .append("               COUNT(\"").append(colname).append("\") OVER(PARTITION BY \"")
						                                     .append(colname).append("\", \"class\") * 1.0 AS cac,")
						   .append("               COUNT(*) OVER() * 1.0 AS tot")
						   .append("        FROM ").append(tablename).append(" AS r ").append(" WHERE 1 = 1 ").append(conditions)
						   .append("    ) SELECT DISTINCT \"").append(colname).append("\",")
						   .append("                      \"class\",")
						   .append("                      ca,")
						   .append("                      cc,")
						   .append("                      cac,")
						   .append("                      cac / ca AS prob,")
						   .append("                      tot")
						   .append("    FROM computed_gain")
						   .append(") AS eagain;");
						try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
							try (ResultSet rs2 = st.executeQuery()) {
								while (rs2.next()) {
									double E_INFO_ATTR = rs2.getDouble(1);
									double GAIN_ATTR = E_INFO - E_INFO_ATTR;
									System.out.println("Information gain over attribute " + colname + ": " + GAIN_ATTR);
									node_candidate.put(colname, GAIN_ATTR);
								}
							}
						} catch (SQLException e) {
							LoggerUtil.logError(e);
						}
					} else {
						sql = new StringBuilder();
						sql.append("SELECT superjacent_gain.midpoint,")
						   .append("       superjacent_gain.gain AS superjacent,")
						   .append("       subjacent_gain.gain AS subjacent,")
						   .append("       (superjacent_gain.gain + subjacent_gain.gain) AS midpoint_gain ")
						   .append("FROM (")
						   .append("    SELECT midpoint, SUM((cc / tot) * (-1.0 * prob * LOG(2, prob))) AS gain FROM (")
						   .append("        WITH superjacent_computed_gain AS (")
						   .append("            SELECT DISTINCT mid.midpoint,")
						   .append("                r.\"class\",")
						   .append("                COUNT(r.\"").append(colname).append("\") OVER(PARTITION BY mid.midpoint) * 1.0 AS ca,")
						   .append("                COUNT(r.\"class\") OVER(PARTITION BY mid.midpoint, r.\"class\") * 1.0 AS cc,")
						   .append("                mid.tot * 1.0 AS tot")
						   .append("            FROM ").append(tablename).append(" AS r")
						   .append("            JOIN (")
						   .append("                SELECT (x + y) / 2 as midpoint, COUNT(x + y) OVER() + 1 AS tot FROM (")
						   .append("                    WITH candidate_pair AS (")
						   .append("                        SELECT X.eid, X.\"").append(colname).append("\" AS x, Y.\"").append(colname).append("\" AS y")
						   .append("                        FROM ").append(tablename).append(" AS X,")
						   .append("                             ").append(tablename).append(" AS Y")
						   .append("                        WHERE X.\"").append(colname).append("\" > ").append("Y.\"").append(colname).append("\" ")
						   .append("                        ORDER BY X.\"").append(colname).append("\" DESC, Y.\"").append(colname).append("\" DESC")
						   .append("                    ) SELECT eid, MAX(x) AS x, MAX(y) AS y FROM candidate_pair GROUP BY eid ORDER BY eid")
						   .append("                ) AS midpoint")
						   .append("            ) AS mid ON r.\"").append(colname).append("\" > mid.midpoint").append(" WHERE 1 = 1 ").append(conditions)
						   .append("            ORDER BY mid.midpoint DESC")
						   .append("        ) SELECT DISTINCT midpoint, \"class\", ca, cc, tot, cc / ca AS prob FROM superjacent_computed_gain ORDER BY midpoint DESC")
						   .append("    ) AS eagain GROUP BY midpoint ORDER BY midpoint DESC")
						   .append(") AS superjacent_gain JOIN (")
						   .append("    SELECT * FROM (")
						   .append("        SELECT midpoint, SUM((cc / tot) * (-1.0 * prob * LOG(2, prob))) AS gain FROM (")
						   .append("            WITH subjacent_computed_gain AS (")
						   .append("                SELECT DISTINCT mid.midpoint,")
						   .append("                    r.\"class\",")
						   .append("                    COUNT(r.\"").append(colname).append("\") OVER(PARTITION BY mid.midpoint) * 1.0 AS ca,")
						   .append("                    COUNT(r.\"class\") OVER (PARTITION BY mid.midpoint, r.\"class\") * 1.0 AS cc,")
						   .append("                    mid.tot * 1.0 AS tot")
						   .append("                FROM ").append(tablename).append(" AS r")
						   .append("                JOIN (")
						   .append("                    SELECT (x + y) / 2 as midpoint, COUNT(x + y) OVER() + 1 as tot FROM (")
						   .append("                        WITH candidate_pair AS (")
						   .append("                            SELECT X.eid, X.\"").append(colname).append("\" AS x, Y.\"").append(colname).append("\" AS y")
						   .append("                            FROM ").append(tablename).append(" AS X,")
						   .append("                                 ").append(tablename).append(" AS Y")
						   .append("                            WHERE X.\"").append(colname).append("\" > Y.\"").append(colname).append("\" ")
						   .append("                            ORDER BY X.\"").append(colname).append("\" DESC, Y.\"").append(colname).append("\" DESC")
						   .append("                        ) SELECT eid, MAX(x) AS x, MAX(y) AS y FROM candidate_pair GROUP BY eid ORDER BY eid")
						   .append("                    ) AS midpoint")
						   .append("                ) AS mid ON r.\"").append(colname).append("\" <= mid.midpoint").append(" WHERE 1 = 1 ").append(conditions)
						   .append("                ORDER BY mid.midpoint DESC")
						   .append("            )")
						   .append("            SELECT DISTINCT midpoint, \"class\", ca, cc, tot, cc / ca AS prob")
						   .append("            FROM subjacent_computed_gain")
						   .append("            ORDER BY midpoint DESC")
						   .append("        ) AS eagain")
						   .append("        GROUP BY midpoint")
						   .append("        ORDER BY midpoint DESC")
						   .append("    ) AS subjacent_gain")
						   .append(") AS subjacent_gain ON superjacent_gain.midpoint = subjacent_gain.midpoint ")
						   .append("ORDER BY superjacent_gain.midpoint;");
						try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
							try (ResultSet rs3 = st.executeQuery()) {
								List<List<Double>> gain_candidates = new ArrayList<>();
								while (rs3.next()) {
									double E_INFO_ATTR = rs3.getDouble(4);
									double GAIN_ATTR = E_INFO - E_INFO_ATTR;
									List<Double> gain = new ArrayList<>();
									gain.add(GAIN_ATTR);
									gain.add(rs3.getDouble(1));
									gain_candidates.add(gain);
								}
								if (gain_candidates.size() == 0) {
									String removed = node.entrySet().stream().map(entry -> entry.getKey()).reduce((first, last) -> last).get();
									node.remove(removed);
									curr = node.entrySet().stream().map(entry -> entry.getKey()).reduce((first, last) -> last).get();
									String lastcond = conditions.split("AND")[conditions.split("AND").length - 1];
									conditions = conditions.replace("AND" + lastcond, "");
									String flag = lastcond.split(" ")[2];
									String midpoint = lastcond.split(" ")[3];
									colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
											.collect(Collectors.toMap(
													Entry::getKey, 
													entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
															.collect(Collectors.toMap(
																	Entry::getKey, 
																	ent -> (List<String>) ent.getValue()
																		.stream()
																		.filter(l -> !l.equals(flag + " " + midpoint + "000000000000000"))
																		.collect(Collectors.toList())))));
									List<String> candidates = colcandidate.entrySet().stream()
											.flatMap(entry -> entry.getValue().entrySet().stream()
											.flatMap(ent -> ent.getValue().stream()))
											.collect(Collectors.toList());
									if (candidates.contains(flag + " " + midpoint + "000000000000000")) {
										traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
									} else {
										if (flag.equals("<=")) {
											colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
													.collect(Collectors.toMap(
															Entry::getKey, 
															entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
																	.collect(Collectors.toMap(
																			Entry::getKey, 
																			ent -> (List<String>) ent.getValue()
																				.stream()
																				.filter(l -> !l.equals(">" + " " + midpoint + "000000000000000"))
																				.collect(Collectors.toList())))));
											traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
										} else {
											colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
													.collect(Collectors.toMap(
															Entry::getKey, 
															entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
																	.collect(Collectors.toMap(
																			Entry::getKey, 
																			ent -> (List<String>) ent.getValue()
																				.stream()
																				.filter(l -> !l.equals("<=" + " " + midpoint + "000000000000000"))
																				.collect(Collectors.toList())))));
											traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
										}
									}
								} else {
									double GAIN_ATTR = gain_candidates.stream().map(candidates -> candidates.get(0))
											.mapToDouble(d -> d)
											.max().orElseThrow(NoSuchElementException::new);
									double midpoint = gain_candidates.stream()
											.filter(candidate -> candidate.get(0) == GAIN_ATTR)
											.map(candidate -> candidate.get(1))
											.collect(Collectors.toList()).get(0);
									System.out.println("Information gain over attribute " + colname + " with " + midpoint + " as midpoint: " + GAIN_ATTR);
									node_candidate.put(colname + "-" + midpoint, GAIN_ATTR);
								}
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							LoggerUtil.logError(e);
						}
					}
				}
			}
			double parent_value = node_candidate.entrySet().stream()
					.map(entry -> entry.getValue())
					.mapToDouble(d -> d)
					.max().orElseThrow(NoSuchElementException::new);
			Map<String, Double> parent = node_candidate.entrySet().stream()
					.filter(entry -> entry.getValue() == parent_value)
					.map(entry -> {
						Map<String, Double> n = new LinkedHashMap<>();
						n.put(entry.getKey(), entry.getValue());
						return n;
					}).collect(Collectors.toList()).get(0);
			boolean isContinous = parent.entrySet().stream().allMatch(p -> p.getKey().contains("-"));
			String attr = parent.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toList()).get(0);
			Map<String, List<String>> ancestor = new LinkedHashMap<>();
			if (!isContinous) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT DISTINCT \"").append(attr).append("\" FROM ").append(tablename).append(" AS r;");
				try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
					try (ResultSet rs = st.executeQuery()) {
						List<String> v = new ArrayList<>();
						while (rs.next()) {
							v.add(rs.getString(1));
						}
						ancestor.put(curr, v);
					}
				}
				for (String cond : ancestor.get(curr)) {
					List<String> candidates = colcandidate.entrySet().stream()
							.flatMap(entry -> entry.getValue().entrySet().stream()
							.flatMap(ent -> ent.getValue().stream()))
							.collect(Collectors.toList());
					if (!candidates.contains(cond)) {
						continue;
					} else {
						String cond1 = "";
						cond1 += conditions;
						sql = new StringBuilder();
						sql.append("SELECT DISTINCT \"class\" FROM ").append(tablename).append(" AS r WHERE 1 = 1 ");
						try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
							try (ResultSet rs = st.executeQuery()) {
								while (rs.next()) {
									String removed = "AND r.\"" + attr + "\" = '" + rs.getString(1) + "' ";
									cond1 = cond1.replace(removed, "");
								}
							}
						}
						cond1 += "AND r.\"" + attr + "\" = '" + cond + "' ";
						node.put(attr, ancestor);
						curr = attr;
						colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
								.collect(Collectors.toMap(
										Entry::getKey, 
										entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
												.collect(Collectors.toMap(
														Entry::getKey, 
														ent -> (List<String>) ent.getValue()
															.stream()
															.filter(l -> !l.equals(cond))
															.collect(Collectors.toList())))));
						List<String> candidate = colcandidate.get(attr).entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()).get(0);
						if (candidate.size() == 0) {
							colcandidate.remove(attr);
						}
						traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, cond1);
					}
				}
			} else {
				String name = attr.split("-")[0];
				double val = parent.entrySet().stream().map(entry -> Double.parseDouble(entry.getKey().split("-")[1])).mapToDouble(d -> d).max().getAsDouble();
				List<String> v = new ArrayList<>();
				v.add("<= " + val);
				v.add("> " + val);
				ancestor.put(curr, v);
				for (String cond : v) {
					String flag = cond.split(" ")[0];
					double midpoint = Double.parseDouble(cond.split(" ")[1]);
					if (flag.equals("<=")) {
						List<String> candidates = colcandidate.entrySet().stream()
								.flatMap(entry -> entry.getValue().entrySet().stream()
								.flatMap(ent -> ent.getValue().stream()))
								.collect(Collectors.toList());
						if (!candidates.contains(flag + " " + String.valueOf(midpoint) + "000000000000000")) {
							continue;
						} else {
							String cond1 = "";
							cond1 += conditions;
							cond1 += "AND r.\"" + name + "\" " + cond + " ";
							node.put(attr, ancestor);
							curr = attr;

							@SuppressWarnings("unchecked")
							Map<String, Map<String, List<String>>> newcolcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
									.collect(Collectors.toMap(
											Entry::getKey, 
											entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
													.collect(Collectors.toMap(
															Entry::getKey, 
															ent -> (List<String>) ent.getValue()
																.stream()
																.filter(l -> !l.equals(flag + " " + midpoint + "000000000000000"))
																.collect(Collectors.toList())))));
							List<String> candidate = newcolcandidate.get(name).entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()).get(0);
							if (candidate.size() == 0) {
								newcolcandidate.remove(name);
							}
							traverseTree(conn, tablename, E_INFO, newcolcandidate, node, curr, cond1);
						}
					} else if (flag.equals(">") ) {
						List<String> candidates = colcandidate.entrySet().stream()
								.flatMap(entry -> entry.getValue().entrySet().stream()
								.flatMap(ent -> ent.getValue().stream()))
								.collect(Collectors.toList());
						
						if (!candidates.contains(flag + " " + String.valueOf(midpoint) + "000000000000000")) {
							continue;
						} else {
							String cond2 = "";
							cond2 += conditions;
							cond2 += "AND r.\"" + name + "\" " + cond + " ";
							node.put(attr, ancestor);
							curr = attr;

							@SuppressWarnings("unchecked")
							Map<String, Map<String, List<String>>> newcolcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
									.collect(Collectors.toMap(
											Entry::getKey, 
											entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
													.collect(Collectors.toMap(
															Entry::getKey, 
															ent -> (List<String>) ent.getValue()
																.stream()
																.filter(l -> !l.equals(flag + " " + midpoint + "000000000000000"))
																.collect(Collectors.toList())))));
							List<String> candidate = newcolcandidate.get(name).entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()).get(0);
							if (candidate.size() == 0) {
								newcolcandidate.remove(name);
							}
<<<<<<< HEAD
							// extract to method
							handleEmptyGainCandidate(conn, tablename, E_INFO, colcandidate, node, curr, conditions, node_candidate, gain_candidates, colname);
=======
							traverseTree(conn, tablename, E_INFO, newcolcandidate, node, curr, cond2);
>>>>>>> b03d0e59db3a76d6f1b7f16b9391580d67cdfc5a
						}
					}
				}
			}
		}
<<<<<<< HEAD
		
		// min or max value of continuous amount is less favorable
		double max_entropy = node_candidate.entrySet().stream()
				.map(entry -> entry.getValue())
				.mapToDouble(d -> d)
				.max().orElseThrow(NoSuchElementException::new);
		Map<String, Double> winner = node_candidate.entrySet().stream()
				.filter(entry -> entry.getValue() == max_entropy)
				.map(entry -> {
					Map<String, Double> n = new LinkedHashMap<>();
					n.put(entry.getKey(), entry.getValue());
					return n;
				}).collect(Collectors.toList()).get(0);
		boolean isContinous = winner.entrySet().stream().allMatch(p -> p.getKey().contains("-"));
		String attr_name = winner.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toList()).get(0);
		StringBuilder sql;
		if (!isContinous) {
			colcandidate.remove(attr_name);
			sql = new StringBuilder();
			sql.append("SELECT DISTINCT \"").append(attr_name).append("\" FROM ").append(tablename);
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					Map<String, Map<String, String>> parent = new LinkedHashMap<>();
					Map<String, String> leaf = new LinkedHashMap<>();
					while (rs.next()) {
						if (node.isEmpty()) leaf.put(rs.getString(1), null);
						if (colcandidate.size() == 0) {
							String cond = "\"" + attr_name + "\"" + " = '" + rs.getString(1) + "'";
							sql = new StringBuilder();
							sql.append("SELECT DISTINCT \"class\" FROM ")
								.append(tablename).append(" WHERE ")
								.append(cond).append(";");
							try (PreparedStatement st1 = conn.prepareStatement(sql.toString());) {
								try (ResultSet rs1 = st1.executeQuery();) {
									StringBuilder result = new StringBuilder();
									String prefix = "";
									while (rs1.next()) {
										result.append(prefix).append(rs1.getString(1));
										prefix = "^";
									}
									leaf.put(rs.getString(1), result.toString());
								}
							}
						} else {
//							System.out.println("traverse tree");
						}
					}
					parent.put(curr, leaf);
					node.put(attr_name, parent);
				}
			}
			System.out.println(node);
		} else {
			String name = attr_name.split("-")[0];
			double midpoint = Double.parseDouble(attr_name.split("-")[1]);
			String flags[] = new String[] { "<=", ">" };
			Map<String, Map<String, String>> parent = new LinkedHashMap<>();
			Map<String, String> leaf = new LinkedHashMap<>();
			for (String flag : flags) {
				if (node.isEmpty()) leaf.put(flag + " " + midpoint, null);
				if (colcandidate.size() == 0) {
					String cond = "\"" + name + "\" " + flag + " '" + midpoint + "'";
					sql = new StringBuilder();
					sql.append("SELECT DISTINCT \"class\" FROM ")
						.append(tablename).append(" WHERE ")
						.append(cond).append(";");
					try (PreparedStatement st1 = conn.prepareStatement(sql.toString());) {
						try (ResultSet rs1 = st1.executeQuery();) {
							StringBuilder result = new StringBuilder();
							String prefix = "";
							while (rs1.next()) {
								result.append(prefix).append(rs1.getString(1));
								prefix = "^";
							}
							leaf.put(flag + " " + midpoint, result.toString());
						}
					}
				} else {
//					System.out.println("traverse tree");
				}
			}
			parent.put(curr, leaf);
			node.put(attr_name, parent);
			System.out.println(node);
			curr = attr_name;
			for (String flag : flags) {
				boolean colcandidate_exists = colcandidate.entrySet().stream()
						.flatMap(entry -> entry.getValue().entrySet().stream())
						.flatMap(entry1 -> entry1.getValue().stream())
						.anyMatch(item -> item.equals(flag + " " + midpoint));
				if (colcandidate.size() > 0 && colcandidate_exists) {
					colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
							.collect(Collectors.toMap(
									Entry::getKey, 
									entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
											.collect(Collectors.toMap(
													Entry::getKey, 
													ent -> (List<String>) ent.getValue().stream()
														.filter(l -> !l.equals(flag + " " + midpoint))
														.collect(Collectors.toList())))));
					conditions += " AND \"" + name + "\" " + flag + " '" + midpoint + "'";
					traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
				}
			}
		}
=======
>>>>>>> b03d0e59db3a76d6f1b7f16b9391580d67cdfc5a
	}

	private double computeExpectedInformation(Connection conn, String tablename) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(gain) AS e_information FROM (")
		   .append("    WITH computed_gain AS (")
		   .append("        SELECT \"class\", ")
		   .append("               COUNT(\"class\") OVER(PARTITION BY \"class\") * 1.0 AS partitioned, ")
		   .append("               COUNT(*) OVER() * 1.0 AS total FROM ").append(tablename)
		   .append("    )")
		   .append("    SELECT DISTINCT \"class\", partitioned, total, ")
		   .append("                    -1 * (partitioned / total) * LOG(2, (partitioned / total)) AS gain")
		   .append("    FROM computed_gain")
		   .append(") AS E_information;");
		double E_information = 0;
		try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					E_information = rs.getDouble(1);
				}
			}
		}
		return E_information;
	}
	
	private void inferUsingEntropyRatioMeasure(Connection conn, ResultSet rs) throws SQLException {
		String tablename = rs.getString(1);
		StringBuilder colname = new StringBuilder();
		
		colname.append("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '").append(rs.getString(1)).append("';");
		
		try (PreparedStatement st1 = conn.prepareStatement(colname.toString());) {
			try (ResultSet rs1 = st1.executeQuery();) {
				while (rs1.next()) {
					
				}
			}
		}
	}

	private void inferUsingGiniMeasure(Connection conn, ResultSet rs) throws SQLException {
		String tablename = rs.getString(1);
		StringBuilder colname = new StringBuilder();
		colname.append("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '").append(rs.getString(1)).append("';");
		
		try (PreparedStatement st1 = conn.prepareStatement(colname.toString());) {
			try (ResultSet rs1 = st1.executeQuery();) {
				List<String> colnames = new ArrayList<>();
				while (rs1.next()) {
					
				}
			}
		}
	}

	private void alterColumnType(Connection conn, StringBuilder sql, ResultSet rs) throws SQLException {
		StringBuilder colname = new StringBuilder();
		colname.append("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '")
			   .append(rs.getString(1)).append("';");
		try (PreparedStatement st1 = conn.prepareStatement(colname.toString());) {
			try (ResultSet rs1 = st1.executeQuery();) {
				List<String> colnames = new ArrayList<>();
				while (rs1.next()) colnames.add(rs1.getString(1));
				StringBuilder colisalterable = new StringBuilder();
				colisalterable.append("SELECT DISTINCT ");
				String prefix = "";
				for (String name : colnames) {
					colisalterable.append(prefix)
								  .append("CASE WHEN (\"").append(name)
								  .append("\"::varchar~ '^\\-?(\\d+\\.?\\d*|\\d*\\.?\\d+)$') THEN true ELSE false END as \"")
								  .append(name).append("\"");
					prefix = ", ";
				}
				colisalterable.append(" FROM ").append(rs.getString(1));
				applyColType(conn, rs, colnames, colisalterable);
			}
		}
	}

	private void applyColType(Connection conn, ResultSet rs, List<String> colnames, StringBuilder colisalterable) throws SQLException {
		try (PreparedStatement st2 = conn.prepareStatement(colisalterable.toString());) {
			try (ResultSet rs2 = st2.executeQuery();) {
				ResultSetMetaData md = rs2.getMetaData();
				StringBuilder alteredcols = new StringBuilder();
				alteredcols.append("ALTER TABLE ").append(rs.getString(1)).append(" ");
				while (rs2.next()) {
					String prefix = "";
					for (int i = 1; i <= md.getColumnCount(); i++) {
						if (rs2.getBoolean(i)) {
							alteredcols.append(prefix).append("ALTER COLUMN \"").append(colnames.get(i-1))
									   .append("\" TYPE NUMERIC (100) USING \"").append(colnames.get(i-1))
									   .append("\"::numeric");
							prefix = ", ";
						}
					}
				}
				try (PreparedStatement st3 = conn.prepareStatement(alteredcols.toString());) {
					st3.execute();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleEmptyGainCandidate(Connection conn, String tablename, double E_INFO, 
			Map<String, Map<String, List<String>>> colcandidate, Map<String, Map<String, Map<String, String>>> node, 
			String curr, String conditions, Map<String, Double> node_candidate, List<List<Double>> gain_candidates, String colname) 
		throws SQLException {
		if (gain_candidates.size() == 0) {
			String removed = node.entrySet().stream()
					.map(entry -> entry.getKey()).reduce((first, last) -> last).get();
			if (node.size() > 1) {
				node.remove(removed);
			}
			curr = node.entrySet().stream()
					.map(entry -> entry.getKey()).reduce((first, last) -> last).get();
			String lastcond = conditions.split("AND")[conditions.split("AND").length - 1];
			conditions = conditions.replace("AND" + lastcond, "");
			String flag = lastcond.split(" ")[2];
			double midpoint = Double.parseDouble(lastcond.split(" ")[3].replace("'", ""));
			List<String> candidates = colcandidate.entrySet().stream()
					.flatMap(entry -> entry.getValue().entrySet().stream()
					.flatMap(ent -> ent.getValue().stream()))
					.collect(Collectors.toList());
			String key = curr.split("-")[0];
			if (candidates.contains(flag + " " + midpoint)) {
				// if gain candidates is empty, prune all candidate <flag> <midpoint>
				Map<String, Map<String, List<String>>> replacement = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
						.filter(p -> p.getKey().equals(key))
						.collect(Collectors.toMap(
								Entry::getKey, 
								entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
										.filter(e -> e.getKey().equals("numeric"))
										.collect(Collectors.toMap(Entry::getKey, Entry::getValue))));
				replacement = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
						.filter(p -> p.getKey().equals(key))
						.collect(Collectors.toMap(
								Entry::getKey, 
								entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
										.filter(e -> e.getKey().equals("numeric"))
										.collect(Collectors.toMap(
												Entry::getKey, 
												ent -> (List<String>) ent.getValue()
													.stream()
													.filter(l -> Double.parseDouble(l.split(" ")[1]) != midpoint)
													.collect(Collectors.toList())))));
				colcandidate.put(key, replacement.get(key));
				traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
			} else {
				if (flag.equals("<=")) {
					if (conditions.equals(" ")) {
						conditions = "AND \"" + key + "\" > " + midpoint;
						traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
					} else {
						colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
								.collect(Collectors.toMap(
										Entry::getKey, 
										entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
												.collect(Collectors.toMap(
														Entry::getKey, 
														ent -> (List<String>) ent.getValue()
															.stream()
															.filter(l -> !l.equals(">" + " " + midpoint))
															.collect(Collectors.toList())))));
						traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
					}
				} else {
					if (conditions.isEmpty()) {
						conditions = "AND " + key + " <= " + midpoint;
						traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
					} else {
						colcandidate = (Map<String, Map<String,List<String>>>)(Map<?, ?>) colcandidate.entrySet().stream()
								.collect(Collectors.toMap(
										Entry::getKey, 
										entry -> (Map<String, List<String>>) entry.getValue().entrySet().stream()
												.collect(Collectors.toMap(
														Entry::getKey, 
														ent -> (List<String>) ent.getValue()
															.stream()
															.filter(l -> !l.equals("<=" + " " + midpoint))
															.collect(Collectors.toList())))));
						traverseTree(conn, tablename, E_INFO, colcandidate, node, curr, conditions);
					}
				}
			}
		} else {
			List<Double> colcandidateflat = colcandidate.entrySet().stream()
					.flatMap(entry -> entry.getValue().entrySet().stream())
					.filter(p -> p.getKey().equals("numeric"))
					.flatMap(entry1 -> entry1.getValue().stream())
					.map(s -> s.split(" ")[1])
					.map(d -> Double.parseDouble(d))
					.collect(Collectors.toList());
			System.out.println(colcandidateflat);
			// error here, and why Amount-125.0 is always picked?
			gain_candidates = gain_candidates.stream()
					.filter(p -> colcandidateflat.contains(p.get(1)))
					.collect(Collectors.toList());
			if (gain_candidates.size() == 0) {
				handleEmptyGainCandidate(conn, tablename, E_INFO, colcandidate, node, curr, conditions, node_candidate, gain_candidates, colname);
			} else {
				double GAIN_ATTR = gain_candidates.stream().map(candidates -> candidates.get(0))
						.mapToDouble(d -> d).max().orElseThrow(NoSuchElementException::new);
				double midpoint = gain_candidates.stream()
						.filter(candidate -> candidate.get(0) == GAIN_ATTR)
						.map(candidate -> candidate.get(1))
						.collect(Collectors.toList()).get(0);
				System.out.println("Information gain over attribute " + colname + " with " + midpoint + " as midpoint: " + GAIN_ATTR);
				node_candidate.put(colname + "-" + midpoint, GAIN_ATTR);
			}
		}
	}
}
