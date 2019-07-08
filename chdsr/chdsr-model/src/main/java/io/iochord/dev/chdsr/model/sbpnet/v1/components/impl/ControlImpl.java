package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Control;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ConfigurationImpl;
import lombok.Getter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class ControlImpl extends ConfigurationImpl implements Control {

	@Getter
	private final String elementType = Control.TYPE;

}
