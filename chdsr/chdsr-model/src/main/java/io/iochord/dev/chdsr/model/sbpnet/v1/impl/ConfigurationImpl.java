package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.iochord.dev.chdsr.model.sbpnet.v1.Configuration;
import lombok.Getter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JacksonXmlRootElement(localName="configuration")
public class ConfigurationImpl extends ElementImpl implements Configuration {
	@Getter
	private final String elementType = Configuration.TYPE;
}
