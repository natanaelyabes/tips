package io.iochord.apps.ips.model.analysis.services.dtm;

import java.util.List;

import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class DecisionMinerResult {
	
	@Getter @Setter
	private IsmGraph processmodel;
	
	@Getter @Setter
	private List<DecisionRule> rule;
}
