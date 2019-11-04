package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Configuration;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
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
@JsonTypeName(IsmGraph.TYPE)
public class IsmGraphImpl extends ElementImpl implements IsmGraph {
	@Getter
	private final String elementType = IsmGraph.TYPE;

	@Getter
	private final String version = "1.0";

	@Getter
	@Setter
	private Map<String, Page> pages = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Configuration> configurations = new LinkedHashMap<>();

	@Getter
	@Setter
	private Control control;

	@Getter
	@Setter
	private Map<String, Data> data = new LinkedHashMap<>();

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
				if (rd instanceof ObjectType) {
					ObjectType d = (ObjectType) rd;
				} else if (rd instanceof Generator) {
					Generator d = (Generator) rd;
					if (d.getObjectType() != null) {
						d.getObjectType().setValueRepository(p.getData());
					}
				} else if (rd instanceof Resource) {
					Resource d = (Resource) rd;
					if (d.getData() != null) {
						d.getData().setValueRepository(p.getData());
					}
					if (d.getDataTable() != null) {
						d.getDataTable().setValueRepository(p.getData());
					}
				} else if (rd instanceof Function) {
					Function d = (Function) rd;
				}
			}
			for (Node rd : p.getNodes().values()) {
				if (rd instanceof Activity) {
					Activity d = (Activity) rd;
					if (d.getResource() != null) {
						d.getResource().setValueRepository(p.getData());
					}
					if (d.getQueue() != null) {
						d.getQueue().setValueRepository(p.getData());
					}
					if (d.getFunction() != null) {
						d.getFunction().setValueRepository(p.getData());
					}
				}
			}
			for (Connector rd : p.getConnectors().values()) {
				if (rd instanceof Connector) {
					Connector d = (Connector) rd;
					if (d.getSource() != null) {
						d.getSource().setValueRepository(p.getNodes());
					}
					if (d.getTarget() != null) {
						d.getTarget().setValueRepository(p.getNodes());
					}
				}
			}
		}
		
	}

}
