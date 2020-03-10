package io.iochord.apps.ips.model.analysis.services.resm;

import lombok.Getter;
import lombok.Setter;

public class ResourceToResource {
	
	public ResourceToResource(String resource1, String resource2) {
		this.resource1 = resource1;
		this.resource2 = resource2;
	}
	
	@Getter
	@Setter 
	private String resource1;
	
	@Getter
	@Setter 
	private String resource2;
}
