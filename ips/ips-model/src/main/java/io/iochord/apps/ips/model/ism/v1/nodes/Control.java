package io.iochord.apps.ips.model.ism.v1.nodes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Configuration;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ControlImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ControlImpl.class)
@JsonTypeName(Control.TYPE)
public interface Control extends Configuration {
	public static final String TYPE = "control";
}
