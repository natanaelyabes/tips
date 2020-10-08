/**
 * 
 */
package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic decision miner algorithm.
 *
 * @package ips-model
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2020
 * 
 */
public class DecisionMinerAlgorithm implements DecisionMiner {
	
	@Getter @Setter
	private DecisionMinerResult result = new DecisionMinerResult();
	
	/**
	 * Tables were dropped to reset the environment. 
	 * Extension to execute PL/Python script is enabled.
	 * Then, it learns a decision tree model that best-fit the data (observation matrix).
	 * Also, two UDF to predict decision and its target class probability given input is created 
	 * based upon the learned model.
	 * 
	 * @param context
	 * @param config
	 * @param branches
	 * @param result
	 */
	public DecisionMinerAlgorithm(ServiceContext context, DecisionMinerConfig config, List<Node> branches, DecisionMinerResult result) {
		this.result = result;
		StringBuilder q = new StringBuilder();
		String splittedId[] = config.getDatasetId().split("_");
		String datasetId = splittedId[0] + "_" + splittedId[1] + "_" + splittedId[2];
		q.append("-- Clear environment\n");
		q.append("DROP TABLE IF EXISTS ").append(datasetId).append("_").append("dt_model;\n");
		q.append("DROP FUNCTION IF EXISTS ").append(datasetId).append("_").append("predict_dt;\n");
		q.append("\n"); // Requires PL/Python to be enabled during DB installation.
		q.append("-- Enable extensions\n");
		q.append("CREATE EXTENSION IF NOT EXISTS plpython3u;\n");
		q.append("\n"); // Create UDFs.
		q.append(predictDecisionTreeUDF(context, config, q));
		q.append(predictProbabilityDecisionTreeUDF(context, config, q));
		try (Connection conn = context.getDataSource().getConnection()) {
			String query = q.toString();
			try (PreparedStatement st = conn.prepareStatement(query)) {
				st.execute();
			}
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
		branches.stream().forEach(node -> getInputOutputNodes(context, config, node, q));
	}

	/**
	 * Returns pair of input and output nodes of a branch.
	 * 
	 * @param context
	 * @param config
	 * @param node
	 * @param q
	 */
	private void getInputOutputNodes(ServiceContext context, DecisionMinerConfig config, Node node, StringBuilder q) {
		q.setLength(0);
		Branch branch = (Branch) node; // Retrieve the branch node object.
		Supplier<Stream<Entry<String, Page>>> pageEntrySet = () -> result.getProcessmodel().getPages().entrySet().stream();
		Stream<String> pageKeys = pageEntrySet.get().map(page -> page.getKey());
		Supplier<Stream<Page>> pages = () -> pageEntrySet.get().map(page -> page.getValue());
		Supplier<Stream<Map<String, Node>>> nodes = () -> pages.get().map(page -> page.getNodes());
		Supplier<Stream<Map<String, Connector>>> connectors = () -> pages.get().map(page -> page.getConnectors());
		List<Referenceable<Element>> inputReference = connectors.get().map(collection -> collection.values().stream()
				// Retrieve the connector, the target node of which is matched with the current branch.
				.filter(connector -> branch.getId().equals(connector.getTarget().getId()))
				// Retrieve the source node of this connector.
				.map(connector -> connector.getSource()).collect(Collectors.toList()))
				.flatMap(Collection::stream).collect(Collectors.toList());
		Stream<Node> inputNode = nodes.get().map(collection -> collection.values().stream()
				// Match the node with input references.
				.filter(node2 -> inputReference.stream().map(input -> input.getId())
						.collect(Collectors.toList()).contains(node2.getId()))
				.collect(Collectors.toList())).flatMap(Collection::stream);
		List<Referenceable<Element>> outputReference = connectors.get().map(collection -> collection.values().stream()
				// Retrieve the connector, the source node of which is matched with the current branch.
				.filter(connector -> branch.getId().equals(connector.getSource().getId()))
				// Retrieve the target node of these connectors.
				.map(connector -> connector.getTarget()).collect(Collectors.toList()))
				.flatMap(Collection::stream).collect(Collectors.toList());
		Stream<Node> outputNodes = nodes.get().map(collection -> collection.values().stream()
				// Match the node with the output references.
				.filter(node2 -> outputReference.stream().map(output -> output.getId())
						.collect(Collectors.toList()).contains(node2.getId()))
				.collect(Collectors.toList())).flatMap(Collection::stream);
		List<Node> input = inputNode.collect(Collectors.toList());
		List<Node> output = outputNodes.collect(Collectors.toList());
		getObservationMatrix(context, config, input, output, q);
	}

	/**
	 * User-Defined-Function (UDF) to predict outcome based on
	 * an input (feature tuple) using decision tree classifier.
	 * 
	 * <<USAGE>>
	 * 
	 * -- CALL predict_dt example. INPUT: [amount, status, policytype], OUTPUT: {Issue payment, Send approval letter}
	 * SELECT ips_dataset_1598329190376_predict_dt('ips_dataset_1598329190376_dt_model', 'model', 'Evaluate claim', ARRAY[[500.0, 0, 0]]);
	 * 
	 * -- CALL predict_dt example: INPUT: [amount, status, policytype], OUTPUT: {Send rejection letter}
	 * SELECT ips_dataset_1598329190376_predict_dt('ips_dataset_1598329190376_dt_model', 'model', 'Evaluate claim', ARRAY[[50.0, 1, 0]]);
	 * 
	 * -- INPUT columns are lexically ordered.
	 * 
	 * [param] model_table
	 * [param] model_colname
	 * [param] model_name
	 * [param] input_val
	 * 
	 * @param context
	 * @param config
	 * @param q
	 * @return 
	 */
	private StringBuilder predictDecisionTreeUDF(ServiceContext context, DecisionMinerConfig config, StringBuilder q) {
		String splittedId[] = config.getDatasetId().split("_");
		String datasetId = splittedId[0] + "_" + splittedId[1] + "_" + splittedId[2];
		q.append("CREATE OR REPLACE FUNCTION ").append(datasetId).append("_").append("predict_dt(model_table text, model_colname text, "
				+ "model_name varchar, input_val real[])\n");
		q.append("RETURNS varchar[] AS $$\n");
		q.append("    from _pickle import loads\n");
		q.append("\n");
		q.append("    resultset = plpy.execute('SELECT %s FROM %s WHERE ea = %s;' % (plpy.quite_ident(model_colname), "
							   + "plpy.quote_ident(model_table), plpy.quote_literal(model_name)))\n");
		q.append("    model = loads(resultset[0][model_colname])\n");
		q.append("    prediction = model.predict(input_val)\n");
		q.append("    plpy.info(prediction)\n");
		q.append("    return prediction\n");
		q.append("$$ LANGUAGE plpython3u;\n");
		q.append("\n");
		return q;
	}
	
	/**
	 * User-Defined-Function (UDF) to predict outcome probability
	 * based on input (feature tuple) using decision tree classifier.
	 * 
	 * [param] model_table
	 * [param] model_colname
	 * [param] model_name
	 * [param] input_val
	 * 
	 * @param context
	 * @param config
	 * @param q
	 * @return
	 */
	private StringBuilder predictProbabilityDecisionTreeUDF(ServiceContext context, DecisionMinerConfig config,
			StringBuilder q) {
		String splittedId[] = config.getDatasetId().split("_");
		String datasetId = splittedId[0] + "_" + splittedId[1] + "_" + splittedId[2];
		q.append("CREATE OR REPLACE FUNCTION ").append(datasetId).append("_").append("predict_prob_dt(model_table text, model_colname text, "
				+ "model_name varchar, input_val real[])\n");
		q.append("RETURNS varchar[] AS $$\n");
		q.append("    from _pickle import loads\n");
		q.append("\n");
		q.append("    resultset = plpy.execute('SELECT %s FROM %s WHERE ea = %s;' % (plpy.quite_ident(model_colname), "
							   + "plpy.quote_ident(model_table), plpy.quote_literal(model_name)))\n");
		q.append("    model = loads(resultset[0][model_colname])\n");
		q.append("    prediction = model.predict_proba(input_val)\n");
		q.append("    plpy.info(prediction)\n");
		q.append("    return prediction\n");
		q.append("$$ LANGUAGE plpython3u;\n");
		q.append("\n");
		return q;
	}
	
	/**
	 * Returns observation matrix from event data.
	 * 
	 * [void]
	 * 
	 * @param context
	 * @param config
	 * @param input
	 * @param output
	 * @param q 
	 */
	private void getObservationMatrix(ServiceContext context, DecisionMinerConfig config, List<Node> input,
			List<Node> output, StringBuilder q) {
		q.append("--------------------------------------------------------------\n");
		q.append("-- Transform event log into observation matrix.\n");
		q.append("--\n");
		q.append("-- [void]\n");
		q.append("------\n");
		q.append("DO $$ -- Declare PL/SQL block & variable initialization\n");
		q.append("DECLARE casemapping       varchar(2)[];            -- Mapping of colnames.\n");
		q.append("DECLARE colnames          varchar(255)[];          -- Original column names.\n");
		q.append("DECLARE datasetid         varchar(255);            -- The dataset identifier to query from.\n");
		q.append("DECLARE narowscount       integer;                 -- Count of null values for each colnames.\n");
		q.append("DECLARE prefix            varchar;                 -- Random prefix used to build the query sentence.\n");
		q.append("DECLARE querysentence     text;                    -- The query to execute.\n");
		q.append("DECLARE rowscount         integer;                 -- Size of the event log.\n");
		q.append("DECLARE schemaname        varchar(255);            -- The dataset schema name to refer from.\n");
		q.append("BEGIN -- Convert event log into observation matrix (n-tuples of independent variables and label attribute).\n");
		q.append("    schemaname := '" + config.getSchemaName() + "';\n");
		q.append("    datasetid := '" + config.getDatasetId() + "';\n");
		q.append("    casemapping := array['ci', 'ea', 'ec', 'eo', 'er', 'es', 'et'];\n");
		q.append("    EXECUTE format('SELECT array_agg(column_name::varchar)\n");
		q.append("            FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = ''%s'' AND column_name != ''eid''; ', datasetid)\n");
		q.append("    INTO colnames;\n");
		q.append("    prefix := ''; querysentence := '';\n");
		q.append("    FOR i IN 1 .. array_upper(colnames, 1) LOOP\n");
		q.append("        EXECUTE format('SELECT COUNT(*) FROM %s', schemaname || '.' || datasetid) INTO rowscount;\n");
		q.append("        EXECUTE format('SELECT COUNT(\"%s\") FROM %s WHERE \"%s\" IS NULL OR \"%s\" = '''';', \n");
		q.append("                colnames[i], schemaname || '.' || datasetid, colnames[i], colnames[i])\n");
		q.append("        INTO narowscount;\n");
		q.append("        IF rowscount <> narowscount THEN  -- Exclude N/A valued columns.\n");	
		q.append("            IF NOT colnames[i] = ANY(casemapping) THEN\n");
		q.append("                RAISE NOTICE '%', colnames[i];\n");
		q.append("                querysentence := querysentence || prefix || 'a.' || colnames[i];\n");
		q.append("                prefix := ', ';\n");
		q.append("            END IF;\n");
		q.append("        END IF;\n");
		q.append("    END LOOP;\n");
		q.append("    RAISE NOTICE '%', querysentence; -- Show non-null colnames.\n");
		q.append("    prefix := ''; prefix := querysentence;\n");
		q.append("    querysentence := 'SELECT ' || querysentence || ', array_to_string(ARRAY[array_agg(b.ea ORDER BY b.ea)], '', '') AS label' || E'\\n';\n");
		q.append("    querysentence := querysentence || 'FROM ' || schemaname || '.' || datasetid || ' AS a' || E'\\n';\n");
		q.append("    querysentence := querysentence || 'LEFT JOIN ' || schemaname || '.' || datasetid || ' AS b' || E'\\n';\n");
		q.append("    querysentence := querysentence || 'ON a.ci = b.ci' || E'\\n'; -- Do self-join to match event name with possible execution sequences.\n");
		q.append("    querysentence := querysentence || 'WHERE a.ea = ''" + input.get(0).getLabel() + "''' || E'\\n'; -- Input reference.\n");
		q.append("    querysentence := querysentence || 'AND (' || E'\\n'; -- Output references. \n");
		String prefix = ""; for (int i = 0; i < output.size(); i++) {
			String ea = output.get(i).getLabel();
			q.append("    querysentence := querysentence || '" + prefix + "b.ea = ''"+ ea +"''' || E'\\n';\n");
			prefix = "OR ";
		}
		q.append("    querysentence := querysentence || ')' || E'\\n';\n");
		q.append("    querysentence := querysentence || 'GROUP BY ' || prefix || ';';\n");
		q.append("    RAISE NOTICE '%', querysentence; -- Show complete query sentence.\n");
		q.append("    DROP TABLE IF EXISTS observation;\n");
		q.append("    EXECUTE format('CREATE TEMPORARY TABLE observation AS %s;', querysentence);\n");
		q.append("END; $$;\n\n");
		inferDecisionTree(context, config, input, output, q);
	}

	/* (non-Javadoc)
	 * @see io.iochord.apps.ips.model.analysis.services.dtm.DecisionMiner#inferDecisionTree(io.iochord.apps.ips.core.services.ServiceContext, io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerConfig, java.lang.StringBuilder)
	 */
	@Override
	public void inferDecisionTree(ServiceContext context, DecisionMinerConfig config, List<Node> input, List<Node> output, StringBuilder q) {
		q.append("--------------------------------------------------------------\n");
		q.append("-- Decision tree algorithm to infer model from observation matrix.\n");
		q.append("--\n");
		q.append("-- [void]\n");
		q.append("------\n");
		q.append("DO $$\n");
		q.append("    from sklearn.model_selection import train_test_split\n");
		q.append("    from sklearn.tree import DecisionTreeClassifier\n");
		q.append("    from sklearn.tree import export_graphviz\n");
		q.append("    from sklearn.tree import export_text\n");
		q.append("    from sklearn import metrics\n");
		q.append("    from _pickle import dumps\n");
		q.append("    import pandas as pd\n");
		q.append("    import numpy as np\n");
		q.append("\n");
		q.append("    criterion = '" + config.getStrategy().toString().toLowerCase() + "'\n");
		q.append("    splitter = '" + config.getSplitter().toString().toLowerCase() + "'\n");
		q.append("    max_depth = " + config.getMaxDepth() != null ? config.getMaxDepth() : "None" + "\n");
		q.append("    dt_random_state = " + config.getRandomState() != null ? config.getRandomState() : "None" + "\n");
		q.append("    test_size = " + config.getTestSize() + "\n");
		q.append("    train_test_random_state = " + config.getTrainTestRandomState() + "\n");
		q.append("\n");
		q.append("    def castcoltype(obs):\n");
		q.append("        for colname in obs.columns:\n");
		q.append("            try:\n");
		q.append("                obs[colname] = obs[colname].astype(float)\n");
		q.append("            except ValueError:\n");
		q.append("                obs[colname] = obs[colname]\n");
		q.append("            if (obs[colname] == '').all():\n");
		q.append("                del obs[colname]\n");
		q.append("        return obs\n");
		q.append("\n");
		q.append("    def inducedt(obs, feature_cols):\n");
		q.append("        X = pd.get_dummies(obs[feature_cols],drop_first=True)\n");
		q.append("        plpy.info(X)\n"); // one-hot encoded feature matrix.
		q.append("        y = obs.label\n");
		q.append("        plpy.info(y)\n");
		q.append("        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=test_size,\n");
		q.append("                                                            random_state=train_test_random_state)\n");
		q.append("        clf = DecisionTreeClassifier(criterion=criterion, splitter=splitter,\n");
		q.append("                                     max_depth=max_depth, random_state=dt_random_state)\n");
		q.append("        clf = clf.fit(X_train,y_train)\n");
		q.append("        y_pred = clf.predict(X_test)\n");
		q.append("        acc = metrics.accuracy_score(y_test, y_pred)\n");
		q.append("        plpy.info(\"Accuracy:\", acc)\n");
		q.append("        createtable  = 'CREATE TABLE IF NOT EXISTS " + config.getSchemaName() + "." + config.getDatasetId() + "_dt_model ('\n");
		q.append("        createtable += '    ea VARCHAR(255),'\n");
		q.append("        createtable += '    model BYTEA NOT NULL,'\n");
		q.append("        createtable += '    accuracy NUMERIC'\n");
		q.append("        createtable += ');'\n");
		q.append("        plpy.execute(createtable)\n");
		q.append("        querysentence  = 'INSERT INTO " + config.getSchemaName() + "." + config.getDatasetId() + "_dt_model(ea, model, accuracy) \\n'\n");
		q.append("        querysentence += 'SELECT \\'" + input.get(0).getLabel() + "\\' AS ea, $1 AS model , $2 AS accuracy;'\n");
		q.append("        queryplan = plpy.prepare(querysentence, ['BYTEA', 'numeric'])\n");
		q.append("        plpy.execute(queryplan, [dumps(clf), acc])\n");
		q.append("        r = export_graphviz(clf, feature_names=feature_cols, class_names=y.unique(), filled=True, rounded=True, special_characters=True)\n");
		q.append("        return r\n");
		q.append("\n");
		q.append("    obs = pd.DataFrame.from_records(plpy.execute('SELECT * FROM observation'))\n");
		q.append("    obs = castcoltype(obs)\n");
		q.append("    feature_cols = obs.columns.tolist()\n");
		q.append("    feature_cols.remove('label')\n");
		q.append("    s = inducedt(obs, feature_cols)\n");
		q.append("    plpy.execute('DROP TABLE IF EXISTS r;')\n");
		q.append("    plpy.execute('CREATE TEMPORARY TABLE r AS SELECT ea, accuracy, %s AS rule FROM " + config.getSchemaName() + "." + config.getDatasetId() + "_dt_model "
				+ "WHERE ea = %s;' % (plpy.quote_literal(s), plpy.quote_literal('" + input.get(0).getLabel() + "')))\n");
		q.append("$$ LANGUAGE plpython3u;\n");
		q.append("\n");
		execQuery(context, config, q);
	}

	/**
	 * Execute query and return decision rules.
	 * 
	 * @param context
	 * @param config
	 * @param q
	 */
	private void execQuery(ServiceContext context, DecisionMinerConfig config, StringBuilder q) {
		try (Connection conn = context.getDataSource().getConnection()) {
			String procedure = q.toString();
			try (Statement st = conn.createStatement()) {
				st.execute(procedure);
			}
			q.setLength(0);
			q.append("-- Return the decision rules.\n");
			q.append("SELECT * FROM r LIMIT 1;");
			String query = q.toString();
			try (PreparedStatement st = conn.prepareStatement(query)) {
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					DecisionRule rule = new DecisionRule();
					String ea = rs.getString(1);
					double ac = rs.getDouble(2);
					String ru = rs.getString(3);
					rule.setEventName(ea);
					rule.setModelAccuracy(ac);
					rule.setDecisionRule(ru);
					result.getRule().add(rule);
				}
				rs.close();
			}
		} catch (Exception e) {
			LoggerUtil.logError(e);
		}
	}
}
