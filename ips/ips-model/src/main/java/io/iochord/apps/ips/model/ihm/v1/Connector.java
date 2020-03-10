package io.iochord.apps.ips.model.ihm.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ihm.v1.ElementType;
import io.iochord.apps.ips.model.ihm.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ihm.v1.impl.ElementImpl;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
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
