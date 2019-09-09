package io.iochord.apps.ips.model.ism.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.components.impl.BranchImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
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
