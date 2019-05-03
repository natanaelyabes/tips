package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.MonitorImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = MonitorImpl.class)
public interface Monitor extends Node {
	public static final String TYPE = "monitor";
}
