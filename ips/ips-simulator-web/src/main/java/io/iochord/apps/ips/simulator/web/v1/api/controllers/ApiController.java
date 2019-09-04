package io.iochord.apps.ips.simulator.web.v1.api.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

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
public class ApiController extends AServiceController {
	public static final String BASE_URI = AServiceController.BASE_URI + "";
	private final RequestMappingHandlerMapping handlerMapping;

	@Autowired
	public ApiController(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@RequestMapping(BASE_URI + "")
	public Map<String, Object> getIndex() {
		Map<String, Object> result = new LinkedHashMap<>();
		Set<String> routes = new TreeSet<>();
		for (RequestMappingInfo info : handlerMapping.getHandlerMethods().keySet()) {
			routes.add(info.getPatternsCondition().toString());
		}
		result.put("version", 3);
		result.put("routes", routes);
		return result;
	}
	
}