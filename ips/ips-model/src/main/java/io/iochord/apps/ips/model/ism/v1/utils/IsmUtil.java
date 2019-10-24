package io.iochord.apps.ips.model.ism.v1.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.ism.v1.Element;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class IsmUtil {
	public static <T extends Element> String generateRef(T e) {
		if (e != null) {
			return e.getId();
		}
		return null;
	}

	public static <T extends Element> Map<String, String> generateRefs(Map<String, T> map) {
		Map<String, String> refs = new LinkedHashMap<>();
		if (map != null) {
			for (String k : map.keySet()) {
				T v = map.get(k);
				if (v != null) {
					refs.put(k, v.getId());
				} else {
					refs.put(k, null);
				}
			}
		}
		return refs;
	}
}
