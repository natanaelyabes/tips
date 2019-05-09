export interface JointGraphElementHasMarkup {
  fn_joint_graph_element_get_markup(): string | null;
  fn_joint_graph_element_set_markup(markup: string): void;
  fn_joint_graph_element_get_attr(): joint.dia.Cell.Selectors | null;
  fn_joint_graph_element_set_attr(attr: joint.dia.Cell.Selectors): void;
}
