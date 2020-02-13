package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.Map;
import java.util.TreeMap;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.model.ism.v1.Configuration;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.data.impl.DataTableImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.FunctionImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.QueueImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ResourceImpl;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Monitor;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.MonitorImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StopImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class IsmFactoryImpl implements IsmFactory {
	@Getter
	private static final IsmFactory instance = new IsmFactoryImpl();

	@Getter
	private final Map<String, Class<? extends ElementImpl>> dataImplementations = new TreeMap<>();

	@Getter
	private final Map<String, Class<? extends NodeImpl>> nodeImplementations = new TreeMap<>();

	@Getter
	@Setter
	private int netCounter = 0;
	
	protected IsmFactoryImpl() {
		getDataImplementations().put(ElementType.DATA_DATATABLE, DataTableImpl.class);
		getDataImplementations().put(ElementType.DATA_OBJECTTYPE, ObjectTypeImpl.class);
		getDataImplementations().put(ElementType.DATA_GENERATOR, GeneratorImpl.class);
		getDataImplementations().put(ElementType.DATA_FUNCTION, FunctionImpl.class);
		getDataImplementations().put(ElementType.DATA_QUEUE, QueueImpl.class);
		getDataImplementations().put(ElementType.DATA_RESOURCE, ResourceImpl.class);

		getNodeImplementations().put(ElementType.NODE_START, StartImpl.class);
		getNodeImplementations().put(ElementType.NODE_STOP, StopImpl.class);
		getNodeImplementations().put(ElementType.NODE_ACTIVITY, ActivityImpl.class);
		getNodeImplementations().put(ElementType.NODE_BRANCH, BranchImpl.class);
		getNodeImplementations().put(ElementType.NODE_MONITOR, MonitorImpl.class);
	}

	@Override
	public IsmGraph create() {
		return create(null);
	}

	@Override
	public IsmGraph create(IsmGraph ref) {
		IsmGraphImpl net = new IsmGraphImpl();
		net.setId("MODEL-" + netCounter);
		addPage(net);
		addConfiguration(net);
		return net;
	}

	@Override
	public Page addPage(IsmGraph net) {
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
					data.setId(page.getId() + "-" + dataType + "-" + page.getData().size());
					page.getData().put(data.getId(), data);
					return data;
				} catch (Exception ex) {
					LoggerUtil.logError(ex);
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
					node.setId(page.getId() + "-" + nodeType + "-" + page.getNodes().size());
					page.getNodes().put(node.getId(), node);
					return node;
				} catch (Exception ex) {
					LoggerUtil.logError(ex);
				}
			}
		}
		return null;
	}

	@Override
	public Connector addConnector(Page page, Element source, Element target) {
		if (page != null) {
			ConnectorImpl arc = new ConnectorImpl();
			arc.setId(page.getId() + "-" + page.getConnectors().size());
			arc.setSource(new Referenceable<Element>(source));
			arc.setTarget(new Referenceable<Element>(target));
			page.getConnectors().put(arc.getId(), arc);
			return arc;
		}
		return null;
	}

	@Override
	public Configuration addConfiguration(IsmGraph net) {
		if (net != null) {
			ConfigurationImpl config = new ConfigurationImpl();
			config.setId(String.valueOf(net.getConfigurations().size()));
			net.getConfigurations().put(config.getId(), config);
			return config;
		}
		return null;
	}

	@Override
	public DataTable addDataTable(Page page) {
		return (DataTable) addData(page, ElementType.DATA_DATATABLE);
	}

	@Override
	public ObjectType addObjectType(Page page) {
		return (ObjectType) addData(page, ElementType.DATA_OBJECTTYPE);
	}

	@Override
	public Generator addGenerator(Page page) {
		return (Generator) addData(page, ElementType.DATA_GENERATOR);
	}

	@Override
	public Function addFunction(Page page) {
		return (Function) addData(page, ElementType.DATA_FUNCTION);
	}

	@Override
	public Queue addQueue(Page page) {
		return (Queue) addData(page, ElementType.DATA_QUEUE);
	}

	@Override
	public Resource addResource(Page page) {
		return (Resource) addData(page, ElementType.DATA_RESOURCE);
	}

	@Override
	public Start addStart(Page page) {
		return (Start) addNode(page, ElementType.NODE_START);
	}

	@Override
	public Stop addStop(Page page) {
		return (Stop) addNode(page, ElementType.NODE_STOP);
	}

	@Override
	public Activity addActivity(Page page) {
		return (Activity) addNode(page, ElementType.NODE_ACTIVITY);
	}

	@Override
	public Branch addBranch(Page page) {
		return (Branch) addNode(page, ElementType.NODE_BRANCH);
	}

	@Override
	public Monitor addMonitor(Page page) {
		return (Monitor) addNode(page, ElementType.NODE_MONITOR);
	}	
}
