package io.iochord.dev.chdsr.model.sbpnet.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ConnectorImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = ConnectorImpl.class)
public interface Connector extends Element {
	public static final String TYPE = "connector";

	Element getSource();

	Element getTarget();
}
