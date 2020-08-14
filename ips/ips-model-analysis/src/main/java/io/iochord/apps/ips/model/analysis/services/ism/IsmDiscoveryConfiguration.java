package io.iochord.apps.ips.model.analysis.services.ism;

import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class IsmDiscoveryConfiguration {

	@Getter
	@Setter
	private String datasetId;

	@Getter
	@Setter
	private String colCaseId = "ci";

	@Getter
	@Setter
	private String colEventActivity = "ea";

	@Getter
	@Setter
	private String colEventTimestamp = "es";

	@Getter
	@Setter
	private int skipRows = 1;

	@Getter
	@Setter
	private int positiveObservationThreshold = 0;
	
	@Getter
	@Setter
	private float dependencyThreshold = 0.9f;
	
}
