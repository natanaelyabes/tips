import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';
import { RESOURCE_CRITERIA } from '../../enums/RESOURCE';

/**
 * The interface of graph data resource.
 *
 * @export
 * @interface GraphDataResource
 * @extends {GraphData}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphDataResource extends GraphData {

  /**
   * Returns the group id of current resource data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResource
   */
  getGroupId(): string | null;

  /**
   * Assigns group id to current resource data node.
   *
   * @param {string} groupId
   * @memberof GraphDataResource
   */
  setGroupId(groupId: string): void;

  /**
   * Returns the data table assigned to current resource data node.
   *
   * @returns {(GraphDataTable | null)}
   * @memberof GraphDataResource
   */
  getData(): GraphDataTable | null;

  /**
   * Assigns data table to current resource data node.
   *
   * @param {GraphDataTable} data
   * @memberof GraphDataResource
   */
  setData(data: GraphDataTable): void;

  /**
   * Returns the string reference of data table assigned to current resource data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResource
   */
  getDataRef(): string | null;

  /**
   * Assigns the string reference of data table to current resource data node.
   *
   * @param {string} data
   * @memberof GraphDataResource
   */
  setDataRef(data: string): void;

  /**
   * Returns the setup time of current resource data node following a distribution model.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphDataResource
   */
  getSetupTime(): DISTRIBUTION_TYPE | null;

  /**
   * Assigns the setup time of current resource data node by selecting a distribution model.
   *
   * @param {DISTRIBUTION_TYPE} setupTime
   * @memberof GraphDataResource
   */
  setSetupTime(setupTime: DISTRIBUTION_TYPE): void;

  /**
   * Returns the expresion of selected distribution model for current resource node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResource
   */
  getExpression(): string | null;

  /**
   * Assigns the expression of selected distribution model for current resource node.
   *
   * @param {string} expression
   * @memberof GraphDataResource
   */
  setExpression(expression: string): void;

  /**
   * Returns the time unit assigned to the current resource data node.
   *
   * @returns {(TIME_UNIT | null)}
   * @memberof GraphDataResource
   */
  getTimeUnit(): TIME_UNIT | null;

  /**
   * Assigns the time unit for the current resource data node.
   *
   * @param {TIME_UNIT} timeUnit
   * @memberof GraphDataResource
   */
  setTimeUnit(timeUnit: TIME_UNIT): void;

  /**
   * Returns the resource criteria for current resource data node.
   *
   * @returns {(RESOURCE_CRITERIA | null)}
   * @memberof GraphDataResource
   */
  getCriteria(): RESOURCE_CRITERIA | null;

  /**
   * Assigns resource criteria to current resource data node.
   *
   * @param {RESOURCE_CRITERIA} criteria
   * @memberof GraphDataResource
   */
  setCriteria(criteria: RESOURCE_CRITERIA): void;

  /**
   * Returns the hourly idle cost assigned to current resource data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataResource
   */
  getHourlyIdleCost(): number | null;

  /**
   * Assigns the hourly idle cost to current resource data node.
   *
   * @param {number} cost
   * @memberof GraphDataResource
   */
  setHourlyIdleCost(cost: number): void;

  /**
   * Returns the hourly busy cost assigned to current resource data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataResource
   */
  getHourlyBusyCost(): number | null;

  /**
   * Returns the hourly busy cost assigned to current resource node.
   *
   * @param {number} cost
   * @memberof GraphDataResource
   */
  setHourlyBusyCost(cost: number): void;

  /**
   * Boolean function to evaluate whether the resource properties is imported from a data table node.
   *
   * @returns {(boolean | null)}
   * @memberof GraphDataResource
   */
  isImported(): boolean | null;

  /**
   * Assigns the current resource data node to import resource properties from a data table node. False otherwise.
   *
   * @param {boolean} imported
   * @memberof GraphDataResource
   */
  setImported(imported: boolean): void;

  /**
   * Returns the data table assigned to the current resource node.
   *
   * @returns {(GraphDataTable | null)}
   * @memberof GraphDataResource
   */
  getDataTable(): GraphDataTable | null;

  /**
   * Assigns data table to current resource node.
   *
   * @param {GraphDataTable} dataTable
   * @memberof GraphDataResource
   */
  setDataTable(dataTable: GraphDataTable): void;

  /**
   * Returns the number of resource assigned to current resource data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataResource
   */
  getNumberOfResource(): number | null;

  /**
   * Assigns number of resources to the current resource data node.
   *
   * @param {(number | null)} numOfResources
   * @memberof GraphDataResource
   */
  setNumberOfResource(numOfResources: number | null): void;
}
