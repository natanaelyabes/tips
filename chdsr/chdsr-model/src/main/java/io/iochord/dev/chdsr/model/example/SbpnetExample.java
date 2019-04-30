package io.iochord.dev.chdsr.model.example;

import java.util.concurrent.TimeUnit;

import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import io.iochord.dev.chdsr.model.sbpnet.v1.SbpnetFactory;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Branch.BRANCH_TYPE;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue.QUEUE_TYPE;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ActivityImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.BranchImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.GeneratorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ObjectTypeImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.QueueImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ResourceImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StartImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.StopImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.ConnectorImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.PageImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetImpl;

public class SbpnetExample {

	public static Sbpnet create() {
		SbpnetFactory factory = SbpnetFactoryImpl.getInstance();
		SbpnetImpl net = (SbpnetImpl) factory.create();
		
		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		ObjectTypeImpl cust = (ObjectTypeImpl) factory.addObjectType(page);
		cust.setLabel("Customer");
		
		GeneratorImpl custMu = (GeneratorImpl) factory.addGenerator(page);
		custMu.setLabel(cust.getLabel() + " MU");
		custMu.setObjectType(cust);
		custMu.setExpression("Exp(20");
		custMu.setUnit(TimeUnit.MINUTES);
		custMu.setMaxArrival(100);
		
		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(custMu);
		
		QueueImpl qTeller = (QueueImpl) factory.addQueue(page);
		qTeller.setLabel("Teller Queue");
		qTeller.setSize(35);
		qTeller.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resTeller = (ResourceImpl) factory.addResource(page);
		resTeller.setLabel("Teller Resource");
		ActivityImpl actTeller = (ActivityImpl) factory.addActivity(page);
		actTeller.setLabel("Teller Service");
		actTeller.setQueue(qTeller);
		actTeller.setResource(resTeller);
		actTeller.setProcessingTimeExpression("constant(5,35)");
		actTeller.setUnit(TimeUnit.MINUTES);
		
		QueueImpl qATM = (QueueImpl) factory.addQueue(page);
		qATM.setLabel("ATM Queue");
		qATM.setSize(-1);
		qATM.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resATM = (ResourceImpl) factory.addResource(page);
		resATM.setLabel("ATM Resource");
		ActivityImpl actATM = (ActivityImpl) factory.addActivity(page);
		actATM.setLabel("ATM Service");
		actATM.setQueue(qATM);
		actATM.setResource(resATM);
		actATM.setProcessingTimeExpression("constant(5,15)");
		actATM.setUnit(TimeUnit.MINUTES);
		
		StopImpl end = (StopImpl) factory.addStop(page);
		
		BranchImpl xorSplit1 = (BranchImpl) factory.addBranch(page);
		xorSplit1.setType(BRANCH_TYPE.XOR_SPLIT);
		
		BranchImpl xorSplit2 = (BranchImpl) factory.addBranch(page);
		xorSplit2.setType(BRANCH_TYPE.XOR_SPLIT);
		
		BranchImpl xorJoin1 = (BranchImpl) factory.addBranch(page);
		xorJoin1.setType(BRANCH_TYPE.XOR_JOIN);

		BranchImpl xorJoin2 = (BranchImpl) factory.addBranch(page);
		xorJoin1.setType(BRANCH_TYPE.XOR_JOIN);
		
		factory.addConnector(page, start, xorSplit1);
		ConnectorImpl arc1 = (ConnectorImpl) factory.addConnector(page, xorSplit1, xorJoin1);
		arc1.getAttributes().put("condition", "<0.4");
		ConnectorImpl arc2 = (ConnectorImpl) factory.addConnector(page, xorSplit1, actATM);
		arc2.getAttributes().put("condition", ">=0.4");

		factory.addConnector(page, actATM, xorSplit2);
		ConnectorImpl arc3 = (ConnectorImpl) factory.addConnector(page, xorSplit2, xorJoin1);
		arc3.getAttributes().put("condition", "<0.3");
		ConnectorImpl arc4 = (ConnectorImpl) factory.addConnector(page, xorSplit2, xorJoin2);
		arc4.getAttributes().put("condition", ">=0.3");

		factory.addConnector(page, xorJoin1, actTeller);

		factory.addConnector(page, actTeller, xorJoin2);
		factory.addConnector(page, xorJoin2, end);
		
		return net;
	}
	
}
