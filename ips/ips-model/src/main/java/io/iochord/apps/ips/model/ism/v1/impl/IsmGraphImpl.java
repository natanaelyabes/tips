package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Configuration;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.ELEMENT_NET)
public class IsmGraphImpl extends ElementImpl implements IsmGraph {
	private static final String VERSION = "1.0";

	@Getter
	@Setter
	private Map<String, Page> pages = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Configuration> configurations = new LinkedHashMap<>();

	@Getter
	@Setter
	private Control control;
	
	public String getElementType() {
		return ElementType.ELEMENT_NET;
	}
	
	public String getVersion() {
		return VERSION;
	}

	@Override
	public Page getDefaultPage() {
		if (getPages() != null) {
			return getPages().values().iterator().next();
		}
		return null;
	}

	@Override
	public void loadReferences() {
		for (Page p : pages.values()) {
			for (Data rd : p.getData().values()) {
				if (rd instanceof Generator) {
					Generator d = (Generator) rd;
					d.getObjectType().setValueRepository(p.getData());
				}
			}
			for (Node rd : p.getNodes().values()) {
				if (rd instanceof Activity) {
					Activity d = (Activity) rd;
					if(d.getResource() != null)
						d.getResource().setValueRepository(p.getData());
					if(d.getQueue() != null)
						d.getQueue().setValueRepository(p.getData());
					if(d.getFunction() != null)
						d.getFunction().setValueRepository(p.getData());
				} else if (rd instanceof Start) {
					Start d = (Start) rd;
					d.getGenerator().setValueRepository(p.getData());
				}
			}
			for (Connector rd : p.getConnectors().values()) {
				if (rd instanceof Connector) {
					Connector d = rd;
					d.getSource().setValueRepository(p.getNodes());
					d.getTarget().setValueRepository(p.getNodes());
				}
			}
		}
		
	}

}
