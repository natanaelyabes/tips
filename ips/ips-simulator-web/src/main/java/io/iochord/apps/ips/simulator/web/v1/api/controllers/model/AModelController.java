package io.iochord.apps.ips.simulator.web.v1.api.controllers.model;

import io.iochord.apps.ips.simulator.web.v1.api.controllers.AServiceController;

/**
 *
 * Base controller for model (/model)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
public abstract class AModelController extends AServiceController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = AServiceController.BASE_URI + "/model";

}
