import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphConnector } from './GraphConnector';
import { GraphData } from './GraphData';

export interface GraphPage extends GraphElement {
  getData(): Map<string, GraphData> | null;
  setData(data: Map<string, GraphData>): void;
  getNodes(): Map<string, GraphNode> | null;
  setNodes(nodes: Map<string, GraphNode>): void;
  getArcs(): Map<string, GraphConnector> | null;
  setArcs(arcs: Map<string, GraphConnector>): void;
}
