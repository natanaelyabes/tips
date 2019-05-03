package io.iochord.dev.chdsr.model.converter.test;

import org.junit.Test;

import io.iochord.dev.chdsr.model.converter.sbp2cpn.Sbpnet2CpnBiConverter;
import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph;
import io.iochord.dev.chdsr.model.example.SbpnetExample;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.util.MapperUtils;

public class Sbpnet2CpnBiConverterTest {
	
	@Test
	public void test01TestConvertExample() throws Exception {
		Sbpnet snet = SbpnetExample.create();
		Sbpnet2CpnBiConverter converter = new Sbpnet2CpnBiConverter();
		CPNGraph cnet = converter.convert(snet);
		System.out.println(MapperUtils.getJsonSerializer().writerWithDefaultPrettyPrinter().writeValueAsString(cnet));
	}
}
