package io.iochord.apps.ips.model.analysis.services.dtm;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class DecisionMinerConfig {

	@Getter
	@Setter
	private String schemaName;
	
	@Getter
	@Setter
	private String datasetId;

	@Getter
	@Setter
	private int pdPositiveObservationThreshold = 0;
	
	@Getter
	@Setter
	private float pdDependencyThreshold = 0.9f;
	
	@Getter
	@Setter
	private DecisionTreeStrategy strategy;
	
	@Getter
	@Setter
	private int skipRow = 1;
}
