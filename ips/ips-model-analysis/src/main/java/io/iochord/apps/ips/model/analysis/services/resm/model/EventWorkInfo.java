package io.iochord.apps.ips.model.analysis.services.resm.model;

import lombok.Getter;
import lombok.Setter;

public class EventWorkInfo {
	
	@Getter
	@Setter 
	private String starttime;
	
	@Getter
	@Setter 
	private String comptimeprev;
	
	@Getter
	@Setter 
	private String gapmin;
	
	@Getter
	@Setter 
	private String gapdt;
}
