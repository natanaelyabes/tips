package io.iochord.dev.chdsr.simulator.web.api.controllers;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
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
	public Set<String> getIndex() {
		Set<String> result = new TreeSet<>();
		for (RequestMappingInfo info : handlerMapping.getHandlerMethods().keySet()) {
			result.add(info.getPatternsCondition().toString());
		}
		return result;
	}
}
