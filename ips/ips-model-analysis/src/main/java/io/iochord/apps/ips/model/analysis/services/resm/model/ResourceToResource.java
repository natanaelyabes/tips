package io.iochord.apps.ips.model.analysis.services.resm.model;

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
	
	@Override
    public int hashCode() {
        return (resource1 + " - " + resource2).hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        
        ResourceToResource other = (ResourceToResource) obj;
        if (!resource1.equals(other.resource1) || !resource2.equals(other.resource2))
            return false;
        return true;
    }
}
