package io.iochord.apps.ips.model.analysis.services.ism;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class IsmDiscoveryTokenService extends AnIpsAsyncService<IsmDiscoveryConfiguration, String> {

	@Getter
	@Setter
	private IsmDiscoveryService discoveryService = new IsmDiscoveryService();

	@Getter
	@Setter
	private Map<String, String> activitySet = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, String> revActivitySet = new LinkedHashMap<>();
	
	@Getter
	@Setter
	Map<String, Map<String, String>> outputConnectors = new LinkedHashMap<>();
	
	public Set<String> findLinks(String afi, String ati, Set<String> visited) {
		Set<String> result = new HashSet<>();
		if (outputConnectors.containsKey(afi)) {
			for (Entry<String, String> cate : getOutputConnectors().get(afi).entrySet()) {
				String cat = cate.getKey();
				String cati = cate.getValue();
				if (cat.equals(ati)) {
					result.add(cati);
					break;
				} else if (!getRevActivitySet().containsKey(cat)) {
					visited.add(cat);
					Set<String> rResult = findLinks(cat, ati, visited);
					if (rResult.size() > 0) {
						result.add(cati);
						result.addAll(rResult);
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public String run(ServiceContext context, IsmDiscoveryConfiguration config) {
		IsmGraph graph = getDiscoveryService().run(context, config);
		StringBuilder animStr = new StringBuilder();
		if (config.getConnectorPaths() == null || config.getConnectorPaths().size() < 1) {
			return "";
		}
		// find path
		for (Entry<String, Page> pe : graph.getPages().entrySet()) {
			for (Entry<String, Connector> ce : pe.getValue().getConnectors().entrySet()) {
				String cId = ce.getKey();
				Connector c = ce.getValue();
				if (config.getConnectorPaths().containsKey(cId)) {
					c.getAttributes().put("ti", config.getConnectorPaths().get(cId));
				}
				String afi = c.getSource().getValue().getId();
				String af = c.getSource().getValue().getLabel();
				String ati = c.getTarget().getValue().getId();
				if (!getOutputConnectors().containsKey(afi)) {
					getOutputConnectors().put(afi, new LinkedHashMap<>());
				}
				getOutputConnectors().get(afi).put(ati, c.getAttributes().get("ti"));
				if (c.getSource().getValue() instanceof ActivityImpl) {
					getActivitySet().put(af, afi);
					getRevActivitySet().put(afi, af);
				}
			}
		}
		Map<String, Set<String>> arcsSet = new LinkedHashMap<>();
		for (Entry<String, Map<String, Double>> afe : getDiscoveryService().getDpMatrixMemory().entrySet()) {
			String af = afe.getKey();
			for (Entry<String, Double> ate : afe.getValue().entrySet()) {
				String at = ate.getKey();
				String arcId = af + "-->" + at;
				if (getActivitySet().containsKey(af) && getActivitySet().containsKey(at)) {
					arcsSet.put(arcId, findLinks(getActivitySet().get(af), getActivitySet().get(at), new HashSet<>()));
				}
			}
		}
		// get tokens
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ").append("\r\n")
			.append("	ce.a || '-->' || ne.a AS aid, ").append("\r\n")
			.append("	EXTRACT(EPOCH FROM ce.ec::TIMESTAMP) AS afs, ").append("\r\n")
			.append("	EXTRACT(EPOCH FROM ne.es::TIMESTAMP) AS atc, ").append("\r\n")
			.append("	count(*) AS f ").append("\r\n")
			.append("FROM ( ").append("\r\n")
			.append("	SELECT ").append("\r\n")
			.append("		row_number() over (ORDER BY ").append(config.getColCaseId()).append(", ").append(config.getColEventTimeStart()).append(") AS ri,  ").append("\r\n")
			.append("		").append(config.getColCaseId()).append(" AS c,  ").append("\r\n")
			.append("		").append(config.getColEventTimeStart()).append(" AS es,  ").append("\r\n")
			.append("		").append(config.getColEventTimeComplete()).append(" AS ec,  ").append("\r\n")
			.append("		").append(config.getColEventActivity()).append(" AS a  ").append("\r\n")
			.append("	FROM ").append(config.getDatasetId()).append("\r\n")
			.append("   WHERE eid > ").append(config.getSkipRows()).append("\r\n")
			.append("	ORDER BY ").append(config.getColCaseId()).append(", ").append(config.getColEventTimeStart()).append(" ").append("\r\n")
			.append(") AS ce ").append("\r\n")
			.append("JOIN ").append("\r\n")
			.append("( ").append("\r\n")
			.append("	SELECT  ").append("\r\n")
			.append("		row_number() over (ORDER BY ").append(config.getColCaseId()).append(", ").append(config.getColEventTimeStart()).append(") AS ri,  ").append("\r\n")
			.append("		").append(config.getColCaseId()).append(" AS c,  ").append("\r\n")
			.append("		").append(config.getColEventTimeStart()).append(" AS es,  ").append("\r\n")
			.append("		").append(config.getColEventTimeComplete()).append(" AS ec,  ").append("\r\n")
			.append("		").append(config.getColEventActivity()).append(" AS a  ").append("\r\n")
			.append("	FROM ").append(config.getDatasetId()).append("\r\n")
			.append("   WHERE eid > ").append(config.getSkipRows()).append("\r\n")
			.append("	ORDER BY ").append(config.getColCaseId()).append(", ").append(config.getColEventTimeStart()).append(" ").append("\r\n")
			.append(") AS ne ").append("\r\n")
			.append("ON ne.ri = ce.ri + 1 ").append("\r\n")
			.append("AND ne.c = ce.c ").append("\r\n")
			.append("WHERE (ce.a || '-->' || ne.a) IN ('").append(String.join("','", arcsSet.keySet())).append("')")
			.append("GROUP BY ce.a, ne.a, ce.ec, ne.es").append("\r\n")
			.append("ORDER BY afs, atc").append("\r\n");
			double tsMin = Double.MAX_VALUE;
			double tsMax = Double.MIN_VALUE;
			double fMin = Integer.MAX_VALUE;
			double fMax = Integer.MIN_VALUE;
			// build token
			List<Object[]> allTokens = new ArrayList<>();
			Map<String, String> tokenArcs = new LinkedHashMap<>();
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String arcId = rs.getString("aid");
					long afs = rs.getLong("afs");
					long atc = rs.getLong("atc");
					int f = rs.getInt("f");
					tsMin = Math.min(tsMin, afs);
					tsMax = Math.max(tsMax, atc);
					fMin = Math.min(fMin, f);
					fMax = Math.max(fMax, f);
					allTokens.add(new Object[] { arcId, afs, atc, f });
					tokenArcs.put(arcId, null);
				}
			}
			// generate svg
			animStr.append("<g class=\"log-replay\">");
			double tsRange = tsMax - tsMin;
			double fRange = fMax - fMin;
			int tzRange = config.getTokenMaxSize() - config.getTokenMinSize();
			int tid = 0;
			for (Object[] token : allTokens) {
				String arcId = (String) token[0];
				long afs = (long) token[1];
				long atc = (long) token[2];
				int f = (int) token[3];
				Set<String> tis = arcsSet.get(arcId);
				if (tis != null && tis.size() > 0) {
					String tb = String.format("%.2f", tsRange == 0 ? 0 : Math.max(0, ((afs - tsMin) / tsRange) * config.getAnimatorLength()));
					String td = String.format("%.2f", (tsRange == 0 ? 1 : (((atc - afs) / tsRange) * config.getAnimatorLength())) / tis.size());
					double tbs = Double.parseDouble(tb);
					double tds = Double.parseDouble(td);
					// if (tds <= 0) continue;
					if (tds <= 0) {
						tds = 0.1;
					}
					// System.out.println(arcId + " " + tis.size() + " " + tbs + " " + (tds * tis.size()));
					int i = 0;
					for (String ti : tis) {
						String tz = String.format("%.0f", fRange == 0 ? config.getTokenMinSize() : Math.max(config.getTokenMinSize(), Math.floor(config.getTokenMinSize() + (((f - fMin) / fRange) * tzRange))));
						double tzs = Double.parseDouble(tz);
						double tzsh = tzs / 2;
						animStr.append("<g id=\"token" + tid + "\" style=\"opacity: 0;\">\r\n");
						animStr.append("\t<animateMotion id=\"token" + tid + "motion\" begin=\"" + (tbs + (i * tds)) + "s\" dur=\"" + tds + "s\" repeatCount=\"0\"><mpath xlink:href=\"#" + ti + "\"></mpath></animateMotion>\r\n");
						animStr.append("\t<animate begin=\"" + (tbs + (i * tds) + 0.01) + "s\" dur=\"0.01s\" attributeName=\"opacity\" from=\"0\" to=\"1\" repeatCount=\"0\" fill=\"freeze\" />\r\n");
						animStr.append("\t<animate begin=\"token" + tid + "motion.end\" dur=\"0.01s\" attributeName=\"opacity\" from=\"1\" to=\"0\" repeatCount=\"0\" fill=\"freeze\" />\r\n");
						// <image joint-selector="image" id="v-610" x="0.5" y="0.5" xlink:href="/icons/port_icons/quay.png" width="63" height="63"></image>
						animStr.append("\t<image x=\"-" + tzsh + "\" y=\"-" + tzsh + "\" xlink:href=\"/icons/port_icons/move-red.png\" width=\"" + tzs + "\" height=\"" + tzs + "\"></image>\r\n");
						// animStr.append("\t<circle r=\"" + tzsh + "\" stroke=\"red\" stroke-width=\"3\" fill=\"transparent\">\r\n");
						animStr.append("</g>\r\n");
						i++;
						tid++;
					}
				}
			}
			animStr.append("</g>");
		} catch (Exception ex) {
			ex.printStackTrace();
			LoggerUtil.logError(ex);
		}
		return animStr.toString();
	}
	
}
