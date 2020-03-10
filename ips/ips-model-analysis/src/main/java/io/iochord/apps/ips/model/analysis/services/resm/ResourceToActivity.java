package io.iochord.apps.ips.model.analysis.services.resm;

import lombok.Getter;
import lombok.Setter;

public class ResourceToActivity {
	
	public ResourceToActivity(String resource, String activity) {
		this.resource = resource;
		this.activity = activity;
	}
	
	@Getter
	@Setter 
	private String resource;
	
	@Getter
	@Setter 
	private String activity;
}
