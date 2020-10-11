package io.iochord.apps.ips.model.report;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class Report {

	@Getter
	@Setter
	private String modelId;

	@Getter
	@Setter
	private String modelHash;

	@Getter
	@Setter
	private String replayId;
	
	@Getter
	private final Map<String, GroupStatistics> groups = new LinkedHashMap<>();

}
