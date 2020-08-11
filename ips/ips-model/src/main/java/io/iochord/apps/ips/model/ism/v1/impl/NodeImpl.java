package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.Node;
import lombok.Getter;
import lombok.Setter;

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

	@Getter
	@Setter
	@JsonIgnore
	private List<Connector> inputConnectors = new ArrayList<>();

	@Getter
	@Setter
	@JsonIgnore
	private List<Connector> outputConnectors = new ArrayList<>();
	
}
