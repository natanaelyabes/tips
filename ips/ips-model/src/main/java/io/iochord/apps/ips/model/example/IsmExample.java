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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class IsmExample {
	private static final String MATH_ROUND_100 = "Math.round(Gaussian(100,10).draw())";
	private static final String MATH_ROUND_300 = "Math.round(Gaussian(300, 30).draw())";
	private static final String MATH_ROUND_400 = "Math.round(Gaussian(400, 70).draw())";

	public static IsmGraph createDefault() {
		IsmFactory factory = IsmFactoryImpl.getInstance();
		IsmGraphImpl net = (IsmGraphImpl) factory.create();

		PageImpl page = (PageImpl) net.getPages().values().iterator().next();
		ObjectTypeImpl cust = (ObjectTypeImpl) factory.addObjectType(page);
		cust.setLabel("Unit");

		GeneratorImpl custMu = (GeneratorImpl) factory.addGenerator(page);
		custMu.setLabel(cust.getLabel() + " MU");
		custMu.setObjectType(new Referenceable<>(cust));
		custMu.setExpression(MATH_ROUND_100);
		custMu.setUnit(TimeUnit.MINUTES);
		custMu.setMaxArrival(100);

		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(new Referenceable<>(custMu));

		ActivityImpl actTeller = (ActivityImpl) factory.addActivity(page);
		actTeller.setLabel("Activity Service");
		actTeller.setProcessingTimeDistribution(DistributionType.CONSTANT);
		actTeller.setProcessingTimeExpression(MATH_ROUND_400);
		actTeller.setProcessingTimeUnit(TimeUnit.MINUTES);

		ResourceImpl resTeller = (ResourceImpl) factory.addResource(page);
		resTeller.setLabel("Activity Resource");
		resTeller.setNumberOfResource(1);
		actTeller.setResource(new Referenceable<>(resTeller));

		QueueImpl qTeller = (QueueImpl) factory.addQueue(page);
		qTeller.setLabel("Activity Queue");
		qTeller.setSize(35);
		qTeller.setType(QUEUE_TYPE.FIFO);
		actTeller.setQueue(new Referenceable<>(qTeller));

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
		custMu.setExpression(MATH_ROUND_100);
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
		actTeller.setProcessingTimeExpression(MATH_ROUND_400);
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
		actATM.setProcessingTimeExpression(MATH_ROUND_300);
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
		custMu.setExpression(MATH_ROUND_100);
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
		actTeller.setProcessingTimeExpression(MATH_ROUND_400);
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
		actATM.setProcessingTimeExpression(MATH_ROUND_300);
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
		String conditionStr = "condition";
		ConnectorImpl arc1 = (ConnectorImpl) factory.addConnector(page, xorSplit1, xorJoin1);
		arc1.getAttributes().put(conditionStr, "<0.4");
		ConnectorImpl arc2 = (ConnectorImpl) factory.addConnector(page, xorSplit1, actATM);
		arc2.getAttributes().put(conditionStr, ">=0.4");
		arc2.setSourceIndex(1);

		factory.addConnector(page, actATM, xorSplit2);
		ConnectorImpl arc3 = (ConnectorImpl) factory.addConnector(page, xorSplit2, xorJoin1);
		arc3.getAttributes().put(conditionStr, "<0.3");
		arc3.setTargetIndex(1);
		ConnectorImpl arc4 = (ConnectorImpl) factory.addConnector(page, xorSplit2, xorJoin2);
		arc4.getAttributes().put(conditionStr, ">=0.3");
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
		custGenerator.setExpression(MATH_ROUND_100);
		custGenerator.setUnit(TimeUnit.HOURS);
		custGenerator.setMaxArrival(100);

		StartImpl start = (StartImpl) factory.addStart(page);
		start.setGenerator(new Referenceable<>(custGenerator));

		ActivityImpl actQSDisc = (ActivityImpl) factory.addActivity(page);
		actQSDisc.setLabel("선박에서 양하 (Quay side Discharge)");
		actQSDisc.setIcon("/icons/port_icons/quay.png");
		actQSDisc.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actQSDisc.setProcessingTimeExpression(MATH_ROUND_400);
		actQSDisc.setProcessingTimeUnit(TimeUnit.MINUTES);
		actQSDisc.getAttributes().put("2", "대기 시간||92|223200|92|92|duration");
		

		ActivityImpl actMVDisc = (ActivityImpl) factory.addActivity(page);
		actMVDisc.setLabel("야드로 트럭 이동 (Move Discharge)");
		actMVDisc.setIcon("/icons/port_icons/move.png");
		actMVDisc.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actMVDisc.setProcessingTimeExpression(MATH_ROUND_400);
		actMVDisc.setProcessingTimeUnit(TimeUnit.MINUTES);
		actMVDisc.getAttributes().put("2", "대기 시간||3180|259200|132|1674|duration");

		ActivityImpl actYSDisc = (ActivityImpl) factory.addActivity(page);
		actYSDisc.setLabel("트럭에서 야드에 양하 (Yardside Discharge)");
		actYSDisc.setIcon("/icons/port_icons/yard.png");
		actYSDisc.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actYSDisc.setProcessingTimeExpression(MATH_ROUND_400);
		actYSDisc.setProcessingTimeUnit(TimeUnit.MINUTES);
		actYSDisc.getAttributes().put("2", "대기 시간||92|190800|92|92|duration");

		ActivityImpl actYSLoad = (ActivityImpl) factory.addActivity(page);
		actYSLoad.setLabel("야드에서 트럭에 적재 (Yard side Loading)");
		actYSLoad.setIcon("/icons/port_icons/yard.png");
		actYSLoad.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actYSLoad.setProcessingTimeExpression(MATH_ROUND_400);
		actYSLoad.setProcessingTimeUnit(TimeUnit.MINUTES);
		actYSLoad.getAttributes().put("2", "대기 시간||150|144000|150|150|duration");

		ActivityImpl actMVLoad = (ActivityImpl) factory.addActivity(page);
		actMVLoad.setLabel("부두로 트럭 이동 (Move Loading)");
		actMVLoad.setIcon("/icons/port_icons/move.png");
		actMVLoad.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actMVLoad.setProcessingTimeExpression(MATH_ROUND_400);
		actMVLoad.setProcessingTimeUnit(TimeUnit.MINUTES);
		actMVLoad.getAttributes().put("2", "대기 시간||2160|241200|180|3528|duration");

		ActivityImpl actQSLoad = (ActivityImpl) factory.addActivity(page);
		actQSLoad.setLabel("선적 (Quay side Loading)");
		actQSLoad.setIcon("/icons/port_icons/quay.png");
		actQSLoad.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
		actQSLoad.setProcessingTimeExpression(MATH_ROUND_400);
		actQSLoad.setProcessingTimeUnit(TimeUnit.MINUTES);
		actQSLoad.getAttributes().put("2", "대기 시간||180|151200|92|150|duration");

		ResourceImpl resYT = (ResourceImpl) factory.addResource(page);
		resYT.setLabel("야드 트럭 (Yard Trucks)");
		resYT.setNumberOfResource(10);
		resYT.getAttributes().put("2", "유휴 시간||7200|50400|900|14400|duration");
		resYT.getAttributes().put("3", "야드 트럭 평균 처리 시간 (1 cycle)||1200|154800|900|3528|duration");
		
		ResourceImpl resYC = (ResourceImpl) factory.addResource(page);
		resYC.setLabel("야드 크레인 (Yard Cranes)");
		resYC.setNumberOfResource(10);
		resYC.getAttributes().put("2", "유휴 시간||1020|72000|180|2400|duration");
		resYC.getAttributes().put("3", "YC 생산성||65||55|78|%");
		resYC.getAttributes().put("4", "YC 컨테이너 처리 비용||3.5|4725|1.5|15|$");
		
		ResourceImpl resQC = (ResourceImpl) factory.addResource(page);
		resQC.setLabel("키 크레인 (Quay Cranes)");
		resQC.setNumberOfResource(10);
		resQC.getAttributes().put("2", "유휴 시간||1020|72000|180|2400|duration");
		resQC.getAttributes().put("3", "QC 생산성||70||63|88|%");
		resQC.getAttributes().put("4", "QC 컨테이너 처리 비용||2|4096|2|2|$");
		
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
		arc1.toString();
		ConnectorImpl arc2a = (ConnectorImpl) factory.addConnector(page, xorSplit1, actQSDisc);
		arc2a.toString();
		ConnectorImpl arc2b = (ConnectorImpl) factory.addConnector(page, actQSDisc, actMVDisc);
		arc2b.toString();
		ConnectorImpl arc2c = (ConnectorImpl) factory.addConnector(page, actMVDisc, actYSDisc);
		arc2c.toString();
		ConnectorImpl arc2d = (ConnectorImpl) factory.addConnector(page, actYSDisc, xorJoin1);
		arc2d.toString();
		ConnectorImpl arc3a = (ConnectorImpl) factory.addConnector(page, xorSplit1, actYSLoad);
		arc3a.setSourceIndex(1);
		ConnectorImpl arc3b = (ConnectorImpl) factory.addConnector(page, actYSLoad, actMVLoad);
		arc3b.toString();
		ConnectorImpl arc3c = (ConnectorImpl) factory.addConnector(page, actMVLoad, actQSLoad);
		arc3c.toString();
		ConnectorImpl arc3d = (ConnectorImpl) factory.addConnector(page, actQSLoad, xorJoin1);
		arc3d.setTargetIndex(1);
		ConnectorImpl arc4 = (ConnectorImpl) factory.addConnector(page, xorJoin1, end);
		arc4.toString();
		return net;
	}
}
