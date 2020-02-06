package io.iochord.apps.ips.model.ism.v1;

import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.data.Function;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.data.Resource;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Branch;
import io.iochord.apps.ips.model.ism.v1.nodes.Monitor;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism.v1.nodes.Stop;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public interface IsmFactory {
	IsmGraph create();

	IsmGraph create(IsmGraph ref);

	Page addPage(IsmGraph net);

	Data addData(Page page, String dataType);

	Node addNode(Page page, String nodeType);

	Connector addConnector(Page page, Element source, Element target);

	Configuration addConfiguration(IsmGraph net);

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
