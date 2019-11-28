package io.iochord.apps.ips.model.report;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ElementStatistics {

	public ElementStatistics() {
		subElements = null;
	}
	
	public ElementStatistics(String name, String type) {
		setName(name);
		setType(type);
		subElements = new LinkedHashMap<String, ElementStatistics>();
	}

	public ElementStatistics(String description, Long count, Double total, Double min, Double max) {
		setName("");
		setType("");
		setDescription(description);
		setCount(count);
		setTotal(total);
		if (total != null && count != null) {
			setAverage(total == 0 ? total : total / count);
		}
		setMin(min);
		setMax(max);
		subElements = null;
	}
	
	public ElementStatistics(String name, String type, String description, Long count, Double total, Double min, Double max) {
		setName(name);
		setType(type);
		setDescription(description);
		setCount(count);
		setTotal(total);
		if (total != null && count != null) {
			setAverage(total == 0 ? total : total / count);
		}
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
	private Long count;
	
	@Getter
	@Setter
	private Double average;
	
	@Getter
	@Setter
	private Double total;
	
	@Getter
	@Setter
	private Double min;
	
	@Getter
	@Setter
	private Double max;

	@Getter
	private final Map<String, ElementStatistics> subElements;

}
