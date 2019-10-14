package io.iochord.apps.ips.model.analysis.services.ism;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model-analysis
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class IsmDiscoveryConfiguration {

	@Getter
	@Setter
	private String datasetId;

	@Getter
	@Setter
	private String colCaseId = "c0";

	@Getter
	@Setter
	private String colEventActivity = "c1";

	@Getter
	@Setter
	private String colEventTimestamp = "c121";

	@Getter
	@Setter
	private int skipRows = 1;
	
	
}
