package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ElementImpl;

@JsonDeserialize(as = ElementImpl.class)
public interface Element {
	String getId();

	String getName();

	String getType();

	Map<String, String> getAttributes();
}
