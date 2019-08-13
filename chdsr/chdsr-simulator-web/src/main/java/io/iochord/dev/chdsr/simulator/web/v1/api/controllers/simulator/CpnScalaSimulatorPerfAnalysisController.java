package io.iochord.dev.chdsr.simulator.web.v1.api.controllers.simulator;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.dev.chdsr.simulator.engine.SimulatorPerformAnalysisJava;

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
	
	@RequestMapping(value = BASE_URI + "/atm/perf/{noStep}", method = RequestMethod.POST)
	public String perfATMWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		//SimulatorPerformAnalysis spa = new SimulatorPerformAnalysis();
		//return spa.doATMTestWithManyToken(noStep, jsonStr);
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, "../chdsr-simulator/simulscala.txt");
	}
	
	@RequestMapping(value = BASE_URI + "/big/perf/{noStep}", method = RequestMethod.POST)
	public String perfBIGWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysisJava spa = new SimulatorPerformAnalysisJava();
		return spa.doTestWithManyToken(noStep, jsonStr, "../chdsr-simulator/bigsimulscala.txt");
	}
}
