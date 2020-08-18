package io.iochord.apps.ips.model.analysis.services.dtm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 *
 */
public class ChanceNode {
	
	/**
	 * Assign label to a chance node.
	 * 
	 * @param label The name of the label for a chance node.
	 * @return The label assigned to the chance node.
	 */
	@Getter @Setter private String label;
	
	public ChanceNode(String label) {
		this.label = label;
	}
}
