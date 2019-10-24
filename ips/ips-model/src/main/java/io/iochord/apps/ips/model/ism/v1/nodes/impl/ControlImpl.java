package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.impl.ConfigurationImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;
import lombok.Getter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonTypeName(Control.TYPE)
public class ControlImpl extends ConfigurationImpl implements Control {
	@Getter
	private final String elementType = Control.TYPE;
	

}
