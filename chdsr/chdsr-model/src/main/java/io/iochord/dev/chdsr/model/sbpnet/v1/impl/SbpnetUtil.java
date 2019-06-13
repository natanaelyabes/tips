package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Element;

public class SbpnetUtil {
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
