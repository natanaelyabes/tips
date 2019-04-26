package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Stop;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import lombok.Getter;

public class StopImpl extends NodeImpl implements Stop {
	@Getter
	private final String elementType = Stop.TYPE;

}
