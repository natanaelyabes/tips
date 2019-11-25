package io.iochord.apps.ips.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class GroupStatistics {

	public GroupStatistics() {
	}

	public GroupStatistics(String name) {
		setName(name);
	}
	
	@Getter
	@Setter
	private String name = "";

	@Getter
	private final Map<String, ElementStatistics> elements = new LinkedHashMap<String, ElementStatistics>();

}
