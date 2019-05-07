package io.iochord.dev.chdsr.model.converter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public interface ConversionModel<I, O> {
	I getOriginalModel();

	O getConvertedModel();
}
