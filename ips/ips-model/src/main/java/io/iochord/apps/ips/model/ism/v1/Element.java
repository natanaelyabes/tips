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
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ElementImpl.class)
@JsonTypeInfo(use = Id.NAME, property = "elementType", include = As.EXISTING_PROPERTY)
@JsonTypeName(Element.TYPE)
public interface Element extends Identifiable {
	public static final String TYPE = "element";
	
	String getElementType();

	String getLabel();

	Map<String, String> getAttributes();
}
