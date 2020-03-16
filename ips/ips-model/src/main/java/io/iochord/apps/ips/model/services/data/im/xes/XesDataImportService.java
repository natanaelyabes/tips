package io.iochord.apps.ips.model.services.data.im.xes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.extension.XExtension;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class XesDataImportService extends AnIpsAsyncService<XesDataImportConfiguration, XesDataImportResult> {

	@Override
	public XesDataImportResult run(ServiceContext context, XesDataImportConfiguration config) {
			
		XesDataImportResult result = new XesDataImportResult();
		
		try (Connection conn = context.getDataSource().getConnection();) {
			
			XLog log = config.getLog();
			
			String suffix = log.getAttributes().get("concept:name").toString();
			suffix = "_" + suffix;
			String name = Dataset.TABLE_PREFIX + context.getId();
			
			result.setConfig(config);
			result.setName(name);
			
			// Extensions
			Set<XExtension> exts = log.getExtensions();
			if (exts != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("DROP TABLE IF EXISTS ").append(name + "_extensions;");
				sql.append("CREATE TABLE IF NOT EXISTS ").append(name + "_extensions")
				   .append(" ( ")
				   .append("   eid SERIAL PRIMARY KEY, ")
				   .append("   name VARCHAR(100) NULL, ")
				   .append("   prefix VARCHAR(100) NULL, ")
				   .append("   uri VARCHAR(100) NULL")
				   .append(" ); ");
				try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
					st.execute();
				}
				StringBuilder row = new StringBuilder();
				row.append("INSERT INTO ").append(name + "_extensions").append(" VALUES ")
				   .append("(DEFAULT, ?, ?, ?);");
				try (PreparedStatement st = conn.prepareStatement(row.toString());) {
					for (XExtension ext : exts) {
						st.setString(1, ext.getName());
						st.setString(2, ext.getPrefix());
						st.setString(3, ext.getUri().toString());
						st.addBatch();
						st.executeBatch();
					}
				}
			}
			
			// Classifiers
			List<XEventClassifier> classifiers = log.getClassifiers();
			if (classifiers != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("DROP TABLE IF EXISTS ").append(name + "_classifiers;");
				sql.append("CREATE TABLE IF NOT EXISTS ").append(name + "_classifiers")
				   .append(" ( ")
				   .append("   eid SERIAL PRIMARY KEY, ")
				   .append("   name VARCHAR(100) NULL, ")
				   .append("   keys VARCHAR(100) NULL")
				   .append(" ); ");
				try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
					st.execute();
				}
				StringBuilder row = new StringBuilder();
				row.append("INSERT INTO ").append(name + "_classifiers").append(" VALUES ")
				   .append("(DEFAULT, ?, ?);");
				try (PreparedStatement st = conn.prepareStatement(row.toString());) {
					for (XEventClassifier classifier : classifiers) {
						st.setString(1, classifier.name());
						String list = Stream.of(classifier.getDefiningAttributeKeys())
								.reduce("", (acc, next) -> acc += next + ",");
						list = StringUtils.removeEnd(list, ",");
						st.setString(2, list);
						st.addBatch();
						st.executeBatch();
					}
				}
			}
			
			// Global Trace Attributes
			List<XAttribute> globTrcAttrs = log.getGlobalEventAttributes();
			if (globTrcAttrs != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("DROP TABLE IF EXISTS ").append(name + "_globaltraceattributes;");
				sql.append("CREATE TABLE IF NOT EXISTS ").append(name + "_globaltraceattributes")
				   .append(" ( ")
				   .append("   eid SERIAL PRIMARY KEY, ")
				   .append("   key VARCHAR(100) NULL, ")
				   .append("   value VARCHAR(100) NULL")
				   .append(" ); ");
				try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
					st.execute();
				}
				StringBuilder row = new StringBuilder();
				row.append("INSERT INTO ").append(name + "_globaltraceattributes").append(" VALUES ")
				   .append("(DEFAULT, ?, ?);");
				try (PreparedStatement st = conn.prepareStatement(row.toString());) {
					for (XAttribute attr : globTrcAttrs) {
						st.setString(1, attr.getKey());
						st.setString(2, attr.toString());
						st.addBatch();
						st.executeBatch();
					}
				}
			}
			
			// Global Event Attributes
			List<XAttribute> globEvtAttrs = log.getGlobalEventAttributes();
			if (globEvtAttrs != null) {
				StringBuilder sql = new StringBuilder();
				sql.append("DROP TABLE IF EXISTS ").append(name + "_globaleventattributes;");
				sql.append("CREATE TABLE IF NOT EXISTS ").append(name + "_globaleventattributes")
				   .append(" ( ")
				   .append("   eid SERIAL PRIMARY KEY, ")
				   .append("   key VARCHAR(100) NULL, ")
				   .append("   value VARCHAR(100) NULL")
				   .append(" ); ");
				try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
					st.execute();
				}
				StringBuilder row = new StringBuilder();
				row.append("INSERT INTO ").append(name + "_globaleventattributes").append(" VALUES ")
				   .append("(DEFAULT, ?, ?);");
				try (PreparedStatement st = conn.prepareStatement(row.toString());) {
					for (XAttribute attr : globEvtAttrs) {
						st.setString(1, attr.getKey());
						st.setString(2, attr.toString());
						st.addBatch();
						st.executeBatch();
					}
				}
			}
			
			List<List<Map<String, String>>> eventlogcols = new ArrayList<>();
			List<List<Map<String, String>>> datacols = new ArrayList<>();
			
			for (XTrace trace : log) {
				for (int i = 0; i < trace.size(); i++) {
					XEvent event = trace.get(i);
					List<Set<Map.Entry<String, XAttribute>>> colsrows = Stream.of(event.getAttributes()
							.entrySet()).collect(Collectors.toList());
					for (Set<Entry<String, XAttribute>> cols : colsrows) {
						Map<String, String> case_id = new HashMap<>();
						case_id.put("case_id", trace.getAttributes().get("concept:name").toString());
						List<Map<String, String>> ecols = cols.stream()
							.filter(keys -> "concept:name".equals(keys.getKey())
							 || "org:resource".equals(keys.getKey())
							 || "lifecycle:transition".equals(keys.getKey())
							 || "time:timestamp".equals(keys.getKey()))
							.map(keys -> {
								Map<String, String> k = new HashMap<>();
								k.put(keys.getKey(), keys.getValue().toString());
								return k;
							}).collect(Collectors.toList());
						Collections.sort(ecols, new Comparator<Map<String, String>>() {
							@Override
							public int compare(Map<String, String> o1, Map<String, String> o2) {
								String k1 = new ArrayList<>(o1.keySet()).get(0).toLowerCase();
								String k2 = new ArrayList<>(o2.keySet()).get(0).toLowerCase();
								return k1.compareTo(k2);
							}
						});
						if (ecols.size() > 0) ecols.add(0, case_id);
						boolean timestampExists = ecols.stream().filter(attr ->
							attr.containsKey("time:timestamp"))
								.collect(Collectors.toList()).size() > 0;
						if (!timestampExists) {
							Map<String, String> timestamp = new HashMap<>();
							timestamp.put("time:timestamp", String.valueOf(i + 1));
							int desiredPosition = ecols.stream().filter(attr -> attr.containsKey("concept:name")).map(attr -> ecols.indexOf(attr)).collect(Collectors.toList()).get(0);
							ecols.add(desiredPosition + 1, timestamp);
						}	
						eventlogcols.add(ecols);
						List<Map<String, String>> dcols = cols.stream()
							.filter(keys -> // !"concept:name".equals(keys.getKey())
							    !"org:resource".equals(keys.getKey())
							 && !"lifecycle:transition".equals(keys.getKey())
							 && !"time:timestamp".equals(keys.getKey()))
							.map(keys -> {
								Map<String, String> k = new HashMap<>();
								k.put(keys.getKey(), keys.getValue().toString());
								return k;
							}).collect(Collectors.toList());
						Collections.sort(dcols, new Comparator<Map<String, String>>() {
							@Override
							public int compare(Map<String, String> o1, Map<String, String> o2) {
								String k1 = new ArrayList<>(o1.keySet()).get(0).toLowerCase();
								String k2 = new ArrayList<>(o2.keySet()).get(0).toLowerCase();
								return k1.compareTo(k2);
							}
						});
						if (dcols.size() > 1) { 
							dcols.add(0, case_id);
							Map<String, String> targetClass = new HashMap<>();
							targetClass.put("class", trace.get(i+1).getAttributes().get("concept:name").toString());
							dcols.add(targetClass);
						} 
						datacols.add(dcols);
					}
				}
			}
			
			// Event log
			final StringBuilder sql = new StringBuilder();
			sql.append("DROP TABLE IF EXISTS ").append(name).append(";");
			sql.append("CREATE TABLE IF NOT EXISTS ").append(name)
			   .append(" ( ")
			   .append("   eid SERIAL PRIMARY KEY");
			List<String> colname = log.stream().flatMap(trace -> trace.stream().flatMap(event -> 
				StreamSupport.stream(((Iterable<Map.Entry<String, XAttribute>>) () -> 
					event.getAttributes().entrySet().iterator()).spliterator(), false)))
						.map(attr -> attr.getKey())
						.filter(key -> key.equals("concept:name")
						 || key.equals("org:resource") 
						 || key.equals("lifecycle:transition") 
						 || key.equals("time:timestamp"))
						.distinct().sorted()
						.collect(Collectors.toList());
			colname.add(0, "case_id");
			if (!colname.contains("time:timestamp"))
				colname.add(colname.indexOf("concept:name") + 1, "time:timestamp");
			List<Map<String, String>> colnamerow = new ArrayList<>();
			colname.stream().forEach(col -> {
				sql.append(",")
				   .append("\"").append("c" + colname.indexOf(col)).append("\"")
				   .append(" VARCHAR(255) NULL");
				Map<String, String> c = new HashMap<>();
				c.put(col, col);
				colnamerow.add(c); 
			});
			eventlogcols.add(0, colnamerow);
			sql.append(");");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				st.execute();
			}
			StringBuilder elog_comment = new StringBuilder();
			elog_comment.append("COMMENT ON TABLE ").append(name).append(" IS '").append(SerializationUtil.encode(config)).append("';");
			try (PreparedStatement st = conn.prepareStatement(elog_comment.toString());) {
				st.execute();
			}
			
			final StringBuilder sqlrows = new StringBuilder();
			sqlrows.append("INSERT INTO ").append(name).append(" VALUES ")
			   	   .append("(DEFAULT ");
			colname.stream().forEach(col -> sqlrows.append(",").append("?"));
			sqlrows.append(");");
			
			// TODO: seed data into database
			for (int i = 0; i < eventlogcols.size(); i++) {
				List<Map<String, String>> row = eventlogcols.get(i);
				if (row.size() > 0) {
					List<String> d = row.stream().map(cols -> cols.values().iterator().next())
							.collect(Collectors.toList());
					try (PreparedStatement st = conn.prepareStatement(sqlrows.toString());) {	
						for (int j = 0; j < d.size(); j++) {
							st.setString(j+1, d.get(j));
						}
						st.addBatch();
						st.executeBatch();
					}
				}
			}
			
			for (List<Map<String, String>> row : datacols) {
				String cname = row.stream().filter(keys -> 
				keys.containsKey("concept:name"))
					.map(key -> key.get("concept:name"))
					.collect(Collectors.toList()).get(0).toLowerCase().replace(" ", "");
				// Event log data
				final StringBuilder data = new StringBuilder();
				data.append("DROP TABLE IF EXISTS ").append(name + "_dataeventlog_" + cname  + ";");
				try (PreparedStatement st = conn.prepareStatement(data.toString());) {
					st.execute();
				}
			}	
			
			for (List<Map<String, String>> row : datacols) {
				if (row.size() > 2) {
					String cname = row.stream().filter(keys -> 
						keys.containsKey("concept:name"))
							.map(key -> key.get("concept:name"))
							.collect(Collectors.toList()).get(0).toLowerCase().replace(" ", "");
					// Event log data
					final StringBuilder data = new StringBuilder();
					data.append("CREATE TABLE IF NOT EXISTS ").append(name + "_dataeventlog_" + cname)
					    .append(" ( ")
					    .append("   eid SERIAL PRIMARY KEY");
					for (Map<String, String> col : row) {
						String[] keys = col.keySet().stream()
								.filter(k -> !k.equals("concept:name"))
								.toArray(String[]::new);
						for (String key : keys) {
							data.append(",")
							    .append("\"").append(key).append("\"")
							    .append(" VARCHAR(255) NULL");
						}
					}
					data.append(");");
					try (PreparedStatement st = conn.prepareStatement(data.toString());) {
						st.execute();
					}
					List<String> d = row.stream().filter(cols -> !cols.containsKey("concept:name"))
						.map(cols -> cols.values().iterator().next()).collect(Collectors.toList());
					final StringBuilder datarows = new StringBuilder();
					datarows.append("INSERT INTO ").append(name + "_dataeventlog_" + cname).append(" VALUES ")
					   	    .append("(DEFAULT ");
					for (String string : d) {
						datarows.append(",").append("?");
					}
					datarows.append(");");
					try (PreparedStatement st = conn.prepareStatement(datarows.toString());) {
						for (int i = 0; i < d.size(); i++) {
							st.setString(i+1, d.get(i));
						}
						st.addBatch();
						st.executeBatch();
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
		return result;
	}
}
