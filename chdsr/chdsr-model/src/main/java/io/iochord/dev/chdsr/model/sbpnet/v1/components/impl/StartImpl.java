package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.MovingUnit;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Start;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import lombok.Getter;
import lombok.Setter;

public class StartImpl extends NodeImpl implements Start {
	@Getter
	private final String elementType = "start";

	@Getter @Setter
	private MovingUnit movingUnit;
	
}
