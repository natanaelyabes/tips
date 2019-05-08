export interface JointGraphElementHasPosition {
  fn_joint_graph_element_get_position(): { x: number, y: number } | null;
  fn_joint_graph_element_set_position(position: { x: number, y: number }): void;
}
