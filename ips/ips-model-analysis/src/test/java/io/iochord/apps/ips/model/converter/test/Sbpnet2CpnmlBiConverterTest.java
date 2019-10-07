package io.iochord.apps.ips.model.converter.test;

import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.exporter.DOMGenerator;
import org.junit.Test;

import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnmlBiConverter;
import io.iochord.apps.ips.model.example.SbpnetExample;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;

public class Sbpnet2CpnmlBiConverterTest {
	
	@Test
	public void test01CpnMlCreation() throws Exception {
//
//		Sbpnet2CpnmlBiConverter converter = new Sbpnet2CpnmlBiConverter();
//		PetriNet net = converter.createPetriNet("Net 1");
//		converter.addTypeDeclaration(net, "INT", converter.getCfactory().createCPNInt(), true);
//		converter.addVariableDeclaration(net, "i", "INT");
//		Page page = converter.addPage(net, "Page 01");
//		Place p1 = converter.addPlace(page, "p1", "INT", "1`1");
//		Transition t = converter.addTransition(page, "t");
//		Place p2 = converter.addPlace(page, "p2", "INT", "1`1");
//		converter.addArc(page, p1, t, "i");
//		converter.addArc(page, t, p2, "i");
		
		IsmGraph snet = SbpnetExample.createComplete();
		Sbpnet2CpnmlBiConverter converter = new Sbpnet2CpnmlBiConverter();
		PetriNet net = converter.convert(snet);
		
		DOMGenerator.export(net, "test.cpn");
	}
}
