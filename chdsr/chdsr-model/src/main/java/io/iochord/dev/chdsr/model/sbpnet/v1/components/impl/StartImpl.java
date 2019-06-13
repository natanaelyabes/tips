package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Generator;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Start;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class StartImpl extends NodeImpl implements Start {
	@Getter
	private final String elementType = Start.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	private Generator generator;
	
	public String getGeneratorRef() {
		return SbpnetUtil.generateRef(getGenerator());
	}
}
