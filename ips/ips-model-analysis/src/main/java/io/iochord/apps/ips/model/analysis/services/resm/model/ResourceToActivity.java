package io.iochord.apps.ips.model.analysis.services.resm.model;

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
	
	@Override
    public int hashCode() {
		return (resource + " - " + activity).hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
    	if (this == obj)
            return true;
        if (obj == null)
            return false;
        
        ResourceToActivity other = (ResourceToActivity) obj;
        if (!resource.equals(other.resource) || !activity.equals(other.activity))
            return false;
        return true;
    }
}
