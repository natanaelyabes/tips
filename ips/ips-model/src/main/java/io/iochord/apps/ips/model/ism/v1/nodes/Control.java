package io.iochord.apps.ips.model.ism.v1.nodes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Configuration;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ControlImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ControlImpl.class)
@JsonTypeName(ElementType.NODE_CONTROL)
public interface Control extends Configuration {
	public enum STOPPING_CRITERIA {
		STEP,
		TIME
	}
	
	int getReplicationNumber();
	
	STOPPING_CRITERIA getStoppingCriteria();
	
	Long getSeed();
	
	Long getStoppingTime();
	
	Long getStartTime();
	
}
