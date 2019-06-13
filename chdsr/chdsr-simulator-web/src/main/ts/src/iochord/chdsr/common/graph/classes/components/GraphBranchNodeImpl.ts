import { BRANCH_GATE } from './../../enums/BRANCH';
import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphBranchNode } from '../../interfaces/components/GraphBranchNode';
import { BRANCH_TYPE, BRANCH_RULE } from '../../enums/BRANCH';

export class GraphBranchNodeImpl extends GraphNodeImpl implements GraphBranchNode {
  public static readonly TYPE: string | null = 'branch';
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

  private gate: BRANCH_GATE | null = BRANCH_GATE.XOR;
  private type: BRANCH_TYPE | null = BRANCH_TYPE.SPLIT;
  private rule: BRANCH_RULE | null = BRANCH_RULE.PROBABILITY;
  private conditions: Map<string, string> | null = new Map<string, string>();

  constructor();
  constructor(gate: BRANCH_GATE, type: BRANCH_TYPE, rule: BRANCH_RULE, conditions: Map<string, string>);
  constructor(gate?: BRANCH_GATE, type?: BRANCH_TYPE, rule?: BRANCH_RULE, conditions?: Map<string, string>) {
    super();
    this.gate = gate || BRANCH_GATE.XOR || null;
    this.type = type || BRANCH_TYPE.SPLIT || null;
    this.rule = rule || BRANCH_RULE.PROBABILITY || null;
    this.conditions = conditions || new Map<string, string>() || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getBranchType(): BRANCH_TYPE | null {
    return this.type;
  }

  public setBranchType(type: BRANCH_TYPE): void {
    this.type = type;
  }

  public getGate(): BRANCH_GATE | null {
    return this.gate;
  }

  public setGate(gate: BRANCH_GATE): void {
    this.gate = gate;
  }

  public getRule(): BRANCH_RULE | null {
    return this.rule;
  }

  public setRule(rule: BRANCH_RULE): void {
    this.rule = rule;
  }

  public getConditions(): Map<string, string> | null {
    return this.conditions;
  }

  public setConditions(conditions: Map<string, string>): void {
    this.conditions = conditions;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
