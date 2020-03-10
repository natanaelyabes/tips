package io.iochord.apps.ips.model.ihm.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.ihm.v1.Connector;
import io.iochord.apps.ips.model.ihm.v1.Model;
import io.iochord.apps.ips.model.ihm.v1.Node;
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
public class ModelImpl extends ElementImpl implements Model {
	
	@Getter
	@Setter
	Map<String, Node> nodes = new LinkedHashMap<>();

	@Getter
	@Setter
	Map<String, Connector> connectors = new LinkedHashMap<>();
	
	public String getElementType() {
		return ElementType.ELEMENT_MODEL;
	}
}
