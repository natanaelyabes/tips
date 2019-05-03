package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = NodeImpl.class)
public interface Node extends Element {
	public static final String TYPE = "node";

	String getGroupName();

	boolean isReportStatistics();

	boolean accept(Collection<Element> elements);

	Collection<Element> getInputTypes();

	Collection<Element> getOutputTypes();

	Collection<Node> getInputNodes();

	Collection<Node> getOutputNodes();

}
