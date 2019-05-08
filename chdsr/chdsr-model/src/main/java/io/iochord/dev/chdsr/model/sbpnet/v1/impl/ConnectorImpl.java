package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
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
