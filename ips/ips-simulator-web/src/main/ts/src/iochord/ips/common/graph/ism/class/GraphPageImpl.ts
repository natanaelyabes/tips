import { GraphPage } from '@/iochord/ips/common/graph/ism/interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphData } from '../interfaces/GraphData';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphConnectorImpl } from './GraphConnectorImpl';
import { NODE_TYPE } from '../enums/NODE';
import { DATA_TYPE } from '../enums/DATA';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphPageImpl extends GraphElementImpl implements GraphPage {
  public static deserialize(object: any): TSMap<string, GraphPage> | null {
    const graphMap: TSMap<string, GraphPage> = new TSMap<string, GraphPage>();
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key];
        const graphPage: GraphPage = new GraphPageImpl();
        const graphNodeMap: TSMap<string, GraphNode> = new TSMap<string, GraphNode>();
        const graphDataMap: TSMap<string, GraphData> = new TSMap<string, GraphData>();
        const graphConnectorMap: TSMap<string, GraphConnector> = new TSMap<string, GraphConnector>();

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
        graphPage.setArcs(GraphConnectorImpl.deserialize(element.connectors) as TSMap<string, GraphConnector>);
        graphPage.setNodes(graphNodeMap);
        graphPage.setData(graphDataMap);
        graphMap.set(key, graphPage);
      }
    }
    return graphMap;
  }

  private data?: TSMap<string, GraphData> | null = new TSMap<string, GraphData>();
  private nodes?: TSMap<string, GraphNode> | null = new TSMap<string, GraphNode>();
  private arcs?: TSMap<string, GraphConnector> | null = new TSMap<string, GraphConnector>();

  constructor() {
    super();
  }

  public getData(): TSMap<string, GraphData> | null {
    return this.data as TSMap<string, GraphData> | null;
  }

  public setData(data: TSMap<string, GraphData>): void {
    this.data = data || this.data;
  }

  public getNodes(): TSMap<string, GraphNode> | null {
    return this.nodes as TSMap<string, GraphNode> | null;
  }

  public setNodes(nodes: TSMap<string, GraphNode>): void {
    this.nodes = nodes || this.nodes;
  }

  public getArcs(): TSMap<string, GraphConnector> | null {
    return this.arcs as TSMap<string, GraphConnector> | null;
  }

  public setArcs(arcs: TSMap<string, GraphConnector>): void {
    this.arcs = arcs || this.arcs;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
