package io.iochord.apps.ips.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
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
