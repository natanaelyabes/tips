package io.iochord.apps.ips.model.ihm.v1.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ihm.v1.Node;
import io.iochord.apps.ips.model.ism.v1.ElementType;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
@JsonTypeName(ElementType.ELEMENT_NODE)
public class NodeImpl extends ElementImpl implements Node {
	
	public String getElementType() {
		return ElementType.ELEMENT_NODE;
	}
	
}
