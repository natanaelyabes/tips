package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.Map;
import java.util.TreeMap;

import io.iochord.dev.chdsr.model.sbpnet.v1.Arc;
import io.iochord.dev.chdsr.model.sbpnet.v1.Configuration;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import io.iochord.dev.chdsr.model.sbpnet.v1.Node;
import io.iochord.dev.chdsr.model.sbpnet.v1.Page;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.SbpnetFactory;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.BranchImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.DataTableImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.DeclarationImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.EndImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.FunctionImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.MonitorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.MovingUnitImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.QueueImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ResourceImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StartImpl;
import lombok.Getter;

public class SbpnetFactoryImpl implements SbpnetFactory {
	
	@Getter
	private static final SbpnetFactory instance = new SbpnetFactoryImpl();

	@Getter
	private final Map<String, Class<? extends ElementImpl>> dataImplementations = new TreeMap<>();
	
	@Getter
	private final Map<String, Class<? extends NodeImpl>> nodeImplementations = new TreeMap<>();
	
	protected SbpnetFactoryImpl() {
		getDataImplementations().put("datatable", DataTableImpl.class);
		getDataImplementations().put("declaration", DeclarationImpl.class);
		getDataImplementations().put("movingunit", MovingUnitImpl.class);
		getDataImplementations().put("function", FunctionImpl.class);
		getDataImplementations().put("queue", QueueImpl.class);
		getDataImplementations().put("resource", ResourceImpl.class);

		getNodeImplementations().put("start", StartImpl.class);
		getNodeImplementations().put("end", EndImpl.class);
		getNodeImplementations().put("activity", ActivityImpl.class);
		getNodeImplementations().put("branch", BranchImpl.class);
		getNodeImplementations().put("monitor", MonitorImpl.class);
	}
	
	
	@Override
	public Sbpnet create() {
		return create(null);
	}

	@Override
	public Sbpnet create(Sbpnet ref) {
		SbpnetImpl net = new SbpnetImpl();
		addPage(net);
		return net;
	}

	@Override
	public Page addPage(Sbpnet net) {
		if (net != null) {
			PageImpl page = new PageImpl();
			page.setId(String.valueOf(net.getPages().size()));
			net.getPages().put(page.getId(), page);
			return page;
		}
		return null;
	}

	@Override
	public Data addData(Page page, String dataType) {
		if (page != null) {
			String dt = dataType.toLowerCase();
			if (getDataImplementations().containsKey(dt)) {
				try {
					DataImpl data = (DataImpl) getDataImplementations().get(dt).getConstructors()[0].newInstance();
					data.setId(page.getId() + "-" + String.valueOf(page.getData().size()));
					page.getData().put(data.getId(), data);
					return data;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Node addNode(Page page, String nodeType) {
		if (page != null) {
			String nt = nodeType.toLowerCase();
			if (getNodeImplementations().containsKey(nt)) {
				try {
					NodeImpl node = (NodeImpl) getNodeImplementations().get(nt).getConstructors()[0].newInstance();
					node.setId(page.getId() + "-" + String.valueOf(page.getNodes().size()));
					page.getNodes().put(node.getId(), node);
					return node;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Arc addArc(Page page, Element source, Element target) {
		if (page != null) {
			ArcImpl arc = new ArcImpl();
			arc.setId(page.getId() + "-" + String.valueOf(page.getArcs().size()));
			arc.setSource(source);
			arc.setTarget(target);
			page.getArcs().put(arc.getId(), arc);
			return arc;
		}
		return null;
	}

	@Override
	public Configuration addConfiguration(Sbpnet net) {
		if (net != null) {
			ConfigurationImpl config = new ConfigurationImpl();
			config.setId(String.valueOf(net.getConfigurations().size()));
			net.getConfigurations().put(config.getId(), config);
			return config;
		}
		return null;
	}


}
