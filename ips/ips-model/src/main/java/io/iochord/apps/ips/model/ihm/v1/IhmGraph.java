package io.iochord.apps.ips.model.ihm.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ihm.v1.ElementType;
import io.iochord.apps.ips.model.ihm.v1.impl.IhmGraphImpl;
/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
@JsonDeserialize(as = IhmGraphImpl.class)
@JsonTypeName(ElementType.ELEMENT_NET)
public interface IhmGraph extends Element {
	
	String getVersion();

	Map<String, Model> getModels();

	@JsonIgnore
	Model getDefaultModel();
}
