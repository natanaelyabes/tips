import { GraphElement } from './GraphElement';

export interface GraphNode extends GraphElement {
  readonly TYPE: string | 'node';
  fn_graph_node_get_group_node(): string | undefined;
  fn_graph_node_is_report_statistics(): boolean;
  fn_graph_node_accept(elements: GraphElement[]): boolean;
  fn_graph_node_get_input_types(): GraphElement[];
  fn_graph_node_get_output_types(): GraphElement[];
  fn_graph_node_get_input_nodes(): GraphNode[];
  fn_graph_node_get_output_nodes(): GraphNode[];
}
