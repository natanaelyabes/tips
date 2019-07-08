package io.iochord.dev.chdsr.model.converter;

/**
 *
 * @package chdsr-model-analysis
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 * @param <I>
 * @param <O>
 *
 */
public interface Converter<I, O> {
	O convert(I i);

	I revert(O o); 
}
