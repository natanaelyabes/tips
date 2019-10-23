package io.iochord.apps.ips.model.ism.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.components.impl.FunctionImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = FunctionImpl.class)
public interface Function extends Data {
	public static final String TYPE = "function";

	Map<String, ObjectType> getInputParameters();

	String getCode();

	Map<String, ObjectType> getOutputVariables();
}