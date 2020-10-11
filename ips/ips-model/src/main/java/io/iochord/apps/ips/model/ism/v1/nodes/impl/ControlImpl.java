package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.impl.ConfigurationImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.NODE_CONTROL)
public class ControlImpl extends ConfigurationImpl implements Control {
	@Getter
	@Setter
	private int replicationNumber;
	
	@Getter
	@Setter
	private STOPPING_CRITERIA stoppingCriteria = STOPPING_CRITERIA.TIME;
	
	@Getter
	@Setter
	private Long stoppingTime;
	
	@Getter
	@Setter
	private Long startTime;
	
	@Getter
	@Setter
	private Long seed;

	public String getElementType() {
		return ElementType.NODE_CONTROL;
	}
}
