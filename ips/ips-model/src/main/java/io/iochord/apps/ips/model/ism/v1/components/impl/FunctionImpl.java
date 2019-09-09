package io.iochord.apps.ips.model.ism.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.model.ism.v1.components.Function;
import io.iochord.apps.ips.model.ism.v1.components.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmUtil;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class FunctionImpl extends DataImpl implements Function {
	@Getter
	private final String elementType = Function.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	Map<String, ObjectType> inputParameters = new LinkedHashMap<>();
	
	public Map<String, String> getInputParameterRefs() {
		return IsmUtil.generateRefs(getInputParameters());
	}

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	@JsonIgnore
	private Map<String, ObjectType> outputVariables = new LinkedHashMap<>();
	
	public Map<String, String> getOutputVariableRefs() {
		return IsmUtil.generateRefs(getOutputVariables());
	}
}
