package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

public class ConnectorImpl extends ElementImpl implements Connector {
	@Getter
	private final String elementType = Connector.TYPE;

	@Getter
	@Setter
	private Element source;

	@Getter
	@Setter
	private Element target;

}
