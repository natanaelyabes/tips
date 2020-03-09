package io.iochord.apps.ips.model.ihm.v1;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ElementType {
	
	public static final String ELEMENT = "element";

	public static final String ELEMENT_NET = "c-net";

	public static final String ELEMENT_MODEL = "model";

	public static final String ELEMENT_NODE = "node";

	public static final String ELEMENT_CONNECTOR = "connector";
}
