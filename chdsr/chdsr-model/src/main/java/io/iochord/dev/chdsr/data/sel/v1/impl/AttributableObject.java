package io.iochord.dev.chdsr.data.sel.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.data.sel.v1.Attributable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class AttributableObject implements Attributable {

	@Getter
	@Setter
	private Map<String, String> attributes = new LinkedHashMap<>();

}
