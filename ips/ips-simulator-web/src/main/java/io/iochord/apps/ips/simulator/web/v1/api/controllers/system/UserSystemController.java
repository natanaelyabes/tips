package io.iochord.apps.ips.simulator.web.v1.api.controllers.system;

import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * User controller (/user/user)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class UserSystemController extends ASystemController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = ASystemController.BASE_URI + "/user";

}
