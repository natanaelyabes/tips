package io.iochord.dev.chdsr.simulator.web.api.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.dev.chdsr.model.example.SbpnetExample;
import io.iochord.dev.chdsr.model.sbpnet.v1.Page;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.SbpnetFactory;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.GeneratorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ObjectTypeImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StartImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StopImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
//TODO: Modelling Controller
@RestController
@CrossOrigin
public class ModellingController extends AServiceController {
	public static final String BASE_URI = AServiceController.BASE_URI + "/model";
	
	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}
	
	private Map<String, Sbpnet> nets = new LinkedHashMap<>();
	
	@RequestMapping(BASE_URI + "/create")
	public Sbpnet getCreateDefault() {
		return getCreate(0);
	}

	@RequestMapping(BASE_URI + "/create/{defaultNodes}")
	public Sbpnet getCreate(@PathVariable int defaultNodes) {
		SbpnetFactory factory = SbpnetFactoryImpl.getInstance();
		Sbpnet net = factory.create();
		nets.put(net.getId(), net);
		if (defaultNodes > 0) {
			Page page = net.getPages().values().iterator().next();
			ObjectTypeImpl objType = (ObjectTypeImpl) factory.addObjectType(page);
			objType.setLabel("UNIT");
			GeneratorImpl generator = (GeneratorImpl) factory.addGenerator(page);
			generator.setLabel("UNIT GENERATOR");
			generator.setObjectType(objType);
			StartImpl start = (StartImpl) factory.addStart(page);
			start.setLabel("START");
			start.setGenerator(generator);
			ActivityImpl act = (ActivityImpl) factory.addActivity(page);
			act.setLabel("ACTIVITY");
			StopImpl stop = (StopImpl) factory.addStop(page);
			stop.setLabel("STOP");
			factory.addConnector(page, start, act);
			factory.addConnector(page, act, stop);
		}
		return net;
	}
	
	@RequestMapping(BASE_URI + "/view/{modelId}")
	public Sbpnet getView(@PathVariable String modelId) {
		if (nets.containsKey(modelId)) {
			Sbpnet net = nets.get(modelId);
			return net;
		}
		return null;
	}
	
	@RequestMapping(BASE_URI + "/edit/{modelId}")
	public Sbpnet getEdit(@PathVariable String modelId) {
		if (nets.containsKey(modelId)) {
			Sbpnet net = nets.get(modelId);
			Page page = net.getPages().values().iterator().next();
			ActivityImpl act = (ActivityImpl) SbpnetFactoryImpl.getInstance().addActivity(page);
			act.setLabel("Activity " + (page.getNodes().size() + 1));
			return net;
		}
		return null;
	}
	
	@RequestMapping(BASE_URI + "/example")
	public Sbpnet getCreateExampleSimulationModel() {
		return SbpnetExample.create();
	}	
}
