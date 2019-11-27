package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModel;
import io.iochord.apps.ips.model.report.ElementStatistics;
import io.iochord.apps.ips.model.report.GroupStatistics;
import io.iochord.apps.ips.model.report.Report;
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
		Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		Ism2CpnscalaModel conversionResult = converter.convert(graph);
		// System.out.println(conversionResult.getConvertedModel());
		Report report = new Report();
		// MOCKUP REPORT START HERE
		GroupStatistics gs, gsg, gsa, gsr;
		ElementStatistics es, es2;
		// Add Group
		gs = new GroupStatistics("GENERAL");
		gsg = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
//			// Add Element Row
//			es = new ElementStatistics("Container Generator", "Generator", "Instance Generated", 100l, 1000.0, 10.0, 100.0);
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			// Add Element Row
//			es = new ElementStatistics("Start", "Start", "Instance Started", 100l, 0.0, 0.0, 0.0);
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			// Add Element Row
//			es = new ElementStatistics("Stop", "Stop", "Instance Stopped", 100l, 0.0, 0.0, 0.0);
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// Add Group
		gs = new GroupStatistics("ACTIVITIES");
		gsa = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
//			// Add Element Row
//			es = new ElementStatistics("Quayside Discharge", "Activity");
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			// Add Element Row
//			es = new ElementStatistics("Move Discharge", "Activity");
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			// Add Element Row
//			es = new ElementStatistics("Yardside Discharge", "Activity");
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			// Add Element Row
//			es = new ElementStatistics("Yardside Loading", "Activity");
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			// Add Element Row
//			es = new ElementStatistics("Move Loading", "Activity");
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			// Add Element Row
//			es = new ElementStatistics("Quayside Loading", "Activity");
//			gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
//			es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
//			es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
//			es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// Add Group
		gs = new GroupStatistics("RESOURCES");
		gsr = gs;
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

		try {
			MemoryScalaCompiler msfc = new MemoryScalaCompiler(conversionResult.getConvertedModel());
			Simulation simulationInstance = msfc.getInstance();
			simulationInstance.addObserver(conversionResult.getKpiObserver());
			simulationInstances.add(simulationInstance);
			simulationObservers.add(conversionResult.getKpiObserver());
			simulationInstance.runUntilMaxArrival();
			Map<Element, ElementStatistics> stats = conversionResult.getKpiObserver().getData();
			for (Element e : stats.keySet()) {
				ElementStatistics s = stats.get(e);
				if (e instanceof Generator) {
					gsg.getElements().put(String.valueOf(gsg.getElements().size() + 1), s);
				}
				if (e instanceof Activity) {
					gsa.getElements().put(String.valueOf(gsg.getElements().size() + 1), s);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
