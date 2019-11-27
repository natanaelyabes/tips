package io.iochord.apps.ips.model.ism2cpn.converter;

import org.cpntools.accesscpn.model.PetriNet;

import io.iochord.apps.ips.model.converter.ConversionModel;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
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
public class Ism2CpnmlModel implements ConversionModel<IsmGraph, PetriNet> {

	@Getter
	@Setter
	private IsmGraph originalModel;
	
	@Getter
	@Setter
	private PetriNet convertedModel;


}
