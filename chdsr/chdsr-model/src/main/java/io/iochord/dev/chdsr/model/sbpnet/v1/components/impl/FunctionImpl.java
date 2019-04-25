package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Declaration;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Function;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class FunctionImpl extends DataImpl implements Function {
	@Getter
	private final String elementType = "function";

	@Getter @Setter
	Map<String, Declaration> inputParameters;
	
	@Getter @Setter
	String code;

	@Getter @Setter
	Map<String, Declaration> outputVariables;
}
