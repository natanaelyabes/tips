package io.iochord.apps.ips.model.ism.v1.data;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.impl.ResourceImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ResourceImpl.class)
@JsonTypeName(ElementType.DATA_RESOURCE)
public interface Resource extends Data {

	public enum RESOURCE_SELECTION {
		RANDOM
	}

	String getGroupId();
	
	RESOURCE_SELECTION getResourceSelection();
	
	int getNumberOfResource();
	
	DistributionType getSetupTimeDistribution();
	
	String getSetupTimeExpression();
	
	TimeUnit getSetupTimeUnit();
}
