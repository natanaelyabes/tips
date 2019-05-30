import { GraphConnector } from './../interfaces/GraphConnector';
import { GraphPage } from '@/iochord/chdsr/common/graph/interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphData } from '../interfaces/GraphData';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphConnectorImpl } from './GraphConnectorImpl';
import { NODE_TYPE } from '../enums/NODE';
import { DATA_TYPE } from '../enums/DATA';
import { stringify } from 'querystring';

export class GraphPageImpl extends GraphElementImpl implements GraphPage {
  public static readonly TYPE: string | null = 'page';

  public static deserialize(object: any): Map<string, GraphPage> | null {
    const graphMap: Map<string, GraphPage> = new Map<string, GraphPage>();
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key];
        const graphPage: GraphPageImpl = new GraphPageImpl();
        const graphNodeMap: Map<string, GraphNode> = new Map<string, GraphNode>();
        const graphDataMap: Map<string, GraphData> = new Map<string, GraphData>();
        const graphConnectorMap: Map<string, GraphConnector> = new Map<string, GraphConnector>();

        for (const dataKey in element.data) {
          if (element.data.hasOwnProperty(dataKey)) {
            const data = element.data[dataKey];
            graphDataMap.set(dataKey, (DATA_TYPE as any)[data.elementType].deserialize(data));
          }
        }

        for (const nodeKey in element.nodes) {
          if (element.nodes.hasOwnProperty(nodeKey)) {
            const node = element.nodes[nodeKey];
            graphNodeMap.set(nodeKey, (NODE_TYPE as any)[node.elementType].deserialize(node));
          }
        }

        graphPage.setId(element.id);
        graphPage.setLabel(element.label);
        graphPage.setType(element.elementType);
        graphPage.setAttributes(element.attributes);
        graphPage.setArcs(GraphConnectorImpl.deserialize(element.connectors) as Map<string, GraphConnector>);
        graphPage.setNodes(graphNodeMap);
        graphPage.setData(graphDataMap);
        graphMap.set(key, graphPage);
      }
    }
    return graphMap;
  }

  private data: Map<string, GraphData> | null = new Map<string, GraphData>();
  private nodes: Map<string, GraphNode> | null = new Map<string, GraphNode>();
  private arcs: Map<string, GraphConnector> | null = new Map<string, GraphConnector>();

  constructor();
  constructor(data: Map<string, GraphData>, nodes: Map<string, GraphNode>, arcs: Map<string, GraphConnector>);
  constructor(data?: Map<string, GraphData>, nodes?: Map<string, GraphNode>, arcs?: Map<string, GraphConnector>) {
    super();
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getData(): Map<string, GraphData> | null {
    return this.data;
  }

  public setData(data: Map<string, GraphData>): void {
    this.data = data;
  }

  public getNodes(): Map<string, GraphNode> | null {
    return this.nodes;
  }

  public setNodes(nodes: Map<string, GraphNode>): void {
    this.nodes = nodes;
  }

  public getArcs(): Map<string, GraphConnector> | null {
    return this.arcs;
  }

  public setArcs(arcs: Map<string, GraphConnector>): void {
    this.arcs = arcs;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
