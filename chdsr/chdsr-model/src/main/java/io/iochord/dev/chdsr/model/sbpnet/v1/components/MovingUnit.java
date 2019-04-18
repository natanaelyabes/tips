package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.MovingUnitImpl;

@JsonDeserialize(as = MovingUnitImpl.class)
public interface MovingUnit extends Data {
	Declaration getDeclaration();
	String getExpression();
	TimeUnit getUnit();
	int getEntitiesPerArrival();
	int getMaxArrival();
	long getFirstCreation();
}
