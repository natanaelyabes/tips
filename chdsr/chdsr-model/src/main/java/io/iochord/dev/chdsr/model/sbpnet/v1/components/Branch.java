package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;

public interface Branch extends Node {
	public enum BRANCH_TYPE {
		AND_SPLIT,
		AND_JOIN,
		OR_SPLIT,
		OR_JOIN,
	}
	
	public enum BRANCH_RULE {
		PROBABILITY,
		CONDITION,
		DATA,
	}
	
	BRANCH_TYPE getType();
	BRANCH_RULE getRule();
	
}
