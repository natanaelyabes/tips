package io.iochord.dev.chdsr.simulator.web.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.dev.chdsr.model.example.SbpnetExample;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl;

@RestController
public class ModellingController extends AServiceController {
	
	public static final String BASE_URI = AServiceController.BASE_URI + "/model";
	
	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}
	
	@RequestMapping(BASE_URI + "/create")
	public Sbpnet getCreateSimulationModel() {
		return SbpnetFactoryImpl.getInstance().create();
	}
	
	@RequestMapping(BASE_URI + "/example")
	public Sbpnet getCreateExampleSimulationModel() {
		return SbpnetExample.create();
	}
	
}
