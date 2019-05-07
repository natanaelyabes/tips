import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphBranchNode } from '../../interfaces/components/GraphBranchNode';
import { BRANCH_TYPE, BRANCH_RULE } from '../../enums/BRANCH';

export class GraphBranchNodeImpl extends GraphNodeImpl implements GraphBranchNode {
  public static readonly TYPE: 'branch' = 'branch';

  public static fn_object_deserialize(object: any): GraphBranchNode {
    const graphBranchNode: GraphBranchNode = new GraphBranchNodeImpl();
    graphBranchNode.fn_graph_element_set_id(object.id);
    graphBranchNode.fn_graph_element_set_label(object.label);
    graphBranchNode.fn_graph_element_set_type(object.elementType);
    graphBranchNode.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    graphBranchNode.fn_graph_node_set_group_name(object.groupName);
    graphBranchNode.fn_graph_node_set_report_statistics(object.reportStatistics);
    graphBranchNode.fn_graph_branch_node_set_rule(BRANCH_RULE[object.rule] as unknown as BRANCH_RULE);
    graphBranchNode.fn_graph_branch_node_set_type(BRANCH_TYPE[object.type] as unknown as BRANCH_TYPE);
    return graphBranchNode;
  }

  private type: BRANCH_TYPE | null;
  private rule: BRANCH_RULE | null;

  constructor();
  constructor(type: BRANCH_TYPE, rule: BRANCH_RULE);
  constructor(type?: BRANCH_TYPE, rule?: BRANCH_RULE) {
    super();
    this.type = type || null;
    this.rule = rule || null;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_branch_node_get_type(): BRANCH_TYPE | null {
    return this.type;
  }

  public fn_graph_branch_node_set_type(type: BRANCH_TYPE): void {
    this.type = type;
  }

  public fn_graph_branch_node_get_rule(): BRANCH_RULE | null {
    return this.rule;
  }

  public fn_graph_branch_node_set_rule(rule: BRANCH_RULE): void {
    this.rule = rule;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
