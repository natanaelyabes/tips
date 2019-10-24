package io.iochord.apps.ips.model.ism.v1.data.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
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
@JsonTypeName(Generator.TYPE)
public class GeneratorImpl extends DataImpl implements Generator {
	@Getter
	private final String elementType = Generator.TYPE;
	
	@Getter
	@Setter
	Referenceable<ObjectType> objectType;

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
