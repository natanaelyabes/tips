package io.iochord.apps.ips.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;

import lombok.Getter;

/**
 *
 * @package ips-common
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
public class JsonDataCodec implements DataCodec<String> {

	@Getter
	private final String type = "json";

	public static ObjectMapper getSerializer() {
		ObjectMapper omDefault = new ObjectMapper();
		omDefault.registerModule(new DefaultScalaModule());
		omDefault.setSerializationInclusion(Include.NON_NULL);
		omDefault.setSerializationInclusion(Include.NON_EMPTY);
		return omDefault;
	}

	public static ObjectMapper getDeserializer() {
		ObjectMapper omDefault = new ObjectMapper();
		omDefault.registerModule(new DefaultScalaModule());
		omDefault.setSerializationInclusion(Include.NON_NULL);
		omDefault.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return omDefault;
	}

	@Override
	public String encode(Object obj) {
		try {
			return getSerializer().writeValueAsString(obj);
		} catch (Exception ex) {
			LoggerUtil.log(ex);
		}
		return null;
	}

	@Override
	public String encodePretty(Object obj) {
		try {
			return getSerializer().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception ex) {
			LoggerUtil.log(ex);
		}
		return null;
	}

	@Override
	public <U> U decode(String eobj, Class<U> type) {
		try {
			return getDeserializer().readValue(eobj, type);
		} catch (Exception ex) {
			LoggerUtil.log(ex);
		}
		return null;
	}

}
