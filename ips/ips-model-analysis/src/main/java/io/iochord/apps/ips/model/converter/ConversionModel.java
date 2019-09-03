package io.iochord.apps.ips.model.converter;

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
public interface ConversionModel<I, O> {
	I getOriginalModel();

	O getConvertedModel();
}
