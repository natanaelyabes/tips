package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ElementImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
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
