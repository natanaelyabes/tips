package io.iochord.apps.ips.model.services.data.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.deckfour.xes.extension.XExtension;

import io.iochord.apps.ips.common.models.Dataset;
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
public class MappingRepositoryService extends AnIpsService<String, MappingResource> {
	
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
				resource.setTechnicalNames(technical_names);
			}
			
			// Return column header
			StringBuilder colheaderssql = new StringBuilder();
			colheaderssql.append("SELECT * FROM ").append(config).append(" LIMIT 1;");
			try (PreparedStatement st = conn.prepareStatement(colheaderssql.toString());
				ResultSet rs = st.executeQuery();) {
				Map<String, String> map = new LinkedHashMap<>();
				while(rs.next()) {
					for (int i = 0; i < resource.getTechnicalNames().size(); i++) {
						map.put(resource.getTechnicalNames().get(i), rs.getString(i + 1));
					}
				}
				map.remove("eid");
				resource.setColHeaders(map);
			}
			
			// Return settings
			StringBuilder mapsettingssql = new StringBuilder();
			mapsettingssql.append("SELECT * FROM ").append(config).append("_mappings").append(";");
			try (PreparedStatement st = conn.prepareStatement(mapsettingssql.toString())) {
				ResultSet rs = st.executeQuery();
				Map<String, String> map = new LinkedHashMap<>();
				while(rs.next()) {
					map.put(rs.getString(2), rs.getString(3));
				}
				map.remove("eid");
				resource.setMapSettings(map);
			} catch (Exception e) {
				// Build tables
				mapsettingssql = new StringBuilder();
				mapsettingssql.append("CREATE TABLE IF NOT EXISTS ")
					.append(config).append("_mappings")
					.append(" ( ")
					.append("   eid SERIAL PRIMARY KEY, ")
					.append("   technical_names VARCHAR(100) NULL, ")
					.append("   mappings VARCHAR(100) NULL ")
					.append(" ); ");
				try (PreparedStatement st = conn.prepareStatement(mapsettingssql.toString())) {
					st.execute();
				}
				
				resource.setMapSettings(resource.getColHeaders());

				mapsettingssql = new StringBuilder();
				mapsettingssql.append("INSERT INTO ").append(config).append("_mappings").append(" VALUES ")
				   .append("(DEFAULT, ?, ?);");
				try (PreparedStatement st = conn.prepareStatement(mapsettingssql.toString());) {
					for (Entry<String, String> map : resource.getMapSettings().entrySet()) {
						st.setString(1, map.getKey());
						st.setString(2, map.getValue());
						st.addBatch();
					}
					st.executeBatch();
				}
				
				mapsettingssql = new StringBuilder();
				mapsettingssql.append("SELECT * FROM ").append(config).append("_mappings").append(";");
				try (PreparedStatement st = conn.prepareStatement(mapsettingssql.toString())) {
					ResultSet rs = st.executeQuery();
					Map<String, String> map = new LinkedHashMap<>();
					while(rs.next()) {
						map.put(rs.getString(2), rs.getString(3));
					}
					map.remove("eid");
					resource.setMapSettings(map);
				}
			}
			
			// Return firstNRecord
			StringBuilder first_n_record = new StringBuilder();
			first_n_record.append("SELECT * FROM ").append(config).append(" LIMIT 100 OFFSET 1;");
			try (PreparedStatement st = conn.prepareStatement(first_n_record.toString());
				ResultSet rs = st.executeQuery();) {
				List<Map<String, String>> firstNRows = new LinkedList<>();
				while(rs.next()) {
					Map<String, String> row = new LinkedHashMap<>();
					for (int i = 0; i < resource.getTechnicalNames().size(); i++) {
						row.put(resource.getTechnicalNames().get(i), rs.getString(i + 1));
					}
					firstNRows.add(row);
				}				
				resource.setFirstNRows(firstNRows);
			}
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
		return resource;
	}
}
