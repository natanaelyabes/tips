import { GraphElement } from './GraphElement';

export interface GraphConnector extends GraphElement {
  // readonly TYPE: string | 'connector';
  fn_graph_connector_get_source(): GraphElement | null;
  fn_graph_connector_set_source(source: GraphElement): void;
  fn_graph_connector_get_target(): GraphElement | null;
  fn_graph_connector_set_target(target: GraphElement): void;
}
