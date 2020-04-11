package io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerConfig;
import io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerResult;
import io.iochord.apps.ips.model.analysis.services.dtm.DecisionMinerService;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerService;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerResult;

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

	/**
	 * Resource Mining action
	 * 
	 * @author Nur Ichsan Utama <ichsan83@gmail.com>
	 * @param datasetId dataset Id
	 * @param config resource mining configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/resm", BASE_URI + "/resm/{datasetId}" })
	public ServiceContext mineResource(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) ResourceMinerConfig config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new ResourceMinerConfig();
			config.setDatasetId(datasetId.get());
		}
		ServiceContext context = getServiceContext();
		ResourceMinerResult rmr = null;
		try {
			 rmr = new ResourceMinerService().run(context, config);
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		context.completeAndDestroy(rmr);		
		return context;
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
