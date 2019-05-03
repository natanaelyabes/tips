package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StopImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = StopImpl.class)
public interface Stop extends Node {
	public static final String TYPE = "stop";
}
