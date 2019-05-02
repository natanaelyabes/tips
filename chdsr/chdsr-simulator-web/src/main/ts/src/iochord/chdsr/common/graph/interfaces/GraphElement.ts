export interface GraphElement {
  readonly TYPE: string | 'element';
  fn_graph_element_get_id(): string | undefined;
  fn_graph_element_get_label(): string | undefined;
  fn_graph_element_get_type(): string | undefined;
  fn_graph_element_get_attributes(): Map<string, string> | undefined;
}
