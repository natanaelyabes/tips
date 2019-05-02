import { BRANCH_TYPE, BRANCH_RULE } from './../../enums/BRANCH';
import { GraphNode } from '../GraphNode';

export interface GraphBranchNode extends GraphNode {
  readonly type: string | 'branch';
  fn_graph_branch_node_get_type(): BRANCH_TYPE;
  fn_graph_branch_node_get_rule(): BRANCH_RULE;
}
