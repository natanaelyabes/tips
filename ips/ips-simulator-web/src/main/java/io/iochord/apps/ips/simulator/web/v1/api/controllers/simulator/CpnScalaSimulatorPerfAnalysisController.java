package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.simulator.compiler.GenGraph;
import io.iochord.apps.ips.simulator.engine.SimulatorPerformAnalysisJava;

/**
 *
 * @package chdsr-simulator-web
 * @author  Nur Ichsan Utama <ichsan83@gmail.com>
 * @since   2019
 *
 *
 */
@RestController
@CrossOrigin
public class CpnScalaSimulatorPerfAnalysisController extends ASimulatorController {
	public static final String BASE_URI = ASimulatorController.BASE_URI + "/cpnscala";
	
  @RequestMapping(value = BASE_URI + "/atm/perf/generate", method = RequestMethod.POST)
	public void test01CpnScalaCreation() throws Exception {
		IsmGraph snet = IsmExample.createBankExample();
		System.out.println(SerializationUtil.encode(snet));
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		String net = converter.convert(snet);
		
		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net);
		out.close();
	}
  
	@RequestMapping(value = BASE_URI + "/atm/perf/{noStep}", method = RequestMethod.POST)
	public String perfATMWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, "simulscala.txt");
	}
	
	@RequestMapping(value = BASE_URI + "/big/perf/{noStep}", method = RequestMethod.POST)
	public String perfBIGWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, "bigsimulscala.txt");
	}
	
	@RequestMapping(value = BASE_URI + "/flex/perf/{noStep}", method = RequestMethod.POST)
	public String perfFlexWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, null);
	}
	
	@RequestMapping(value = BASE_URI + "/flex/cgraph/{noTrans}", method = RequestMethod.GET)
	public String perfCreateGraph(@PathVariable("noTrans") int noTrans) {
		GenGraph gg = new GenGraph();
		return gg.create(noTrans);
	}
	
	@RequestMapping(value = BASE_URI + "/flex/cgraphperf/{noTrans}/{noStep}", method = RequestMethod.POST)
	public String perfCreateGraphAndFlexWithSpecNumbToken(@PathVariable("noTrans") int noTrans, @PathVariable("noStep") int noStep, @RequestBody String jsonStr) throws FileNotFoundException {
		GenGraph gg = new GenGraph();
		
		String fpath = "flex-"+System.currentTimeMillis()+".txt";
		
		PrintWriter out = new PrintWriter(fpath);
		out.write(gg.create(noTrans));
		out.close();
		
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, fpath);
	}
}
