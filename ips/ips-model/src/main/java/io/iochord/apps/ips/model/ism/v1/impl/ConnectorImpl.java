package io.iochord.apps.ips.model.ism.v1.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Connector;
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
@JsonTypeName(ElementType.ELEMENT_CONNECTOR)
public class ConnectorImpl extends ElementImpl implements Connector {

	@Getter
	@Setter
	private Referenceable<Element> source;

	@Getter
	@Setter
	private int sourceIndex = 0;

	@Getter
	@Setter
	private Referenceable<Element> target;

	@Getter
	@Setter
	private int targetIndex = 0;

	public String getElementType() {
		return ElementType.ELEMENT_CONNECTOR;
	}

}
