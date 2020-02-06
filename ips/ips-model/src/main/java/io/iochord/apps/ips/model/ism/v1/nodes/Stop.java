package io.iochord.apps.ips.model.ism.v1.nodes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StopImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = StopImpl.class)
@JsonTypeName(Stop.TYPE)
public interface Stop extends Node {
	public static final String TYPE = "stop";
}
