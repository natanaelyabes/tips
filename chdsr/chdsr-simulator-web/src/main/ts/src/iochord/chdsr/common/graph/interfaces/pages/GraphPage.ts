import { GraphElement } from '../GraphElement';
import { GraphNode } from '../base/nodes/GraphNode';
import { GraphArc } from '../base/arcs/GraphArc';

export interface GraphPage extends GraphElement {
  fn_graph_page_get_nodes(): Map<string, GraphNode>;
  fn_graph_page_get_arcs(): Map<string, GraphArc>;
}
