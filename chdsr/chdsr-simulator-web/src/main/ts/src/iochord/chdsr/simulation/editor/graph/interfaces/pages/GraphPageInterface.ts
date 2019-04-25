import { GraphElementInterface } from '../GraphElementInterface';
import { GraphNodeInterface } from '../base/nodes/GraphNodeInterface';
import { GraphArcInterface } from '../base/arcs/GraphArcInterface';

export interface GraphPageInterface extends GraphElementInterface {
  fn_graph_page_get_nodes(): Map<string, GraphNodeInterface>;
  fn_graph_page_get_arcs(): Map<string, GraphArcInterface>;
}
