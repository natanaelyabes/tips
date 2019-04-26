package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.BranchImpl;

@JsonDeserialize(as = BranchImpl.class)
public interface Branch extends Node {
	public static final String TYPE = "branch";

	public enum BRANCH_TYPE {
		AND_SPLIT,
		AND_JOIN,
		XOR_SPLIT,
		XOR_JOIN,
	}
	
	public enum BRANCH_RULE {
		PROBABILITY,
		CONDITION,
		DATA,
	}
	
	BRANCH_TYPE getType();
	BRANCH_RULE getRule();
	
}
