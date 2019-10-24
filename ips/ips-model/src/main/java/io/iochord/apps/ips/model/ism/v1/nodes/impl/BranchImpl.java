package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
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
@JsonTypeName(Branch.TYPE)
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
