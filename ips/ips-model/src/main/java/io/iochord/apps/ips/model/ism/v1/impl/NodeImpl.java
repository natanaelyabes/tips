package io.iochord.apps.ips.model.ism.v1.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.Node;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.ELEMENT_NODE)
public class NodeImpl extends ElementImpl implements Node {

	public String getElementType() {
		return ElementType.ELEMENT_NODE;
	}

}
