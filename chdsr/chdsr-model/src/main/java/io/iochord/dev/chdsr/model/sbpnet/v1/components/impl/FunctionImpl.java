package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Function;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class FunctionImpl extends DataImpl implements Function {
	@Getter
	private final String elementType = Function.TYPE;

	@Getter
	@Setter
	Map<String, ObjectType> inputParameters;

	@Getter
	@Setter
	String code;

	@Getter
	@Setter
	Map<String, ObjectType> outputVariables;
}
