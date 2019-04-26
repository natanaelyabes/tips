package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.concurrent.TimeUnit;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Generator;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class GeneratorImpl extends DataImpl implements Generator {
	@Getter
	private final String elementType = Generator.TYPE;

	@Getter @Setter
	ObjectType declaration;
	
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
