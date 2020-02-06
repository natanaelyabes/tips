package io.iochord.apps.ips.model.ism.v1.data.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(Resource.TYPE)
public class ResourceImpl extends DataImpl implements Resource {
	@Getter
	private final String elementType = Resource.TYPE;
	
	@Getter
	@Setter
	private String groupId;
	
	@Getter
	@Setter
	private RESOURCE_SELECTION resourceSelection;
	
	@Getter
	@Setter
	private int numberOfResource;
	
//	@Getter
//	@Setter
//	private Referenceable<DataTable> data;

	@Getter
	@Setter
	private DistributionType setupTimeDistribution = DistributionType.RANDOM;

	@Getter
	@Setter
	private String setupTimeExpression = "";

	@Getter
	@Setter
	private TimeUnit setupTimeUnit = TimeUnit.HOURS;
//	
//	@Getter
//	@Setter
//	private ResourceCriteria criteria;
//
//	@Getter
//	@Setter
//	private double hourlyIdleCost; 
//	
//	@Getter
//	@Setter
//	private double hourlyBusyCost; 
//	
//	@Getter
//	@Setter
//	private boolean imported = false; 
//	
//	@Getter
//	@Setter
//	private Referenceable<DataTable> dataTable; 

}
