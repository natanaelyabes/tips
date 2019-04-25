package io.iochord.dev.chdsr.simulator.web.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller(ApiController.BASE_URI)
public class ApiController extends AServiceController {

	public static final String BASE_URI = AServiceController.BASE_URI + "";
	private final RequestMappingHandlerMapping handlerMapping;

	@Autowired
	public ApiController(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@RequestMapping("/v1")
	public Map<RequestMappingInfo, HandlerMethod> getIndex() {
		return handlerMapping.getHandlerMethods();
	}

}
