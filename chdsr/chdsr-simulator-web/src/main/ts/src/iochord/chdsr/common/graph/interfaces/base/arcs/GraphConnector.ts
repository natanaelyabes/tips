import { GraphNode } from '../nodes/GraphNode';

export interface GraphConnector {
  readonly TYPE: string | 'connector';
  fn_graph_connector_get_source(): GraphNode;
  fn_graph_connector_get_target(): GraphNode;
}
