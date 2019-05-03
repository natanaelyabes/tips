package io.iochord.dev.chdsr.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;

public class MapperUtils {

	public static ObjectMapper getJsonSerializer() {
		ObjectMapper omDefault = new ObjectMapper();
		omDefault.registerModule(new DefaultScalaModule());
		omDefault.setSerializationInclusion(Include.NON_NULL);
		return omDefault;
	}

	public static ObjectMapper getJsonDeserializer() {
		ObjectMapper omDefault = new ObjectMapper();
		omDefault.registerModule(new DefaultScalaModule());
		omDefault.setSerializationInclusion(Include.NON_NULL);
		return omDefault;
	}
	
}
