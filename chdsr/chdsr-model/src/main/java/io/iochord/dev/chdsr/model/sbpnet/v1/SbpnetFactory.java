package io.iochord.dev.chdsr.model.sbpnet.v1;

public interface SbpnetFactory {
	Sbpnet create();
	Sbpnet create(Sbpnet ref);
	Page addPage(Sbpnet net);
	Data addData(Page page, String dataType);
	Node addNode(Page page, String nodeType);
	Arc addArc(Page page, Element source, Element target);
	Configuration addConfiguration(Sbpnet net);
}
