package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.FunctionImpl;

@JsonDeserialize(as = FunctionImpl.class)
public interface Function extends Data {
	Map<String, Declaration> getInputParameters();
	String getCode();
	Map<String, Declaration> getOutputVariables();
}
