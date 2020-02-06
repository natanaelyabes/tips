package io.iochord.apps.ips.simulator.web.v1.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Ping controller for testing service status
 * 
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
@CrossOrigin
public class PingController extends AController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = AController.BASE_URI + "";

	/**
	 * Ping action 
	 * 
	 * @return PONG string
	 */
	@GetMapping(value = BASE_URI + "/ping")
	public String getPing() {
		return "PONG";
	}

}
