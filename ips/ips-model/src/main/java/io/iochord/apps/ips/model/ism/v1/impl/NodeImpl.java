package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.Node;
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
@JsonTypeName(Node.TYPE)
public class NodeImpl extends ElementImpl implements Node {
	@Getter
	private final String elementType = Node.TYPE;

	@Getter
	@Setter
	private String groupName;

	@Getter
	@Setter
	private boolean reportStatistics = false;

	@Override
	public boolean accept(Collection<Element> elements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Element> getInputTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Element> getOutputTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Node> getInputNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Node> getOutputNodes() {
		// TODO Auto-generated method stub
		return null;
	}
}
