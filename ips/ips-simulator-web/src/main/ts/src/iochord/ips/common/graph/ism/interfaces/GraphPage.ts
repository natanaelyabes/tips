import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphConnector } from './GraphConnector';
import { GraphData } from './GraphData';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphPage extends GraphElement {
  getData(): TSMap<string, GraphData> | null;
  setData(data: TSMap<string, GraphData>): void;
  getNodes(): TSMap<string, GraphNode> | null;
  setNodes(nodes: TSMap<string, GraphNode>): void;
  getArcs(): TSMap<string, GraphConnector> | null;
  setArcs(arcs: TSMap<string, GraphConnector>): void;
}
