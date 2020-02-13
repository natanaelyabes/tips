package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.ELEMENT)
public class ElementImpl implements Element {

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String label = "";

	@Getter
	@Setter
	private String icon = "";

	@Getter
	@Setter
	private Map<String, String> attributes = new LinkedHashMap<>();
	
	public String getElementType() {
		return ElementType.ELEMENT;
	}

}
