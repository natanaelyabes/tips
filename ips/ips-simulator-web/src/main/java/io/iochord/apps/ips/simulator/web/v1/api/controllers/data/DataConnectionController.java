package io.iochord.apps.ips.simulator.web.v1.api.controllers.data;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.deckfour.xes.factory.XFactory;
import org.deckfour.xes.factory.XFactoryRegistry;
import org.deckfour.xes.model.XLog;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xeslite.lite.factory.XFactoryLiteImpl;
import org.xeslite.parser.XesLiteXmlParser;

import io.iochord.apps.ips.common.models.Dataset;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.services.data.DatasetRepositoryService;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportConfiguration;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportResult;
import io.iochord.apps.ips.model.services.data.im.csv.CsvDataImportService;
import io.iochord.apps.ips.model.services.data.im.xes.XesDataImportConfiguration;
import io.iochord.apps.ips.model.services.data.im.xes.XesDataImportResult;
import io.iochord.apps.ips.model.services.data.im.xes.XesDataImportService;

/**
 * 
 * Base controller for data (/data)
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class DataConnectionController extends ADataController {

	/**
	 * API URI prefix
	 */
	public static final String BASE_URI = ADataController.BASE_URI + "";

	/**
	 * Data connection list action
	 * 
	 * @param headers autowired http headers
	 * @return service context
	 */
	@GetMapping(value = BASE_URI + "/connection/list")
	public ServiceContext getDataConnectionsList(@RequestHeader HttpHeaders headers) {
		ServiceContext context = getServiceContext();
		Map<String, Dataset> datasets = null;
		try {
			datasets = new DatasetRepositoryService().run(context, "");
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		context.completeAndDestroy(datasets);
		return context;
	}

	/**
	 * IPR csv data import action
	 * 
	 * @param jsonConfig data import configuration as JSON string
	 * @param file csv file
	 * @param headers autowired http headers
	 * @return service context
	 * @throws IOException 
	 * @throws Exception
	 */
	@PostMapping(value = BASE_URI + "/import/csv")
	public ServiceContext postImportCsv(@RequestPart("config") String jsonConfig,
			@RequestPart("file") MultipartFile file, @RequestHeader HttpHeaders headers) throws IOException {
		CsvDataImportConfiguration config = SerializationUtil.decode(jsonConfig, CsvDataImportConfiguration.class);
		config.setFilename(file.getOriginalFilename());
		config.setReader(new InputStreamReader(file.getInputStream()));
		return run(new CsvDataImportService(), config, CsvDataImportResult.class, headers);
	}
	
	/**
	 * IPR xes data import action
	 * 
	 * @param jsonConfig data import configuration as JSON string
	 * @param file xes file
	 * @param headers autowired http headers
	 * @return service context
	 * @throws IOException 
	 * @throws Exception
	 */
	@PostMapping(value = BASE_URI + "/import/xes")
	public ServiceContext postImportXes(@RequestPart("config") String jsonConfig,
			@RequestPart("file") MultipartFile file, @RequestHeader HttpHeaders headers) throws IOException {
		XesDataImportConfiguration config = SerializationUtil.decode(jsonConfig, XesDataImportConfiguration.class);
		XFactory factory = new XFactoryLiteImpl();
		XFactoryRegistry.instance().setCurrentDefault(factory);
		XesLiteXmlParser parser = new XesLiteXmlParser(factory, false);
		try {
			List<XLog> logs = parser.parse(file.getInputStream());
			XLog log = logs.get(0);
			config.setFilename(file.getOriginalFilename());
			config.setLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return run(new XesDataImportService(), config, XesDataImportResult.class, headers);
	}

}
