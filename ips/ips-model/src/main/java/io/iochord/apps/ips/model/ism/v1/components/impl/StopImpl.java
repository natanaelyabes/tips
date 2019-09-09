package io.iochord.apps.ips.model.ism.v1.components.impl;

import io.iochord.apps.ips.model.ism.v1.components.Stop;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import lombok.Getter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class StopImpl extends NodeImpl implements Stop {
	@Getter
	private final String elementType = Stop.TYPE;
	
}
