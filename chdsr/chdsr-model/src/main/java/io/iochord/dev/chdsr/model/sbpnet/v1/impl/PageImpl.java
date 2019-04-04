package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Arc;
import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.Page;
import lombok.Getter;
import lombok.Setter;

public class PageImpl extends ElementImpl implements Page {

	@Getter @Setter
	private Map<String, Node> nodes = new LinkedHashMap<>();

	@Getter @Setter
	private Map<String, Arc> arcs = new LinkedHashMap<>();

}
