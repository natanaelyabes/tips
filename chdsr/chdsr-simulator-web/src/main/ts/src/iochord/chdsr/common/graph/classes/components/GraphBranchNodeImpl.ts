import { BRANCH_GATE } from './../../enums/BRANCH';
import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphBranchNode } from '../../interfaces/components/GraphBranchNode';
import { BRANCH_TYPE, BRANCH_RULE } from '../../enums/BRANCH';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphBranchNodeImpl extends GraphNodeImpl implements GraphBranchNode {
  public static TYPE: string = 'branch';
  public static instance: Map<string, GraphBranchNode> = new Map<string, GraphBranchNode>();

  public static deserialize(object: any): GraphBranchNode | null {
    const graphBranchNode: GraphBranchNode = new GraphBranchNodeImpl();
    graphBranchNode.setId(object.id);
    graphBranchNode.setLabel(object.label);
    graphBranchNode.setType(object.elementType);
    graphBranchNode.setAttributes(object.attributes as Map<string, string>);
    graphBranchNode.setGroupName(object.groupName);
    graphBranchNode.setReportStatistics(object.reportStatistics);
    graphBranchNode.setRule(BRANCH_RULE[object.rule] as unknown as BRANCH_RULE);
    graphBranchNode.setBranchType(BRANCH_TYPE[object.type] as unknown as BRANCH_TYPE);
    GraphBranchNodeImpl.instance.set(graphBranchNode.getId() as string, graphBranchNode);
    return graphBranchNode;
  }

  private gate?: BRANCH_GATE | null = BRANCH_GATE.XOR;
  private type?: BRANCH_TYPE | null = BRANCH_TYPE.SPLIT;
  private rule?: BRANCH_RULE | null = BRANCH_RULE.PROBABILITY;
  private conditions?: Map<string, string> | null = new Map<string, string>();

  constructor() {
    super();
  }

  public getBranchType(): BRANCH_TYPE | null {
    return this.type as BRANCH_TYPE | null;
  }

  public setBranchType(type: BRANCH_TYPE): void {
    this.type = type || this.type;
  }

  public getGate(): BRANCH_GATE | null {
    return this.gate as BRANCH_GATE | null;
  }

  public setGate(gate: BRANCH_GATE): void {
    this.gate = gate || this.gate;
  }

  public getRule(): BRANCH_RULE | null {
    return this.rule as BRANCH_RULE | null;
  }

  public setRule(rule: BRANCH_RULE): void {
    this.rule = rule || this.rule;
  }

  public getConditions(): Map<string, string> | null {
    return this.conditions as Map<string, string> | null;
  }

  public setConditions(conditions: Map<string, string>): void {
    this.conditions = conditions || this.conditions;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
