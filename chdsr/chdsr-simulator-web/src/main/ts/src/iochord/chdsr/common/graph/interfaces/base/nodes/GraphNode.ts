import { GraphElement } from '../../GraphElement';

export interface GraphNode extends GraphElement {
  readonly TYPE: string | 'node';
  fn_graph_node_get_group_node(): string;
  fn_graph_is_report_statistics(): boolean;
}
