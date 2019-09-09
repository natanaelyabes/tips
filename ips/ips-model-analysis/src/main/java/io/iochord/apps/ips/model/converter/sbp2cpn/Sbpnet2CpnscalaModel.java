package io.iochord.apps.ips.model.converter.sbp2cpn;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.util.Pair;

import io.iochord.apps.ips.model.converter.ConversionModel;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.Ism;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model-analysis
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class Sbpnet2CpnscalaModel implements ConversionModel<Ism, String> {

	@Getter
	@Setter
	private Ism originalModel;
	
	@Getter
	@Setter
	private String convertedModel;

	@Getter
	@Setter
	private Map<Element, Pair<String, String>> basicMonitors = new TreeMap<>();

}
