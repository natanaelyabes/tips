import { GraphNodeInterface } from '../nodes/GraphNodeInterface';

export interface GraphArcInterface {
  fn_graph_arc_get_source(): GraphNodeInterface;
  fn_graph_arc_get_target(): GraphNodeInterface;
}
