import { BRANCH_GATE } from '../../enums/BRANCH';
import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphBranchNode } from '../../interfaces/components/GraphBranchNode';
import { BRANCH_TYPE, BRANCH_RULE } from '../../enums/BRANCH';
import { TSMap } from 'typescript-map';
import { GraphConnectorImpl } from '../GraphConnectorImpl';
import { GraphConnector } from '../../interfaces/GraphConnector';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphBranchNodeImpl extends GraphNodeImpl implements GraphBranchNode {
  public static TYPE: string = 'branch';

  public static deserialize(object: any): GraphBranchNode | null {
    const graphBranchNode: GraphBranchNode = new GraphBranchNodeImpl();
    graphBranchNode.setId(object.id);
    graphBranchNode.setLabel(object.label);
    graphBranchNode.setType(object.elementType);
    graphBranchNode.setAttributes(object.attributes as TSMap<string, string>);
    graphBranchNode.setGroupName(object.groupName);
    graphBranchNode.setReportStatistics(object.reportStatistics);
    graphBranchNode.setRule(object.rule);
    graphBranchNode.setGate(object.gate);
    graphBranchNode.setBranchType(object.type);
    GraphBranchNodeImpl.instance.set(graphBranchNode.getId() as string, graphBranchNode);
    return graphBranchNode;
  }

  private gate?: BRANCH_GATE | null = BRANCH_GATE.XOR;
  private type?: BRANCH_TYPE | null = BRANCH_TYPE.SPLIT;
  private rule?: BRANCH_RULE | null = BRANCH_RULE.PROBABILITY;
  private conditions?: TSMap<string, string> | null = new TSMap<string, string>();

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

  public getConditions(): TSMap<string, string> | null {
    return this.conditions as TSMap<string, string> | null;
  }

  public setConditions(conditions: TSMap<string, string>): void {
    this.conditions = conditions as TSMap<string, string>;
  }

  /** @Override */
  public validateInputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its input nodes
    const inputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getTargetRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const inputNodesMoreThanOne = inputNodes.length >= 1 ? true : false;

    // Assert rule
    if (this.getBranchType() === BRANCH_TYPE.SPLIT && inputNodesMoreThanOne) {
      return new Error('Split branch node should not have more than one input nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /** @Override */
  public validateOutputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its output nodes
    const outputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getSourceRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const outputNodesMoreThanZero = outputNodes.length >= 1 ? true : false;

    // Assert rule
    if (this.getBranchType() === BRANCH_TYPE.JOIN && outputNodesMoreThanZero) {
      return new Error('Join branch node should not have more than one output nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
