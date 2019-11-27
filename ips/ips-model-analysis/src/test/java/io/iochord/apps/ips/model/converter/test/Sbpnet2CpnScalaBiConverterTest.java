package io.iochord.apps.ips.model.converter.test;

import java.io.PrintWriter;

import org.junit.Test;

import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaBiConverter;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModel;

public class Sbpnet2CpnScalaBiConverterTest {
	
	@Test
	public void test01CpnScalaCreation() throws Exception {
		IsmGraph snet = IsmExample.createPortExample();
		System.out.println(SerializationUtil.encode(snet));
		Ism2CpnscalaBiConverter converter = new Ism2CpnscalaBiConverter();
		Ism2CpnscalaModel net = converter.convert(snet);
		
		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net.getConvertedModel());
		out.close();
	}
}
