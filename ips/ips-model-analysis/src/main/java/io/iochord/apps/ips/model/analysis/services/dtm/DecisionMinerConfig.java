package io.iochord.apps.ips.model.analysis.services.dtm;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 * 
 */
public class DecisionMinerConfig {

	@Getter
	@Setter
	private String schemaName = "public";
	
	@Getter
	@Setter
	private String datasetId = null;

	@Getter
	@Setter
	private int pdPositiveObservationThreshold = 0;
	
	@Getter
	@Setter
	private float pdDependencyThreshold = 0.9f;
	
	@Getter
	@Setter
	private DecisionTreeStrategy strategy = DecisionTreeStrategy.GINI;
	
	@Getter
	@Setter
	private DecisionTreeSplitter splitter = DecisionTreeSplitter.BEST;
	
	@Getter
	@Setter
	private int maxDepth;
	
	@Getter
	@Setter
	private int randomState;
	
	@Getter
	@Setter
	private int trainTestRandomState;
	
	@Getter
	@Setter
	private float testSize = 0.3f;
	
	@Getter
	@Setter
	private int skipRow = 1;
}
