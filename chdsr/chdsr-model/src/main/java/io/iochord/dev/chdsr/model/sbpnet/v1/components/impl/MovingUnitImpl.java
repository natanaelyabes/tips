package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.concurrent.TimeUnit;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Declaration;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.MovingUnit;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class MovingUnitImpl extends DataImpl implements MovingUnit {
	@Getter
	private final String elementType = "movingunit";

	@Getter @Setter
	Declaration declaration;
	
	@Getter @Setter
	String expression;
	
	@Getter @Setter
	TimeUnit unit;
	
	@Getter @Setter
	int entitiesPerArrival;
	
	@Getter @Setter
	int maxArrival;
	
	@Getter @Setter
	long firstCreation;

}
