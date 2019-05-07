import { GraphStartEventNodeImpl } from '../classes/components/GraphStartEventNodeImpl';
import { GraphStopEventNodeImpl } from '../classes/components/GraphEndEventNodeImpl';
import { GraphActivityNodeImpl } from '../classes/components/GraphActivityNodeImpl';
import { GraphBranchNodeImpl } from '../classes/components/GraphBranchNodeImpl';
import { GraphControlImpl } from '../classes/components/GraphControlImpl';
import { GraphMonitorNodeImpl } from '../classes/components/GraphMonitorNodeImpl';

export const NODE_TYPE = {
  activity: GraphActivityNodeImpl,
  branch: GraphBranchNodeImpl,
  start: GraphStartEventNodeImpl,
  stop: GraphStopEventNodeImpl,
  monitor: GraphMonitorNodeImpl,
};
