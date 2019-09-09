package io.iochord.apps.ips.model.converter.test;

import java.io.PrintWriter;

import org.junit.Test;

import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.apps.ips.model.example.SbpnetExample;
import io.iochord.apps.ips.model.ism.v1.Ism;
import io.iochord.apps.ips.util.SerializationUtil;

public class Sbpnet2CpnScalaBiConverterTest {
	
	@Test
	public void test01CpnScalaCreation() throws Exception {
		Ism snet = SbpnetExample.createComplete();
		System.out.println(SerializationUtil.encode(snet));
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		String net = converter.convert(snet);
		
		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net);
		out.close();
	}
}
