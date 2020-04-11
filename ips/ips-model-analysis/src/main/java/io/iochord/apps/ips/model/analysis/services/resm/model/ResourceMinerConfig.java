package io.iochord.apps.ips.model.analysis.services.resm.model;

import lombok.Getter;
import lombok.Setter;

public class ResourceMinerConfig {

	@Getter
	@Setter
	private String datasetId;
	
	@Getter
	@Setter 
	private String resMinAlg;
	  
	@Getter
	@Setter 
	private String distMesAlg;
	  
	@Getter
	@Setter 
	private float threshold;
	  
	@Getter
	@Setter 
	private boolean timeAnalysis;
	
	@Getter
	@Setter 
	private String[] propTimeAnalysis;
	
	@Getter
	@Setter 
	private String colCaseId;

	@Getter
	@Setter 
	private String colEventActivity;

	@Getter
	@Setter 
	private String colEventResource;
	  
	@Getter
	@Setter 
	private String colEventTimestampStart;
	
	@Getter
	@Setter 
	private String colEventTimestampEnd;
	
	@Getter
	@Setter 
	private long skipRows;
	
}
