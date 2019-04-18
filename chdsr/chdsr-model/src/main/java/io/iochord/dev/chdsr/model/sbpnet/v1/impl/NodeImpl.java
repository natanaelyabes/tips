package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.Collection;

import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import lombok.Getter;
import lombok.Setter;

public class NodeImpl extends ElementImpl implements Node {

	@Getter @Setter
	private String groupName;
	
	@Getter @Setter
	private boolean reportStatistics = false;

	@Override
	public boolean accept(Collection<Element> elements) {
		return false;
	}

	@Override
	public Collection<Element> getInputTypes() {
		return null;
	}

	@Override
	public Collection<Element> getOutputTypes() {
		return null;
	}

	@Override
	public Collection<Node> getInputNodes() {
		return null;
	}

	@Override
	public Collection<Node> getOutputNodes() {
		return null;
	}
	
}
