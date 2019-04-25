package io.iochord.dev.chdsr.simulator.web.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(SimulatorController.BASE_URI)
public class SimulatorController extends AServiceController {
	
	public static final String BASE_URI = AServiceController.BASE_URI + "/simulator";
	
	@RequestMapping("")
	public String getIndex() {
		return "Ok";
	}
	
}
