package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonTypeName(Page.TYPE)
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
	private Map<String, Connector> connectors = new LinkedHashMap<>();
}
