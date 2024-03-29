package io.iochord.apps.ips.simulator.web.v1.api.controllers.analysis;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryTokenService;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.IsmFactory;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.data.impl.GeneratorImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;
import io.iochord.apps.ips.model.ism.v1.data.impl.ResourceImpl;
import io.iochord.apps.ips.model.ism.v1.impl.ConnectorImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmFactoryImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchGate;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchRule;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.DistributionType;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.ActivityImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.BranchImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.impl.StartImpl;

/**
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@RestController
public class IsmDiscoveryController extends AnAnalysisController {

	public static final String BASE_URI = AnAnalysisController.BASE_URI + "/discover";

	/**
	 * Process discovery action
	 * 
	 * @param datasetId dataset Id
	 * @param config process discovery configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/ism", BASE_URI + "/ism/{datasetId}" })
	public ServiceContext getPostDiscoverIsm(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new IsmDiscoveryConfiguration();
			config.setDatasetId(datasetId.get());
		}
		
		return run(new IsmDiscoveryService(), config, IsmGraph.class, headers);
	}

	/**
	 * Process discovery action
	 * 
	 * @param datasetId dataset Id
	 * @param config process discovery configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/hybrid", BASE_URI + "/hybrid/{datasetId}" })
	public ServiceContext getPostDiscoverIsmHybrid(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new IsmDiscoveryConfiguration();
			config.setDatasetId(datasetId.get());
		}
		
		ServiceContext context = run(new IsmDiscoveryService(), config, IsmGraph.class, null);
		IsmGraphImpl graph = (IsmGraphImpl) context.getData();
		
		IsmFactory factory = IsmFactoryImpl.getInstance();
		
		Map<String, ?> mapact = null;
		Map<String, List<String>> mapOrgRes = null;
		Map<String, List<String>> mapActOrg = null;
		Map<String, ResourceImpl> mapResImpl = new HashMap<String, ResourceImpl>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			InputStream pdFile = getClass().getClassLoader().getResourceAsStream("ProcessDist.json");
			InputStream rmFile = getClass().getClassLoader().getResourceAsStream("ResourceDist.json");

			Map<?, ?> map1 = mapper.readValue(pdFile, Map.class);
			Map<?, ?> map2 = mapper.readValue(rmFile, Map.class);

		    mapact = (Map<String, ?>) map1.get("MODEL_4");
		    mapOrgRes = (Map<String, List<String>>) map2.get("mgroupres");
		    mapActOrg = (Map<String, List<String>>) map2.get("mactgroup");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Page p : graph.getPages().values()) {
			for(String org : mapOrgRes.keySet()) {
				ResourceImpl resImpl = (ResourceImpl) factory.addResource(p);
				resImpl.setLabel(org);
				resImpl.setNumberOfResource(mapOrgRes.get(org).size());
				mapResImpl.put(org, resImpl);
			}
			for (Node rd : p.getNodes().values()) {
				if (rd instanceof Start) {
					StartImpl d = (StartImpl) rd;
					
					String MATH_ROUND_GEN = "Math.round(Gaussian(18.444333996023857,31.905588692352455)(Simulation.randBasis).draw())";
					
					ObjectTypeImpl obj = (ObjectTypeImpl) factory.addObjectType(p);
					obj.setLabel("Unit");
					
					GeneratorImpl gen = (GeneratorImpl) factory.addGenerator(p);
					gen.setLabel("Generator");
					gen.setObjectType(new Referenceable<>(obj));
					gen.setExpression(MATH_ROUND_GEN);
					gen.setUnit(TimeUnit.SECONDS);
					
					d.setGenerator(new Referenceable<>(gen));
				}
				
				if (rd instanceof Activity) {
					ActivityImpl act = (ActivityImpl) rd;
					//System.out.println(act.getId()+" - "+act.getLabel());
					act.setProcessingTimeDistribution(DistributionType.GAUSSIAN);
					Map<?, List> mapdist = (Map<?,List>)mapact.get(rd.getLabel());
					if(mapdist != null) {
						double param1 = (double) mapdist.get("Normal").get(0);
						double param2 = (double) mapdist.get("Normal").get(1);
						act.setProcessingTimeExpression(" Math.round(Gaussian("+param1+","+param2+")(Simulation.randBasis).draw()) ");
						ResourceImpl resImpl = mapResImpl.get(mapActOrg.get(rd.getLabel()).get(0));
						act.setResource(new Referenceable<>(resImpl));
					}
					//System.out.println(rd.getLabel()+" : "+mapact.get(rd.getLabel()));
				}
				
				if (rd instanceof Branch) {
					BranchImpl branch = (BranchImpl) rd;
					boolean xorFinBranch = false;
					List<Connector> cons = branch.getOutputConnectors();
					for(Connector con : cons) {
						Element el = con.getTarget().getValue();
						if(branch.getType() == BranchType.SPLIT && branch.getGate() == BranchGate.XOR && el instanceof Branch && el.getLabel().equals("jb-stop")) {
							xorFinBranch = true;
							break;
						}
					}
					if(xorFinBranch) {
						branch.setRule(BranchRule.DATA);
						String dataStr = "data";
						for(Connector con : cons) {
							ConnectorImpl conImpl = (ConnectorImpl) con;
							Element el = con.getTarget().getValue();
							if(el instanceof Branch && el.getLabel().equals("jb-stop")) {
								conImpl.getAttributes().put(dataStr, "b.data.get.inc >= 3");
							}
							else {
								conImpl.getAttributes().put(dataStr, "b.data.get.inc < 3");
							}
						}
					}
				}
			}
		}
		return context;
	}
	
	/**
	 * Process discovery action
	 * 
	 * @param datasetId dataset Id
	 * @param config process discovery configuration
	 * @param headers autowired http headers
	 * @return service context instance
	 * @throws Exception exception
	 */
	@PostMapping(value = { BASE_URI + "/ism/animate/{datasetId}" })
	public ServiceContext getPostDiscoverIsmAnimStr(@PathVariable Optional<String> datasetId,
			@RequestBody(required = false) IsmDiscoveryConfiguration config, @RequestHeader HttpHeaders headers) {
		if (config == null && datasetId.isPresent()) {
			config = new IsmDiscoveryConfiguration();
			config.setDatasetId(datasetId.get());
		}
		
		return run(new IsmDiscoveryTokenService(), config, String.class, headers);
	}

}
