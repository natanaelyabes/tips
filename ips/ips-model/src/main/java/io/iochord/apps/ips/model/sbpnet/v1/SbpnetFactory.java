package io.iochord.apps.ips.model.sbpnet.v1;

import io.iochord.apps.ips.model.sbpnet.v1.components.Activity;
import io.iochord.apps.ips.model.sbpnet.v1.components.Branch;
import io.iochord.apps.ips.model.sbpnet.v1.components.DataTable;
import io.iochord.apps.ips.model.sbpnet.v1.components.Function;
import io.iochord.apps.ips.model.sbpnet.v1.components.Generator;
import io.iochord.apps.ips.model.sbpnet.v1.components.Monitor;
import io.iochord.apps.ips.model.sbpnet.v1.components.ObjectType;
import io.iochord.apps.ips.model.sbpnet.v1.components.Queue;
import io.iochord.apps.ips.model.sbpnet.v1.components.Resource;
import io.iochord.apps.ips.model.sbpnet.v1.components.Start;
import io.iochord.apps.ips.model.sbpnet.v1.components.Stop;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public interface SbpnetFactory {
	Sbpnet create();

	Sbpnet create(Sbpnet ref);

	Page addPage(Sbpnet net);

	Data addData(Page page, String dataType);

	Node addNode(Page page, String nodeType);

	Connector addConnector(Page page, Element source, Element target);

	Configuration addConfiguration(Sbpnet net);

	DataTable addDataTable(Page page);

	ObjectType addObjectType(Page page);

	Generator addGenerator(Page page);

	Function addFunction(Page page);

	Queue addQueue(Page page);

	Resource addResource(Page page);

	Start addStart(Page page);

	Stop addStop(Page page);

	Activity addActivity(Page page);

	Branch addBranch(Page page);

	Monitor addMonitor(Page page);
}
