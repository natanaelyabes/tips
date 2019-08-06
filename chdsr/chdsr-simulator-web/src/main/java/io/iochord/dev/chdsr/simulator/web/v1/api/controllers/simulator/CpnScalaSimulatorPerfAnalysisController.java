package io.iochord.dev.chdsr.simulator.web.v1.api.controllers.simulator;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.dev.chdsr.simulator.compiler.test.SimulatorPerformAnalysis;

/**
 *
 * @package chdsr-simulator-web
 * @author  Nur Ichsan Utama <nichsan@gmail.com>
 * @since   2019
 *
 *
 */
@RestController
@CrossOrigin
public class CpnScalaSimulatorPerfAnalysisController extends ASimulatorController {
	public static final String BASE_URI = ASimulatorController.BASE_URI + "/cpnscala";
	
	@RequestMapping(value = BASE_URI + "/atm/ctoken/{noStep}", method = RequestMethod.POST)
	public String runATMWithSpecNumbToken(@PathVariable("noStep") int noStep, @RequestBody String jsonStr) {
		SimulatorPerformAnalysis spa = new SimulatorPerformAnalysis();
		return spa.doATMTestWithManyToken(noStep, jsonStr);
	}
}
