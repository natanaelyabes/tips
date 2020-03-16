import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphData } from '../interfaces/GraphData';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphConnectorImpl } from './GraphConnectorImpl';
import { NODE_TYPE } from '../enums/NODE';
import { DATA_TYPE } from '../enums/DATA';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphPage interface.
 *
 * @export
 * @class GraphPageImpl
 * @extends {GraphElementImpl}
 * @implements {GraphPage}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphPageImpl extends GraphElementImpl implements GraphPage {

  /**
   * Deserialize JSON object to GraphPageImpl.
   *
   * @static
   * @param {*} object
   * @returns {(TSMap<string, GraphPage> | null)}
   * @memberof GraphPageImpl
   */
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
        graphPage.setConnectors(GraphConnectorImpl.deserialize(element.connectors) as TSMap<string, GraphConnector>);
        graphPage.setNodes(graphNodeMap);
        graphPage.setData(graphDataMap);
        graphMap.set(key, graphPage);
      }
    }
    return graphMap;
  }

  /**
   * Data nodes of the current page.
   *
   * @private
   * @type {(TSMap<string, GraphData> | null)}
   * @memberof GraphPageImpl
   */
  private data?: TSMap<string, GraphData> | null = new TSMap<string, GraphData>();

  /**
   * Nodes of the current page.
   *
   * @private
   * @type {(TSMap<string, GraphNode> | null)}
   * @memberof GraphPageImpl
   */
  private nodes?: TSMap<string, GraphNode> | null = new TSMap<string, GraphNode>();

  /**
   * Connectors of the current page.
   *
   * @private
   * @type {(TSMap<string, GraphConnector> | null)}
   * @memberof GraphPageImpl
   */
  private connectors?: TSMap<string, GraphConnector> | null = new TSMap<string, GraphConnector>();

  /**
   * Creates an instance of GraphPageImpl.
   *
   * @memberof GraphPageImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the data nodes of current page.
   *
   * @returns {(TSMap<string, GraphData> | null)}
   * @memberof GraphPageImpl
   */
  public getData(): TSMap<string, GraphData> | null {
    return this.data as TSMap<string, GraphData> | null;
  }

  /**
   * Assigns data nodes to the current page.
   *
   * @param {TSMap<string, GraphData>} data
   * @memberof GraphPageImpl
   */
  public setData(data: TSMap<string, GraphData>): void {
    this.data = data || this.data;
  }

  /**
   * Returns the nodes of current page.
   *
   * @returns {(TSMap<string, GraphNode> | null)}
   * @memberof GraphPageImpl
   */
  public getNodes(): TSMap<string, GraphNode> | null {
    return this.nodes as TSMap<string, GraphNode> | null;
  }

  /**
   * Assigns the nodes for the current page.
   *
   * @param {TSMap<string, GraphNode>} nodes
   * @memberof GraphPageImpl
   */
  public setNodes(nodes: TSMap<string, GraphNode>): void {
    this.nodes = nodes || this.nodes;
  }

  /**
   * Returns the connectors of the current page.
   *
   * @returns {(TSMap<string, GraphConnector> | null)}
   * @memberof GraphPageImpl
   */
  public getConnectors(): TSMap<string, GraphConnector> | null {
    return this.connectors as TSMap<string, GraphConnector> | null;
  }

  /**
   * Assigns the connectors for the current page.
   *
   * @param {TSMap<string, GraphConnector>} connectors
   * @memberof GraphPageImpl
   */
  public setConnectors(connectors: TSMap<string, GraphConnector>): void {
    this.connectors = connectors || this.connectors;
  }

  /**
   * Serialize GraphPageImpl as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphPageImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
