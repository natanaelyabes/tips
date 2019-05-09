package io.iochord.dev.chdsr.util;

public interface DataCodec<T> {
	String getType();
	T encode(Object obj);
	T encodePretty(Object obj);
	<U> U decode(T eobj, Class<U> type);
}
