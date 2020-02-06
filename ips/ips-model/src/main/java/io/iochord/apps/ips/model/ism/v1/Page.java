package io.iochord.apps.ips.model.ism.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.PageImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = PageImpl.class)
@JsonTypeName(Page.TYPE)
public interface Page extends Element {
	public static final String TYPE = "page";

	Map<String, Data> getData();

	Map<String, Node> getNodes();

	Map<String, Connector> getConnectors();
}
