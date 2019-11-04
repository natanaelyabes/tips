package io.iochord.apps.ips.model.example;

import java.util.concurrent.TimeUnit;

import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.data.Queue.QUEUE_TYPE;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.QueueImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ResourceImpl;
import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.PageImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StopImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;

/**
 *
 * Example SBPNet Model
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class IsmExample {
	public static IsmGraph createDefault() {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		IsmGraphImpl net = (IsmGraphImpl) factory.create();

		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		ObjectTypeImpl cust = (ObjectTypeImpl) factory.addObjectType(page);
		cust.setLabel("Unit");

		GeneratorImpl custMu = (GeneratorImpl) factory.addGenerator(page);
		custMu.setLabel(cust.getLabel() + " MU");
		custMu.setObjectType(new Referenceable<>(cust));
		custMu.setExpression("Math.round(Gaussian(100,10).draw())");
		custMu.setUnit(TimeUnit.MINUTES);
		custMu.setMaxArrival(100);

		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(new Referenceable<>(custMu));

		QueueImpl qTeller = (QueueImpl) factory.addQueue(page);
		qTeller.setLabel("Activity Queue");
		qTeller.setSize(35);
		qTeller.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resTeller = (ResourceImpl) factory.addResource(page);
		resTeller.setLabel("Activity Resource");
		ActivityImpl actTeller = (ActivityImpl) factory.addActivity(page);
		actTeller.setLabel("Activity Service");
		actTeller.setQueue(new Referenceable<>(qTeller));
		actTeller.setResource(new Referenceable<>(resTeller));
		actTeller.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actTeller.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actTeller.setProcessingTimeUnit(TimeUnit.MINUTES);

		StopImpl end = (StopImpl) factory.addStop(page);
		factory.addConnector(page, start, actTeller);
		factory.addConnector(page, actTeller, end);
	
		return net;
	}
	
	public static IsmGraph create() {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		IsmGraphImpl net = (IsmGraphImpl) factory.create();

		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		ObjectTypeImpl cust = (ObjectTypeImpl) factory.addObjectType(page);
		cust.setLabel("Customer");

		GeneratorImpl custMu = (GeneratorImpl) factory.addGenerator(page);
		custMu.setLabel(cust.getLabel() + " MU");
		custMu.setObjectType(new Referenceable<>(cust));
		custMu.setExpression("Math.round(Gaussian(100,10).draw())");
		custMu.setUnit(TimeUnit.MINUTES);
		custMu.setMaxArrival(100);

		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(new Referenceable<>(custMu));

		QueueImpl qTeller = (QueueImpl) factory.addQueue(page);
		qTeller.setLabel("Teller Queue");
		qTeller.setSize(35);
		qTeller.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resTeller = (ResourceImpl) factory.addResource(page);
		resTeller.setLabel("Teller Resource");
		ActivityImpl actTeller = (ActivityImpl) factory.addActivity(page);
		actTeller.setLabel("Teller Service");
		actTeller.setQueue(new Referenceable<>(qTeller));
		actTeller.setResource(new Referenceable<>(resTeller));
		actTeller.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actTeller.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actTeller.setProcessingTimeUnit(TimeUnit.MINUTES);

		QueueImpl qATM = (QueueImpl) factory.addQueue(page);
		qATM.setLabel("ATM Queue");
		qATM.setSize(-1);
		qATM.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resATM = (ResourceImpl) factory.addResource(page);
		resATM.setLabel("ATM Resource");
		ActivityImpl actATM = (ActivityImpl) factory.addActivity(page);
		actATM.setLabel("ATM Service");
		actATM.setQueue(new Referenceable<>(qATM));
		actATM.setResource(new Referenceable<>(resATM));
		actATM.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actATM.setProcessingTimeExpression("Math.round(Gaussian(300, 30).draw())");
		actATM.setProcessingTimeUnit(TimeUnit.MINUTES);

		StopImpl end = (StopImpl) factory.addStop(page);
		factory.addConnector(page, start, actATM);
		factory.addConnector(page, actATM, actTeller);
		factory.addConnector(page, actTeller, end);
	
		return net;
	}
	
	public static IsmGraph createComplete() {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		IsmGraphImpl net = (IsmGraphImpl) factory.create();

		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		ObjectTypeImpl cust = (ObjectTypeImpl) factory.addObjectType(page);
		cust.setLabel("Customer");

		GeneratorImpl custMu = (GeneratorImpl) factory.addGenerator(page);
		custMu.setLabel(cust.getLabel() + " MU");
		custMu.setObjectType(new Referenceable<>(cust));
		custMu.setExpression("Math.round(Gaussian(100,10).draw())");
		custMu.setUnit(TimeUnit.MINUTES);
		custMu.setMaxArrival(100);

		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(new Referenceable<>(custMu));

		QueueImpl qTeller = (QueueImpl) factory.addQueue(page);
		qTeller.setLabel("Teller Queue");
		qTeller.setSize(35);
		qTeller.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resTeller = (ResourceImpl) factory.addResource(page);
		resTeller.setLabel("Teller Resource");
		ActivityImpl actTeller = (ActivityImpl) factory.addActivity(page);
		actTeller.setLabel("Teller Service");
		actTeller.setQueue(new Referenceable<>(qTeller));
		actTeller.setResource(new Referenceable<>(resTeller));
		actTeller.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actTeller.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actTeller.setProcessingTimeUnit(TimeUnit.MINUTES);

		QueueImpl qATM = (QueueImpl) factory.addQueue(page);
		qATM.setLabel("ATM Queue");
		qATM.setSize(-1);
		qATM.setType(QUEUE_TYPE.FIFO);
		ResourceImpl resATM = (ResourceImpl) factory.addResource(page);
		resATM.setLabel("ATM Resource");
		ActivityImpl actATM = (ActivityImpl) factory.addActivity(page);
		actATM.setLabel("ATM Service");
		actATM.setQueue(new Referenceable<>(qATM));
		actATM.setResource(new Referenceable<>(resATM));
		actATM.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actATM.setProcessingTimeExpression("Math.round(Gaussian(300, 30).draw())");
		actATM.setProcessingTimeUnit(TimeUnit.MINUTES);

		StopImpl end = (StopImpl) factory.addStop(page);

		BranchImpl xorSplit1 = (BranchImpl) factory.addBranch(page);
		xorSplit1.setLabel("XOR Split 1");
		xorSplit1.setGate(BranchGate.XOR);
		xorSplit1.setType(BranchType.SPLIT);

		BranchImpl xorSplit2 = (BranchImpl) factory.addBranch(page);
		xorSplit2.setLabel("XOR Split 2");
		xorSplit2.setGate(BranchGate.XOR);
		xorSplit2.setType(BranchType.SPLIT);

		BranchImpl xorJoin1 = (BranchImpl) factory.addBranch(page);
		xorJoin1.setLabel("XOR Join 1");
		xorJoin1.setGate(BranchGate.XOR);
		xorJoin1.setType(BranchType.JOIN);

		BranchImpl xorJoin2 = (BranchImpl) factory.addBranch(page);
		xorJoin2.setLabel("XOR Join 2");
		xorJoin2.setGate(BranchGate.XOR);
		xorJoin2.setType(BranchType.JOIN);

		factory.addConnector(page, start, xorSplit1);
		ConnectorImpl arc1 = (ConnectorImpl) factory.addConnector(page, xorSplit1, xorJoin1);
		arc1.getAttributes().put("condition", "<0.4");
		ConnectorImpl arc2 = (ConnectorImpl) factory.addConnector(page, xorSplit1, actATM);
		arc2.getAttributes().put("condition", ">=0.4");
		arc2.setSourceIndex(1);

		factory.addConnector(page, actATM, xorSplit2);
		ConnectorImpl arc3 = (ConnectorImpl) factory.addConnector(page, xorSplit2, xorJoin1);
		arc3.getAttributes().put("condition", "<0.3");
		arc3.setTargetIndex(1);
		ConnectorImpl arc4 = (ConnectorImpl) factory.addConnector(page, xorSplit2, xorJoin2);
		arc4.getAttributes().put("condition", ">=0.3");
		arc4.setSourceIndex(1);
		arc4.setTargetIndex(1);

		factory.addConnector(page, xorJoin1, actTeller);
		ConnectorImpl arc5 = (ConnectorImpl) factory.addConnector(page, actTeller, xorJoin2);
		arc5.setTargetIndex(1);
		factory.addConnector(page, xorJoin2, end);

		return net;
	}
}
