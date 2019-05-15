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

	ActivityType getType();

	Resource getResource();
	
	ResourceSelectionMethod getResourceSelectionMethod();

	DistributionType getSetupTime();
	
	String getSetupTimeParameter();

	DistributionType getProcessingTime();

	String getProcessingTimeParameter();
	
	VariableType getVariable();
	
	boolean isReportScrap();

	TimeUnit getUnit();
	
	double getCost();
	
	Queue getQueue();

	Function getFunction();
}
