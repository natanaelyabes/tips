package io.iochord.dev.chdsr.model.converter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public interface Converter<I, O> {
	O convert(I i);

	I revert(O o); 
}
