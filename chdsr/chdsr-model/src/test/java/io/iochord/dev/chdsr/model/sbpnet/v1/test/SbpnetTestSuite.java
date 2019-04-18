package io.iochord.dev.chdsr.model.sbpnet.v1.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.iochord.dev.chdsr.model.sbpnet.v1.SbpnetFactory;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch.BRANCH_TYPE;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue.QUEUE_TYPE;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.BranchImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.DeclarationImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.EndImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.MovingUnitImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.QueueImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ResourceImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StartImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ArcImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.PageImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetImpl;

public class SbpnetTestSuite {
	
	@Test
	public void test01Serialization() throws Exception {
		SbpnetFactory factory = SbpnetFactoryImpl.getInstance();
		SbpnetImpl net = (SbpnetImpl) factory.create();
		net.setId("Example Net");
		
		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		DeclarationImpl cust = (DeclarationImpl) factory.addData(page, "declaration");
		cust.setLabel("Customer");
		
		MovingUnitImpl custMu = (MovingUnitImpl) factory.addData(page, "movingunit");
		custMu.setLabel(cust.getLabel() + " MU");
		custMu.setDeclaration(cust);
		custMu.setExpression("Exp(20");
		custMu.setUnit(TimeUnit.MINUTES);
		custMu.setMaxArrival(100);
		
		StartImpl start = (StartImpl) factory.addNode(page, "start");
		start.setMovingUnit(custMu);
		
		QueueImpl qTeller = (QueueImpl) factory.addData(page, "queue");
		qTeller.setLabel("Teller Queue");
		qTeller.setSize(35);
		qTeller.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resTeller = (ResourceImpl) factory.addData(page, "resource");
		resTeller.setLabel("Teller Resource");
		ActivityImpl actTeller = (ActivityImpl) factory.addNode(page, "activity");
		actTeller.setLabel("Teller Service");
		actTeller.setQueue(qTeller);
		actTeller.setResource(resTeller);
		actTeller.setProcessingTimeExpression("constant(5,35)");
		actTeller.setUnit(TimeUnit.MINUTES);
		
		QueueImpl qATM = (QueueImpl) factory.addData(page, "queue");
		qATM.setLabel("ATM Queue");
		qATM.setSize(-1);
		qATM.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resATM = (ResourceImpl) factory.addData(page, "resource");
		resATM.setLabel("ATM Resource");
		ActivityImpl actATM = (ActivityImpl) factory.addNode(page, "activity");
		actATM.setLabel("ATM Service");
		actATM.setQueue(qATM);
		actATM.setResource(resATM);
		actATM.setProcessingTimeExpression("constant(5,15)");
		actATM.setUnit(TimeUnit.MINUTES);
		
		EndImpl end = (EndImpl) factory.addNode(page, "end");
		
		BranchImpl xorSplit1 = (BranchImpl) factory.addNode(page, "branch");
		xorSplit1.setType(BRANCH_TYPE.XOR_SPLIT);
		
		BranchImpl xorSplit2 = (BranchImpl) factory.addNode(page, "branch");
		xorSplit2.setType(BRANCH_TYPE.XOR_SPLIT);
		
		BranchImpl xorJoin1 = (BranchImpl) factory.addNode(page, "branch");
		xorJoin1.setType(BRANCH_TYPE.XOR_JOIN);

		BranchImpl xorJoin2 = (BranchImpl) factory.addNode(page, "branch");
		xorJoin1.setType(BRANCH_TYPE.XOR_JOIN);
		
		factory.addArc(page, start, xorSplit1);
		ArcImpl arc1 = (ArcImpl) factory.addArc(page, xorSplit1, xorJoin1);
		arc1.getAttributes().put("condition", "<0.4");
		ArcImpl arc2 = (ArcImpl) factory.addArc(page, xorSplit1, actATM);
		arc2.getAttributes().put("condition", ">=0.4");

		factory.addArc(page, actATM, xorSplit2);
		ArcImpl arc3 = (ArcImpl) factory.addArc(page, xorSplit2, xorJoin1);
		arc3.getAttributes().put("condition", "<0.3");
		ArcImpl arc4 = (ArcImpl) factory.addArc(page, xorSplit2, xorJoin2);
		arc4.getAttributes().put("condition", ">=0.3");

		factory.addArc(page, xorJoin1, actTeller);

		factory.addArc(page, actTeller, xorJoin2);
		factory.addArc(page, xorJoin2, end);
		
		// Test Serialize
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(net));
		
		XmlMapper xmapper = new XmlMapper();
		xmapper.setSerializationInclusion(Include.NON_NULL);
//		System.out.println(xmapper.writerWithDefaultPrettyPrinter().writeValueAsString(net));
		
	}
}
