package io.iochord.dev.chdsr.simulator.web.v1.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import io.iochord.dev.chdsr.simulator.web.v1.controllers.AController;
import lombok.Getter;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public abstract class AServiceController extends AController {
	public static final String BASE_URI = AController.BASE_URI + "/api/v1";
	
	@Autowired
	@Getter
	private SimpMessagingTemplate wsmTemplate; 

}
