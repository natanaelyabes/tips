package io.iochord.dev.chdsr.simulator.web.api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
// TODO: Converter Controller
@RestController
@CrossOrigin
public class ConverterController extends AServiceController {
	
	public static final String BASE_URI = AServiceController.BASE_URI + "/converter";
	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}
	
	@RequestMapping(BASE_URI + "/convert/{modelId}")
	public String getConvert() {
		return "Ok";
	}
	
}
