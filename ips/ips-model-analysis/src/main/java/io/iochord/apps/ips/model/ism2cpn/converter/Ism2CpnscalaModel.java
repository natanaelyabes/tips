package io.iochord.apps.ips.model.ism2cpn.converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.util.Pair;

import io.iochord.apps.ips.model.converter.ConversionModel;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism2cpn.monitor.Ism2CpnscalaObserver;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class Ism2CpnscalaModel implements ConversionModel<IsmGraph, String> {

	@Getter
	@Setter
	private IsmGraph originalModel;
	
	@Getter
	@Setter
	private String convertedModel;

	@Getter
	@Setter
	private Map<Element, Pair<String, String>> basicMonitors = new TreeMap<>();

	@Getter
	private final Ism2CpnscalaObserver kpiObserver;
	
	@Getter
	private final Map<String, Element> conversionMap = new LinkedHashMap<>();
	
	public Ism2CpnscalaModel() {
		kpiObserver = new Ism2CpnscalaObserver(this.getConversionMap());
	}
	
}
