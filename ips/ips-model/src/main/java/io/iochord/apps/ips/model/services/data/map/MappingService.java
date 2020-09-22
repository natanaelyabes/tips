package io.iochord.apps.ips.model.services.data.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.iochord.apps.ips.common.util.JsonDataCodec;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.AnIpsService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportConfiguration;

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
		
		try (Connection conn = context.getDataSource().getConnection()) {
			CsvDataImportConfiguration imConfig = null;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT tablename, obj_description(tablename::regclass) AS json FROM pg_catalog.pg_tables WHERE ")
			   .append("tablename = '").append(config.getDatasetId()).append("'");
			try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					String tableJson = rs.getString("json");
					if (tableJson != null) {
						imConfig = JsonDataCodec.getDeserializer().readValue(tableJson, CsvDataImportConfiguration.class);
					}
				}
			}
			
			if (imConfig != null) {
				sql = new StringBuilder();
				sql.append("SELECT COUNT(*) FROM pg_catalog.pg_views WHERE viewname LIKE '")
				   .append(config.getDatasetId()).append("_repo")
				   .append("%' AND obj_description(viewname::regclass) IS NOT NULL AND schemaname != 'pg_catalog' AND schemaname != 'information_schema'");
				int repoCount = 0; 
				try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
					ResultSet rs = st.executeQuery();
					rs.next();
					repoCount = rs.getInt(1);
				}
				
				String repoId = config.getDatasetId() + "_repo" + (repoCount + 1);
				Map<String, List<String>> maps = new LinkedHashMap<>();
				for (Entry<String, String> colEnt : config.getResource().getMapSettings().entrySet()) {
					String col = colEnt.getKey();
					String map = colEnt.getValue();
					if (!maps.containsKey(map)) {
						maps.put(map, new ArrayList<>());
					}
					maps.get(map).add(col);
				}
	
				if (!maps.containsKey("ec") && maps.containsKey("es")) {
					maps.put("ec", maps.get("es"));
				}
				
				sql = new StringBuilder();
				sql.append("CREATE VIEW ").append(repoId).append(" AS \r\n")
					.append("SELECT eid");
				for (Entry<String, List<String>> mapEnt : maps.entrySet()) {
					String map = mapEnt.getKey();
					List<String> cols = mapEnt.getValue();
					sql.append(", \r\n");
					if (cols.size() > 1) {
						sql.append("CONCAT(").append(String.join(",'|',", cols)).append(")");
					} else {
						sql.append(cols.get(0));
					}
					sql.append(" AS ").append(map);
				}
				sql.append(" FROM ").append(config.getDatasetId()); 
				if (imConfig.getHeaderRowIdx() > -1) {
					sql.append(" WHERE eid > ").append(imConfig.getHeaderRowIdx() + 1);
				}
				try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
					st.execute();
				}
				
				MappingResult repo = result;
				repo.setId(repoId);
				repo.setParentId(config.getDatasetId());
				repo.setName(imConfig.getName());
				sql = new StringBuilder();
				sql.append("COMMENT ON VIEW ").append(repoId).append(" IS '")
					.append(SerializationUtil.encode(repo)).append("'");
				try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
					st.execute();
				}
			}
				
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
			
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
		return result;
	}
}
