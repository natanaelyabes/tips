package io.iochord.apps.ips.model.ism.v1.data;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
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
@JsonTypeName(Resource.TYPE)
public interface Resource extends Data {
	public static final String TYPE = "resource";

	public enum RESOURCE_SELECTION {
		RANDOM
	}

	String getGroupId();
	
	RESOURCE_SELECTION getResourceSelection();
	
	// TODO: Phase 1
	// @Deprecated
	int getNumberOfResource();
//
//	@JsonProperty(value = "dataRef")
//	Referenceable<DataTable> getData();
	
	DistributionType getSetupTimeDistribution();
	
	String getSetupTimeExpression();
	
	TimeUnit getSetupTimeUnit();
	
//	TODO: Phase 2
//	ResourceCriteria getCriteria();
//
//	double getHourlyIdleCost();
//	
//	double getHourlyBusyCost();
//	
//	boolean isImported();
//	
//	@JsonProperty(value = "dataTableRef")
//	Referenceable<DataTable> getDataTable();
}
