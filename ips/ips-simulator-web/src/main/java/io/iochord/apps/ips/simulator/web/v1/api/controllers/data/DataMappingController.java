package io.iochord.apps.ips.simulator.web.v1.api.controllers.data;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.services.data.map.MappingResource;
import io.iochord.apps.ips.model.services.data.map.MappingResult;
import io.iochord.apps.ips.model.services.data.map.MappingService;
import io.iochord.apps.ips.model.services.data.map.MappingConfiguration;
import io.iochord.apps.ips.model.services.data.map.MappingRepositoryService;

/**
 *
 * @package ips-simulator-web
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 * 
 */
@RestController
public class DataMappingController extends ADataController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = ADataController.BASE_URI + "";
	
	/**
	 * Data mapping action
	 * 
	 * @param headers autowired http headers
	 * @return service context
	 */
	@GetMapping(value = { BASE_URI + "/mapping", BASE_URI + "/mapping/{datasetId}" })
	public ServiceContext getDataMappingSettings(@PathVariable Optional<String> datasetId, @RequestHeader HttpHeaders headers) {
		ServiceContext context = getServiceContext();
		MappingResource resource = null;
		if (datasetId.isPresent()) {
			try {
				resource = new MappingRepositoryService().run(context, datasetId.get());		
			} catch (Exception ex) {
				LoggerUtil.logError(ex);
			}
		}
		context.completeAndDestroy(resource);
		return context;
	}
	
	@PostMapping(value = { BASE_URI + "/mapping/{datasetId}" })
	public ServiceContext postDataMappingSettings(@PathVariable Optional<String> datasetId, @RequestBody String mapping) {
		MappingResource resource = SerializationUtil.decode(mapping, MappingResource.class);
		MappingConfiguration config = new MappingConfiguration();
		config.setDatasetId(datasetId.get());
		config.setResource(resource);
		
		ServiceContext context = getServiceContext();
		MappingResult res = null;
		try {
			 res = new MappingService().run(context, config);
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		context.completeAndDestroy(res);		
		return context;
	}
}
