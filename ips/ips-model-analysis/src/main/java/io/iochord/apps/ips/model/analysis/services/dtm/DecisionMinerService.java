package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

public class DecisionMinerService extends AnIpsAsyncService<DecisionMinerConfig, DecisionMinerResult> {

	@Override
	public DecisionMinerResult run(ServiceContext context, DecisionMinerConfig config) {
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
