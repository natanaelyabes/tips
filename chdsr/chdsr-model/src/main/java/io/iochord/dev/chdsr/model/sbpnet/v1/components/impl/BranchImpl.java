package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import lombok.Getter;
import lombok.Setter;

public class BranchImpl extends NodeImpl implements Branch {
	@Getter
	private final String elementType = Branch.TYPE;

	@Getter
	@Setter
	BRANCH_TYPE type;

	@Getter
	@Setter
	BRANCH_RULE rule;
}
