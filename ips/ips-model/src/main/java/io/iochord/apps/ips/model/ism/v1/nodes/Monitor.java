package io.iochord.apps.ips.model.ism.v1.nodes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.MonitorImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = MonitorImpl.class)
@JsonTypeName(Monitor.TYPE)
public interface Monitor extends Node {
	public static final String TYPE = "monitor";
}
