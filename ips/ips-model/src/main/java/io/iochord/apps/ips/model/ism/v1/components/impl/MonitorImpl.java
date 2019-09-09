package io.iochord.apps.ips.model.ism.v1.components.impl;

import io.iochord.apps.ips.model.ism.v1.components.Monitor;
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
public class MonitorImpl extends NodeImpl implements Monitor {
	@Getter
	private final String elementType = Monitor.TYPE;
}
