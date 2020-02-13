package io.iochord.apps.ips.simulator.web.v1.api.controllers.simulator;

import io.iochord.apps.ips.simulator.web.v1.api.controllers.AServiceController;

/**
 *
 * Base controller for simulator (/simulator)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
public abstract class ASimulatorController extends AServiceController {

	/**
	 * API URI Prefix
	 */
	public static final String BASE_URI = AServiceController.BASE_URI + "/simulator";

}
