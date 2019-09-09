package io.iochord.apps.ips.model.ism.v1.components;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.components.impl.ResourceImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ResourceImpl.class)
public interface Resource extends Data {
	public static final String TYPE = "resource";

	public enum RESOURCE_SELECTION {
		RANDOM
	}

	String getGroupId();

	DataTable getData();
	
	DistributionType getSetupTime();
	
	String getExpression();
	
	TimeUnit getTimeUnit();
	
	ResourceCriteria getCriteria();
	
	double getHourlyIdleCost();
	
	double getHourlyBusyCost();
	
	boolean isImported();
	
	DataTable getDataTable();
}
