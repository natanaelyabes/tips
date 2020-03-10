package io.iochord.apps.ips.model.ihm.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ihm.v1.Element;
import io.iochord.apps.ips.model.ihm.v1.ElementType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
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
	private Map<String, String> properties = new LinkedHashMap<>();
	
	public String getElementType() {
		return ElementType.ELEMENT;
	}
}
