package io.iochord.apps.ips.model.ism.v1.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
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
public class ConnectorImpl extends ElementImpl implements Connector {
	@Getter
	private final String elementType = Connector.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	private Element source;
	
	public String getSourceRef() {
		return IsmUtil.generateRef(getSource());
	}

	@Getter
	@Setter
	private int sourceIndex = 0;

	@Getter
	@Setter
	@JsonIgnore
	private Element target;

	public String getTargetRef() {
		return IsmUtil.generateRef(getTarget());
	}

	@Getter
	@Setter
	private int targetIndex = 0;
}
