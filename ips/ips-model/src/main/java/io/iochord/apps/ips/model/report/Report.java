package io.iochord.apps.ips.model.report;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public class Report {

	@Getter
	private final Map<String, GroupStatistics> groups = new LinkedHashMap<String, GroupStatistics>();

}
