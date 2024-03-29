package io.iochord.apps.ips.model.ism.v1.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.impl.FunctionImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = FunctionImpl.class)
@JsonTypeName(ElementType.DATA_FUNCTION)
public interface Function extends Data {

	@JsonProperty(value = "inputParametersRef")
	Map<String, Referenceable<ObjectType>> getInputParameters();

	String getCode();

	@JsonProperty(value = "outputVariablesRef")
	Map<String, Referenceable<ObjectType>> getOutputVariables();
}
