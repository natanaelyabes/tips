import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { VARIABLE_TYPE } from '../../enums/VARIABLE';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { RESOURCE_SELECTION } from '../../enums/RESOURCE';
import { ACTIVITY_TYPE } from '../../enums/ACTIVITY';
import { GraphDataFunction } from './GraphDataFunction';
import { GraphDataQueue } from './GraphDataQueue';
import { GraphDataResource } from './GraphDataResource';
import { GraphNode } from '../GraphNode';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphActivityNode extends GraphNode {
  getActivityType(): ACTIVITY_TYPE | null;
  setActivityType(type: ACTIVITY_TYPE): void;
  getResource(): GraphDataResource | null;
  setResource(resource: GraphDataResource): void;
  setResourceRef(resource: string): void;
  getResourceRef(): string | null;
  getResourceSelectionMethod(): RESOURCE_SELECTION | null;
  setResourceSelectionMethod(method: RESOURCE_SELECTION): void;
  getSetupTime(): DISTRIBUTION_TYPE | null;
  setSetupTime(setupTime: DISTRIBUTION_TYPE): void;
  getSetupTimeParameter(): string | null;
  setSetupTimeParameter(setupTimeParameter: string): void;
  getProcessingTime(): DISTRIBUTION_TYPE | null;
  setProcessingTime(processingTime: DISTRIBUTION_TYPE): void;
  getProcessingTimeParameter(): string | null;
  setProcessingTimeParameter(processingTimeParameter: string): void;
  getVariable(): VARIABLE_TYPE | null;
  setVariable(variable: VARIABLE_TYPE): void;
  isReportScrap(): boolean | null;
  setReportScrap(reportScrap: boolean): void;
  getQueue(): GraphDataQueue | null;
  setQueue(queue: GraphDataQueue): void;
  getQueueRef(): string | null;
  setQueueRef(queue: string): void;
  getFunction(): GraphDataFunction | null;
  setFunction(func: GraphDataFunction): void;
  getFunctionRef(): string | null;
  setFunctionRef(func: string): void;
  getUnit(): TIME_UNIT | null;
  setUnit(unit: TIME_UNIT): void;
  getCost(): number | null;
  setCost(cost: number): void;
}
