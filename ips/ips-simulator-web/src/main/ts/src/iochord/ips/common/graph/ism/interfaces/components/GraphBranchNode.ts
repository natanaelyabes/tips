import { BRANCH_TYPE, BRANCH_RULE, BRANCH_GATE } from '../../enums/BRANCH';
import { GraphNode } from '../GraphNode';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphBranchNode extends GraphNode {
  getGate(): BRANCH_GATE | null;
  setGate(gate: BRANCH_GATE): void;
  getBranchType(): BRANCH_TYPE | null;
  setBranchType(type: BRANCH_TYPE): void;
  getRule(): BRANCH_RULE | null;
  setRule(rule: BRANCH_RULE): void;
  getConditions(): TSMap<string, string> | null;
  setConditions(conditions: TSMap<string, string>): void;
}
