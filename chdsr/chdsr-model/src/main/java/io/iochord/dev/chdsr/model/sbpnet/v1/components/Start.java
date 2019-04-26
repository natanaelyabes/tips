package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StartImpl;

@JsonDeserialize(as = StartImpl.class)
public interface Start extends Node {
	public static final String TYPE = "start";

	Generator getGenerator();
}
