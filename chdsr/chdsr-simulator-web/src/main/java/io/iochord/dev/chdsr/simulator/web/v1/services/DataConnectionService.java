package io.iochord.dev.chdsr.simulator.web.v1.services;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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
	public CompletableFuture<String> importCsv(String name, String filename, InputStreamReader reader) {
		try {
			Connection conn = getDataSource().getConnection();
			CSVReader csvReader = new CSVReaderBuilder(reader)
				.withCSVParser(
					new CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build();
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
					sql.append("COMMENT ON TABLE ").append(name).append(" IS '").append(filename).append("';");
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
						getWsmTemplate().convertAndSend("/response/test2", rows + " / ");
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
			getWsmTemplate().convertAndSend("/response/test3", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(name);
	}

	@Async(ServiceExecutor.NAME)
	public CompletableFuture<String> getTest() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				getWsmTemplate().convertAndSend("/response/test", "OK");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		getWsmTemplate().convertAndSend("/response/complete", "OK");
		return CompletableFuture.completedFuture("OK");
	}
}
