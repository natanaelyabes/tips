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
	private String datasetId;

	@Getter
	@Setter
	private DecisionTreeStrategy strategy;
}
