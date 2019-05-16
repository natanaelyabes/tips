package io.iochord.dev.chdsr.model.converter.sbp2cpn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.iochord.dev.chdsr.model.converter.ConversionModel;
import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph;
import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import lombok.Getter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class Sbpnet2CpnBiConverterModel implements ConversionModel<Sbpnet, CPNGraph> {
	@Getter
	private final Sbpnet originalModel;
	
	@Getter
	private final CPNGraph convertedModel;
	
	@Getter
	private final Map<Element, List<io.iochord.dev.chdsr.model.cpn.v1.Element>> o2cAssets = new HashMap<>();
 
	@Getter
	private final Map<io.iochord.dev.chdsr.model.cpn.v1.Element, Element> c2oAssets = new HashMap<>();
	
	public Sbpnet2CpnBiConverterModel(Sbpnet o, CPNGraph c) {
		originalModel = o;
		convertedModel = c;
	}
}
