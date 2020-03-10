package io.iochord.apps.ips.model.ihm.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ihm.v1.impl.ElementImpl;
import io.iochord.apps.ips.model.ihm.v1.impl.NodeImpl;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */

@JsonDeserialize(as = NodeImpl.class)
@JsonTypeName(ElementType.ELEMENT_NODE)
public interface Node extends Element {
}
