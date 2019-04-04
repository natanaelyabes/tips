package io.iochord.dev.chdsr.model.sbpnet.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ArcImpl;

@JsonDeserialize(as = ArcImpl.class)
public interface Arc extends Element {
	Element getSource();

	Element getTarget();
}
