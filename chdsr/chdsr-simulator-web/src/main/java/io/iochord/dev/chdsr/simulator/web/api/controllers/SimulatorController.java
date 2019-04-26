package io.iochord.dev.chdsr.simulator.web.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulatorController extends AServiceController {
	
	public static final String BASE_URI = AServiceController.BASE_URI + "/simulator";
	
	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}
	
}
