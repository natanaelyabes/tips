package io.iochord.apps.ips.model.analysis.services.resm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.ServiceContext;

public class ModelQuery {

	public static List<String> getResources(ServiceContext context, ResourceMinerConfig config) {
		List<String> resources = new ArrayList<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
				.append("DISTINCT ").append(config.getColEventResource()).append(" as resource ")
				.append("FROM ").append(config.getDatasetId()).append(" ")
				.append("WHERE eid > ").append(config.getSkipRows());
			//System.out.println(sql.toString());
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String resource = rs.getString(1);
					resources.add(resource);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return resources;
	}
	
	public static List<String> getActivities(ServiceContext context, ResourceMinerConfig config) {
		List<String> activities = new ArrayList<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
				.append("DISTINCT ").append(config.getColEventActivity()).append(" as activity ")
				.append("FROM ").append(config.getDatasetId()).append(" ")
				.append("WHERE eid > ").append(config.getSkipRows());
			//System.out.println(sql.toString());
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String activity = rs.getString(1);
					activities.add(activity);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return activities;
	}
	
	public static Map<String, List<String>> getDefOrgUnits(ServiceContext context, ResourceMinerConfig config) {
		Map<String, List<String>> orgUnits = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
				.append("DISTINCT ").append(config.getColEventResource()).append(" as resource, ")
				.append(config.getColEventActivity()).append(" as activity ")
				.append("FROM ").append(config.getDatasetId()).append(" ")
				.append("WHERE eid > ").append(config.getSkipRows()).append(" ")
				.append("ORDER BY ")
				.append(config.getColEventActivity());
			//System.out.println(sql.toString());
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String resource = rs.getString(1);
					String activity = rs.getString(2);
					List<String> listOfRes = orgUnits.getOrDefault(activity, new ArrayList<String>());
					listOfRes.add(resource);
					orgUnits.put(activity, listOfRes);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return orgUnits;
	}
	
	public static Map<ResourceToActivity, Integer> getOrgActMatrix(ServiceContext context, ResourceMinerConfig config) {
		Map<ResourceToActivity, Integer> oaMatrix = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
				.append(config.getColEventResource()).append(" as resource, ")
				.append(config.getColEventActivity()).append(" as activity, ")
				.append("count(*)").append(" as frequency ")
				.append("FROM ").append(config.getDatasetId()).append(" ")
				.append("WHERE eid > ").append(config.getSkipRows()).append(" ")
				.append("GROUP BY ")
				.append("(").append(config.getColEventResource())
				.append(",")
				.append(config.getColEventActivity()).append(")");
			//System.out.println(sql.toString());
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					ResourceToActivity rta = new ResourceToActivity(rs.getString(1),rs.getString(2));
					Integer frequency = rs.getInt(3);
					oaMatrix.put(rta, frequency);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return oaMatrix;
	}
}
