package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Observer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaPerModuleBiConverter;
import io.iochord.apps.ips.simulator.compiler.GenGraph;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompilerPerModule;
import io.iochord.apps.ips.simulator.engine.SimulatorPerformAnalysisJava;

/**
 *
 * CPN Scala performance analysis controller (/simulator/cpnscala)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class CpnScalaSimulatorPerfAnalysisController extends ASimulatorController {
	public static final String BASE_URI = ASimulatorController.BASE_URI + "/cpnscala";

	@PostMapping(value = BASE_URI + "/atm/perf/generate")
	public void test01CpnScalaCreation() throws FileNotFoundException {
		IsmGraph snet = IsmExample.createBankExample();
		LoggerUtil.logInfo(SerializationUtil.encode(snet));
		Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		String net = converter.convert(snet).getConvertedModel();

		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net);
		out.close();
	}

	/**
	 * Example method to run simulation by per module compiled
	 */
	@GetMapping(value = BASE_URI + "/atm/permodule/generate")
	public void test01CpnScalaPerModuleCreation() {
		IsmGraph snet = IsmExample.createBankExample();
		LoggerUtil.logInfo(SerializationUtil.encode(snet));
		Ism2CpnscalaPerModuleBiConverter converter = new Ism2CpnscalaPerModuleBiConverter();
		LinkedHashMap<String, String> net = converter.convert(snet).getConvertedModel();
		MemoryScalaCompilerPerModule msfc = new MemoryScalaCompilerPerModule(net);
		io.iochord.apps.ips.simulator.compiler.Simulation simulation = msfc.getInstance();
		Observer obs = (o, arg) -> {
			LoggerUtil.logInfo("JAVAOBS: " + o);
			LoggerUtil.logInfo("" + arg);
		};
		simulation.addObserver(obs);
		simulation.runStep(5);
	}

	@PostMapping(value = BASE_URI + "/atm/perf/{noStep}")
	public String perfATMWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, "simulscala.txt");
	}

	@PostMapping(value = BASE_URI + "/big/perf/{noStep}")
	public String perfBIGWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, "bigsimulscala.txt");
	}

	@PostMapping(value = BASE_URI + "/flex/perf/{noStep}")
	public String perfFlexWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, null);
	}

	@GetMapping(value = BASE_URI + "/flex/cgraph/{noTrans}")
	public String perfCreateGraph(@PathVariable("noTrans") int noTrans) {
		GenGraph gg = new GenGraph();
		return gg.create(noTrans);
	}

	@PostMapping(value = BASE_URI + "/flex/cgraphperf/{noTrans}/{noStep}")
	public String perfCreateGraphAndFlexWithSpecNumbToken(@PathVariable("noTrans") int noTrans,
			@PathVariable("noStep") int noStep, @RequestBody String jsonStr) throws FileNotFoundException {
		GenGraph gg = new GenGraph();

		String fpath = "flex-" + System.currentTimeMillis() + ".txt";

		PrintWriter out = new PrintWriter(fpath);
		out.write(gg.create(noTrans));
		out.close();

		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, fpath);
	}
}
