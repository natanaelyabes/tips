package io.iochord.apps.ips.model.converter.test;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModel;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class Sbpnet2CpnScalaBiConverterTest {
	
	@Test
	public void test01CpnScalaCreation() throws FileNotFoundException {
		IsmGraph snet = IsmExample.createBankExample();
		Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		Ism2CpnscalaModel net = converter.convert(snet);
		
		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net.getConvertedModel());
		out.close();
		assertTrue(true);
	}
}
