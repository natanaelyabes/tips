export interface GraphElementInterface {
  fn_graph_element_get_id(): string;
  fn_graph_element_get_name(): string;
  fn_graph_element_get_type(): string;
  fn_graph_element_get_attributes(): Map<string, string>;
}
