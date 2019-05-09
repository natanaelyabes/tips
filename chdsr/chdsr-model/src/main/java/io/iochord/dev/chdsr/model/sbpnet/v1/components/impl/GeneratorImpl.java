package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.DistributionType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Generator;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
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
		return SbpnetUtil.generateRef(getObjectType());
	}

	@Getter
	@Setter
	DistributionType distributionType = DistributionType.RANDOM;

	@Getter
	@Setter
	String expression = "";

	@Getter
	@Setter
	TimeUnit unit = TimeUnit.HOURS;

	@Getter
	@Setter
	int entitiesPerArrival = 1;

	@Getter
	@Setter
	int maxArrival = 0;

	@Getter
	@Setter
	long firstCreation = 0;
}
