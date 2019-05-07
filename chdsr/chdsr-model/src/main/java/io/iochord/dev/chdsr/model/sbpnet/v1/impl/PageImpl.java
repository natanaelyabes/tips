package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class PageImpl extends ElementImpl implements Page {
	@Getter
	private final String elementType = Page.TYPE;

	@Getter
	@Setter
	private Map<String, Data> data = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Node> nodes = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Connector> arcs = new LinkedHashMap<>();
}
