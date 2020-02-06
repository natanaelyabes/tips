package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModel;
import io.iochord.apps.ips.model.report.ElementStatistics;
import io.iochord.apps.ips.model.report.GroupStatistics;
import io.iochord.apps.ips.model.report.Report;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompiler;
import io.iochord.apps.ips.simulator.compiler.Simulation;
import lombok.Getter;

/**
 * 
 * CPN Scala simulator controller (/simulator/cpnscala)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
@CrossOrigin
public class CpnScalaSimulatorController extends ASimulatorController {
	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = ASimulatorController.BASE_URI + "/cpnscala";

	/**
	 * Simulation instances
	 */
	@Getter
	private List<Simulation> simulationInstances = new ArrayList<>();

	/**
	 * Simulation observers
	 */
	@Getter
	private List<Observer> simulationObservers = new ArrayList<>();

	/**
	 * Load and simulate ISM model
	 */
	@PostMapping(value = BASE_URI + "/loadnplay")
	public Report postLoadNPlay(@RequestBody IsmGraphImpl graph) {
		graph.loadReferences();
		Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		Ism2CpnscalaModel conversionResult = converter.convert(graph);
		System.out.println(conversionResult.getConvertedModel());
		Report report = new Report();
		// MOCKUP REPORT START HERE
		GroupStatistics gs, gsg, gsa, gsr;
		// ElementStatistics es, es2;
		// Add Group
		gs = new GroupStatistics("GENERAL");
		gsg = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		// // Add Element Row
		// es = new ElementStatistics("Container Generator", "Generator", "Instance
		// Generated", 100l, 1000.0, 10.0, 100.0);
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// // Add Element Row
		// es = new ElementStatistics("Start", "Start", "Instance Started", 100l, 0.0,
		// 0.0, 0.0);
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// // Add Element Row
		// es = new ElementStatistics("Stop", "Stop", "Instance Stopped", 100l, 0.0,
		// 0.0, 0.0);
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// Add Group
		gs = new GroupStatistics("ACTIVITIES");
		gsa = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		// // Add Element Row
		// es = new ElementStatistics("Quayside Discharge", "Activity");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Move Discharge", "Activity");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Yardside Discharge", "Activity");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Yardside Loading", "Activity");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Move Loading", "Activity");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Quayside Loading", "Activity");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Processed", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Waiting Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Processing Time", 50l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// Add Group
		gs = new GroupStatistics("RESOURCES");
		gsr = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		// // Add Element Row
		// es = new ElementStatistics("Yard Trucks", "Resource");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Used", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Idle Time", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Turnaround Time", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Yard Cranes", "Resource");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Used", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Idle Time", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Turnaround Time", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// // Add Element Row
		// es = new ElementStatistics("Quay Cranes", "Resource");
		// gs.getElements().put(String.valueOf(gs.getElements().size() + 1), es);
		// es2 = new ElementStatistics("Instance Used", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Idle Time", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);
		// es2 = new ElementStatistics("Turnaround Time", 100l, 1000.0, 10.0, 100.0);
		// es.getSubElements().put(String.valueOf(es.getSubElements().size() + 1), es2);

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
				if (e instanceof Generator || e instanceof Start || e instanceof Stop) {
					gsg.getElements().put(String.valueOf(gsg.getElements().size() + 1), s);
				}
				if (e instanceof Activity) {
					gsa.getElements().put(String.valueOf(gsa.getElements().size() + 1), s);
				}
				if (e instanceof Resource) {
					gsr.getElements().put(String.valueOf(gsr.getElements().size() + 1), s);
				}
				if (s.getSubElements() != null) {
					for (String k : e.getAttributes().keySet()) {
						String[] v = e.getAttributes().get(k).split("\\|");
						if (v.length < 6)
							continue;
						s.getSubElements().put(k,
								new ElementStatistics(v[0], v[1].length() == 0 ? null : Long.parseLong(v[1]),
										v[2].length() == 0 ? null : Double.parseDouble(v[2]),
										v[3].length() == 0 ? null : Double.parseDouble(v[3]),
										v[4].length() == 0 ? null : Double.parseDouble(v[4]),
										v[5].length() == 0 ? null : Double.parseDouble(v[5])));
						if (v.length > 6) {
							s.getSubElements().get(k).setFormat(v[6]);
						}
					}
				}
			}
		} catch (Exception ex) {}
		return report;
	}

	@GetMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/load/{cpnId}")
	public String getLoad() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/step/{cpnId}/{n}")
	public String getStep() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/warptime/{cpnId}/{t}")
	public String getWarp() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/fastforward/{cpnId}")
	public String getFastForward() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/start")
	public String start() {
		return "Ok";
	}

	// */
	@GetMapping(BASE_URI + "/test")
	public String test() {
		String myclassSyntax = "new Simulation {\n" + "val test1 = \"Simulation MODEL-0\"\n"
				+ "override def runSimulation(cpnGraph: CPNGraph):Unit =" + "{ println(\"Run Simulation\") }\n"
				+ "override def expState():Unit =" + "{ println(\"Explore State\") }\n"
				+ "override def calcKPI[T](kpiFunc:T):Double ="
				+ "{ println(\"Calculate KPI\"); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()); return 5.0 }\n "
				+ "def test(var1:String):Unit = " + "{ println(var1) }\n"
				+ "class Coba1 { val varc1 = \"lain lagi\" }\n" + "}";

		MemoryScalaCompiler memoryScalaFactory = new MemoryScalaCompiler(myclassSyntax);
		Simulation memoryScala = memoryScalaFactory.getInstance();
		memoryScala.simulator();
		String result = "-";// = (String) memoryScala.getVar("test1");
		return result;
	}
	// */
}
