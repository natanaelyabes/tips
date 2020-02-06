import { BRANCH_TYPE, BRANCH_RULE, BRANCH_GATE } from '../../enums/BRANCH';
import { GraphNode } from '../GraphNode';
import { TSMap } from 'typescript-map';

/**
 * The interface for graph branch node.
 *
 * @export
 * @interface GraphBranchNode
 * @extends {GraphNode}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphBranchNode extends GraphNode {

  /**
   * Returns gate type of current branch node.
   *
   * @returns {(BRANCH_GATE | null)}
   * @memberof GraphBranchNodeImpl
   */
  getGate(): BRANCH_GATE | null;

  /**
   * Assigns gate type to current branch node.
   *
   * @param {BRANCH_GATE} gate
   * @memberof GraphBranchNodeImpl
   */
  setGate(gate: BRANCH_GATE): void;

  /**
   * Returns branch type of current branch node.
   *
   * @returns {(BRANCH_TYPE | null)}
   * @memberof GraphBranchNodeImpl
   */
  getBranchType(): BRANCH_TYPE | null;

  /**
   * Assigns branch type to current branch node.
   *
   * @param {BRANCH_TYPE} type
   * @memberof GraphBranchNodeImpl
   */
  setBranchType(type: BRANCH_TYPE): void;

  /**
   * Returns rule of current branch node.
   *
   * @returns {(BRANCH_RULE | null)}
   * @memberof GraphBranchNodeImpl
   */
  getRule(): BRANCH_RULE | null;

  /**
   * Assigns rule to current branch node.
   *
   * @param {BRANCH_RULE} rule
   * @memberof GraphBranchNodeImpl
   */
  setRule(rule: BRANCH_RULE): void;

  /**
   * Returns conditions of current branch node.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphBranchNodeImpl
   */
  getConditions(): TSMap<string, string> | null;

  /**
   * Assigns conditions to current branch node.
   *
   * @param {TSMap<string, string>} conditions
   * @memberof GraphBranchNodeImpl
   */
  setConditions(conditions: TSMap<string, string>): void;
}
