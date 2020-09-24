package io.iochord.apps.ips.model.services.data.ex.csv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class CsvDataExportService extends AnIpsAsyncService<CsvDataExportConfiguration, String> {
	
	@Override
	public String run(ServiceContext context, CsvDataExportConfiguration config) {
		StringBuilder result = new StringBuilder();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM " + config.getDatasetId());
			Set<Integer> ci = new LinkedHashSet<>();
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					if (ci.size() < 1) {
						int cc = rs.getMetaData().getColumnCount();
						List<String> sa = new ArrayList<>(); 
						for (int i = 1; i < cc; i++) {
							String cn = rs.getMetaData().getColumnName(i);
							if (!cn.equalsIgnoreCase("eid")) {
								ci.add(i);
								sa.add(cn);
							}
						}
						result.append(String.join(config.getDelimeter(), sa)).append(config.getLineBreak());
					}
					List<String> sa = new ArrayList<>(); 
					for (int i : ci) {
						sa.add(rs.getString(i));
					}
					result.append(String.join(config.getDelimeter(), sa)).append(config.getLineBreak());
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return result.toString();
	}

}
