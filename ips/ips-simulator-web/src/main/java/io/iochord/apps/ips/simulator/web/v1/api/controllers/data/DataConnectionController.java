package io.iochord.apps.ips.simulator.web.v1.api.controllers.data;

import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.services.data.DatasetRepositoryService;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportConfiguration;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportResult;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportService;

/**
*
* @package ips-simulator-web
* @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since   2019
*
*/
@RestController
@CrossOrigin
public class DataConnectionController extends ADataController {

	public static final String BASE_URI = ADataController.BASE_URI + "";
	
	@RequestMapping(value = BASE_URI + "/connection/list")
	public ServiceContext getDataConnectionsList(@RequestHeader HttpHeaders headers) {
		ServiceContext context = getServiceContext();
		Map<String, Dataset> datasets = null;
		try {
			datasets = new DatasetRepositoryService().run(context, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.completeAndDestroy(datasets);
		return context;
	}
	
	@RequestMapping(value = BASE_URI + "/import/csv", method = RequestMethod.POST)
	public ServiceContext postImportCsv(
			@RequestPart("config") String jsonConfig,
			@RequestPart("file") MultipartFile file,
			@RequestHeader HttpHeaders headers
		) throws Exception {
		CsvDataImportConfiguration config = SerializationUtil.decode(jsonConfig, CsvDataImportConfiguration.class);
		config.setFilename(file.getOriginalFilename());
		config.setReader(new InputStreamReader(file.getInputStream()));
		ServiceContext result = run(new CsvDataImportService(), config, CsvDataImportResult.class, headers);
		return result;
	}
	
}
