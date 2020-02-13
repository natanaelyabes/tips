package io.iochord.apps.ips.model.ism.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ConnectorImpl.class)
@JsonTypeName(ElementType.ELEMENT_CONNECTOR)
public interface Connector extends Element {

	@JsonProperty(value = "sourceRef")
	Referenceable<Element> getSource();

	int getSourceIndex();

	@JsonProperty(value = "targetRef")
	Referenceable<Element> getTarget();

	int getTargetIndex();
}
