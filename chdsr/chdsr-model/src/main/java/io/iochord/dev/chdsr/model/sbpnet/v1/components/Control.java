package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Configuration;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ControlImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = ControlImpl.class)
public interface Control extends Configuration {
	public static final String TYPE = "control";
}
