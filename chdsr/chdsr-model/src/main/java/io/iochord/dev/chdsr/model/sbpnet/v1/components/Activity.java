package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.concurrent.TimeUnit;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;

public interface Activity extends Node {
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
