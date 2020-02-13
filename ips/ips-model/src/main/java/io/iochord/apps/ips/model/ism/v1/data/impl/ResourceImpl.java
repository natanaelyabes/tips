package io.iochord.apps.ips.model.ism.v1.data.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.DATA_RESOURCE)
public class ResourceImpl extends DataImpl implements Resource {
	
	@Getter
	@Setter
	private String groupId;
	
	@Getter
	@Setter
	private RESOURCE_SELECTION resourceSelection;
	
	@Getter
	@Setter
	private int numberOfResource;

	@Getter
	@Setter
	private DistributionType setupTimeDistribution = DistributionType.RANDOM;

	@Getter
	@Setter
	private String setupTimeExpression = "";

	@Getter
	@Setter
	private TimeUnit setupTimeUnit = TimeUnit.HOURS;

	public String getElementType() {
		return ElementType.DATA_RESOURCE;
	}

}
