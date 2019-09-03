package io.iochord.apps.ips.util;

/**
 * Data Codec class for serialize / deserialize object
 *
 * @package chdsr-common
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 * @param <T> resulting data type
 *
 */
public interface DataCodec<T> {
	/**
	 *
	 * @return get resulting type name
	 *
	 */
	String getType();

	/**
	 *
	 * Encode / serialize obj into resulting type object 
	 * 
	 * @param obj object
	 * @return encoded object
	 *
	 */
	T encode(Object obj);

	/**
	 * 
	 * Encode / serialize obj into resulting type object (pretty version)
	 *
	 * @param obj object
	 * @return encoded object (pretty)
	 *
	 */
	T encodePretty(Object obj);

	/**
	 * 
	 * Decode / deserialize eobj into object of type
	 *
	 * @param eobj encoded object
	 * @param type object type
	 * @return object
	 *
	 */
	<U> U decode(T eobj, Class<U> type);
}
