package io.iochord.apps.ips.model.ism.v1.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = StartImpl.class)
@JsonTypeName(Start.TYPE)
public interface Start extends Node {
	public static final String TYPE = "start";

	@JsonProperty(value = "generatorRef")
	Referenceable<Generator> getGenerator();
}
