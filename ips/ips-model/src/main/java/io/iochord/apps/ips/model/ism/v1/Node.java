package io.iochord.apps.ips.model.ism.v1;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;
import io.iochord.apps.ips.model.ism.v1.nodes.Monitor;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = NodeImpl.class)
@JsonTypeName(Node.TYPE)
@JsonSubTypes({
	@Type(value = Activity.class),
	@Type(value = Branch.class),
	@Type(value = Control.class),
	@Type(value = Monitor.class),
	@Type(value = Start.class),
	@Type(value = Stop.class),
})
public interface Node extends Element {
	public static final String TYPE = "node";

//	// TODO: Phase 2
//	String getGroupName();
//
//	// TODO: Phase 2
//	boolean isReportStatistics();
//
//	// TODO: Phase 2
//	boolean accept(Collection<Element> elements);

//	Collection<Element> getInputTypes();
//
//	Collection<Element> getOutputTypes();
//
//	Collection<Node> getInputNodes();
//
//	Collection<Node> getOutputNodes();

}
