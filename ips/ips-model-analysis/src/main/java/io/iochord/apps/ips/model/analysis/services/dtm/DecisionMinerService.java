package io.iochord.apps.ips.model.analysis.services.dtm;

import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Random;

import javax.swing.JFrame;

import org.apache.commons.io.IOUtils;

import com.opencsv.CSVWriter;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.ArffLoader.ArffReader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.core.converters.CSVLoader;

public class DecisionMinerService extends AnIpsAsyncService<DecisionMinerConfig, DecisionMinerResult> {

	@Override
	public DecisionMinerResult run(ServiceContext context, DecisionMinerConfig config) {
		try (Connection conn = context.getDataSource().getConnection();) {
			// Get from configuration
			String datasetId = config.getDatasetId();
			// Skeleton to run SQL
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM public.ips_dataset_1583912025620_dataeventlog_registerclaim");
			
			StringBuilder csv = new StringBuilder(); 
			String prefix = "";
			
			try (PreparedStatement st = conn.prepareStatement(sql.toString())) {
				try (ResultSet rs = st.executeQuery();) {
					ResultSetMetaData rsmt = rs.getMetaData();
					
					for (int i = 1; i <= rsmt.getColumnCount(); i++) {
						String colname = rsmt.getColumnName(i);
						csv.append(prefix).append(colname);
						prefix = ",";
					}
					
					csv.append("\n");
					
					while (rs.next()) {
						prefix = "";
						for (int i = 1; i <= rsmt.getColumnCount(); i++) {
							csv.append(prefix).append(rs.getString(i));
							prefix = ",";
						}
						csv.append("\n");
					}
				}
			}
			
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\test";
			
			FileWriter writer = new FileWriter(path.concat(".csv"));
			writer.write(csv.toString());
			writer.flush();
			writer.close();
			
			CSVLoader csvloader = new CSVLoader();
			csvloader.setSource(new File(path.concat(".csv")));
			
			Instances data = csvloader.getDataSet();
			
			ArffSaver arffsaver = new ArffSaver();
			arffsaver.setInstances(data);
			arffsaver.setFile(new File(path.concat(".arff")));
			arffsaver.writeBatch();
			
			ArffLoader arffloader = new ArffLoader();
			arffloader.setFile(new File(path.concat(".arff")));
			
			Instances arff = arffloader.getDataSet();
			arff.setClassIndex(arff.numAttributes()-1);
			
			// Filtering
			String[] options = new String[2];
			options[0] = "-R";
			options[1] = "1-2";
			Remove remove = new Remove();
			remove.setOptions(options);
			remove.setInputFormat(arff);
			Instances filtered = Filter.useFilter(arff, remove);
			
			// Build classifier
			String[] options1 = new String[1];
			options1[0] = "-U";
			J48 tree = new J48();
			tree.setOptions(options1);
			tree.buildClassifier(filtered);
			
//			// Evaluation method
//			Evaluation eval = new Evaluation(filtered);
//			eval.crossValidateModel(tree, filtered, 10, new Random(1));
			
			System.out.println(tree.graph());
			
			// Build the result
			DecisionMinerResult result = new DecisionMinerResult();
			result.setConfig(config);
			return result;
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return null;
	}

}
