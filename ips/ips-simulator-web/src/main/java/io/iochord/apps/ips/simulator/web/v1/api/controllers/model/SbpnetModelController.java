package io.iochord.apps.ips.simulator.web.v1.api.controllers.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.apps.ips.model.example.SbpnetExample;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.Ism;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.components.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.components.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.components.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.components.impl.StartImpl;
import io.iochord.apps.ips.model.ism.v1.components.impl.StopImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmImpl;
import io.iochord.apps.ips.util.SerializationUtil;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@RestController
@CrossOrigin
public class SbpnetModelController extends AModelController {
	public static final String BASE_URI = AModelController.BASE_URI + "/sbpnet";
	
	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}
	
	private Map<String, Ism> nets = new LinkedHashMap<>();
	
	@RequestMapping(BASE_URI + "/create")
	public Ism getCreateDefault() {
		return getCreate(0);
	}

	@RequestMapping(BASE_URI + "/create/{defaultNodes}")
	public Ism getCreate(@PathVariable int defaultNodes) {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		Ism net = factory.create();
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
	public Ism getView(@PathVariable String modelId) {
		if (nets.containsKey(modelId)) {
			Ism net = nets.get(modelId);
			return net;
		}
		return null;
	}
	
	@RequestMapping(value = BASE_URI + "/edit/{modelId}", method = RequestMethod.POST)
	public Ism postEdit(@PathVariable String modelId, 
		@ModelAttribute IsmImpl graph) {
		getWsmTemplate().convertAndSend("/res" + BASE_URI + "/edit/" + modelId, graph);
		if (nets.containsKey(modelId)) {
			Ism net = nets.get(modelId);
			Page page = net.getPages().values().iterator().next();
			ActivityImpl act = (ActivityImpl) IsmFactoryImpl.getInstance().addActivity(page);
			act.setLabel("Activity " + (page.getNodes().size() + 1));
			return net;
		}
		return graph;
	}
	
	@RequestMapping(value=BASE_URI + "/example",produces= {MediaType.APPLICATION_JSON_VALUE})
	public String getCreateExampleSimulationModel() {
		Ism snet = SbpnetExample.create();
		return SerializationUtil.encode(snet);
	}	
	
	@RequestMapping(value=BASE_URI + "/examplecpn",produces= {MediaType.APPLICATION_JSON_VALUE})
	public String getConvertExampleSimulationModel() {
		Ism snet = SbpnetExample.create();
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		String cnet = converter.convert(snet);
		return cnet;
	}	
}
