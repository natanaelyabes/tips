package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.model.ElementStatistics;
import io.iochord.apps.ips.model.GroupStatistics;
import io.iochord.apps.ips.model.Report;
import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompiler;
import io.iochord.apps.ips.simulator.compiler.Simulation;
import lombok.Getter;
import scala.Some;
import scala.Tuple2;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.mutable.HashMap;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@RestController
@CrossOrigin
public class CpnScalaSimulatorController extends ASimulatorController {
	public static final String BASE_URI = ASimulatorController.BASE_URI + "/cpnscala";

	@Getter
	private List<Simulation> simulationInstances = new ArrayList<>();

	@Getter
	private List<Observer> simulationObservers = new ArrayList<>();
	
	@RequestMapping(value = BASE_URI + "/loadnplay", method = RequestMethod.POST)
	public Report postLoadNPlay(@RequestBody IsmGraphImpl graph) {
		graph.loadReferences();
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		String conversionResult = converter.convert(graph);
		System.out.println(conversionResult);
		Report report = new Report();
		// MOCKUP REPORT START HERE
		GroupStatistics gs;
		ElementStatistics es, es2;
		// Add Group
		gs = new GroupStatistics("GENERAL");
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
			// Add Element Row
			es = new ElementStatistics("Container Generator", "Generator", "Instance Generated", 100l, 1000.0, 10.0, 100.0);
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			// Add Element Row
			es = new ElementStatistics("Start", "Start", "Instance Started", 100l, 0.0, 0.0, 0.0);
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			// Add Element Row
			es = new ElementStatistics("Stop", "Stop", "Instance Stopped", 100l, 0.0, 0.0, 0.0);
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// Add Group
		gs = new GroupStatistics("ACTIVITIES");
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
			// Add Element Row
			es = new ElementStatistics("Quayside Discharge", "Activity");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Move Discharge", "Activity");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Yardside Discharge", "Activity");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Yardside Loading", "Activity");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Move Loading", "Activity");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Quayside Loading", "Activity");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// Add Group
		gs = new GroupStatistics("RESOURCES");
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
			// Add Element Row
			es = new ElementStatistics("Yard Trucks", "Resource");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Used", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Idle Time", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Turnaround Time", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Yard Cranes", "Resource");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Used", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Idle Time", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Turnaround Time", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			// Add Element Row
			es = new ElementStatistics("Quay Cranes", "Resource");
			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
			es2 = new ElementStatistics("Instance Used", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Idle Time", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
			es2 = new ElementStatistics("Turnaround Time", 100l, 1000.0, 10.0, 100.0);
			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);

//		try {
//			MemoryScalaCompiler msfc = new MemoryScalaCompiler(conversionResult);
//			Simulation simulationInstance = msfc.getInstance();
//			Observer obs = new Observer() {
//				
//				@SuppressWarnings({ "rawtypes", "unchecked" })
//				@Override
//				public void update(Observable o, Object arg) {
//					Tuple5 tuple5 = (Tuple5) arg;
//					System.out.println("JAVAOBS: " + o);
//					System.out.println(tuple5._4().getClass().getCanonicalName());
//					HashMap before = (HashMap) tuple5._4();
//					Iterator it = before.keysIterator();
//					while (it.hasNext()) {
//						Tuple2 k = (Tuple2) it.next();
//						Some v = (Some) before.get((Object) k);
//						System.out.println("KB " + k);
//						System.out.println("VB " + v.get());
//					}
//					HashMap after = (HashMap) tuple5._5();
//					Iterator it2 = after.keysIterator();
//					while (it2.hasNext()) {
//						Tuple2 k = (Tuple2) it2.next();
//						Some v = (Some) after.get((Object) k);
//						System.out.println("KA " + k);
//						System.out.println("VA " + v.get());
//					}
//					System.out.println(arg);
//				}
//				
//			};
//			simulationInstance.addObserver(obs);
//			simulationInstances.add(simulationInstance);
//			simulationObservers.add(obs);
//			simulationInstance.runUntilMaxArrival();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		return report;
	}

	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/load/{cpnId}")
	public String getLoad() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/step/{cpnId}/{n}")
	public String getStep() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/warptime/{cpnId}/{t}")
	public String getWarp() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/fastforward/{cpnId}")
	public String getFastForward() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/start")
	public String start() {
		return "Ok";
	}

//*/
	@RequestMapping(BASE_URI + "/test")
	public String test() {
		String myclassSyntax = "new Simulation {\n"+
	      "val test1 = \"Simulation MODEL-0\"\n"+
	      "override def runSimulation(cpnGraph: CPNGraph):Unit ="+
	        "{ println(\"Run Simulation\") }\n"+ 
	      "override def expState():Unit ="+
	        "{ println(\"Explore State\") }\n"+ 
	      "override def calcKPI[T](kpiFunc:T):Double ="+
	        "{ println(\"Calculate KPI\"); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()); return 5.0 }\n "+  
	      "def test(var1:String):Unit = "+
	        "{ println(var1) }\n"+
	      "class Coba1 { val varc1 = \"lain lagi\" }\n"+
	    "}";
			    
		MemoryScalaCompiler memoryScalaFactory = new MemoryScalaCompiler(myclassSyntax);
	    Simulation memoryScala = memoryScalaFactory.getInstance();
	    memoryScala.simulator();
	    String result = "-";// = (String) memoryScala.getVar("test1");
		return result;
	} 
	//*/
}
