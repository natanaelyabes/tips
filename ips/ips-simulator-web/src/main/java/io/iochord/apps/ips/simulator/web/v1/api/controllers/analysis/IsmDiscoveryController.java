package io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryTokenService;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;

/**
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class IsmDiscoveryController extends AnAnalysisController {

	public static final String BASE_URI = AnAnalysisController.BASE_URI + "/discover";

	/**
	 * Process discovery action
	 * 
	 * @param datasetId dataset Id
	 * @param config process discovery configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/ism", BASE_URI + "/ism/{datasetId}" })
	public ServiceContext getPostDiscoverIsm(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new IsmDiscoveryConfiguration();
			config.setDatasetId(datasetId.get());
		}
		
		return run(new IsmDiscoveryService(), config, IsmGraph.class, headers);
	}

	/**
	 * Process discovery action
	 * 
	 * @param datasetId dataset Id
	 * @param config process discovery configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/ism/animate/{datasetId}" })
	public ServiceContext getPostDiscoverIsmAnimStr(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new IsmDiscoveryConfiguration();
			config.setDatasetId(datasetId.get());
		}
		
		return run(new IsmDiscoveryTokenService(), config, String.class, headers);
	}

}
