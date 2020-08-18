package io.iochord.apps.ips.model.analysis.services.dtm;

import io.iochord.apps.ips.model.analysis.services.dtm.model.DecisionEdge;
import io.iochord.apps.ips.model.analysis.services.dtm.model.DecisionNode;
import io.iochord.apps.ips.model.analysis.services.dtm.model.DecisionTree;
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
	private DecisionTree<DecisionEdge, DecisionNode> tree;
}
