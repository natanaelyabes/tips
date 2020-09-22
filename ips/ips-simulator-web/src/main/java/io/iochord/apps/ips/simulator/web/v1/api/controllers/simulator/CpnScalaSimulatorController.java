package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Observer;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithm;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithmDefaultMining;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithmDoingSimilarTask;
import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModel;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModelPerModule;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaPerModuleBiConverter;
import io.iochord.apps.ips.model.report.ElementStatistics;
import io.iochord.apps.ips.model.report.GroupStatistics;
import io.iochord.apps.ips.model.report.Report;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompiler;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompilerPerModule;
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
	 * 
	 * Load and simulate ISM model with datasetId param
	 */
	@PostMapping(value = { BASE_URI + "/loadnplay/{datasetId}" })
	public Report getPostDiscoverIsm(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new IsmDiscoveryConfiguration();
			config.setDatasetId(datasetId.get());
		}
		
		//IsmGraphImpl graph = (IsmGraphImpl) IsmExample.createPortExample();
		
		ServiceContext context = run(new IsmDiscoveryService(), config, IsmGraph.class, headers);
		IsmGraphImpl graph = (IsmGraphImpl) context.getData();
		
		IsmFactory factory = IsmFactoryImpl.getInstance();
		
		for (Page p : graph.getPages().values()) {
			for (Node rd : p.getNodes().values()) {
				if (rd instanceof Start) {
					StartImpl d = (StartImpl) rd;
					
					String MATH_ROUND_GEN = "Math.round(Gaussian(18.45924453280318,38.20021323603019).draw())";
					
					ObjectTypeImpl obj = (ObjectTypeImpl) factory.addObjectType(p);
					obj.setLabel("Unit");
					
					GeneratorImpl gen = (GeneratorImpl) factory.addGenerator(p);
					gen.setLabel("Generator");
					gen.setObjectType(new Referenceable<>(obj));
					gen.setExpression(MATH_ROUND_GEN);
					gen.setUnit(TimeUnit.MINUTES);
					gen.setMaxArrival(50);
					
					d.setGenerator(new Referenceable<>(gen));
				}
			}
			/*
			for (Connector rd : p.getConnectors().values()) {
				if (rd instanceof Connector) {
					Connector d = rd;
					if(d.getSource().getValue() instanceof Branch && ((Branch) d.getSource().getValue()).getGate() == BranchGate.XOR && ((Branch) d.getSource().getValue()).getType() == BranchType.SPLIT) {
						System.out.println("Ini cabang "+d.getSource().getValue().getId()+" - "+d.getSource().getValue().getLabel());
						System.out.println("Ini arc "+d.getSourceIndex()+", "+d.getTargetIndex());
						System.out.println("Ini Destination "+d.getTarget().getValue().getId()+" - "+d.getTarget().getValue().getLabel());
					}
				}
			}
			*/
		}
		
		return postLoadNPlayDirect(graph);
	}
	
	public Report postLoadNPlayDirect(IsmGraphImpl graph) {
		Ism2CpnscalaPerModuleBiConverter converter = new Ism2CpnscalaPerModuleBiConverter();
		Ism2CpnscalaModelPerModule conversionResult = converter.convert(graph);
		//Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		//Ism2CpnscalaModel conversionResult = converter.convert(graph);
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
	
	/**
	 * Load and simulate ISM model
	 */
	@PostMapping(value = BASE_URI + "/loadnplay")
	public Report postLoadNPlay(@RequestBody IsmGraphImpl graph) {
		graph.loadReferences();
		
		Ism2CpnscalaPerModuleBiConverter converter = new Ism2CpnscalaPerModuleBiConverter();
		Ism2CpnscalaModelPerModule conversionResult = converter.convert(graph);
		//Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		//Ism2CpnscalaModel conversionResult = converter.convert(graph);
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

	private String setupObservers(/*Ism2CpnscalaModel*/Ism2CpnscalaModelPerModule conversionResult, GroupStatistics gsg, GroupStatistics gsa, GroupStatistics gsr) {
		String filePath = String.valueOf("Simulation_"+System.currentTimeMillis()+".csv");
		
		try {
			MemoryScalaCompilerPerModule msfc = new MemoryScalaCompilerPerModule(conversionResult.getConvertedModel());
			//MemoryScalaCompiler msfc = new MemoryScalaCompiler(conversionResult.getConvertedModel());
			Simulation simulationInstance = msfc.getInstance();
			simulationInstance.setFileReportPath(filePath);
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
		
		return filePath;
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
