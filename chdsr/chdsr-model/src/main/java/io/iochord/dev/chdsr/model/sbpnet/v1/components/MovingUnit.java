package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.concurrent.TimeUnit;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;

public interface MovingUnit extends Data {
	Declaration getDeclaration();
	String getExpression();
	TimeUnit getUnit();
	int getEntitiesPerArrival();
	int getMaxArrival();
	long getFirstCreation();
}
