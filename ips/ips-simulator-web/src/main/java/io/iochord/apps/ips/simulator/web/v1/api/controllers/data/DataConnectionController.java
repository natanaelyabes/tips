package io.iochord.apps.ips.simulator.web.v1.api.controllers.data;

import java.io.InputStreamReader;
import java.util.concurrent.Future;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.iochord.apps.ips.data.services.models.ImportCsvConfiguration;
import io.iochord.apps.ips.data.services.models.ImportCsvResult;
import io.iochord.apps.ips.simulator.web.v1.models.WebServiceResponse;
import io.iochord.apps.ips.util.SerializationUtil;

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
public class DataConnectionController extends ADataController {

	public static final String BASE_URI = ADataController.BASE_URI + "";

	@RequestMapping(value = BASE_URI + "/import/csv", method = RequestMethod.POST)
	public WebServiceResponse<ImportCsvResult> postImportCsv(
			@RequestPart("req") String jsonReq,
			@RequestPart("file") MultipartFile file,
			@RequestHeader HttpHeaders headers
		) throws Exception {
		ImportCsvConfiguration config = SerializationUtil.decode(jsonReq, ImportCsvConfiguration.class);
		config.setFilename(file.getOriginalFilename());
		config.setReader(new InputStreamReader(file.getInputStream()));
		WebServiceResponse<ImportCsvResult> response = getServices().createResponse(ImportCsvResult.class);
		Future<ImportCsvResult> future = getServices().getDataConnectionService().importCsv(config, response.getState());
		return printResult(headers, future, response);
	}
	
}
