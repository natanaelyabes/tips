package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;

@JsonDeserialize(as = ActivityImpl.class)
public interface Activity extends Node {
	public static final String TYPE = "activity";

	public enum ACTIVITY_TYPE {
		
	}
	
	ACTIVITY_TYPE getType();
	Resource getResource();
	Queue getQueue();
	Function getFunction();
	String getSetupTimeExpression();
	String getProcessingTimeExpression();
	TimeUnit getUnit();
}
