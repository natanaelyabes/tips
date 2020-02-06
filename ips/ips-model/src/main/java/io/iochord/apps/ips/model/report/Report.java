package io.iochord.apps.ips.model.report;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class Report {

	@Getter
	private final Map<String, GroupStatistics> groups = new LinkedHashMap<String, GroupStatistics>();

}
