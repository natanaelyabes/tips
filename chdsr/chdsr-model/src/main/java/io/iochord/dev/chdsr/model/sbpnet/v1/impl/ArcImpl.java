package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.Arc;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

public class ArcImpl extends ElementImpl implements Arc {

	@Getter @Setter
	private Element source;

	@Getter @Setter
	private Element target;
	
}
