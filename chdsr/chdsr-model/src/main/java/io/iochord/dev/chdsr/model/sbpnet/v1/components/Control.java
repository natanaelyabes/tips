package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Configuration;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ControlImpl;

@JsonDeserialize(as = ControlImpl.class)
public interface Control extends Configuration {
	public static final String TYPE = "control";

}
