package io.iochord.apps.ips.model.ism.v1.data.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(Function.TYPE)
public class FunctionImpl extends DataImpl implements Function {
	@Getter
	private final String elementType = Function.TYPE;
	
	@Getter
	@Setter
	Map<String, Referenceable<ObjectType>> inputParameters = new LinkedHashMap<>();

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	private Map<String, Referenceable<ObjectType>> outputVariables = new LinkedHashMap<>();
	
}
