package io.iochord.apps.ips.model.analysis.services.dtm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryConfiguration;
import io.iochord.apps.ips.model.analysis.services.ism.IsmDiscoveryService;
import io.iochord.apps.ips.model.ism.v1.Connector;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.IsmGraph;
import io.iochord.apps.ips.model.ism.v1.Node;
import io.iochord.apps.ips.model.ism.v1.Page;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.enums.BranchType;
import lombok.Getter;
import lombok.Setter;

/**
 * Perform decision point analysis from given event log and discovered process model.
 *
 * @package ips-model
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2020
 * 
 */
public class DecisionMinerService extends AnIpsAsyncService<DecisionMinerConfig, DecisionMinerResult> {

	@Getter @Setter
	private IsmDiscoveryService discoveryService = new IsmDiscoveryService();
	private DecisionMinerResult result = new DecisionMinerResult();

	/**
	 * Discover process model from event log.
	 * 
	 * @param context
	 * @param config
	 * @return 
	 */
	private IsmGraph discoverProcessModel(ServiceContext context, DecisionMinerConfig config) {
		IsmDiscoveryConfiguration dConfig = new IsmDiscoveryConfiguration();
		// An example to get model from event log.
		// Parameter settings should be configured from the UI.
		// DecisionMinerConfig should inherit IsmDiscoveryConfiguration when performing process discovery.
		dConfig.setDatasetId(config.getDatasetId());
		// Perform case mapping towards the selected event log.
		// Case mapping should precede decision point analysis.
		Map<String, String> mappings = getMappings(context, config);
		// Retrieve case id, event name, and timestamp information.
		// Afterwards, set the parameter for process discovery miner.
		dConfig.setColCaseId("ci");
		dConfig.setColEventActivity("ea");
		dConfig.setColEventTimestamp("ec");
		dConfig.setDependencyThreshold(config.getPdDependencyThreshold());
		dConfig.setPositiveObservationThreshold(config.getPdPositiveObservationThreshold());
		// Discover process model from the given configuration.
		IsmGraph ismGraph = getDiscoveryService().run(context, dConfig);
		return ismGraph;
	}
	
	/* (non-Javadoc)
	 * @see io.iochord.apps.ips.core.services.AnIpsService#run(io.iochord.apps.ips.core.services.ServiceContext, java.lang.Object)
	 */
	@Override
	public DecisionMinerResult run(ServiceContext context, DecisionMinerConfig config) {
		IsmGraph ismGraph = discoverProcessModel(context, config); // Discover process model.
		result.setProcessmodel(ismGraph); // Put the discovered process model into result.
		Supplier<Stream<Entry<String, Page>>> pageEntrySet = () -> result.getProcessmodel().getPages().entrySet().stream();
		Stream<String> pageKeys = pageEntrySet.get().map(page -> page.getKey());
		Supplier<Stream<Page>> pages = () -> pageEntrySet.get().map(page -> page.getValue());
		Stream<Map<String, Node>> nodes = pages.get().map(page -> page.getNodes());
		// Retrieve only branch nodes, the type of which are "split".
		List<Node> branchNodes = nodes.map(collection -> collection.values().stream()
				.filter(node -> ElementType.NODE_BRANCH.equals(node.getElementType()) 
						&& BranchType.SPLIT.equals(((Branch) node).getType()))
				.collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
		result.setRule(new ArrayList<DecisionRule>());
		DecisionMinerAlgorithm miner = new DecisionMinerAlgorithm(context, config, branchNodes, result);
		System.out.println(miner.getResult().getRule().size());
		return miner.getResult();
	}

	/**
	 * Retrieve case mapping from record system.
	 * 
	 * @param context
	 * @param config
	 * @return
	 */
	private Map<String, String> getMappings(ServiceContext context, DecisionMinerConfig config) {
		String[] datasetIds = config.getDatasetId().split("_");
		String datasetId = datasetIds[0] + "_" + datasetIds[1] + "_" + datasetIds[2];
		Map<String, String> mappings = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder mappingsettings = new StringBuilder();
			mappingsettings.append("SELECT technical_names, mappings FROM ").append(datasetId).append("_mappings;");
			try (PreparedStatement st = conn.prepareStatement(mappingsettings.toString());) {
				try (ResultSet rs = st.executeQuery();) {
					while (rs.next()) {
						mappings.put(rs.getString(1), rs.getString(2));
					}
				}
			}
		} catch (SQLException e) {
			LoggerUtil.logError(e);
		}
		return mappings;
	}
}
