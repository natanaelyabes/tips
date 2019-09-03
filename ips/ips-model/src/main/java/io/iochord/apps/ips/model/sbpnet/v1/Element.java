package io.iochord.apps.ips.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.sbpnet.v1.impl.ElementImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ElementImpl.class)
public interface Element {
	public static final String TYPE = "element";

	String getId();

	String getLabel();

	String getElementType();

	Map<String, String> getAttributes();
}
