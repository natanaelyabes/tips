package io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis;

import io.iochord.apps.ips.simulator.web.v1.api.controllers.AServiceController;

/**
 *
 * Base controller for analysis (/analysis)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
public abstract class AnAnalysisController extends AServiceController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = AServiceController.BASE_URI + "/analysis";

}
