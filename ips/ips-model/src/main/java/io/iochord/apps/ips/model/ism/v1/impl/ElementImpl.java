package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Element;
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
@JsonTypeName(Element.TYPE)
public class ElementImpl implements Element {
	@Getter
	private final String elementType = Element.TYPE;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String label = "";

	@Getter
	@Setter
	private Map<String, String> attributes = new LinkedHashMap<>();
}
