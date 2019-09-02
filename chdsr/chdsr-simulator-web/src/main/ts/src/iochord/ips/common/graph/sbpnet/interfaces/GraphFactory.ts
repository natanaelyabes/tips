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
import { GraphConnector } from '@/iochord/ips/common/graph/sbpnet/interfaces/GraphConnector';
import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphData } from './GraphData';
import { Graph } from './Graph';
import { GraphPage } from './GraphPage';
import { GraphDataObjectType } from './components/GraphDataObjectType';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphFactory {
  create(ref?: Graph): Graph | null;
  addPage(net: Graph): GraphPage | null;
  addData(page: GraphPage, dataType: string): GraphData | null;
  addNode(page: GraphPage, nodeType: string): GraphNode | null;
  addConnector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector | null;
  addConfiguration(net: Graph): GraphConfiguration | null;

  addDataTable(page: GraphPage): GraphDataTable | null;
  addObjectType(page: GraphPage): GraphDataObjectType | null;
  addGenerator(page: GraphPage): GraphDataGenerator | null;
  addFunction(page: GraphPage): GraphDataFunction | null;
  addQueue(page: GraphPage): GraphDataQueue | null;
  addResource(page: GraphPage): GraphDataResource | null;

  addStart(page: GraphPage): GraphStartEventNode | null;
  addStop(page: GraphPage): GraphEventNode | null;
  addActivity(page: GraphPage): GraphActivityNode | null;
  addBranch(page: GraphPage): GraphBranchNode | null;
  addMonitor(page: GraphPage): GraphMonitorNode | null;
}
