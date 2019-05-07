package io.iochord.dev.chdsr.model.sbpnet.v1.test;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.iochord.dev.chdsr.model.example.SbpnetExample;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.SbpnetFactory;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class SbpnetCreationTest {
	@Test
	public void test01CreateEmptyNet() {
		SbpnetFactory factory = SbpnetFactoryImpl.getInstance();
		SbpnetImpl net = (SbpnetImpl) factory.create();
		net.toString();
	}
	
	@Test
	public void test02Serialization() throws Exception {
		
		Sbpnet net = SbpnetExample.create();
		
		// Test Serialize
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(net));
		
		XmlMapper xmapper = new XmlMapper();
		xmapper.setSerializationInclusion(Include.NON_NULL);
		System.out.println(xmapper.writerWithDefaultPrettyPrinter().writeValueAsString(net));
	}
}
