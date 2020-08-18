package io.iochord.apps.ips.model.analysis.services.dtm.model;

import lombok.Getter;
import lombok.Setter;

public class DecisionEdge {
	
	/**
	 * Set and return the label of decision node.
	 * 
	 * @param label The label of decision node.
	 * @return The label for decision node.
	 */
	@Getter @Setter private String label;
	
	public DecisionEdge(String label) {
		this.label = label;
	}
}
