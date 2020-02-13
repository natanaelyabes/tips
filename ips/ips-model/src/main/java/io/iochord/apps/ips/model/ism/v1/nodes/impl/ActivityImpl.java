package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ActivityType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ResourceSelectionMethod;
import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.NODE_ACTIVITY)
public class ActivityImpl extends NodeImpl implements Activity {
	private ActivityType activityType = ActivityType.STANDARD;

	@Getter
	@Setter
	private Referenceable<Resource> resource;
	
	@Getter
	@Setter
	private Referenceable<Queue> queue;
	
	@Getter
	@Setter
	private Referenceable<Function> function;
	
	@Getter
	@Setter
	private ResourceSelectionMethod resourceSelectionMethod = ResourceSelectionMethod.RANDOM;
	
	@Getter
	@Setter
	private DistributionType processingTimeDistribution = DistributionType.CONSTANT;

	@Getter
	@Setter
	private String processingTimeExpression = "0";

	@Getter
	@Setter
	private TimeUnit processingTimeUnit = TimeUnit.HOURS;

	@Override
	public ActivityType getType() {
		return activityType;
	}
	
	public void setType(ActivityType activityType) {
		this.activityType = activityType;
	}
	
	public String getElementType() {
		return ElementType.NODE_ACTIVITY;
	}

}
