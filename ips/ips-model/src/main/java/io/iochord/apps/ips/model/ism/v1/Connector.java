package io.iochord.apps.ips.model.ism.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ConnectorImpl.class)
public interface Connector extends Element {
	public static final String TYPE = "connector";

	Element getSource();
	
	int getSourceIndex();

	Element getTarget();
	
	int getTargetIndex();
}
