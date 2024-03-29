package io.iochord.apps.ips.simulator.web.v1.api.controllers.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;

/**
 *
 * IOChord Simulation Model (ISM) Controller (/model/ism)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class IsmModelController extends AModelController {
	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = AModelController.BASE_URI + "/ism";

	/**
	 * All created ISM networks
	 */
	private Map<String, IsmGraph> nets = new LinkedHashMap<>();

	/**
	 * Default action
	 * 
	 * @return string 'Ok'
	 */
	@GetMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}

	/**
	 * Create new ISM without default nodes
	 * @return new ISM
	 */
	@GetMapping(BASE_URI + "/create")
	public IsmGraph getCreateDefault() {
		return getCreate(0);
	}

	/**
	 * Create new ISM
	 * 
	 * @param defaultNodes 0: empty ISM, >0: with default nodes
	 * @return new ISM
	 */
	@GetMapping(BASE_URI + "/create/{defaultNodes}")
	public IsmGraph getCreate(@PathVariable int defaultNodes) {
		IsmGraph net;
		if (defaultNodes > 0) {
			net = IsmExample.createDefault();
		} else {
			IsmFactory factory = IsmFactoryImpl.getInstance();
			net = factory.create();
		}
		nets.put(net.getId(), net);
		return net;
	}

	/**
	 * Get ISM
	 * 
	 * @param modelId ISM Id
	 * @return ISM
	 */
	@GetMapping(BASE_URI + "/view/{modelId}")
	public IsmGraph getView(@PathVariable String modelId) {
		if (nets.containsKey(modelId)) {
			return nets.get(modelId);
		}
		return null;
	}

	/**
	 * Update ISM
	 * 
	 * @param modelId ISM Id
	 * @param graph updated ISM
	 * @return updated ISM
	 */
	@PostMapping(value = BASE_URI + "/edit/{modelId}")
	public IsmGraph postEdit(@PathVariable String modelId, @RequestBody IsmGraphImpl graph) {
		if (nets.containsKey(modelId)) {
			IsmGraph net = nets.get(modelId);
			Page page = net.getPages().values().iterator().next();
			ActivityImpl act = (ActivityImpl) IsmFactoryImpl.getInstance().addActivity(page);
			act.setLabel("Activity " + (page.getNodes().size() + 1));
			return net;
		}
		return graph;
	}

	/**
	 * Create example ISM
	 * 
	 * @return example ISM as string
	 * 
	 *  // createBankExample();
	 */
	@GetMapping(value = BASE_URI + "/example", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getCreateExampleSimulationModel() {
		IsmGraph snet = IsmExample.createPortExample();
		return SerializationUtil.encode(snet);
	}

	/**
	 * Create and convert example ISM
	 * 
	 * @return example ISM as CPNScala
	 */
	@GetMapping(value = BASE_URI + "/examplecpn", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getConvertExampleSimulationModel() {
		IsmGraph snet = IsmExample.createBankExample();
		Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		return converter.convert(snet).getConvertedModel();
	}
}
