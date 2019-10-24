package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
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
@JsonTypeName(Start.TYPE)
public class StartImpl extends NodeImpl implements Start {
	@Getter
	private final String elementType = Start.TYPE;
	
	@Getter
	@Setter
	private Referenceable<Generator> generator;
}
