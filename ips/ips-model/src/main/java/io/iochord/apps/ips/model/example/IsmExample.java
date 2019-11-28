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

		ActivityImpl actTeller = (ActivityImpl) factory.addActivity(page);
		actTeller.setLabel("Activity Service");
		actTeller.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actTeller.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actTeller.setProcessingTimeUnit(TimeUnit.MINUTES);
		actTeller.getAttributes().put("2", "대기 시간|2048|151200|92|150|duration");

		ResourceImpl resTeller = (ResourceImpl) factory.addResource(page);
		resTeller.setLabel("Activity Resource");
		resTeller.setNumberOfResource(1);
		actTeller.setResource(new Referenceable<>(resTeller));

//		QueueImpl qTeller = (QueueImpl) factory.addQueue(page);
//		qTeller.setLabel("Activity Queue");
//		qTeller.setSize(35);
//		qTeller.setType(QUEUE_TYPE.FIFO);
//		actTeller.setQueue(new Referenceable<>(qTeller));

		StopImpl end = (StopImpl) factory.addStop(page);
		factory.addConnector(page, start, actTeller);
		factory.addConnector(page, actTeller, end);
	
		return net;
	}
	
	public static IsmGraph createSimpleBankExample() {
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
	
	public static IsmGraph createBankExample() {
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
	

	@SuppressWarnings("unused")
	public static IsmGraph createPortExample() {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		IsmGraphImpl net = (IsmGraphImpl) factory.create();

		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		
		ObjectTypeImpl conObj = (ObjectTypeImpl) factory.addObjectType(page);
		conObj.setLabel("Container");

		GeneratorImpl custGenerator = (GeneratorImpl) factory.addGenerator(page);
		custGenerator.setLabel(conObj.getLabel() + " Generator");
		custGenerator.setObjectType(new Referenceable<>(conObj));
		custGenerator.setExpression("Math.round(Gaussian(100,10).draw())");
		custGenerator.setUnit(TimeUnit.HOURS);
		custGenerator.setMaxArrival(100);

		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(new Referenceable<>(custGenerator));

		ActivityImpl actQSDisc = (ActivityImpl) factory.addActivity(page);
		actQSDisc.setLabel("선박에서 양하");//Quayside Discharge");
		actQSDisc.setIcon("/icons/port_icons/quay.png");
		actQSDisc.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actQSDisc.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actQSDisc.setProcessingTimeUnit(TimeUnit.MINUTES);
		actQSDisc.getAttributes().put("2", "대기 시간|2048|219600|92|122|duration");
		

		ActivityImpl actMVDisc = (ActivityImpl) factory.addActivity(page);
		actMVDisc.setLabel("야드로 트럭 이동"); //Move Discharge");
		actMVDisc.setIcon("/icons/port_icons/move.png");
		actMVDisc.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actMVDisc.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actMVDisc.setProcessingTimeUnit(TimeUnit.MINUTES);
		actMVDisc.getAttributes().put("2", "대기 시간|2048|259200|1320|1674|duration");

		ActivityImpl actYSDisc = (ActivityImpl) factory.addActivity(page);
		actYSDisc.setLabel("트럭에서 야드에 양하"); //Yardside Discharge");
		actYSDisc.setIcon("/icons/port_icons/yard.png");
		actYSDisc.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actYSDisc.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actYSDisc.setProcessingTimeUnit(TimeUnit.MINUTES);
		actYSDisc.getAttributes().put("2", "대기 시간|2048|190800|1320|1674|duration");

		ActivityImpl actYSLoad = (ActivityImpl) factory.addActivity(page);
		actYSLoad.setLabel("야드에서 트럭에 적재"); //Yardside Loading");
		actYSLoad.setIcon("/icons/port_icons/yard.png");
		actYSLoad.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actYSLoad.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actYSLoad.setProcessingTimeUnit(TimeUnit.MINUTES);
		actYSLoad.getAttributes().put("2", "대기 시간|2048|144000|150|150|duration");

		ActivityImpl actMVLoad = (ActivityImpl) factory.addActivity(page);
		actMVLoad.setLabel("부두로 트럭 이동"); //Move Loading");
		actMVLoad.setIcon("/icons/port_icons/move.png");
		actMVLoad.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actMVLoad.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actMVLoad.setProcessingTimeUnit(TimeUnit.MINUTES);
		actMVLoad.getAttributes().put("2", "대기 시간|2048|241200|180|3528|duration");

		ActivityImpl actQSLoad = (ActivityImpl) factory.addActivity(page);
		actQSLoad.setLabel("선적"); //Quayside Loading");
		actQSLoad.setIcon("/icons/port_icons/quay.png");
		actQSLoad.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actQSLoad.setProcessingTimeExpression("Math.round(Gaussian(400, 70).draw())");
		actQSLoad.setProcessingTimeUnit(TimeUnit.MINUTES);
		actQSLoad.getAttributes().put("2", "대기 시간|2048|151200|92|150|duration");

		ResourceImpl resYT = (ResourceImpl) factory.addResource(page);
		resYT.setLabel("야드 트럭"); //Yard Trucks");
		resYT.setNumberOfResource(10);
		resYT.getAttributes().put("2", "유휴 시간|1945|50400|900|14400|duration");
		resYT.getAttributes().put("3", "야드 트럭 평균 처리 시간 (1 cycle)|1945|154800|150|3528|duration");
		
		ResourceImpl resYC = (ResourceImpl) factory.addResource(page);
		resYC.setLabel("야드 크레인"); //Yard Cranes");
		resYC.setNumberOfResource(10);
		resYC.getAttributes().put("2", "유휴 시간|1050|73414|180|2400|duration");
		resYC.getAttributes().put("3", "YC 생산성 (%)|1050|68250|55|78");
		resYC.getAttributes().put("4", "YC 컨테이너 처리 비용 ($)|1050|4725|1.5|15");
		
		ResourceImpl resQC = (ResourceImpl) factory.addResource(page);
		resQC.setLabel("키 크레인"); //Quay Cranes");
		resQC.setNumberOfResource(10);
		resQC.getAttributes().put("2", "유휴 시간|2048|7221|750|14000|duration");
		resQC.getAttributes().put("3", "QC 생산성 (%)|2048|143360.00|63|88");
		resQC.getAttributes().put("4", "QC 컨테이너 처리 비용 ($)|2048|4096|2|2");
		
		actQSDisc.setResource(new Referenceable<>(resQC));
		actMVDisc.setResource(new Referenceable<>(resYT));
		actYSDisc.setResource(new Referenceable<>(resYC));
		actYSLoad.setResource(new Referenceable<>(resYC));
		actMVLoad.setResource(new Referenceable<>(resYT));
		actQSLoad.setResource(new Referenceable<>(resQC));
		
		BranchImpl xorSplit1 = (BranchImpl) factory.addBranch(page);
		xorSplit1.setLabel("XOR Split 1");
		xorSplit1.setGate(BranchGate.XOR);
		xorSplit1.setType(BranchType.SPLIT);
		
		BranchImpl xorJoin1 = (BranchImpl) factory.addBranch(page);
		xorJoin1.setLabel("XOR Join 1");
		xorJoin1.setGate(BranchGate.XOR);
		xorJoin1.setType(BranchType.JOIN);
		
		StopImpl end = (StopImpl) factory.addStop(page);
		
		ConnectorImpl arc1 = (ConnectorImpl) factory.addConnector(page, start, xorSplit1);
		ConnectorImpl arc2a = (ConnectorImpl) factory.addConnector(page, xorSplit1, actQSDisc);
		ConnectorImpl arc2b = (ConnectorImpl) factory.addConnector(page, actQSDisc, actMVDisc);
		ConnectorImpl arc2c = (ConnectorImpl) factory.addConnector(page, actMVDisc, actYSDisc);
		ConnectorImpl arc2d = (ConnectorImpl) factory.addConnector(page, actYSDisc, xorJoin1);
		ConnectorImpl arc3a = (ConnectorImpl) factory.addConnector(page, xorSplit1, actYSLoad);
		arc3a.setSourceIndex(1);
		ConnectorImpl arc3b = (ConnectorImpl) factory.addConnector(page, actYSLoad, actMVLoad);
		ConnectorImpl arc3c = (ConnectorImpl) factory.addConnector(page, actMVLoad, actQSLoad);
		ConnectorImpl arc3d = (ConnectorImpl) factory.addConnector(page, actQSLoad, xorJoin1);
		arc3d.setTargetIndex(1);
		ConnectorImpl arc4 = (ConnectorImpl) factory.addConnector(page, xorJoin1, end);
//		arc1.getAttributes().put("condition", "<0.4");
		return net;
	}
}
