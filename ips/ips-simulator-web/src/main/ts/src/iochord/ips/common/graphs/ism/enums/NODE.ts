// Classes
import { GraphStartEventNodeImpl } from '../class/components/GraphStartEventNodeImpl';
import { GraphStopEventNodeImpl } from '../class/components/GraphStopEventNodeImpl';
import { GraphActivityNodeImpl } from '../class/components/GraphActivityNodeImpl';
import { GraphBranchNodeImpl } from '../class/components/GraphBranchNodeImpl';

/**
 * Enumerations for defining node type.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export const NODE_TYPE = {
  activity: GraphActivityNodeImpl,
  branch: GraphBranchNodeImpl,
  start: GraphStartEventNodeImpl,
  stop: GraphStopEventNodeImpl,
};

