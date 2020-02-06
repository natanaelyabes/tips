package io.iochord.apps.ips.common.util;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
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

	/**
	 *
	 * @param obj
	 * @return
	 *
	 */
	public static String encode(Object obj) {
		return json().encode(obj);
	}

	/**
	 *
	 * @param obj
	 * @return
	 *
	 */
	public static String encodePretty(Object obj) {
		return json().encodePretty(obj);
	}

	/**
	 *
	 * @param eobj
	 * @param type
	 * @return
	 *
	 */
	public static <U> U decode(String eobj, Class<U> type) {
		return json().decode(eobj, type);
	}
}
