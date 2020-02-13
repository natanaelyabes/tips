package io.iochord.apps.ips.model.services.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.JsonDataCodec;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsService;
import io.iochord.apps.ips.core.services.ServiceContext;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class DatasetRepositoryService extends AnIpsService<String, Map<String, Dataset>> {

	@Override
	public Map<String, Dataset> run(ServiceContext context, String config) {
		Map<String, Dataset> datasets = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT tablename, obj_description(tablename::regclass) AS json FROM pg_catalog.pg_tables WHERE tablename LIKE '")
			.append(Dataset.TABLE_PREFIX)
			.append("%' AND schemaname != 'pg_catalog' AND schemaname != 'information_schema';");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String tablename = rs.getString("tablename");
					String tableJson = rs.getString("json");
					Dataset ds = JsonDataCodec.getDeserializer().readValue(tableJson, Dataset.class);
					ds.setId(tablename);
					datasets.put(ds.getId(), ds);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return datasets;
	}

}
