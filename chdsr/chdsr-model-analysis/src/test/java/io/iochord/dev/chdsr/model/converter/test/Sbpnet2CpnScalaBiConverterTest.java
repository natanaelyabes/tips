package io.iochord.dev.chdsr.model.converter.test;

import java.io.PrintWriter;

import org.junit.Test;

import io.iochord.dev.chdsr.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.dev.chdsr.model.example.SbpnetExample;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.util.SerializationUtil;

public class Sbpnet2CpnScalaBiConverterTest {
	
	@Test
	public void test01CpnScalaCreation() throws Exception {
		Sbpnet snet = SbpnetExample.createComplete();
		System.out.println(SerializationUtil.encode(snet));
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		String net = converter.convert(snet);
		
		PrintWriter out = new PrintWriter("simulscala.txt");
		out.write(net);
		out.close();
	}
}
