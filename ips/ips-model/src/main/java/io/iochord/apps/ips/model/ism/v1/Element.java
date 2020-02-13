package io.iochord.apps.ips.model.ism.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Identifiable;
import io.iochord.apps.ips.model.ism.v1.impl.ElementImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ElementImpl.class)
@JsonTypeInfo(use = Id.NAME, property = "elementType", include = As.EXISTING_PROPERTY)
@JsonTypeName(ElementType.ELEMENT)
public interface Element extends Identifiable {

	String getElementType();

	String getLabel();

	String getIcon();

	Map<String, String> getAttributes();

}
