package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import lombok.Getter;
import lombok.Setter;

public class NodeImpl extends ElementImpl implements Node {

	@Getter @Setter
	private String groupName;
	
}
