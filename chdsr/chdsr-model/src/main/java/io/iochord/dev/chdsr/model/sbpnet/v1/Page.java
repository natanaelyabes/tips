package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.PageImpl;

@JsonDeserialize(as = PageImpl.class)
public interface Page extends Element {
	Map<String, Node> getNodes();

	Map<String, Arc> getArcs();
}
