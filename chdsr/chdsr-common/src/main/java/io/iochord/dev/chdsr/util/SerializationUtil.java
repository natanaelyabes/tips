package io.iochord.dev.chdsr.util;

public class SerializationUtil {

	private static JsonDataCodec json;
	
	public static JsonDataCodec json() {
		if (json == null) {
			json = new JsonDataCodec();
		}
		return json;
	}

	private static XmlDataCodec xml;
	
	public static XmlDataCodec xml() {
		if (xml == null) {
			xml = new XmlDataCodec();
		}
		return xml;
	}
	
	public static String encode(Object obj) {
		return json().encode(obj);
	}
	
	public static String encodePretty(Object obj) {
		return json().encodePretty(obj);
	}
	
	public static <U> U decode(String eobj, Class<U> type) {
		return json().decode(eobj, type);
	}
}
