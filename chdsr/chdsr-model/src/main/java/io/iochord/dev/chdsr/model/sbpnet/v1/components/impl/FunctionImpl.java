package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Function;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetUtil;
import lombok.Getter;
import lombok.Setter;

public class FunctionImpl extends DataImpl implements Function {
	@Getter
	private final String elementType = Function.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	Map<String, ObjectType> inputParameters = new LinkedHashMap<>();
	
	public Map<String, String> getInputParameterRefs() {
		return SbpnetUtil.generateRefs(getInputParameters());
	}

	@Getter
	@Setter
	String code;

	@Getter
	@Setter
	@JsonIgnore
	Map<String, ObjectType> outputVariables = new LinkedHashMap<>();
	
	public Map<String, String> getOutputVariableRefs() {
		return SbpnetUtil.generateRefs(getOutputVariables());
	}
}
