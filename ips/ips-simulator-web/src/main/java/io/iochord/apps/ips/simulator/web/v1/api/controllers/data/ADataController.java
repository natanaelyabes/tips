package io.iochord.apps.ips.simulator.web.v1.api.controllers.data;

import io.iochord.apps.ips.simulator.web.v1.api.controllers.AServiceController;

/**
 *
 * Base controller for data (/data)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
public abstract class ADataController extends AServiceController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = AServiceController.BASE_URI + "/data";

}
