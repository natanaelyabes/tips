package io.iochord.apps.ips.simulator.web.v1.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = BASE_URI + "/ping", method = RequestMethod.GET)
	public String getPing() {
		return "PONG";
	}

}
