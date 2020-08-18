package io.iochord.apps.ips.model.analysis.services.dtm.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 *
 */
public class DecisionNode {
	
	/**
	 * Set and return the label of decision node.
	 * 
	 * @param label The label of decision node.
	 * @return The label for decision node.
	 */
	@Getter @Setter private String label;
	
	/**
	 * Set and return the statistics of class occurrences.
	 * 
	 * @param statistics The statistics for decision node.
	 * @return The number of class occurrences found in a decision node.
	 */
	@Getter @Setter private Map<String, Double> statistics;
	
	public DecisionNode(String label) {
		this.label = label;
		statistics = new HashMap<>();
	}
}
