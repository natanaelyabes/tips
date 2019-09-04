package io.iochord.apps.ips.model.sbpnet.v1.impl;

import java.util.Map;
import java.util.TreeMap;

import io.iochord.apps.ips.model.sbpnet.v1.Configuration;
import io.iochord.apps.ips.model.sbpnet.v1.Connector;
import io.iochord.apps.ips.model.sbpnet.v1.Data;
import io.iochord.apps.ips.model.sbpnet.v1.Element;
import io.iochord.apps.ips.model.sbpnet.v1.Node;
import io.iochord.apps.ips.model.sbpnet.v1.Page;
import io.iochord.apps.ips.model.sbpnet.v1.Sbpnet;
import io.iochord.apps.ips.model.sbpnet.v1.SbpnetFactory;
import io.iochord.apps.ips.model.sbpnet.v1.components.Activity;
import io.iochord.apps.ips.model.sbpnet.v1.components.Branch;
import io.iochord.apps.ips.model.sbpnet.v1.components.DataTable;
import io.iochord.apps.ips.model.sbpnet.v1.components.Function;
import io.iochord.apps.ips.model.sbpnet.v1.components.Generator;
import io.iochord.apps.ips.model.sbpnet.v1.components.Monitor;
import io.iochord.apps.ips.model.sbpnet.v1.components.ObjectType;
import io.iochord.apps.ips.model.sbpnet.v1.components.Queue;
import io.iochord.apps.ips.model.sbpnet.v1.components.Resource;
import io.iochord.apps.ips.model.sbpnet.v1.components.Start;
import io.iochord.apps.ips.model.sbpnet.v1.components.Stop;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.ActivityImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.BranchImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.ControlImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.DataTableImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.FunctionImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.GeneratorImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.MonitorImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.QueueImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.ResourceImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.StartImpl;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.StopImpl;
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
public class SbpnetFactoryImpl implements SbpnetFactory {
	@Getter
	private static final SbpnetFactory instance = new SbpnetFactoryImpl();

	@Getter
	private final Map<String, Class<? extends ElementImpl>> dataImplementations = new TreeMap<>();

	@Getter
	private final Map<String, Class<? extends NodeImpl>> nodeImplementations = new TreeMap<>();

	@Getter
	@Setter
	private int netCounter = 0;
	
	protected SbpnetFactoryImpl() {
		getDataImplementations().put(DataTable.TYPE, DataTableImpl.class);
		getDataImplementations().put(ObjectType.TYPE, ObjectTypeImpl.class);
		getDataImplementations().put(Generator.TYPE, GeneratorImpl.class);
		getDataImplementations().put(Function.TYPE, FunctionImpl.class);
		getDataImplementations().put(Queue.TYPE, QueueImpl.class);
		getDataImplementations().put(Resource.TYPE, ResourceImpl.class);

		getNodeImplementations().put(Start.TYPE, StartImpl.class);
		getNodeImplementations().put(Stop.TYPE, StopImpl.class);
		getNodeImplementations().put(Activity.TYPE, ActivityImpl.class);
		getNodeImplementations().put(Branch.TYPE, BranchImpl.class);
		getNodeImplementations().put(Monitor.TYPE, MonitorImpl.class);
	}

	@Override
	public Sbpnet create() {
		return create(null);
	}

	@Override
	public Sbpnet create(Sbpnet ref) {
		SbpnetImpl net = new SbpnetImpl();
		net.setId("MODEL-" + netCounter);
		addPage(net);
		addConfiguration(net);
		net.setControl(new ControlImpl());
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
					data.setId(page.getId() + "-" + dataType + "-" + String.valueOf(page.getData().size()));
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
					node.setId(page.getId() + "-" + nodeType + "-" + String.valueOf(page.getNodes().size()));
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
	public Connector addConnector(Page page, Element source, Element target) {
		if (page != null) {
			ConnectorImpl arc = new ConnectorImpl();
			arc.setId(page.getId() + "-" + String.valueOf(page.getConnectors().size()));
			arc.setSource(source);
			arc.setTarget(target);
			page.getConnectors().put(arc.getId(), arc);
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

	@Override
	public DataTable addDataTable(Page page) {
		return (DataTable) addData(page, DataTable.TYPE);
	}

	@Override
	public ObjectType addObjectType(Page page) {
		return (ObjectType) addData(page, ObjectType.TYPE);
	}

	@Override
	public Generator addGenerator(Page page) {
		return (Generator) addData(page, Generator.TYPE);
	}

	@Override
	public Function addFunction(Page page) {
		return (Function) addData(page, Function.TYPE);
	}

	@Override
	public Queue addQueue(Page page) {
		return (Queue) addData(page, Queue.TYPE);
	}

	@Override
	public Resource addResource(Page page) {
		return (Resource) addData(page, Resource.TYPE);
	}

	@Override
	public Start addStart(Page page) {
		return (Start) addNode(page, Start.TYPE);
	}

	@Override
	public Stop addStop(Page page) {
		return (Stop) addNode(page, Stop.TYPE);
	}

	@Override
	public Activity addActivity(Page page) {
		return (Activity) addNode(page, Activity.TYPE);
	}

	@Override
	public Branch addBranch(Page page) {
		return (Branch) addNode(page, Branch.TYPE);
	}

	@Override
	public Monitor addMonitor(Page page) {
		return (Monitor) addNode(page, Monitor.TYPE);
	}	
}