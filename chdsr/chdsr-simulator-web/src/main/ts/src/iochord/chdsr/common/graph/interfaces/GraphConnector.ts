import { GraphElement } from './GraphElement';

export interface GraphConnector {
  readonly TYPE: string | 'connector';
  fn_graph_connector_get_source(): GraphElement | undefined;
  fn_graph_connector_set_source(source: GraphElement): void;
  fn_graph_connector_get_target(): GraphElement | undefined;
  fn_graph_connector_set_target(target: GraphElement): void;
}
