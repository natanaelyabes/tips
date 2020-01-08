package io.iochord.apps.ips.model.converter;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public interface ConversionModel<I, O> {
	I getOriginalModel();

	O getConvertedModel();
}
