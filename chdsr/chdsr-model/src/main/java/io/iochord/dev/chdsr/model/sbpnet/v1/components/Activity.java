package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = ActivityImpl.class)
public interface Activity extends Node {
	public static final String TYPE = "activity";

	public enum ACTIVITY_TYPE {

	}

	ACTIVITY_TYPE getType();

	Resource getResource();
	
	ResourceSelectionMethod getResourceSelectionMethod();

	Queue getQueue();

	Function getFunction();

	DistributionType getSetupTime();
	
	String getSetupTimeParameter();

	DistributionType getProcessingTime();

	String getProcessingTimeParameter();

	TimeUnit getUnit();
}
