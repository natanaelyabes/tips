package io.iochord.dev.chdsr.simulator.web.v1.api.controllers.system;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @package chdsr-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 *
 */
@RestController
@CrossOrigin
public class UserSystemController extends ASystemController {

	public static final String BASE_URI = ASystemController.BASE_URI + "/user";

	
	
}
