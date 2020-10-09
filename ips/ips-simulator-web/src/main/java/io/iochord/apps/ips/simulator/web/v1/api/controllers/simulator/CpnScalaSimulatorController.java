package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Observer;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ResourceImpl;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModelPerModule;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaPerModuleBiConverter;
import io.iochord.apps.ips.model.ism2cpn.monitor.Ism2CpnscalaObserver;
import io.iochord.apps.ips.model.report.ElementStatistics;
import io.iochord.apps.ips.model.report.GroupStatistics;
import io.iochord.apps.ips.model.report.Report;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportConfiguration;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportResult;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportService;
import io.iochord.apps.ips.model.services.data.map.MappingConfiguration;
import io.iochord.apps.ips.model.services.data.map.MappingResource;
import io.iochord.apps.ips.model.services.data.map.MappingResult;
import io.iochord.apps.ips.model.services.data.map.MappingService;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompilerPerModule;
import io.iochord.apps.ips.simulator.compiler.Simulation;
import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver;
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
	private Map<String, Simulation> simulationInstances = new LinkedHashMap<>();

	/**
	 * Simulation observers
	 */
	@Getter
	private Map<String, Ism2CpnscalaObserver> simulationObservers = new LinkedHashMap<>();
	
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
		
		Map<String, ?> mapact = null;
		Map<String, List<String>> mapOrgRes = null;
		Map<String, List<String>> mapActOrg = null;
		Map<String, ResourceImpl> mapResImpl = new HashMap<String, ResourceImpl>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			File pdFile = new File(getClass().getClassLoader().getResource("process_distribution.json").toURI());
			File rmFile = new File(getClass().getClassLoader().getResource("ResourceMiner.json").toURI());

			Map<?, ?> map1 = mapper.readValue(pdFile, Map.class);
			Map<?, ?> map2 = mapper.readValue(rmFile, Map.class);

		    mapact = (Map<String, ?>) map1.get("MODEL_4");
		    mapOrgRes = (Map<String, List<String>>) map2.get("mgroupres");
		    mapActOrg = (Map<String, List<String>>) map2.get("mactgroup");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Page p : graph.getPages().values()) {
			for(String org : mapOrgRes.keySet()) {
				ResourceImpl resImpl = (ResourceImpl) factory.addResource(p);
				resImpl.setLabel(org);
				resImpl.setNumberOfResource(mapOrgRes.get(org).size());
				mapResImpl.put(org, resImpl);
			}
			for (Node rd : p.getNodes().values()) {
				if (rd instanceof Start) {
					StartImpl d = (StartImpl) rd;
					
					String MATH_ROUND_GEN = "Math.round(Gaussian(18.444333996023857,37.605588692352455).draw())";
					
					ObjectTypeImpl obj = (ObjectTypeImpl) factory.addObjectType(p);
					obj.setLabel("Unit");
					
					GeneratorImpl gen = (GeneratorImpl) factory.addGenerator(p);
					gen.setLabel("Generator");
					gen.setObjectType(new Referenceable<>(obj));
					gen.setExpression(MATH_ROUND_GEN);
					gen.setUnit(TimeUnit.SECONDS);
					
					d.setGenerator(new Referenceable<>(gen));
				}
				
				if (rd instanceof Activity) {
					ActivityImpl act = (ActivityImpl) rd;
					System.out.println(act.getId()+" - "+act.getLabel());
					act.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
					Map<?, List> mapdist = (Map<?,List>)mapact.get(rd.getLabel());
					if(mapdist != null) {
						double param1 = (double) mapdist.get("Normal").get(0);
						double param2 = (double) mapdist.get("Normal").get(1);
						act.setProcessingTimeExpression(" Math.round(Gaussian("+param1+","+param2+").draw()) ");
						ResourceImpl resImpl = mapResImpl.get(mapActOrg.get(rd.getLabel()).get(0));
						act.setResource(new Referenceable<>(resImpl));
					}
					//System.out.println(rd.getLabel()+" : "+mapact.get(rd.getLabel()));
				}
				
				if (rd instanceof Branch) {
					BranchImpl branch = (BranchImpl) rd;
					boolean xorFinBranch = false;
					List<Connector> cons = branch.getOutputConnectors();
					for(Connector con : cons) {
						Element el = con.getTarget().getValue();
						if(branch.getType() == BranchType.SPLIT && branch.getGate() == BranchGate.XOR && el instanceof Branch && el.getLabel().equals("jb-stop")) {
							xorFinBranch = true;
							break;
						}
					}
					if(xorFinBranch) {
						branch.setRule(BranchRule.DATA);
						String dataStr = "data";
						for(Connector con : cons) {
							ConnectorImpl conImpl = (ConnectorImpl) con;
							Element el = con.getTarget().getValue();
							if(el instanceof Branch && el.getLabel().equals("jb-stop")) {
								conImpl.getAttributes().put(dataStr, "b.data.get.inc >= 3");
							}
							else {
								conImpl.getAttributes().put(dataStr, "b.data.get.inc < 3");
							}
						}
					}
				}
			}
		}
		
		return postLoadNPlayDirect(graph);
	}
	
	public Report postLoadNPlayDirect(IsmGraphImpl graph) {
		Ism2CpnscalaPerModuleBiConverter converter = new Ism2CpnscalaPerModuleBiConverter();
		Ism2CpnscalaModelPerModule conversionResult = converter.convert(graph);
		//Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		//Ism2CpnscalaModel conversionResult = converter.convert(graph);
		Report report= new Report();
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
		String modelId = "ips_simulation_" + System.currentTimeMillis() + "";
		String filePath = setupObservers(modelId, graph, conversionResult, gsg, gsa, gsr);
		LoggerUtil.logInfo("SIM: Generate Replay");
		String replayId = parseReport(modelId, filePath);
		report.setReplayId(replayId);
		System.out.println(replayId);
		 
		return report;
	}
	
	/**
	 * Load and simulate ISM model
	 */
	@PostMapping(value = BASE_URI + "/loadnplay")
	public Report postLoadNPlay(@RequestBody IsmGraphImpl graph) {
		graph.loadReferences();
		LoggerUtil.logInfo("SIM: Convert to CPNScala");
		Ism2CpnscalaPerModuleBiConverter converter = new Ism2CpnscalaPerModuleBiConverter();
		Ism2CpnscalaModelPerModule conversionResult = converter.convert(graph);
		//Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		//Ism2CpnscalaModel conversionResult = converter.convert(graph);
		LoggerUtil.logInfo("SIM: Subscribe Reporter");
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
		String modelId = "ips_simulation_" + System.currentTimeMillis() + "";
		String filePath = setupObservers(modelId, graph, conversionResult, gsg, gsa, gsr);
		LoggerUtil.logInfo("SIM: Generate Replay");
		String replayId = parseReport(modelId, filePath);
		report.setReplayId(replayId);
		LoggerUtil.logInfo(replayId);
		
		return report;
	}
	
	public String parseReport(String modelId, String filePath) {
		try {
			ServiceContext context = getServiceContext();
			CsvDataImportConfiguration imConf = new CsvDataImportConfiguration();
			File file = new File(filePath);
			imConf.setName(modelId);
			imConf.setFilename(file.getName());
			imConf.setReader(new FileReader(filePath));
			imConf.setDelimeter('|');
			CsvDataImportResult imRes = new CsvDataImportService().run(context, imConf);
			MappingConfiguration mapConf = new MappingConfiguration();
			mapConf.setDatasetId(imRes.getName());
			MappingResource mapRsc = new MappingResource();
			// ci,eo,ea,er,es,ec
			mapRsc.setMapSettings(new LinkedHashMap<>());
			mapRsc.getMapSettings().put("c0", "ci");
			mapRsc.getMapSettings().put("c1", "eo");
			mapRsc.getMapSettings().put("c2", "ea");
			mapRsc.getMapSettings().put("c3", "er");
			mapRsc.getMapSettings().put("c4", "es");
			mapRsc.getMapSettings().put("c5", "ec");
			mapConf.setResource(mapRsc);
			MappingResult mapRes = new MappingService().run(context, mapConf);
			return mapRes.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String setupObservers(String modelId, IsmGraphImpl graph, Ism2CpnscalaModelPerModule conversionResult, GroupStatistics gsg, GroupStatistics gsa, GroupStatistics gsr) {
		File f = new File("replay");
		if (!f.exists()) {
			f.mkdirs();
		}
		String filePath = String.valueOf("replay/"+modelId+".csv");
		
		try {
			String simHash = DigestUtils.md5DigestAsHex(SerializationUtil.encode(graph).getBytes());
			if (!simulationInstances.containsKey(simHash)) {
				LoggerUtil.logInfo("SIM: Instance Not Found, Compiling Simulation Module");
//				StringBuilder s = new StringBuilder();
//				for (String c : conversionResult.getConvertedModel().values()) {
//					s.append(c).append("---------");
//				}
//				LoggerUtil.logInfo(s.toString());
				MemoryScalaCompilerPerModule msfc = new MemoryScalaCompilerPerModule(conversionResult.getConvertedModel());
				//MemoryScalaCompiler msfc = new MemoryScalaCompiler(conversionResult.getConvertedModel());
				Ism2CpnscalaObserver observerInstance = conversionResult.getKpiObserver(); 
				Simulation simulationInstance = msfc.getInstance();
				simulationInstance.addObserver(observerInstance);
				simulationInstances.put(simHash, simulationInstance);
				simulationObservers.put(simHash, observerInstance);
			}
			Simulation simulationInstance = simulationInstances.get(simHash);
			Ism2CpnscalaObserver observerInstance = simulationObservers.get(simHash);
			simulationInstance.setFileReportPath(filePath);
			// simulationInstance.setToInitialState();
			observerInstance.reset();
			LoggerUtil.logInfo("SIM: Start Simulation");
			//simulationInstance.runUntilMaxArrival();
			//simulationInstance.runUntilGlobalTime(200);
			long simulationHorizon = 24 * 3600;
			observerInstance.setSimulationHorizon(simulationHorizon);
			simulationInstance.runUntilGlobalTime(simulationHorizon);
			Map<Element, ElementStatistics> stats = observerInstance.getData();
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
