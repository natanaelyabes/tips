package io.iochord.apps.ips.model.ism.v1.impl;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.iochord.apps.ips.model.ism.v1.Configuration;
import lombok.Getter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JacksonXmlRootElement(localName="configuration")
public class ConfigurationImpl extends ElementImpl implements Configuration {
	@Getter
	private final String elementType = Configuration.TYPE;
}
