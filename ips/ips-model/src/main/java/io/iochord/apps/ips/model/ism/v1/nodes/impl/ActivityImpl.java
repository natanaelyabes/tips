package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ActivityType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ResourceSelectionMethod;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.VariableType;
import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
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
@JsonTypeName(Activity.TYPE)
public class ActivityImpl extends NodeImpl implements Activity {
	@Getter
	private final String elementType = Activity.TYPE;
	
	@Getter
	@Setter
	private ActivityType type = ActivityType.STANDARD;

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
