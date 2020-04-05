package io.iochord.apps.ips.model.analysis.services.resm.model;

import lombok.Getter;
import lombok.Setter;

public class ResourceToShiftNumb {
	
	public ResourceToShiftNumb(String resource, int shiftnumb) {
		this.resource = resource;
		this.shiftnumb = shiftnumb;
	}
	
	@Getter
	@Setter 
	private String resource;
	
	@Getter
	@Setter 
	private int shiftnumb;
	
	@Override
    public int hashCode() {
		return (resource + " - " + shiftnumb).hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
    	if (this == obj)
            return true;
        if (obj == null)
            return false;
        
        ResourceToShiftNumb other = (ResourceToShiftNumb) obj;
        if (!resource.equals(other.resource) || shiftnumb != other.shiftnumb)
            return false;
        return true;
    }
}
