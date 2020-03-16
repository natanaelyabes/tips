package io.iochord.apps.ips.model.services.data.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class MappingService extends AnIpsService<String, MappingResource> {

	@Override
	public MappingResource run(ServiceContext context, String config) {
		MappingResource resource = new MappingResource();
		try (Connection conn = context.getDataSource().getConnection();) {
			
			// Return first row
			StringBuilder colnamesql = new StringBuilder();
			colnamesql.append("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '")
				.append(config).append("';");
			try (PreparedStatement st = conn.prepareStatement(colnamesql.toString());
				ResultSet rs = st.executeQuery();) {
				List<String> technical_names = new LinkedList<>();
				while (rs.next()) {
					technical_names.add(rs.getString("column_name"));
				}
				resource.setTechnicalName(technical_names);
			}
			
			// Return settings
			StringBuilder mapsettingssql = new StringBuilder();
			mapsettingssql.append("SELECT * FROM ").append(config).append(" LIMIT 1;");
			try (PreparedStatement st = conn.prepareStatement(mapsettingssql.toString());
				ResultSet rs = st.executeQuery();) {
				List<Map<String, String>> map_settings = new LinkedList<>();
				while(rs.next()) {
					for (int i = 0; i < resource.getTechnicalName().size(); i++) {
						Map<String, String> map = new LinkedHashMap<>();
						map.put(resource.getTechnicalName().get(i), rs.getString(i + 1));
						map_settings.add(map);
					}
				}
				resource.setMapSettings(map_settings);
			}
			
			// Return firstNRecord
			StringBuilder first_n_record = new StringBuilder();
			first_n_record.append("SELECT * FROM ").append(config).append(" LIMIT 100 OFFSET 1;");
			try (PreparedStatement st = conn.prepareStatement(first_n_record.toString());
				ResultSet rs = st.executeQuery();) {
				List<Map<String, String>> firstNRows = new LinkedList<>();
				while(rs.next()) {
					for (int i = 0; i < resource.getTechnicalName().size(); i++) {
						Map<String, String> row = new LinkedHashMap<>();
						row.put(resource.getTechnicalName().get(i), rs.getString(i + 1));
						firstNRows.add(row);
					}
				}				
				resource.setFirstNRows(firstNRows);
			}
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
		return resource;
	}
}
