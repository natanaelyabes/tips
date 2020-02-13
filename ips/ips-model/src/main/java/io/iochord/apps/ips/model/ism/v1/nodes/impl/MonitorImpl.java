package io.iochord.apps.ips.model.ism.v1.nodes.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.impl.NodeImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Monitor;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.NODE_MONITOR)
public class MonitorImpl extends NodeImpl implements Monitor {
	public String getElementType() {
		return ElementType.NODE_MONITOR;
	}
	
}
