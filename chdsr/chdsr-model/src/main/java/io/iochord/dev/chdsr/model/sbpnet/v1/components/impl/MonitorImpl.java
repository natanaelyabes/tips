package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Monitor;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import lombok.Getter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class MonitorImpl extends NodeImpl implements Monitor {
	@Getter
	private final String elementType = Monitor.TYPE;
}
