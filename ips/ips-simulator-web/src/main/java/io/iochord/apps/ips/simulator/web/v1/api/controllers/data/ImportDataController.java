package io.iochord.apps.ips.simulator.web.v1.api.controllers.data;

import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.iochord.apps.ips.simulator.web.v1.models.WebServiceResponse;
import io.iochord.apps.ips.simulator.web.v1.models.WebServiceStatus;

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
public class ImportDataController extends ADataController {

	public static final String BASE_URI = ADataController.BASE_URI + "/import";

	@RequestMapping(value = BASE_URI + "/test")
	public WebServiceResponse<String> getTest() throws Exception {
		String name = "log_" + System.nanoTime();
		getServices().getDataConnectionService().getTest();
		WebServiceResponse<String> response = new WebServiceResponse<String>(name);
		response.setStatus(WebServiceStatus.submitted());
		response.getStatus().setProgressWsUri("/response/test");
		response.getStatus().setCompleteWsUri("/response/complete");
		return response;
	}

	@RequestMapping(value = BASE_URI + "/csv", method = RequestMethod.POST)
	public WebServiceResponse<String> postImportCSV(
		@RequestParam("file") MultipartFile file
		) throws Exception {
		String name = "log_" + System.nanoTime();
		CompletableFuture<String> x = getServices().getDataConnectionService().importCsv(name, file.getOriginalFilename(), new InputStreamReader(file.getInputStream()));
		WebServiceResponse<String> response = new WebServiceResponse<String>(name + " " + x);
		response.setStatus(WebServiceStatus.submitted());
		response.getStatus().setProgressWsUri("/response/test2");
		response.getStatus().setCompleteWsUri("/response/test3");
		return response;
	}
	
}
