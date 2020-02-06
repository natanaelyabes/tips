package io.iochord.apps.ips.model.ism.v1.nodes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = BranchImpl.class)
@JsonTypeName(Branch.TYPE)
public interface Branch extends Node {
	public static final String TYPE = "branch";

	BranchGate getGate();

	BranchType getType();

	BranchRule getRule();
	
	Map<String, String> getConditions();
}
