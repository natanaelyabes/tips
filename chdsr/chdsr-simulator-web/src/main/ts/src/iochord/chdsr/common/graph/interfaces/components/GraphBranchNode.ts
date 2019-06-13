import { BRANCH_TYPE, BRANCH_RULE, BRANCH_GATE } from './../../enums/BRANCH';
import { GraphNode } from '../GraphNode';

export interface GraphBranchNode extends GraphNode {
  getGate(): BRANCH_GATE | null;
  setGate(gate: BRANCH_GATE): void;
  getBranchType(): BRANCH_TYPE | null;
  setBranchType(type: BRANCH_TYPE): void;
  getRule(): BRANCH_RULE | null;
  setRule(rule: BRANCH_RULE): void;
  getConditions(): Map<string, string> | null;
  setConditions(conditions: Map<string, string>): void;
}
