package io.iochord.dev.chdsr.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LazyUtils {

	public static <K,V> Map<K, V> linkedHashMap(Map<K, V> configurations) {
		if (configurations == null) {
			configurations = new LinkedHashMap<>();
		}
		return configurations;
	}

}
