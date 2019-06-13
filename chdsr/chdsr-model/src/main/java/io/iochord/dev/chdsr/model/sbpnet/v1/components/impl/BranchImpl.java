package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchGate;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchRule;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchType;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import lombok.Getter;
import lombok.Setter;

public class BranchImpl extends NodeImpl implements Branch {
	@Getter
	private final String elementType = Branch.TYPE;

	@Getter
	@Setter
	private BranchGate gate = BranchGate.XOR;

	@Getter
	@Setter
	private BranchType type = BranchType.SPLIT;

	@Getter
	@Setter
	private BranchRule rule = BranchRule.PROBABILITY;

	@Getter
	@Setter
	private Map<String, String> conditions = new LinkedHashMap<>();
}
