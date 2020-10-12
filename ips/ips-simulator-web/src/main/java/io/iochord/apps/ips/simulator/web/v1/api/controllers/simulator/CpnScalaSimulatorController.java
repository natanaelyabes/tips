package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.core.services.ServiceContext.State;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.report.Report;
import io.iochord.apps.ips.simulator.services.IsmSimulatorService;
import io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis.IsmDiscoveryController;
import lombok.Getter;

/**
 * 
 * CPN Scala simulator controller (/simulator/cpnscala)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class CpnScalaSimulatorController extends ASimulatorController {
	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = ASimulatorController.BASE_URI + "/cpnscala";	
	
	@Autowired
	@Getter
	private IsmDiscoveryController discoController;
	
	@Getter
	private Map<String, ServiceContext> cachedResult = new LinkedHashMap<>();
	
	/**
	 * 
	 * Load and simulate ISM model with datasetId param
	 */
	@PostMapping(value = { BASE_URI + "/loadnplay/{datasetId}" })
	public ServiceContext getPostDiscoverIsm(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		IsmGraphImpl graph = (IsmGraphImpl) getDiscoController().getPostDiscoverIsmHybrid(datasetId, config, headers).getData();
		return postLoadNPlay(graph, headers);
	}

	/**
	 * Load and simulate ISM model
	 */
	@PostMapping(value = BASE_URI + "/loadnplay")
	public ServiceContext postLoadNPlay(@RequestBody IsmGraphImpl graph,
			@RequestHeader HttpHeaders headers) {
		IsmSimulatorService svc = new IsmSimulatorService();
		String graphHash = DigestUtils.md5DigestAsHex(SerializationUtil.encode(graph).getBytes());
		if (!getCachedResult().containsKey(graphHash) || getCachedResult().get(graphHash).getInfo().getState() != State.RUNNING) {
			getCachedResult().put(graphHash, run(svc, graph, Report.class, headers));
		}
		return getCachedResult().get(graphHash);
	}

	@GetMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/load/{cpnId}")
	public String getLoad() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/step/{cpnId}/{n}")
	public String getStep() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/warptime/{cpnId}/{t}")
	public String getWarp() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/fastforward/{cpnId}")
	public String getFastForward() {
		return "Ok";
	}

	@GetMapping(BASE_URI + "/start")
	public String start() {
		return "Ok";
	}

}
