package io.iochord.apps.ips.model.ism.v1;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ElementType {

	public static final String ELEMENT = "element";

	public static final String ELEMENT_NET = "net";

	public static final String ELEMENT_PAGE = "page";

	public static final String ELEMENT_NODE = "node";

	public static final String ELEMENT_DATA = "data";

	public static final String ELEMENT_CONNECTOR = "connector";
	
	public static final String ELEMENT_CONFIGURATION = "configuration";

	public static final String DATA_DATATABLE = "datatable";

	public static final String DATA_FUNCTION = "function";

	public static final String DATA_GENERATOR = "generator";

	public static final String DATA_OBJECTTYPE = "objecttype";

	public static final String DATA_QUEUE = "queue";

	public static final String DATA_RESOURCE = "resource";

	public static final String NODE_ACTIVITY = "activity";

	public static final String NODE_BRANCH = "branch";

	public static final String NODE_CONTROL = "control";

	public static final String NODE_MONITOR = "monitor";

	public static final String NODE_START = "start";

	public static final String NODE_STOP = "stop";
}
