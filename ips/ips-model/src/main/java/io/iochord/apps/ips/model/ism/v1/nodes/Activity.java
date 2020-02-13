package io.iochord.apps.ips.model.ism.v1.nodes;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.ActivityType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ActivityImpl.class)
@JsonTypeName(ElementType.NODE_ACTIVITY)
public interface Activity extends Node {
	ActivityType getType();

	@JsonProperty(value = "resourceRef")
	Referenceable<Resource> getResource();
	
	@JsonProperty(value = "queueRef")
	Referenceable<Queue> getQueue();

	DistributionType getProcessingTimeDistribution();

	String getProcessingTimeExpression();
	
	TimeUnit getProcessingTimeUnit();

	@JsonProperty(value = "functionRef")
	Referenceable<Function> getFunction();
}
