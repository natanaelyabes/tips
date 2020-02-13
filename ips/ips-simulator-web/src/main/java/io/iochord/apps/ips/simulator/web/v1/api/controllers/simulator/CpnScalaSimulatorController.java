package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.LoggerUtil;
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
		Report report = new Report();
		GroupStatistics gs; 
		GroupStatistics gsg;
		GroupStatistics gsa;
		GroupStatistics gsr;
		gs = new GroupStatistics("GENERAL");
		gsg = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		gs = new GroupStatistics("ACTIVITIES");
		gsa = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		gs = new GroupStatistics("RESOURCES");
		gsr = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		setupObservers(conversionResult, gsg, gsa, gsr);
		return report;
	}

	private void setupObservers(Ism2CpnscalaModel conversionResult, GroupStatistics gsg, GroupStatistics gsa, GroupStatistics gsr) {
		try {
			MemoryScalaCompiler msfc = new MemoryScalaCompiler(conversionResult.getConvertedModel());
			Simulation simulationInstance = msfc.getInstance();
			simulationInstance.addObserver(conversionResult.getKpiObserver());
			simulationInstances.add(simulationInstance);
			simulationObservers.add(conversionResult.getKpiObserver());
			simulationInstance.runUntilMaxArrival();
			Map<Element, ElementStatistics> stats = conversionResult.getKpiObserver().getData();
			for (Entry<Element, ElementStatistics> es : stats.entrySet()) {
				Element e = es.getKey();
				ElementStatistics s = es.getValue();
				if (e instanceof Generator || e instanceof Start || e instanceof Stop) {
					gsg.getElements().put(String.valueOf(gsg.getElements().size() + 1), s);
				}
				if (e instanceof Activity) {
					gsa.getElements().put(String.valueOf(gsa.getElements().size() + 1), s);
				}
				if (e instanceof Resource) {
					gsr.getElements().put(String.valueOf(gsr.getElements().size() + 1), s);
				}
				generateSubElementReport(s, e);
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
	}

	private void generateSubElementReport(ElementStatistics s, Element e) {
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

}
