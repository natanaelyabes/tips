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
 * The interface for graph activity node.
 *
 * @export
 * @interface GraphActivityNode
 * @extends {GraphNode}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphActivityNode extends GraphNode {

  /**
   * Returns activity type of activity node.
   *
   * @returns {(ACTIVITY_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  getActivityType(): ACTIVITY_TYPE | null;

  /**
   * Assigns activity type to activity node.
   *
   * @param {ACTIVITY_TYPE} type
   * @memberof GraphActivityNodeImpl
   */
  setActivityType(type: ACTIVITY_TYPE): void;

  /**
   * Returns resource of activity node.
   *
   * @returns {(GraphDataResource | null)}
   * @memberof GraphActivityNodeImpl
   */
  getResource(): GraphDataResource | null;

  /**
   * Assigns resource of activity node.
   *
   * @param {GraphDataResource} resource
   * @memberof GraphActivityNodeImpl
   */
  setResource(resource: GraphDataResource): void;

  /**
   * Returns the string reference of a resource.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  getResourceRef(): string | null;

  /**
   * Assigns a resource as string reference
   *
   * @param {string} resource
   * @memberof GraphActivityNodeImpl
   */
  setResourceRef(resource: string): void;

  /**
   * Returns selection method to draw resource out of selection pool.
   *
   * @returns {(RESOURCE_SELECTION | null)}
   * @memberof GraphActivityNodeImpl
   */
  getResourceSelectionMethod(): RESOURCE_SELECTION | null;

  /**
   * Assigns selection method to draw resource out of selection pool.
   *
   * @param {RESOURCE_SELECTION} method
   * @memberof GraphActivityNodeImpl
   */
  setResourceSelectionMethod(method: RESOURCE_SELECTION): void;

  /**
   * Returns setup time of current activity node following the specified distribution model.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  getSetupTime(): DISTRIBUTION_TYPE | null;

  /**
   * Assign setup time of current activity node following the specified distribution model.
   *
   * @param {DISTRIBUTION_TYPE} setupTime
   * @memberof GraphActivityNodeImpl
   */
  setSetupTime(setupTime: DISTRIBUTION_TYPE): void;

  /**
   * Returns the setup time parameter according to the selected distribution type.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  getSetupTimeParameter(): string | null;

  /**
   * Assigns the setup time parameter according to the selected distribution type.
   *
   * @param {string} setupTimeParameter
   * @memberof GraphActivityNodeImpl
   */
  setSetupTimeParameter(setupTimeParameter: string): void;

  /**
   * Returns processing time of current activity node following the specified distribution model.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  getProcessingTime(): DISTRIBUTION_TYPE | null;

  /**
   * Assigns processing time of current activity node following to the specified distribution model.
   *
   * @param {DISTRIBUTION_TYPE} processingTimeDistribution
   * @memberof GraphActivityNodeImpl
   */
  setProcessingTime(processingTimeDistribution: DISTRIBUTION_TYPE): void;

  /**
   * Returns the processing time parameter according to the selected distribution type.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  getProcessingTimeParameter(): string | null;

  /**
   * Assigns the processing time parameter according to the selected distribution type.
   *
   * @param {string} processingTimeExpression
   * @memberof GraphActivityNodeImpl
   */
  setProcessingTimeParameter(processingTimeExpression: string): void;

  /**
   * Returns the object type variable of current activity node.
   *
   * @todo
   * @returns {(VARIABLE_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  getVariable(): VARIABLE_TYPE | null;

  /**
   * Assigns the object type variable to current activity node.
   *
   * @todo
   * @param {VARIABLE_TYPE} variable
   * @memberof GraphActivityNodeImpl
   */
  setVariable(variable: VARIABLE_TYPE): void;

  /**
   * Boolean function to To attest that the whether activity scrap is reported.
   *
   * @returns {(boolean | null)}
   * @memberof GraphActivityNodeImpl
   */
  isReportScrap(): boolean | null;

  /**
   * Boolean function to control the behavior of activity scrap report.
   *
   * @param {boolean} reportScrap
   * @memberof GraphActivityNodeImpl
   */
  setReportScrap(reportScrap: boolean): void;

  /**
   * Returns the queue object of activity node.
   *
   * @returns {(GraphDataQueue | null)}
   * @memberof GraphActivityNodeImpl
   */
  getQueue(): GraphDataQueue | null;

  /**
   * Assigns queue to activity node.
   *
   * @param {GraphDataQueue} queue
   * @memberof GraphActivityNodeImpl
   */
  setQueue(queue: GraphDataQueue): void;

  /**
   * Returns assigned queue object as string reference from activity node.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  getQueueRef(): string | null;

  /**
   * Assigns queue object as string reference to activity node.
   *
   * @param {string} queue
   * @memberof GraphActivityNodeImpl
   */
  setQueueRef(queue: string): void;

  /**
   * Returns the function assigned to the current activity node.
   *
   * @returns {(GraphDataFunction | null)}
   * @memberof GraphActivityNodeImpl
   */
  getFunction(): GraphDataFunction | null;

  /**
   * Assigns function object to the current activity node.
   *
   * @param {GraphDataFunction} func
   * @memberof GraphActivityNodeImpl
   */
  setFunction(func: GraphDataFunction): void;

  /**
   * Returns function assigned to current activity node as reference.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  getFunctionRef(): string | null;

  /**
   * Returns the function assigned to the current activity node as string reference.
   *
   * @param {string} func
   * @memberof GraphActivityNodeImpl
   */
  setFunctionRef(func: string): void;

  /**
   * Returns time unit of current activity node.
   *
   * @returns {(TIME_UNIT | null)}
   * @memberof GraphActivityNodeImpl
   */
  getUnit(): TIME_UNIT | null;

  /**
   * Assigns time unit to current activity node.
   *
   * @param {TIME_UNIT} unit
   * @memberof GraphActivityNodeImpl
   */
  setUnit(unit: TIME_UNIT): void;

  /**
   * Returns the cost associated to current activity node.
   *
   * @returns {(number | null)}
   * @memberof GraphActivityNodeImpl
   */
  getCost(): number | null;

  /**
   * Assigns a cost to current activity node.
   *
   * @param {number} cost
   * @memberof GraphActivityNodeImpl
   */
  setCost(cost: number): void;
}
