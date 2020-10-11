package io.iochord.apps.ips.simulator.services;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.DigestUtils;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;
import io.iochord.apps.ips.model.ism.v1.nodes.Control.STOPPING_CRITERIA;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;
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
import lombok.Getter;
import lombok.Setter;

public class IsmSimulatorService extends AnIpsAsyncService<IsmGraph, Report> {

	/**
	 * Simulation instances
	 */
	@Getter
	@Setter
	private static Map<String, Simulation> simulationInstances = new LinkedHashMap<>();

	/**
	 * Simulation observers
	 */
	@Getter
	@Setter
	private static Map<String, Ism2CpnscalaObserver> simulationObservers = new LinkedHashMap<>();

	@Override
	public Report run(ServiceContext context, IsmGraph graph) {
		Report report = new Report();
		report.setModelId("ips_simulation_" + System.currentTimeMillis() + "");
		report.setModelHash(DigestUtils.md5DigestAsHex(SerializationUtil.encode(graph).getBytes()));
		graph.loadReferences();

		// Get Simulation Instance
		Object[] si = getSimulationInstance(context, report, graph);
		Simulation simulationInstance = (Simulation) si[0];
		Ism2CpnscalaObserver observerInstance = (Ism2CpnscalaObserver) si[1];
		
		// Run Simulation
		context.updateProgress(20, "Initialize Simulation ...");
		LoggerUtil.logInfo("SIM: Initialize Simulation ...");
		Control control = graph.getControl();
		File f = new File("replay");
		if (!f.exists()) {
			f.mkdirs();
		}
		String filePath = String.valueOf("replay/" + report.getModelId() + ".csv");
		simulationInstance.setFileReportPath(filePath);
		long seed = control == null || control.getSeed() == null ? System.currentTimeMillis() : control.getSeed();
		simulationInstance.setToInitialState(seed);

		observerInstance.setContext(context);
		observerInstance.reset();
		//simulationInstance.runUntilMaxArrival();
		//simulationInstance.runUntilGlobalTime(200);
		context.updateProgress(25, "Running Simulation ...");
		LoggerUtil.logInfo("SIM: Running Simulation ...");
		if (control == null || control.getStoppingCriteria() == STOPPING_CRITERIA.TIME) {
			long simulationHorizon = control == null || control.getStoppingTime() == null ? 24 * 3600 : control.getStoppingTime();
			observerInstance.setSimulationHorizon(simulationHorizon);
			simulationInstance.runUntilGlobalTime(simulationHorizon);
		}

		context.updateProgress(80, "Parsing Report ...");
		LoggerUtil.logInfo("SIM: Parsing Report ...");
		GroupStatistics gs;
		GroupStatistics gsg;
		GroupStatistics gsa;
		GroupStatistics gsr;
		gs = new GroupStatistics("GENERAL"); gsg = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		gs = new GroupStatistics("ACTIVITIES"); gsa = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
		gs = new GroupStatistics("RESOURCES"); gsr = gs;
		report.getGroups().put(String.valueOf(report.getGroups().size() + 1), gs);
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
		
		context.updateProgress(90, "Generate Replay ...");
		LoggerUtil.logInfo("SIM: Generate Replay");
		generateReplay(context, report, graph, filePath);

		context.updateProgress(100, "Done ...");
		return report;
	}

	public Object[] getSimulationInstance(ServiceContext context, Report report, IsmGraph graph) {
		context.updateProgress(0, "Get Simulation Instance ...");
		LoggerUtil.logInfo("SIM: Get Simulation Instance");
		if (!simulationInstances.containsKey(report.getModelHash())) {
			context.updateProgress(5, "Instance Not Found, Converting to CPN Scala ...");
			LoggerUtil.logInfo("SIM: Instance Not Found, Converting to CPNScala");
			Ism2CpnscalaPerModuleBiConverter converter = new Ism2CpnscalaPerModuleBiConverter();
			Ism2CpnscalaModelPerModule cpnScalaModules = converter.convert(graph);

			context.updateProgress(10, "Compiling CPN Scala ...");
			LoggerUtil.logInfo("SIM: Compiling CPN Scala ...");
//			StringBuilder s = new StringBuilder();
//			for (String c : conversionResult.getConvertedModel().values()) {
//				s.append(c).append("---------");
//			}
//			LoggerUtil.logInfo(s.toString());
			MemoryScalaCompilerPerModule msfc = new MemoryScalaCompilerPerModule(cpnScalaModules.getConvertedModel());
			Ism2CpnscalaObserver observerInstance = cpnScalaModules.getKpiObserver(); 
			Simulation simulationInstance = msfc.getInstance(context);

			context.updateProgress(15, "Registering Observers ...");
			LoggerUtil.logInfo("SIM: Registering Observers ...");
			simulationInstance.addObserver(observerInstance);
			simulationInstances.put(report.getModelHash(), simulationInstance);
			simulationObservers.put(report.getModelHash(), observerInstance);
		}
		Simulation simulationInstance = simulationInstances.get(report.getModelHash());
		Ism2CpnscalaObserver observerInstance = simulationObservers.get(report.getModelHash());
		return new Object[] { simulationInstance, observerInstance };
	}

	public void generateReplay(ServiceContext context,  Report report, IsmGraph graph, String filePath) {
		try {
			CsvDataImportConfiguration imConf = new CsvDataImportConfiguration();
			File file = new File(filePath);
			imConf.setName(report.getModelId());
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
			report.setReplayId(mapRes.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateSubElementReport(ElementStatistics s, Element e) {
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

}
