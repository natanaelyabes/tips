package io.iochord.apps.ips.model.analysis.services.resm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.iochord.apps.ips.core.services.ServiceContext;

public abstract class ResMinerAlgorithm {
	ServiceContext context;
	ResourceMinerConfig config;
	ResourceMinerResult result = new ResourceMinerResult();
	
	public ResMinerAlgorithm(ServiceContext context, ResourceMinerConfig config) {
		this.context = context;
		this.config = config;
	}
	
	abstract void compute();
	
	public ResourceMinerResult getResult() {
		return result;
	}
	
	public Map<String, List<String>> reverseMapping(Map<String, List<String>> map, List<String> newKeys) {
		Map<String, List<String>> newMap = new HashMap<>();
		for(String key : newKeys) {
			List<String> values = new ArrayList<>();
			for(Entry<String, List<String>> es : map.entrySet()) {
				if(es.getValue().contains(key))
					values.add(es.getKey());
			}
			newMap.put(key, values);
		}
		return newMap;
	}
}
