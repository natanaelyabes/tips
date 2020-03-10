package io.iochord.apps.ips.model.analysis.services.resm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

public class ResourceMinerService extends AnIpsAsyncService<ResourceMinerConfig, ResourceMinerResult> {

	@Override
	public ResourceMinerResult run(ServiceContext context, ResourceMinerConfig config) {
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
			ResourceMinerResult result = new ResourceMinerResult();
			result.setConfig(config);
			return result;
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return null;
	}

}
