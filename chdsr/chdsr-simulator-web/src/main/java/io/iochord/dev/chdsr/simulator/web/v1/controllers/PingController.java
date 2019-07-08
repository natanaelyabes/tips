package io.iochord.dev.chdsr.simulator.web.v1.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * To check status of API server (health check)
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@RestController
@CrossOrigin
public class PingController extends AController {
	
	public static final String BASE_URI = AController.BASE_URI + "";

	@RequestMapping(value = BASE_URI + "/ping", method = RequestMethod.GET )
	public String getPing() {
		return "PONG";
	}
	
}
