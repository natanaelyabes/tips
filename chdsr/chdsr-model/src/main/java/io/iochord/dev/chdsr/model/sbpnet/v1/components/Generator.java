package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.GeneratorImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = GeneratorImpl.class)
public interface Generator extends Data {
	public static final String TYPE = "generator";
	
	ObjectType getObjectType();

	DistributionType getDistributionType();
	
	String getExpression();

	TimeUnit getUnit();

	int getEntitiesPerArrival();

	int getMaxArrival();

	long getFirstCreation();
}
