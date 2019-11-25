package io.iochord.apps.ips.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ElementStatistics {

	public ElementStatistics() {
		subElements = null;
	}
	
	public ElementStatistics(String name, String type, String description, Long count, double total, double min, double max) {
		setName(name);
		setType(type);
		setDescription(description);
		setCount(count);
		setTotal(total);
		setAverage(total == 0 ? total : total / count);
		setMin(min);
		setMax(max);
		subElements = null;
	}
	
	public ElementStatistics(String name, String type) {
		setName(name);
		setType(type);
		subElements = new LinkedHashMap<String, ElementStatistics>();
	}

	public ElementStatistics(String description, long count, double total, double min, double max) {
		setName("");
		setType("");
		setDescription(description);
		setCount(count);
		setTotal(total);
		setAverage(total == 0 ? total : total / count);
		setMin(min);
		setMax(max);
		subElements = null;
	}

	@Getter
	@Setter
	private String name = "";
	
	@Getter
	@Setter
	private String type = "Node";
	
	@Getter
	@Setter
	private String description = "";
	
	@Getter
	@Setter
	private Long count = 0l;
	
	@Getter
	@Setter
	private Double average = 0d;
	
	@Getter
	@Setter
	private Double total = 0d;
	
	@Getter
	@Setter
	private Double min = 0d;
	
	@Getter
	@Setter
	private Double max = 0d;

	@Getter
	private final Map<String, ElementStatistics> subElements;

}
