package io.iochord.apps.ips.model.converter.sbp2cpn;

import org.cpntools.accesscpn.model.PetriNet;

import io.iochord.apps.ips.model.converter.ConversionModel;
import io.iochord.apps.ips.model.sbpnet.v1.Sbpnet;
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
public class Sbpnet2CpnmlModel implements ConversionModel<Sbpnet, PetriNet> {

	@Getter
	@Setter
	private Sbpnet originalModel;
	
	@Getter
	@Setter
	private PetriNet convertedModel;


}
