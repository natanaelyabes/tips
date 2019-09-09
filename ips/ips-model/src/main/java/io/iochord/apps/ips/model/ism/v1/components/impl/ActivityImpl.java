package io.iochord.apps.ips.model.ism.v1.components.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.model.ism.v1.components.Activity;
import io.iochord.apps.ips.model.ism.v1.components.ActivityType;
import io.iochord.apps.ips.model.ism.v1.components.DistributionType;
import io.iochord.apps.ips.model.ism.v1.components.Function;
import io.iochord.apps.ips.model.ism.v1.components.Queue;
import io.iochord.apps.ips.model.ism.v1.components.Resource;
import io.iochord.apps.ips.model.ism.v1.components.ResourceSelectionMethod;
import io.iochord.apps.ips.model.ism.v1.components.VariableType;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmUtil;
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
public class ActivityImpl extends NodeImpl implements Activity {
	@Getter
	private final String elementType = Activity.TYPE;

	@Getter
	@Setter
	private ActivityType type = ActivityType.STANDARD;

	@Getter
	@Setter
	@JsonIgnore
	private Resource resource;
	
	public String getResourceRef() {
		return IsmUtil.generateRef(getResource());
	}

	@Getter
	@Setter
	@JsonIgnore
	private Queue queue;
	
	public String getQueueRef() {
		return IsmUtil.generateRef(getQueue());
	}

	@Getter
	@Setter
	@JsonIgnore
	private Function function;
	
	public String getFunctionRef() {
		return IsmUtil.generateRef(getFunction());
	}
	
	@Getter
	@Setter
	private ResourceSelectionMethod resourceSelectionMethod = ResourceSelectionMethod.RANDOM;

	@Getter
	@Setter
	private DistributionType processingTime = DistributionType.CONSTANT;
	
	@Getter
	@Setter
	private String setupTimeParameter = "0";
	
	@Getter
	@Setter
	private DistributionType setupTime = DistributionType.CONSTANT;

	@Getter
	@Setter
	private String processingTimeParameter = "0";
	
	@Getter
	@Setter
	private VariableType variable; 

	@Getter
	@Setter
	private TimeUnit unit = TimeUnit.HOURS;

	@Getter
	@Setter
	private double cost = 0;

	@Getter
	@Setter
	private boolean reportScrap = false;

}
