/**
 * 
 */
package io.iochord.apps.ips.model.analysis.services.dtm;

import lombok.Getter;
import lombok.Setter;

/**
 * @author natan
 *
 */
public class DecisionRule {
	
	@Getter @Setter
	private String eventName;
	
	@Getter @Setter
	private double modelAccuracy;
	
	@Getter @Setter
	private String decisionRule;
}
