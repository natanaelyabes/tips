import { GraphNode } from '../nodes/GraphNode';

export interface GraphArc {
  fn_graph_arc_get_source(): GraphNode;
  fn_graph_arc_get_target(): GraphNode;
}
