import { BRANCH_TYPE, BRANCH_RULE, BRANCH_GATE } from './../../enums/BRANCH';
import { GraphNode } from '../GraphNode';

export interface GraphBranchNode extends GraphNode {
  // readonly TYPE: string | 'branch';
  fn_graph_branch_node_get_gate(): BRANCH_GATE | null;
  fn_graph_branch_node_set_gate(gate: BRANCH_GATE): void;
  fn_graph_branch_node_get_type(): BRANCH_TYPE | null;
  fn_graph_branch_node_set_type(type: BRANCH_TYPE): void;
  fn_graph_branch_node_get_rule(): BRANCH_RULE | null;
  fn_graph_branch_node_set_rule(rule: BRANCH_RULE): void;
  fn_graph_branch_node_get_conditions(): Map<string, string>;
  fn_graph_branch_node_get_conditions(conditions: Map<string, string>): void;
}
