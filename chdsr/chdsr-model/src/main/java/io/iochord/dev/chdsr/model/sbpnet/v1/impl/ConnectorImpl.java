package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class ConnectorImpl extends ElementImpl implements Connector {
	@Getter
	private final String elementType = Connector.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	private Element source;
	
	public String getSourceRef() {
		return SbpnetUtil.generateRef(getSource());
	}

	@Getter
	@Setter
	private int sourceIndex = 0;

	@Getter
	@Setter
	@JsonIgnore
	private Element target;

	public String getTargetRef() {
		return SbpnetUtil.generateRef(getTarget());
	}

	@Getter
	@Setter
	private int targetIndex = 0;
}
