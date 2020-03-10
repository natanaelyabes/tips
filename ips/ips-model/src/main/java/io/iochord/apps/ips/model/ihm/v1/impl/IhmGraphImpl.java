package io.iochord.apps.ips.model.ihm.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.ihm.v1.IhmGraph;
import io.iochord.apps.ips.model.ihm.v1.Model;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class IhmGraphImpl extends ElementImpl implements IhmGraph {
	private static final String VERSION = "1.0";

	public String getVersion() {
		return VERSION;
	}
	
	public String getElementType() {
		return ElementType.ELEMENT_NET;
	}
	
	@Getter
	@Setter
	private Map<String, Model> models = new LinkedHashMap<>();

	@Override
	public Model getDefaultModel() {
		if (getModels() != null) {
			return getModels().values().iterator().next();
		}
		return null;
	}
}
