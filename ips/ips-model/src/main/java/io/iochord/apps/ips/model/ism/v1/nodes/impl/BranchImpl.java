package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.NODE_BRANCH)
public class BranchImpl extends NodeImpl implements Branch {
	@Getter
	@Setter
	private BranchGate gate = BranchGate.XOR;

	private BranchType branchType = BranchType.SPLIT;

	@Getter
	@Setter
	private BranchRule rule = BranchRule.PROBABILITY;

	@Getter
	@Setter
	private Map<String, String> conditions = new LinkedHashMap<>();
	
	public String getElementType() {
		return ElementType.NODE_BRANCH;
	}

	@Override
	public BranchType getType() {
		return branchType;
	}
	
	public void setType(BranchType branchType) {
		this.branchType = branchType;
	}
}
