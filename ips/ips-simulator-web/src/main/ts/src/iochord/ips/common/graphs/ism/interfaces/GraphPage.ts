import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphConnector } from './GraphConnector';
import { GraphData } from './GraphData';
import { TSMap } from 'typescript-map';

/**
 * The interface of graph page.
 *
 * @export
 * @interface GraphPage
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphPage extends GraphElement {

  /**
   * Returns the data nodes of current page.
   *
   * @returns {(TSMap<string, GraphData> | null)}
   * @memberof GraphPageImpl
   */
  getData(): TSMap<string, GraphData> | null;

  /**
   * Assigns data nodes to the current page.
   *
   * @param {TSMap<string, GraphData>} data
   * @memberof GraphPageImpl
   */
  setData(data: TSMap<string, GraphData>): void;

  /**
   * Returns the nodes of current page.
   *
   * @returns {(TSMap<string, GraphNode> | null)}
   * @memberof GraphPageImpl
   */
  getNodes(): TSMap<string, GraphNode> | null;

  /**
   * Assigns the nodes for the current page.
   *
   * @param {TSMap<string, GraphNode>} nodes
   * @memberof GraphPageImpl
   */
  setNodes(nodes: TSMap<string, GraphNode>): void;

  /**
   * Returns the connectors of the current page.
   *
   * @returns {(TSMap<string, GraphConnector> | null)}
   * @memberof GraphPageImpl
   */
  getConnectors(): TSMap<string, GraphConnector> | null;

  /**
   * Assigns the connectors for the current page.
   *
   * @param {TSMap<string, GraphConnector>} connectors
   * @memberof GraphPageImpl
   */
  setConnectors(connectors: TSMap<string, GraphConnector>): void;
}
