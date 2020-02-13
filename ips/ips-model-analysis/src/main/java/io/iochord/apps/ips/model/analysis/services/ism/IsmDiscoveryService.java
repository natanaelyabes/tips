package io.iochord.apps.ips.model.analysis.services.ism;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.iochord.apps.ips.common.util.LoggerUtil;
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
	public IsmGraph run(ServiceContext context, IsmDiscoveryConfiguration config) {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		context.updateProgress(25, "Discovering DF matrix.");
		Map<String, Map<String, Long>> dfMatrix = discoverDfMatrix(context, config.getDatasetId(), config.getColCaseId(), config.getColEventActivity(), config.getColEventTimestamp(), config.getSkipRows());
		Map<String, Activity> nodes = new LinkedHashMap<>();
		for (Entry<String, Map<String, Long>> fae : dfMatrix.entrySet()) {
			nodes.put(fae.getKey(), null);
			for (String ta : fae.getValue().keySet()) {
				nodes.put(ta, null);
			}
		}
		return createGraph(context, factory, nodes, dfMatrix);
	}
	
	public IsmGraph createGraph(ServiceContext context, IsmFactory factory, Map<String, Activity> nodes, Map<String, Map<String, Long>> dfMatrix) {
		IsmGraph result = factory.create();
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
		createConnector(context, factory, nodes, dfMatrix, p, ni);
		return result;
	}

	private void createConnector(ServiceContext context, IsmFactory factory, Map<String, Activity> nodes, Map<String, Map<String, Long>> dfMatrix, Page p, int ni) {
		int ci = 0;
		for (Entry<String, Map<String, Long>> fae : dfMatrix.entrySet()) {
			String fa = fae.getKey();
			for (String ta : fae.getValue().keySet()) {
				if (nodes.containsKey(fa) && nodes.containsKey(ta)) {
					ConnectorImpl c = (ConnectorImpl) factory.addConnector(p, nodes.get(fa), nodes.get(ta));
					c.setId("CONNECTOR" + ni);
					c.setLabel(String.valueOf(dfMatrix.get(fa).get(ta)));
					if (++ci % 100 == 0) {
						context.updateProgress(75, ci + " connector found.");
					}
				}
			}
		}
	}

	public Map<String, Map<String, Long>> discoverDfMatrix(ServiceContext context, String tabName, String colCaseId, String colActivity, String colTs,  int skip) {
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
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return dfMatrix;
	}
}
