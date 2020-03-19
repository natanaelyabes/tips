package io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerConfig;
import io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerResult;
import io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerService;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerResult;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerService;

/**
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class IsmAnalysisController extends AnAnalysisController {

	public static final String BASE_URI = AnAnalysisController.BASE_URI + "/discover";

	@PostMapping(value = { BASE_URI + "/resm", BASE_URI + "/resm/{datasetId}" })
	public ServiceContext getPostResourceMining(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) ResourceMinerConfig config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new ResourceMinerConfig();
			config.setDatasetId(datasetId.get());
		}
		return run(new ResourceMinerService(), config, ResourceMinerResult.class, headers);
	}

	@PostMapping(value = { BASE_URI + "/dtm", BASE_URI + "/dtm/{datasetId}" })
	public ServiceContext getPostDecisionMiner(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) DecisionMinerConfig config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new DecisionMinerConfig();
			config.setDatasetId(datasetId.get());
		}
		return run(new DecisionMinerService(), config, DecisionMinerResult.class, headers);
	}

}
