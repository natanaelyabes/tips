package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;

public interface Function extends Data {
	Map<String, Declaration> getInputParameters();
	String getCode();
	Map<String, Declaration> getOutputVariables();
}
