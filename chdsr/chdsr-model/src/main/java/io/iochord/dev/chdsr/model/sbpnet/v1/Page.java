package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.PageImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = PageImpl.class)
public interface Page extends Element {
	public static final String TYPE = "page";

	Map<String, Data> getData();

	Map<String, Node> getNodes();

	Map<String, Connector> getConnectors();
}
