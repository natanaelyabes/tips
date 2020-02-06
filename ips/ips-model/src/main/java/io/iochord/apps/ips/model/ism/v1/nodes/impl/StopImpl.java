package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
import lombok.Getter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(Stop.TYPE)
public class StopImpl extends NodeImpl implements Stop {
	@Getter
	private final String elementType = Stop.TYPE;

}
