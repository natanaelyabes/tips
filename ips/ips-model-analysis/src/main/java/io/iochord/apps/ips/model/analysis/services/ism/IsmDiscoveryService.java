package io.iochord.apps.ips.model.analysis.services.ism;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.components.Activity;
import io.iochord.apps.ips.model.ism.v1.components.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;

public class IsmDiscoveryService extends AnIpsAsyncService<IsmDiscoveryConfiguration, IsmGraph> {

	@Override
	public IsmGraph run(ServiceContext context, IsmDiscoveryConfiguration config) throws Exception {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		IsmGraph result = factory.create();
		context.updateProgress(25, "Discovering DF matrix.");
		Map<String, Map<String, Long>> dfMatrix = discoverDfMatrix(context, config.getDatasetId(), config.getColCaseId(), config.getColEventActivity(), config.getColEventTimestamp(), config.getSkipRows());
		Map<String, Activity> nodes = new LinkedHashMap<String, Activity>();
		for (String fa : dfMatrix.keySet()) {
			nodes.put(fa, null);
			for (String ta : dfMatrix.get(fa).keySet()) {
				nodes.put(ta, null);
			}
		}
		Thread.sleep(500);
		Page p = result.getPages().values().iterator().next();
		int ni = 0;
		for (String ea : nodes.keySet()) {
			ActivityImpl a = (ActivityImpl) factory.addActivity(p);
			a.setLabel(ea);
			nodes.put(ea, a);
			if (++ni % 10 == 0) {
				context.updateProgress(50, ni + " nodes found.");
				Thread.sleep(50);
			}
		}
		int ci = 0;
		for (String fa : dfMatrix.keySet()) {
			for (String ta : dfMatrix.get(fa).keySet()) {
				ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, nodes.get(fa), nodes.get(ta));
				c.setLabel(String.valueOf(dfMatrix.get(fa).get(ta)));
				if (++ci % 100 == 0) {
					context.updateProgress(75, ci + " connector found.");
					Thread.sleep(50);
				}
			}
		}
		return result;
	}

	public Map<String, Map<String, Long>> discoverDfMatrix(ServiceContext context, String tabName, String colCaseId, String colActivity, String colTs,  int skip) {
// 		getLogger().info("Mining Directly-follow Matrix " + tabName + " ( " + colCaseId + ", " + colActivity + ", " + colTs
//				+ ") Started ... ");
		Map<String, Map<String, Long>> dfMatrix = new LinkedHashMap<>();
		try {
			Connection conn = context.getDataSource().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
				.append("	CASE  ")
				.append("		WHEN ce.c = ne.c ")
				.append("			THEN ce.a  ")
				.append("		ELSE 'start' ")
				.append("	END AS af, ")
				.append("	ne.a AS at, ")
				.append("	count(*) AS f ")
				.append("FROM ( ")
				.append("	SELECT  ")
				.append("		row_number() over () AS ri,  ")
				.append("		").append(colCaseId).append(" AS c,  ")
				.append("		").append(colActivity).append(" AS a  ")
				.append("	FROM ").append(tabName)
				.append("  WHERE eid > ").append(skip)
				.append("	ORDER BY ").append(colCaseId).append(", ").append(colTs).append(" ")
				.append(") AS ce ")
				.append("JOIN ")
				.append("( ")
				.append("	SELECT  ")
				.append("		row_number() over () AS ri,  ")
				.append("		").append(colCaseId).append(" AS c,  ")
				.append("		").append(colActivity).append(" AS a  ")
				.append("	FROM ").append(tabName)
				.append("  WHERE eid > ").append(skip)
				.append("	ORDER BY ").append(colCaseId).append(", ").append(colTs).append(" ")
				.append(") AS ne ")
				.append("ON ne.ri = ce.ri + 1 ")
				.append("GROUP BY af, at");
//			getLogger().debug(sql.toString());
			PreparedStatement st = conn.prepareStatement(sql.toString());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String actFrom = rs.getString(1);
				String actTo = rs.getString(2);
				long actFrequency = rs.getLong(3);
				if (!dfMatrix.containsKey(actFrom)) {
					dfMatrix.put(actFrom, new LinkedHashMap<>());
				}
				dfMatrix.get(actFrom).put(actTo, actFrequency);
//				getLogger().debug(actFrom + " --> " + actTo + " : "  + actFrequency);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		getLogger().info("Mining Directly-follow Matrix " + tabName + " ( " + colCaseId + ", " + colActivity + ", " + colTs
//				+ ") Finished ... " + (System.currentTimeMillis() - started) + " ms.");
		return dfMatrix;
	}
}
