package io.iochord.apps.ips.model.ism.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.components.impl.StartImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = StartImpl.class)
public interface Start extends Node {
	public static final String TYPE = "start";

	Generator getGenerator();
}