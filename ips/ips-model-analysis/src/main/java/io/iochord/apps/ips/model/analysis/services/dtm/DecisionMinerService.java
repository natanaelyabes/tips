package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2020
 * 
 */
public class DecisionMinerService extends AnIpsAsyncService<DecisionMinerConfig, DecisionMinerResult> {

	@Getter @Setter
	private IsmDiscoveryService discoveryService = new IsmDiscoveryService();
	
	@Override
	public DecisionMinerResult run(ServiceContext context, DecisionMinerConfig config) {
		IsmDiscoveryConfiguration dConfig = new IsmDiscoveryConfiguration();
		// This is example to get model from some dataset
		// Parameter tuning should be set from UI
		// If graph discovery is necessary then DecisionMinerConfig should inherit IsmDiscoveryConfiguration
		dConfig.setDatasetId(config.getDatasetId());
		// Perform case mapping against selected event log.
		// Case mapping should be performed before decision point analysis.
		Map<String, String> mappings = getMappings(context, config);
		// Retrieve case id, event name, and timestamp information.
		// Afterwards, set the parameter for process discovery miner.
		String caseid_col = mappings.entrySet().stream().filter(set -> set.getValue().equals("case_id"))
				.map(set -> set.getKey()).collect(Collectors.toList()).get(0);
		String evact_col = mappings.entrySet().stream().filter(set -> set.getValue().equals("concept:name"))
				.map(set -> set.getKey()).collect(Collectors.toList()).get(0);
		String tsmp_col = mappings.entrySet().stream().filter(set -> set.getValue().equals("time:timestamp"))
				.map(set -> set.getKey()).collect(Collectors.toList()).get(0);
		dConfig.setColCaseId(caseid_col);
		dConfig.setColEventActivity(evact_col);
		dConfig.setColEventTimestamp(tsmp_col);
		dConfig.setDependencyThreshold(config.getPdDependencyThreshold());
		dConfig.setPositiveObservationThreshold(config.getPdPositiveObservationThreshold());
		// Discover process model from the given configuration.
		IsmGraph ismGraph = getDiscoveryService().run(context, dConfig);
		return null;
	}

	private Map<String, String> getMappings(ServiceContext context, DecisionMinerConfig config) {
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
		return mappings;
	}
}
