package io.iochord.apps.ips.model.ism.v1.data;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = GeneratorImpl.class)
@JsonTypeName(ElementType.DATA_GENERATOR)
public interface Generator extends Data {
	@JsonProperty(value = "objectTypeRef")
	Referenceable<ObjectType> getObjectType();

	DistributionType getDistributionType();
	
	String getExpression();

	TimeUnit getUnit();

	int getEntitiesPerArrival();

	int getMaxArrival();
}
