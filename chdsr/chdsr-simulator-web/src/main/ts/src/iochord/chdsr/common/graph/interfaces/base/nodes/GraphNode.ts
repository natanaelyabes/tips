import { GraphElement } from '../../GraphElement';

export interface GraphNode extends GraphElement {
  fn_graph_node_get_group_node(): string;
}
