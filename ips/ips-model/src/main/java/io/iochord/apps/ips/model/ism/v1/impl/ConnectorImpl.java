package io.iochord.apps.ips.model.ism.v1.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(Connector.TYPE)
public class ConnectorImpl extends ElementImpl implements Connector {
	@Getter
	private final String elementType = Connector.TYPE;

	@Getter
	@Setter
	private Referenceable<Element> source;
//	
//	private String sourceRef;
//	
//	public String getSourceRef() {
//		if (source != null) {
//			return IsmUtil.generateRef(getSource());
//		} else {
//			return sourceRef;
//		}
//	}
//	
//	public void setSourceRef(String ref) {
//		sourceRef = ref;
//	}

	@Getter
	@Setter
	private int sourceIndex = 0;

	@Getter
	@Setter
	private Referenceable<Element> target;
	
//	
//	private String targetRef;
//
//	public String getTargetRef() {
//		if (target != null) {
//			return IsmUtil.generateRef(getTarget());
//		} else {
//			return targetRef;
//		}
//	}
//
//	public void setTargetRef(String ref) {
//		targetRef = ref;
//	}

	@Getter
	@Setter
	private int targetIndex = 0;
}
