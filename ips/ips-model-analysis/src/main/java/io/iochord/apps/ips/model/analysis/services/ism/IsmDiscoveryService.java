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
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
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
		Page p = result.getPages().values().iterator().next();
		int ni = 0;
		for (String ea : nodes.keySet()) {
			ActivityImpl a = (ActivityImpl) factory.addActivity(p);
			a.setLabel(ea);
			a.setId("ACTIVITY" + ni);
			nodes.put(ea, a);
			if (++ni % 10 == 0) {
				context.updateProgress(50, ni + " nodes found.");
			}
			if (ni > 10) break;
		}
		int ci = 0;
		for (String fa : dfMatrix.keySet()) {
			for (String ta : dfMatrix.get(fa).keySet()) {
				if (nodes.containsKey(fa) && nodes.containsKey(ta)) {
					ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, nodes.get(fa), nodes.get(ta));
					c.setId("CONNECTOR" + ni);
					c.setLabel(String.valueOf(dfMatrix.get(fa).get(ta)));
					if (++ci % 100 == 0) {
						context.updateProgress(75, ci + " connector found.");
					}
		//			if (ci > 10) break;
				}
			}
		}
		return result;
	}

	public Map<String, Map<String, Long>> discoverDfMatrix(ServiceContext context, String tabName, String colCaseId, String colActivity, String colTs,  int skip) {
// 		getLogger().info("Mining Directly-follow Matrix " + tabName + " ( " + colCaseId + ", " + colActivity + ", " + colTs
//				+ ") Started ... ");
		Map<String, Map<String, Long>> dfMatrix = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
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
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
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
			} catch (Exception ex) { logException(ex); }
		} catch (Exception ex) { logException(ex); }
//		getLogger().info("Mining Directly-follow Matrix " + tabName + " ( " + colCaseId + ", " + colActivity + ", " + colTs
//				+ ") Finished ... " + (System.currentTimeMillis() - started) + " ms.");
		return dfMatrix;
	}
}
