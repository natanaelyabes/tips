package io.iochord.apps.ips.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;

import lombok.Getter;

/**
 *
 * XML data codec
 *
 * @package chdsr-common
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class XmlDataCodec extends JsonDataCodec implements DataCodec<String> {

	@Getter
	private final String type = "xml";
	
	public static XmlMapper getSerializer() {
		XmlMapper omDefault = new XmlMapper();
		omDefault.registerModule(new DefaultScalaModule());
		omDefault.setSerializationInclusion(Include.NON_NULL);
		omDefault.setSerializationInclusion(Include.NON_EMPTY);
		return omDefault;
	}

	public static XmlMapper getDeserializer() {
		XmlMapper omDefault = new XmlMapper();
		omDefault.registerModule(new DefaultScalaModule());
		omDefault.setSerializationInclusion(Include.NON_NULL);
		omDefault.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return omDefault;
	}

}
