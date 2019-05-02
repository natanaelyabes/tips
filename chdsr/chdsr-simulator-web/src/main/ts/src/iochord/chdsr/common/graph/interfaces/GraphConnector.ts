import { GraphElement } from './GraphElement';

export interface GraphConnector {
  readonly TYPE: string | 'connector';
  fn_graph_connector_get_source(): GraphElement | undefined;
  fn_graph_connector_get_target(): GraphElement | undefined;
}
