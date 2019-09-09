package io.iochord.apps.ips.model.ism.v1.components.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.model.ism.v1.components.DistributionType;
import io.iochord.apps.ips.model.ism.v1.components.Generator;
import io.iochord.apps.ips.model.ism.v1.components.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
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
public class GeneratorImpl extends DataImpl implements Generator {
	@Getter
	private final String elementType = Generator.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	ObjectType objectType;

	// TODO: Deserialize this one back from ref
	public String getObjectTypeRef() {
		return IsmUtil.generateRef(getObjectType());
	}

	@Getter
	@Setter
	private DistributionType distributionType = DistributionType.RANDOM;

	@Getter
	@Setter
	private String expression = "";

	@Getter
	@Setter
	private TimeUnit unit = TimeUnit.HOURS;

	@Getter
	@Setter
	private int entitiesPerArrival = 1;

	@Getter
	@Setter
	private int maxArrival = 0;

	@Getter
	@Setter
	private long firstCreation = 0;
}
