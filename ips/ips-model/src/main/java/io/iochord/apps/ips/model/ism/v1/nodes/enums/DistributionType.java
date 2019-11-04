package io.iochord.apps.ips.model.ism.v1.nodes.enums;

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
public enum DistributionType {
	RANDOM("0"),
	CONSTANT("constant(0)");
	
	@Getter
	@Setter
	private String defaultValue;
	
	private DistributionType(String defaultValue) {
		setDefaultValue(defaultValue);
	}
}
