package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class ElementImpl implements Element {
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String label;

	@Getter
	private final String elementType = Element.TYPE;

	@Getter
	@Setter
	private Map<String, String> attributes = new LinkedHashMap<>();
}
