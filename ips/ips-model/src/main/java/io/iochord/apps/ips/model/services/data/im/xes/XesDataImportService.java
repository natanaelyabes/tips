package io.iochord.apps.ips.model.services.data.im.xes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.extension.XExtension;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XLog;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.LoggerUtil;
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
			// TODO: split between event log and data attributes, then put into database table.
			
			XLog log = config.getLog();
			
			String suffix = log.getAttributes().get("concept:name").toString();
			// suffix = "_" + suffix;
			String name = Dataset.TABLE_PREFIX /** + context.getId() */ + suffix;
			
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
			
			final StringBuilder sql = new StringBuilder();
			sql.append("DROP TABLE IF EXISTS ").append(name + "_eventlog;");
			sql.append("CREATE TABLE IF NOT EXISTS ").append(name + "_eventlog")
			   .append(" ( ")
			   .append("   eid SERIAL PRIMARY KEY,")
			   .append("   case_id VARCHAR(255) NULL");
			log.stream().flatMap(trace -> trace.stream().flatMap(event -> 
				StreamSupport.stream(((Iterable<Map.Entry<String, XAttribute>>) () -> 
					event.getAttributes().entrySet().iterator()).spliterator(), false)))
						.map(attr -> attr.getKey())
						.filter(key -> key.equals("concept:name")
							 || key.equals("org:resource") 
							 || key.equals("lifecycle:transition") 
							 || key.equals("time:timestamp")).distinct().sorted()
						.forEach(col ->
							sql.append(",")
							   .append("\"").append(col).append("\"")
							   .append(" VARCHAR(255) NULL"));
			sql.append(");");
			try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
				st.execute();
			}
			final StringBuilder sqlrows = new StringBuilder();
			sqlrows.append("INSERT INTO ").append(name + "_eventlog").append(" VALUES ")
			   	   .append("(DEFAULT ");
			log.stream().flatMap(trace -> trace.stream().flatMap(event -> 
				StreamSupport.stream(((Iterable<Map.Entry<String, XAttribute>>) () 
					-> event.getAttributes().entrySet().iterator()).spliterator(), false)))
						.map(attr -> attr.getKey())
						.filter(key -> key.equals("concept:name")
							 || key.equals("org:resource") 
							 || key.equals("lifecycle:transition") 
							 || key.equals("time:timestamp")).distinct().sorted()
						.forEach(col -> sqlrows.append(",").append("?"));
			sqlrows.append(");");
			
			try (PreparedStatement st = conn.prepareStatement(sqlrows.toString());) {
				// TODO: seed data into database
				log.stream().flatMap(trace -> trace.stream().flatMap(event -> 
					StreamSupport.stream(((Iterable<Map.Entry<String, XAttribute>>) () 
						-> event.getAttributes().entrySet().iterator()).spliterator(), false)))
							.forEach(event -> System.out.println(event.getValue()));
			}
			
			final StringBuilder data = new StringBuilder();
			data.append("DROP TABLE IF EXISTS ").append(name + "_dataeventlog;");
			data.append("CREATE TABLE IF NOT EXISTS ").append(name + "_dataeventlog")
			   .append(" ( ")
			   .append("   eid SERIAL PRIMARY KEY");
			log.stream().flatMap(trace -> trace.stream().flatMap(event -> 
				StreamSupport.stream(((Iterable<Map.Entry<String, XAttribute>>) () -> 
					event.getAttributes().entrySet().iterator()).spliterator(), false)))
						.map(attr -> attr.getKey())
						.filter(key -> !key.equals("concept:name")
							 && !key.equals("org:resource") 
							 && !key.equals("lifecycle:transition") 
							 && !key.equals("time:timestamp")).distinct().sorted()
						.forEach(col ->
							data.append(",")
							    .append("\"").append(col).append("\"")
							    .append(" VARCHAR(255) NULL"));
			data.append(");");
			try (PreparedStatement st = conn.prepareStatement(data.toString());) {
				st.execute();
			}
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
		return result;
	}
}
