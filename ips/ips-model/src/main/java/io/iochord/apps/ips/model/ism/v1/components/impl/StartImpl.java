package io.iochord.apps.ips.model.ism.v1.components.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.model.ism.v1.components.Generator;
import io.iochord.apps.ips.model.ism.v1.components.Start;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmUtil;
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
public class StartImpl extends NodeImpl implements Start {
	@Getter
	private final String elementType = Start.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	private Generator generator;
	
	public String getGeneratorRef() {
		return IsmUtil.generateRef(getGenerator());
	}
}
