export interface JointGraphElementHasSize {
  fn_joint_graph_element_get_size(): { width: number, height: number } | null;
  fn_joint_graph_element_set_size(size: { width: number, height: number }): void;
}
