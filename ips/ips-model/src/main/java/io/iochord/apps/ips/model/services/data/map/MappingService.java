package io.iochord.apps.ips.model.services.data.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map.Entry;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsService;
import io.iochord.apps.ips.core.services.ServiceContext;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class MappingService extends AnIpsService<MappingConfiguration, MappingResult> {

	@Override
	public MappingResult run(ServiceContext context, MappingConfiguration config) {
		// TODO Auto-generated method stub
		MappingResult result = new MappingResult();
		
		try (Connection conn = context.getDataSource().getConnection();) {
			
			// Build tables
			StringBuilder mapsettingstable = new StringBuilder();
			mapsettingstable.append("DROP TABLE IF EXISTS ")
				.append(config.getDatasetId()).append("_mappings;");
			mapsettingstable.append("CREATE TABLE IF NOT EXISTS ")
				.append(config.getDatasetId()).append("_mappings")
				.append(" ( ")
				.append("   eid SERIAL PRIMARY KEY, ")
				.append("   technical_names VARCHAR(100) NULL, ")
				.append("   mappings VARCHAR(100) NULL ")
				.append(" ); ");
			try (PreparedStatement st = conn.prepareStatement(mapsettingstable.toString())) {
				st.execute();
			}
			
			StringBuilder mapsettingssql = new StringBuilder();
			mapsettingssql.append("INSERT INTO ").append(config.getDatasetId()).append("_mappings").append(" VALUES ")
			   .append("(DEFAULT, ?, ?);");
			
			try (PreparedStatement st = conn.prepareStatement(mapsettingssql.toString());) {
				for (Entry<String, String> map : config.getResource().getMapSettings().entrySet()) {
					st.setString(1, map.getKey());
					st.setString(2, map.getValue());
					st.addBatch();
				}
				st.executeBatch();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LoggerUtil.logError(e);
		}
		return result;
	}
}
