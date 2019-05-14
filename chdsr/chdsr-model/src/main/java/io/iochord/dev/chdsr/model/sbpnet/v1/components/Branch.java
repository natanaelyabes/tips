package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.BranchImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = BranchImpl.class)
public interface Branch extends Node {
	public static final String TYPE = "branch";

	BranchGate getGate();

	BranchType getType();

	BranchRule getRule();
	
	Map<String, String> getConditions();
}
