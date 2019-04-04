package io.iochord.dev.chdsr.model.sbpnet.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;

@JsonDeserialize(as = NodeImpl.class)
public interface Node extends Element {
	String getGroupName();
}
