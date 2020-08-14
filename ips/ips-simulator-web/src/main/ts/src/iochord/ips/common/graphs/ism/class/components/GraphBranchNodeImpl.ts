import { BRANCH_GATE } from '../../enums/BRANCH';
import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphBranchNode } from '../../interfaces/components/GraphBranchNode';
import { BRANCH_TYPE, BRANCH_RULE } from '../../enums/BRANCH';
import { TSMap } from 'typescript-map';
import { GraphConnectorImpl } from '../GraphConnectorImpl';
import { GraphConnector } from '../../interfaces/GraphConnector';

/**
 * Implementation of GraphBranchNodeImpl interface.
 *
 * @export
 * @class GraphBranchNodeImplImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphBranchNodeImpl}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class GraphBranchNodeImpl extends GraphNodeImpl implements GraphBranchNode {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphBranchNodeImpl
   */
  public static TYPE: string = 'branch';

  /**
   * Deserialize JSON object as GraphBranchNode.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphBranchNode | null)}
   * @memberof GraphBranchNodeImpl
   */
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

  /**
   * The gate type of current branch node.
   *
   * @private
   * @type {(BRANCH_GATE | null)}
   * @memberof GraphBranchNodeImpl
   */
  private gate?: BRANCH_GATE | null = BRANCH_GATE.XOR;

  /**
   * The type of current branch node.
   *
   * @private
   * @type {(BRANCH_TYPE | null)}
   * @memberof GraphBranchNodeImpl
   */
  private type?: BRANCH_TYPE | null = BRANCH_TYPE.SPLIT;

  /**
   * The rule of branching of current branch node.
   *
   * @private
   * @type {(BRANCH_RULE | null)}
   * @memberof GraphBranchNodeImpl
   */
  private rule?: BRANCH_RULE | null = BRANCH_RULE.PROBABILITY;

  /**
   * Conditions of current branch node.
   *
   * @private
   * @type {(TSMap<string, string> | null)}
   * @memberof GraphBranchNodeImpl
   */
  private conditions?: TSMap<string, string> | null = new TSMap<string, string>();

  /**
   * Creates an instance of GraphBranchNodeImpl.
   *
   * @memberof GraphBranchNodeImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns branch type of current branch node.
   *
   * @returns {(BRANCH_TYPE | null)}
   * @memberof GraphBranchNodeImpl
   */
  public getBranchType(): BRANCH_TYPE | null {
    return this.type as BRANCH_TYPE | null;
  }

  /**
   * Assigns branch type to current branch node.
   *
   * @param {BRANCH_TYPE} type
   * @memberof GraphBranchNodeImpl
   */
  public setBranchType(type: BRANCH_TYPE): void {
    this.type = type as BRANCH_TYPE;
  }

  /**
   * Returns gate type of current branch node.
   *
   * @returns {(BRANCH_GATE | null)}
   * @memberof GraphBranchNodeImpl
   */
  public getGate(): BRANCH_GATE | null {
    return this.gate as BRANCH_GATE | null;
  }

  /**
   * Assigns gate type to current branch node.
   *
   * @param {BRANCH_GATE} gate
   * @memberof GraphBranchNodeImpl
   */
  public setGate(gate: BRANCH_GATE): void {
    this.gate = gate as BRANCH_GATE;
  }

  /**
   * Returns rule of current branch node.
   *
   * @returns {(BRANCH_RULE | null)}
   * @memberof GraphBranchNodeImpl
   */
  public getRule(): BRANCH_RULE | null {
    return this.rule as BRANCH_RULE | null;
  }

  /**
   * Assigns rule to current branch node.
   *
   * @param {BRANCH_RULE} rule
   * @memberof GraphBranchNodeImpl
   */
  public setRule(rule: BRANCH_RULE): void {
    this.rule = rule as BRANCH_RULE;
  }

  /**
   * Returns conditions of current branch node.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphBranchNodeImpl
   */
  public getConditions(): TSMap<string, string> | null {
    return this.conditions as TSMap<string, string> | null;
  }

  /**
   * Assigns conditions to current branch node.
   *
   * @param {TSMap<string, string>} conditions
   * @memberof GraphBranchNodeImpl
   */
  public setConditions(conditions: TSMap<string, string>): void {
    this.conditions = conditions as TSMap<string, string>;
  }

  /**
   * To attest that the input nodes have adhered the simulation model rule.
   *
   * @override
   * @returns {(Error | null)}
   * @memberof GraphBranchNodeImpl
   */
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

  /**
   * To attest that the output nodes have adhered the simulation model rule.
   *
   * @override
   * @returns {(Error | null)}
   * @memberof GraphBranchNodeImpl
   */
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

  /**
   * Serialize branch node object as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphBranchNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
