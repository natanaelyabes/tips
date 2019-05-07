import { GraphElement } from './GraphElement';

export interface GraphNode extends GraphElement {
  readonly TYPE: string | 'node';
  fn_graph_node_get_group_name(): string | undefined;
  fn_graph_node_set_group_name(groupName: string): void;
  fn_graph_node_is_report_statistics(): boolean;
  fn_graph_node_set_report_statistics(reportStatistics: boolean): void;
  fn_graph_node_accept(elements: GraphElement[]): boolean;
  fn_graph_node_get_input_types(): GraphElement[];
  fn_graph_node_set_input_types(inputTypes: GraphElement[]): void;
  fn_graph_node_get_output_types(): GraphElement[];
  fn_graph_node_set_output_types(outputTypes: GraphElement[]): void;
  fn_graph_node_get_input_nodes(): GraphNode[];
  fn_graph_node_set_input_nodes(inputNodes: GraphNode[]): void;
  fn_graph_node_get_output_nodes(): GraphNode[];
  fn_graph_node_set_output_nodes(outputNodes: GraphNode[]): void;
}
