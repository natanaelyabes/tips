import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphConnector } from './GraphConnector';
import { GraphData } from './GraphData';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphPage extends GraphElement {
  getData(): Map<string, GraphData> | null;
  setData(data: Map<string, GraphData>): void;
  getNodes(): Map<string, GraphNode> | null;
  setNodes(nodes: Map<string, GraphNode>): void;
  getArcs(): Map<string, GraphConnector> | null;
  setArcs(arcs: Map<string, GraphConnector>): void;
}
