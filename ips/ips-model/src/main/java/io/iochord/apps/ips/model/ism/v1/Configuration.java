package io.iochord.apps.ips.model.ism.v1;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.ConfigurationImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ConfigurationImpl.class)
@JsonTypeName(Configuration.TYPE)
public interface Configuration extends Element {
	public static final String TYPE = "configuration";
}
