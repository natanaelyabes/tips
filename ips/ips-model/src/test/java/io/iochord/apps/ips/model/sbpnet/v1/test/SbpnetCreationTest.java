package io.iochord.apps.ips.model.sbpnet.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.data.Queue.QUEUE_TYPE;
import io.iochord.apps.ips.model.ism.v1.data.impl.DataTableImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.FunctionImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.QueueImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ResourceImpl;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Monitor;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.VariableType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.MonitorImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StopImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SbpnetCreationTest {

	public static final IsmFactory factory = IsmFactoryImpl.getInstance();
	
	public static final IsmFactory getFactory() {
		return factory;
	}

	public static IsmGraphImpl net;
	
	public static IsmGraphImpl getNet() {
		if (net == null) {
			net = (IsmGraphImpl) getFactory().create();
		}
		return net;
	}
	
	private <T> void testSerializeDeserialize(T obj, Class<T> type) {
		System.out.println("======================================== " + type.getName());
		System.out.println("---------- Serialization Test");
		System.out.println(SerializationUtil.encodePretty(obj));
		String json = SerializationUtil.encode(obj);
		assertNotEquals(json, null, "Serialization Test");
		System.out.println("---------- Deserialization Test");
		T obj2 = SerializationUtil.decode(json, type);
		assertNotEquals(obj2, null);
		System.out.println("---------- JSON Equality Test");
		String json2 = SerializationUtil.encode(obj);
		assertEquals(json, json2);
//		System.out.println("---------- Object Equality Test");
//		assertEquals(obj, obj2);
	}
	
	private static DataTableImpl dt, dt2, dt3, dt4;

	@Test
	public void test01CreateDataTableComponent() throws Exception {
		DataTableImpl e = (DataTableImpl) getFactory().addDataTable(getNet().getDefaultPage());
		e.setLabel("carType");
		e.getFields().put("carType", "string");
		e.getData().put("carType", new LinkedHashMap<>());
		e.getData().get("carType").put("Big", "B");
		e.getData().get("carType").put("Medium", "M");
		e.getData().get("carType").put("Small", "S");
		testSerializeDeserialize(e, DataTable.class);
		dt = e;
		dt2 = (DataTableImpl) getFactory().addDataTable(getNet().getDefaultPage());
		dt2.setLabel("carSize");
		dt3 = (DataTableImpl) getFactory().addDataTable(getNet().getDefaultPage());
		dt3.setLabel("cost");
		dt4 = (DataTableImpl) getFactory().addDataTable(getNet().getDefaultPage());
		dt4.setLabel("resourceA");
	}
	
	private static ObjectTypeImpl ot;

	@Test
	public void test02CreateObjectTypeComponent() throws Exception {
		ObjectTypeImpl e = (ObjectTypeImpl) getFactory().addObjectType(getNet().getDefaultPage());
		e.setLabel("CAR_DEC");
		e.getTypes().put(dt.getLabel(), new Referenceable<>(dt));
		e.getTypes().put(dt2.getLabel(), new Referenceable<>(dt2));
		e.getTypes().put(dt3.getLabel(), new Referenceable<>(dt3));
		testSerializeDeserialize(e, ObjectType.class);
		ot = e;
	}
	
	private static GeneratorImpl gen;

	@Test
	public void test03CreateGeneratorComponent() throws Exception {
		GeneratorImpl e = (GeneratorImpl) getFactory().addGenerator(getNet().getDefaultPage());
		e.setLabel("Product Generator");
		e.setObjectType(new Referenceable<>(ot));
		e.setExpression("1");
		e.setMaxArrival(100);
		testSerializeDeserialize(e, Generator.class);
		gen = e;
	}
	
	private static FunctionImpl fn;

	@Test
	public void test04CreateFunctionComponent() throws Exception {
		FunctionImpl e = (FunctionImpl) getFactory().addFunction(getNet().getDefaultPage());
		e.setLabel("CAR_FUNC");
		e.getInputParameters().put(ot.getId(), new Referenceable<>(ot));
		e.setCode("somecode here");
		e.getOutputVariables().put(ot.getId(), new Referenceable<>(ot));
		testSerializeDeserialize(e, Function.class);
		fn = e;
	}
	
	private static ResourceImpl res;
	
	@Test
	public void test05CreateResourceComponent() throws Exception {
		ResourceImpl e = (ResourceImpl) getFactory().addResource(getNet().getDefaultPage());
		e.setLabel("resourceA");
		e.setGroupId("resourceA");
		e.setData(new Referenceable<>(dt4));
		testSerializeDeserialize(e, Resource.class);
		res = e;
	}
	
	private static QueueImpl q;

	@Test
	public void test06CreateQueueComponent() throws Exception {
		QueueImpl e = (QueueImpl) getFactory().addQueue(getNet().getDefaultPage());
		e.setLabel("QUEUE A");
		e.setType(QUEUE_TYPE.LIFO);
		e.setSingle(false);
		e.getSizes().put(res.getId(), 10);
		testSerializeDeserialize(e, Queue.class);
		q = e;
	}
	
	private static StartImpl st;

	@Test
	public void test07CreateStartComponent() throws Exception {
		StartImpl e = (StartImpl) getFactory().addStart(getNet().getDefaultPage());
		e.setLabel("Initiate Process");
		e.setGenerator(new Referenceable<>(gen));
		testSerializeDeserialize(e, Start.class);
		st = e;
	}

	@Test
	public void test08CreateStopComponent() throws Exception {
		StopImpl e = (StopImpl) getFactory().addStop(getNet().getDefaultPage());
		e.setLabel("Dispose process");
		testSerializeDeserialize(e, Stop.class);
	}

	private static ActivityImpl act;
	
	@Test
	public void test09CreateActivityComponent() throws Exception {
		ActivityImpl e = (ActivityImpl) getFactory().addActivity(getNet().getDefaultPage());
		e.setLabel("Activity A");
		e.setQueue(new Referenceable<>(q));
		e.setFunction(new Referenceable<>(fn));
		e.setResource(new Referenceable<>(res));
		e.setVariable(VariableType.NONE);
		testSerializeDeserialize(e, Activity.class);
		act = e;
	}

	@Test
	public void test10CreateBranchComponent() throws Exception {
		BranchImpl e = (BranchImpl) getFactory().addBranch(getNet().getDefaultPage());
		e.setGate(BranchGate.XOR);
		e.setType(BranchType.SPLIT);
		e.setRule(BranchRule.PROBABILITY);
		e.getConditions().put("0", "0.6");
		e.getConditions().put("1", "0.4");
		testSerializeDeserialize(e, Branch.class);
	}

	@Test
	public void test11CreateMonitorComponent() throws Exception {
		MonitorImpl e = (MonitorImpl) getFactory().addMonitor(getNet().getDefaultPage());
		testSerializeDeserialize(e, Monitor.class);
	}

	@Test
	public void test12CreateConnector() throws Exception {
		ConnectorImpl e = (ConnectorImpl) getFactory().addConnector(getNet().getDefaultPage(), st, act);
		testSerializeDeserialize(e, Connector.class);
	}
	
	@Test
	public void test13Sbpnet() throws Exception {
		testSerializeDeserialize(getNet(), IsmGraph.class);
	}	
	
}
