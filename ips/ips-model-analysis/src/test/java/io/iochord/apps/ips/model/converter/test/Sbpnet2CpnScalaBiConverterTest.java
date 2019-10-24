package io.iochord.apps.ips.model.converter.test;

import java.io.PrintWriter;

import org.junit.Test;

import io.iochord.apps.ips.common.util.SerializationUtil;
import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.apps.ips.model.example.IsmExample;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;

public class Sbpnet2CpnScalaBiConverterTest {
	
	@Test
	public void test01CpnScalaCreation() throws Exception {
		IsmGraph snet = IsmExample.createComplete();
		System.out.println(SerializationUtil.encode(snet));
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		String net = converter.convert(snet);
		
		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net);
		out.close();
	}
}
