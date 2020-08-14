import { GraphMonitorNode } from './components/GraphMonitorNode';
import { GraphBranchNode } from './components/GraphBranchNode';
import { GraphActivityNode } from './components/GraphActivityNode';
import { GraphEventNode } from './components/GraphEventNode';
import { GraphStartEventNode } from './components/GraphStartEventNode';
import { GraphDataResource } from './components/GraphDataResource';
import { GraphDataQueue } from './components/GraphDataQueue';
import { GraphDataFunction } from './components/GraphDataFunction';
import { GraphDataGenerator } from './components/GraphDataGenerator';
import { GraphDataTable } from './components/GraphDataTable';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphConnector } from '@/iochord/ips/common/graphs/ism/interfaces/GraphConnector';
import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphData } from './GraphData';
import { Graph } from './Graph';
import { GraphPage } from './GraphPage';
import { GraphDataObjectType } from './components/GraphDataObjectType';

/**
 * The interface of graph factory.
 *
 * @export
 * @interface GraphFactory
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphFactory {

  /**
   * Create graph factory.
   *
   * @param {Graph} [ref]
   * @returns {(Graph | null)}
   * @memberof GraphFactory
   */
  create(ref?: Graph): Graph | null;

  /**
   * Add page to current graph.
   *
   * @param {Graph} net
   * @returns {(GraphPage | null)}
   * @memberof GraphFactory
   */
  addPage(net: Graph): GraphPage | null;

  /**
   * Add data node to current graph.
   *
   * @param {GraphPage} page
   * @param {string} dataType
   * @returns {(GraphData | null)}
   * @memberof GraphFactory
   */
  addData(page: GraphPage, dataType: string): GraphData | null;

  /**
   * Add node to current graph.
   *
   * @param {GraphPage} page
   * @param {string} nodeType
   * @returns {(GraphNode | null)}
   * @memberof GraphFactory
   */
  addNode(page: GraphPage, nodeType: string): GraphNode | null;

  /**
   * Add connector to current graph.
   *
   * @param {GraphPage} page
   * @param {GraphElement} source
   * @param {GraphElement} target
   * @returns {(GraphConnector | null)}
   * @memberof GraphFactory
   */
  addConnector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector | null;

  /**
   * Add configuration to current graph.
   *
   * @param {Graph} net
   * @returns {(GraphConfiguration | null)}
   * @memberof GraphFactory
   */
  addConfiguration(net: Graph): GraphConfiguration | null;

  /**
   * Add data table to current page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataTable | null)}
   * @memberof GraphFactory
   */
  addDataTable(page: GraphPage): GraphDataTable | null;

  /**
   * Add object type of current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataObjectType | null)}
   * @memberof GraphFactory
   */
  addObjectType(page: GraphPage): GraphDataObjectType | null;

  /**
   * Add generator to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataGenerator | null)}
   * @memberof GraphFactory
   */
  addGenerator(page: GraphPage): GraphDataGenerator | null;

  /**
   * Add function to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataFunction | null)}
   * @memberof GraphFactory
   */
  addFunction(page: GraphPage): GraphDataFunction | null;

  /**
   * Add queue to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataQueue | null)}
   * @memberof GraphFactory
   */
  addQueue(page: GraphPage): GraphDataQueue | null;

  /**
   * Add resource to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataResource | null)}
   * @memberof GraphFactory
   */
  addResource(page: GraphPage): GraphDataResource | null;

  /**
   * Add start event node to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphStartEventNode | null)}
   * @memberof GraphFactory
   */
  addStart(page: GraphPage): GraphStartEventNode | null;

  /**
   * Add stop event node to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphEventNode | null)}
   * @memberof GraphFactory
   */
  addStop(page: GraphPage): GraphEventNode | null;

  /**
   * Add activity to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphActivityNode | null)}
   * @memberof GraphFactory
   */
  addActivity(page: GraphPage): GraphActivityNode | null;

  /**
   * Add branch to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphBranchNode | null)}
   * @memberof GraphFactory
   */
  addBranch(page: GraphPage): GraphBranchNode | null;

  /**
   * Add monitor to the current graph.
   *
   * @param {GraphPage} page
   * @returns {(GraphMonitorNode | null)}
   * @memberof GraphFactory
   */
  addMonitor(page: GraphPage): GraphMonitorNode | null;
}
