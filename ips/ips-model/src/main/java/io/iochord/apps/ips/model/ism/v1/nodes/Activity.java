package io.iochord.apps.ips.model.ism.v1.nodes;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ActivityType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ResourceSelectionMethod;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.VariableType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ActivityImpl.class)
@JsonTypeName(Activity.TYPE)
public interface Activity extends Node {
	public static final String TYPE = "activity";

	ActivityType getType();

	@JsonProperty(value = "resourceRef")
	Referenceable<Resource> getResource();
	
	ResourceSelectionMethod getResourceSelectionMethod();

	DistributionType getSetupTime();
	
	String getSetupTimeParameter();

	DistributionType getProcessingTime();

	String getProcessingTimeParameter();
	
	VariableType getVariable();
	
	boolean isReportScrap();

	TimeUnit getUnit();
	
	double getCost();
	
	@JsonProperty(value = "queueRef")
	Referenceable<Queue> getQueue();

	@JsonProperty(value = "functionRef")
	Referenceable<Function> getFunction();
}
