package io.iochord.apps.ips.model.analysis.services.resm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ResourceMinerResult {

	@Getter
	@Setter
	private List<String> activities;
	
	@Getter
	@Setter
	private List<String> resources;
	
	@Getter
	@Setter
	private List<String> groups;
	
	@Getter
	@Setter
	private Map<String,List<String>> mactgroup;
	
	@Getter
	@Setter
	private Map<String,List<String>> mgroupres;
	
	@Getter
	@Setter
	private Map<String,List<String>> mgroupact;
	
	@Getter
	@Setter
	private Map<String,List<String>> mresgroup;
}
