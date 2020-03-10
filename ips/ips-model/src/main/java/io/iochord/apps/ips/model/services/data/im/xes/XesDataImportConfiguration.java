package io.iochord.apps.ips.model.services.data.im.xes;

import java.io.InputStream;

import org.deckfour.xes.model.XLog;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class XesDataImportConfiguration {

	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String filename;

	@Getter
	@Setter
	@JsonIgnore
	private XLog log;
	
}
