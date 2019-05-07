import { BRANCH_TYPE, BRANCH_RULE } from './../../enums/BRANCH';
import { GraphNode } from '../GraphNode';

export interface GraphBranchNode extends GraphNode {
  // readonly TYPE: string | 'branch';
  fn_graph_branch_node_get_type(): BRANCH_TYPE | undefined;
  fn_graph_branch_node_set_type(type: BRANCH_TYPE): void;
  fn_graph_branch_node_get_rule(): BRANCH_RULE | undefined;
  fn_graph_branch_node_set_rule(rule: BRANCH_RULE): void;
}
