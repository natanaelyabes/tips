package io.iochord.apps.ips.model.analysis.services.dtm;

import java.util.List;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.Node;

public interface DecisionMiner {
	
	/**
	 * Inheritable decision tree algorithm. Override this method to implement custom algorithm.
	 * For example: DecisionMinerAlgorithmWithLOOCV, DecisionMinerAlgorithmWithJSONOutput, etc.
	 * 
	 * @param context
	 * @param config
	 * @param q
	 */
	public void inferDecisionTree(ServiceContext context, DecisionMinerConfig config, List<Node> input, List<Node> output, StringBuilder q);
}
