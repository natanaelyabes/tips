package io.iochord.apps.ips.model.ihm.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Identifiable;
import io.iochord.apps.ips.model.ihm.v1.impl.ElementImpl;
import io.iochord.apps.ips.model.ism.v1.impl.ConfigurationImpl;

/**
 *
 * @package ips-model
 * @author Administrator
 * @since 2020
 * 
 */
@JsonDeserialize(as = ElementImpl.class)
@JsonTypeInfo(use = Id.NAME, property = "elementType", include = As.EXISTING_PROPERTY)
@JsonTypeName(ElementType.ELEMENT)
public interface Element extends Identifiable {
	
	String getLabel();
	
	Map<String, String> getProperties();
}
