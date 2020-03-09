package io.iochord.apps.ips.model.services.data.im.csv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class CsvDataImportService extends AnIpsAsyncService<CsvDataImportConfiguration, CsvDataImportResult> {
	
	@Override
	public CsvDataImportResult run(ServiceContext context, CsvDataImportConfiguration config) {
		String name = Dataset.TABLE_PREFIX + context.getId();
		CsvDataImportResult result = new CsvDataImportResult();
		result.setConfig(config);
		result.setName(name);
		try (Connection conn = context.getDataSource().getConnection();) {
			CSVReader csvReader = new CSVReaderBuilder(config.getReader())
				.withCSVParser(
					new CSVParserBuilder()
					.withSeparator(config.getDelimeter())
					.build())
				.build();
			for (int i = 0; i < config.getHeaderRowIdx(); i++) {
				csvReader.readNext();
			}
			String[] cells = csvReader.readNext();
			int rows = 0;
			if (cells != null) {
				int cols = cells.length;
				StringBuilder sql = new StringBuilder();
				StringBuilder isql = new StringBuilder();
				sql.append("CREATE TABLE ").append(name).append(" ( eid SERIAL PRIMARY KEY ");
				isql.append("INSERT INTO ").append(name).append(" VALUES ( DEFAULT ");
				for (int i = 0; i < cols; i++) {
					sql.append(",");
					isql.append(",");
					sql.append("c").append(i).append(" VARCHAR(255) NULL");
					isql.append("?");
				}
				sql.append(");");
				isql.append(");");
				try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
					st.execute();
				}
				sql = new StringBuilder();
				sql.append("COMMENT ON TABLE ").append(name).append(" IS '").append(SerializationUtil.encode(config)).append("';");
				try (PreparedStatement st = conn.prepareStatement(sql.toString());) {
					st.execute();
				}
				insertData(context, csvReader, conn, isql, cells, rows, cols);
			}
			csvReader.close();
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return result;
	}

	private void insertData(ServiceContext context, CSVReader csvReader, Connection conn, StringBuilder isql, String[] cells, int rows, int cols) throws SQLException, IOException {
		try (PreparedStatement st = conn.prepareStatement(isql.toString());) {
			int brows = 0;
			while (cells != null) {
				for (int i = 0; i < cols; i++) {
					st.setString(i + 1, i < cells.length ? cells[i] : null);
				}
				st.addBatch();
				if (brows % 1000 == 0) {
					context.updateProgress(rows);
					st.executeBatch();
					brows = 0;
				}
				cells = csvReader.readNext();
				rows++;
				brows++;
			}
			if (brows > 0) {
				st.executeBatch();
			}
		}
	}
}
