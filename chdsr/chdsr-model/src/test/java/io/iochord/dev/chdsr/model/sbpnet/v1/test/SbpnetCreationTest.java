package io.iochord.dev.chdsr.model.sbpnet.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.iochord.dev.chdsr.model.sbpnet.v1.Connector;
import io.iochord.dev.chdsr.model.sbpnet.v1.SbpnetFactory;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Activity;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchGate;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchRule;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.BranchType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Function;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Generator;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Monitor;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue.QUEUE_TYPE;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Resource;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Start;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Stop;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.VariableType;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.BranchImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.DataTableImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.FunctionImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.GeneratorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.MonitorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ObjectTypeImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.QueueImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ResourceImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StartImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StopImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ConnectorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetImpl;
import io.iochord.dev.chdsr.util.SerializationUtil;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SbpnetCreationTest {

	public static final SbpnetFactory factory = SbpnetFactoryImpl.getInstance();
	
	public static final SbpnetFactory getFactory() {
		return factory;
	}

	public static SbpnetImpl net;
	
	public static SbpnetImpl getNet() {
		if (net == null) {
			net = (SbpnetImpl) getFactory().create();
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
		e.getTypes().put(dt.getLabel(), dt);
		e.getTypes().put(dt2.getLabel(), dt2);
		e.getTypes().put(dt3.getLabel(), dt3);
		testSerializeDeserialize(e, ObjectType.class);
		ot = e;
	}
	
	private static GeneratorImpl gen;

	@Test
	public void test03CreateGeneratorComponent() throws Exception {
		GeneratorImpl e = (GeneratorImpl) getFactory().addGenerator(getNet().getDefaultPage());
		e.setLabel("Product Generator");
		e.setObjectType(ot);
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
		e.getInputParameters().put(ot.getId(), ot);
		e.setCode("somecode here");
		e.getOutputVariables().put(ot.getId(), ot);
		testSerializeDeserialize(e, Function.class);
		fn = e;
	}
	
	private static ResourceImpl res;
	
	@Test
	public void test05CreateResourceComponent() throws Exception {
		ResourceImpl e = (ResourceImpl) getFactory().addResource(getNet().getDefaultPage());
		e.setLabel("resourceA");
		e.setGroupId("resourceA");
		e.setData(dt4);
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
		e.setGenerator(gen);
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
		e.setQueue(q);
		e.setFunction(fn);
		e.setResource(res);
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
//	
//	@Test
//	public void test13Sbpnet() throws Exception {
//		testSerializeDeserialize(getNet(), Sbpnet.class);
//	}	
	
}
