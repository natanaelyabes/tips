package io.iochord.apps.ips.model.ihm.v1.impl;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ihm.v1.Connector;
import io.iochord.apps.ips.model.ihm.v1.Element;
import io.iochord.apps.ips.model.ihm.v1.IhmFactory;
import io.iochord.apps.ips.model.ihm.v1.IhmGraph;
import io.iochord.apps.ips.model.ihm.v1.Model;
import io.iochord.apps.ips.model.ihm.v1.Node;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class IhmFactoryImpl extends ElementImpl implements IhmFactory {

	@Getter
	private static final IhmFactory instance = new IhmFactoryImpl();
	
	@Getter
	@Setter
	private int netCounter = 0;
	
	protected IhmFactoryImpl() {
		//
	}

	@Override
	public IhmGraph create() {
		return create(null);
	}

	@Override
	public IhmGraph create(IhmGraph ref) {
		IhmGraphImpl cnet = new IhmGraphImpl();
		cnet.setId("MODEL-" + netCounter);
		addModel(cnet);
		return cnet;
	}

	@Override
	public Model addModel(IhmGraph cnet) {
		if (cnet != null) {
			ModelImpl model = new ModelImpl();
			model.setId(String.valueOf(cnet.getModels().size()));
			cnet.getModels().put(model.getId(), model);
			return model;
		}
		return null;
	}

	@Override
	public Node addNode(Model model) {
		if (model != null) {
			NodeImpl node = new NodeImpl();
			node.setId(model.getId() + "-" + model.getNodes().size());
			model.getNodes().put(node.getId(), node);
			return node;
		}
		return null;
	}

	@Override
	public Connector addConnector(Model model, Element source, Element target) {
		if (model != null) {
			ConnectorImpl conn = new ConnectorImpl();
			conn.setId(model.getId() + "-" + model.getNodes().size());
			conn.setSource(new Referenceable<Element>(source));
			conn.setTarget(new Referenceable<Element>(source));
			model.getConnectors().put(conn.getId(), conn);
			return conn;
		}
		return null;
	}
}
