import { GraphElementInterface } from '../../GraphElementInterface';

export interface GraphNodeInterface extends GraphElementInterface {
  fn_graph_node_get_group_node(): string;
}
