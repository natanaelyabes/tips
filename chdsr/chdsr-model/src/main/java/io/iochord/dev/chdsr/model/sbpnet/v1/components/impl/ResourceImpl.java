package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.DistributionType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Resource;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.ResourceCriteria;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetUtil;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class ResourceImpl extends DataImpl implements Resource {
	@Getter
	private final String elementType = Resource.TYPE;

	@Getter
	@Setter
	private String groupId;

	@Getter
	@Setter
	@JsonIgnore
	private DataTable data;
	
	public String getDataRef() {
		return SbpnetUtil.generateRef(getData());
	}

	@Getter
	@Setter
	private DistributionType setupTime = DistributionType.RANDOM;

	@Getter
	@Setter
	private String expression = "";

	@Getter
	@Setter
	private TimeUnit timeUnit = TimeUnit.HOURS;
	
	@Getter
	@Setter
	private ResourceCriteria criteria;

	@Getter
	@Setter
	private double hourlyIdleCost; 
	
	@Getter
	@Setter
	private double hourlyBusyCost; 
	
	@Getter
	@Setter
	private boolean imported = false; 
	
	@Getter
	@Setter
	private DataTable dataTable; 

}
