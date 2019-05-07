import { GraphPage } from '@/iochord/chdsr/common/graph/interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphData } from '../interfaces/GraphData';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphConnectorImpl } from './GraphConnectorImpl';
import { NODE_TYPE } from '../enums/NODE';
import { DATA_TYPE } from '../enums/DATA';

export class GraphPageImpl extends GraphElementImpl implements GraphPage {
  public static fn_object_deserialize(object: any): Map<string, GraphPage> {
    const graphMap: Map<string, GraphPage> = new Map<string, GraphPage>();
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key];
        const graphPage: GraphPageImpl = new GraphPageImpl();
        const graphNodeMap: Map<string, GraphNode> = new Map<string, GraphNode>();
        const graphDataMap: Map<string, GraphData> = new Map<string, GraphData>();

        graphPage.fn_graph_element_set_id(element.id);
        graphPage.fn_graph_element_set_label(element.label);
        graphPage.fn_graph_element_set_type(element.elementType);
        graphPage.fn_graph_element_set_attributes(element.attributes);
        graphPage.fn_graph_page_set_arcs(GraphConnectorImpl.fn_object_deserialize(element.arcs));
        for (const nodeKey in element.nodes) {
          if (element.nodes.hasOwnProperty(nodeKey)) {
            const node = element.nodes[nodeKey];
            graphNodeMap.set(nodeKey, (NODE_TYPE as any)[node.elementType].fn_object_deserialize(node));
          }
        }
        for (const dataKey in element.data) {
          if (element.data.hasOwnProperty(dataKey)) {
            const data = element.data[dataKey];
            graphDataMap.set(dataKey, (DATA_TYPE as any)[data.elementType].fn_object_deserialize(data));
          }
        }
        graphPage.fn_graph_page_set_nodes(graphNodeMap);
        graphPage.fn_graph_page_set_data(graphDataMap);
        graphMap.set(key, graphPage);
      }
    }
    return graphMap;
  }

  public readonly TYPE: string | 'page' = 'page';

  private data: Map<string, GraphData> = new Map<string, GraphData>();
  private nodes: Map<string, GraphNode> = new Map<string, GraphNode>();
  private arcs: Map<string, GraphConnector> = new Map<string, GraphConnector>();

  constructor();
  constructor(data: Map<string, GraphData>, nodes: Map<string, GraphNode>, arcs: Map<string, GraphConnector>);
  constructor(data?: Map<string, GraphData>, nodes?: Map<string, GraphNode>, arcs?: Map<string, GraphConnector>) {
    super();
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_page_get_data(): Map<string, GraphData> {
    return this.data;
  }

  public fn_graph_page_set_data(data: Map<string, GraphData>): void {
    this.data = data;
  }

  public fn_graph_page_get_nodes(): Map<string, GraphNode> {
    return this.nodes;
  }

  public fn_graph_page_set_nodes(nodes: Map<string, GraphNode>): void {
    this.nodes = nodes;
  }

  public fn_graph_page_get_arcs(): Map<string, GraphConnector> {
    return this.arcs;
  }

  public fn_graph_page_set_arcs(arcs: Map<string, GraphConnector>): void {
    this.arcs = arcs;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
