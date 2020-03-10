package io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerService;
import io.iochord.apps.ips.model.services.data.DatasetRepositoryService;
import io.iochord.apps.ips.model.analysis.services.resm.ResourceMinerResult;

/**
 *
 * @package ips-simulator-web
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 *
 */
@RestController
public class ResourceMinerController extends AnAnalysisController {

	public static final String BASE_URI = AnAnalysisController.BASE_URI + "/resm";

	/**
	 * Resource Mining action
	 * 
	 * @param datasetId dataset Id
	 * @param config resource mining configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/mine", BASE_URI + "/mine/{datasetId}" })
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
		//return run(new ResourceMinerService(), config, ResourceMinerResult.class, headers);
	}

}
