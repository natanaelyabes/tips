package io.iochord.apps.ips.data.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.iochord.apps.ips.data.services.models.ImportCsvConfiguration;
import io.iochord.apps.ips.data.services.models.ImportCsvResult;
import io.iochord.apps.ips.services.AService;
import io.iochord.apps.ips.services.ServiceExecutor;
import io.iochord.apps.ips.services.ServiceState;
import io.iochord.apps.ips.util.SerializationUtil;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@Service
public class DataConnectionService extends AService {
	
	@Async(ServiceExecutor.NAME)
	public CompletableFuture<ImportCsvResult> importCsv(ImportCsvConfiguration config, ServiceState state) {
		String name = "dataset_" + state.getSessionId();
		ImportCsvResult result = new ImportCsvResult();
		result.setConfig(config);
		result.setName(name);
		try {
			Connection conn = getDataSource().getConnection();
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
				try {
					PreparedStatement st = conn.prepareStatement(sql.toString());
					st.execute();
					st.close();
					sql = new StringBuilder();
					sql.append("COMMENT ON TABLE ").append(name).append(" IS '").append(SerializationUtil.encode(config)).append("';");
					st = conn.prepareStatement(sql.toString());
					st.execute();
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				PreparedStatement st = conn.prepareStatement(isql.toString());
				int brows = 0;
				while (cells != null) {
					for (int i = 0; i < cols; i++) {
						st.setString(i + 1, i < cells.length ? cells[i] : null);
					}
					st.addBatch();
					if (brows % 1000 == 0) {
						getWsmTemplate().convertAndSend(state.getProgressWsUri(), rows + " / ");
						st.executeBatch();
						st.close();
						st = conn.prepareStatement(isql.toString());
						brows = 0;
					}
					cells = csvReader.readNext();
					rows++;
					brows++;
				}
				if (brows > 0) {
					st.executeBatch();
				}
				st.close();
			}
			csvReader.close();
			conn.close();
			state.setStatus(ServiceState.STATE.COMPLETED);
			getWsmTemplate().convertAndSend(state.getCompleteWsUri(), result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(result);
	}

}
