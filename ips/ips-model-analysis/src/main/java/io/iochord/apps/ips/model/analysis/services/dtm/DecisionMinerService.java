package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import lombok.Getter;
import lombok.Setter;

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
		sconfig.setDatasetId("ips_dataset_1583992659865");
		sconfig.setColCaseId("c1 || c2");
		sconfig.setColEventActivity("c10");
		sconfig.setColEventTimestamp("c11");
		// the heuristics type is non connected heuristics, so if you need all arcs set
		// dep = 0, posobs = 0
		sconfig.setDependencyThreshold(0f);
		sconfig.setPositiveObservationThreshold(0);
		IsmGraph ismGraph = getDiscoveryService().run(context, sconfig);
		
		try (Connection conn = context.getDataSource().getConnection();) {
			// Get from configuration
			String datasetId = config.getDatasetId();
			// Skeleton to run SQL
			StringBuilder sql = new StringBuilder();
			try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
				try (ResultSet rs = st.executeQuery()) {
					
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
