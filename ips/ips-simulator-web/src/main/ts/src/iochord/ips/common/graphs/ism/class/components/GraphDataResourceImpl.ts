import { GraphUtil } from '../GraphUtil';
import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { RESOURCE_CRITERIA } from '../../enums/RESOURCE';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphDataResourceImpl interface.
 *
 * @export
 * @class GraphDataResourceImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphDataResourceImpl}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphDataResourceImpl extends GraphDataImpl implements GraphDataResource {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphDataResourceImpl
   */
  public static TYPE: string = 'resource';

  /**
   * Deserialize JSON object as GraphDataResourceImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphDataResource | null)}
   * @memberof GraphDataResourceImpl
   */
  public static deserialize(object: any): GraphDataResource | null {
    const graphDataResource: GraphDataResource = new GraphDataResourceImpl();
    graphDataResource.setId(object.id);
    graphDataResource.setLabel(object.label);
    graphDataResource.setType(object.elementType);
    graphDataResource.setAttributes(object.attributes as TSMap<string, string>);
    graphDataResource.setGroupId(object.groupId);
    graphDataResource.setDataRef(object.dataRef);
    graphDataResource.setSetupTime(object.setupTime);
    graphDataResource.setExpression(object.expression);
    graphDataResource.setTimeUnit(object.timeUnit);
    graphDataResource.setCriteria(object.criteria);
    graphDataResource.setHourlyIdleCost(object.hourlyIdleCost);
    graphDataResource.setHourlyBusyCost(object.hourlyBusyCost);
    graphDataResource.setImported(object.imported);
    graphDataResource.setDataTable(object.dataTable);
    graphDataResource.setNumberOfResource(object.numberOfResource);

    GraphDataResourceImpl.instance.set(graphDataResource.getId() as string, graphDataResource);
    return graphDataResource;
  }

  /**
   * The group id of current resource data node.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  private groupId?: string | null;

  /**
   * The data specified for the current resource data node.
   *
   * @private
   * @type {(GraphDataTable | null)}
   * @memberof GraphDataResourceImpl
   */
  private data?: GraphDataTable | null;

  /**
   * The data specified for the current resource data node as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  private dataRef?: string | null;

  /**
   * Setup time of current resource data node following a distribution model.
   *
   * @private
   * @type {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphDataResourceImpl
   */
  private setupTime?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.RANDOM;

  /**
   * The expression for the selected distribution model for the current resource data node.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  private expression?: string | null = '';

  /**
   * The time unit for the current resource data node.
   *
   * @private
   * @type {(TIME_UNIT | null)}
   * @memberof GraphDataResourceImpl
   */
  private timeUnit?: TIME_UNIT | null = TIME_UNIT.HOURS;

  /**
   * Resource criteria of the current resource data node.
   *
   * @private
   * @type {(RESOURCE_CRITERIA | null)}
   * @memberof GraphDataResourceImpl
   */
  private criteria?: RESOURCE_CRITERIA | null;

  /**
   * Capital cost per hour of the resource during idle hour.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataResourceImpl
   */
  private hourlyIdleCost?: number | null = 0;

  /**
   * Capital cost per hour for using the resource during busy hour.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataResourceImpl
   */
  private hourlyBusyCost?: number | null = 0;

  /**
   * Does the resource specified from a data table? False otherwise.
   *
   * @private
   * @type {(boolean | null)}
   * @memberof GraphDataResourceImpl
   */
  private imported?: boolean | null = false;

  /**
   * The data table to specify the properties of a resource.
   *
   * @private
   * @type {(GraphDataTable | null)}
   * @memberof GraphDataResourceImpl
   */
  private dataTable?: GraphDataTable | null;

  /**
   * Specify the number of resource for current resource data node.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataResourceImpl
   */
  private numberOfResource?: number | null = 0;

  /**
   * Creates an instance of GraphDataResourceImpl.
   *
   * @memberof GraphDataResourceImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the group id of current resource data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  public getGroupId(): string | null {
    return this.groupId as string | null;
  }

  /**
   * Assigns group id to current resource data node.
   *
   * @param {string} groupId
   * @memberof GraphDataResourceImpl
   */
  public setGroupId(groupId: string): void {
    this.groupId = groupId || this.groupId;
  }

  /**
   * Returns the data table assigned to current resource data node.
   *
   * @returns {(GraphDataTable | null)}
   * @memberof GraphDataResourceImpl
   */
  public getData(): GraphDataTable | null {
    return this.data as GraphDataTable | null;
  }

  /**
   * Assigns data table to current resource data node.
   *
   * @param {GraphDataTable} data
   * @memberof GraphDataResourceImpl
   */
  public setData(data: GraphDataTable): void {
    this.data = data || this.data;
  }

  /**
   * Returns the string reference of data table assigned to current resource data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  public getDataRef(): string | null {
    return this.dataRef as string;
  }

  /**
   * Assigns the string reference of data table to current resource data node.
   *
   * @param {string} data
   * @memberof GraphDataResourceImpl
   */
  public setDataRef(data: string): void {
    this.dataRef = data;
  }

  /**
   * Returns the setup time of current resource data node following a distribution model.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphDataResourceImpl
   */
  public getSetupTime(): DISTRIBUTION_TYPE | null {
    return this.setupTime as DISTRIBUTION_TYPE | null;
  }

  /**
   * Assigns the setup time of current resource data node by selecting a distribution model.
   *
   * @param {DISTRIBUTION_TYPE} setupTime
   * @memberof GraphDataResourceImpl
   */
  public setSetupTime(setupTime: DISTRIBUTION_TYPE): void {
    this.setupTime = setupTime || this.setupTime;
  }

  /**
   * Returns the expresion of selected distribution model for current resource node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  public getExpression(): string | null {
    return this.expression as string | null;
  }

  /**
   * Assigns the expression of selected distribution model for current resource node.
   *
   * @param {string} expression
   * @memberof GraphDataResourceImpl
   */
  public setExpression(expression: string): void {
    this.expression = expression || this.expression;
  }

  /**
   * Returns the time unit assigned to the current resource data node.
   *
   * @returns {(TIME_UNIT | null)}
   * @memberof GraphDataResourceImpl
   */
  public getTimeUnit(): TIME_UNIT | null {
    return this.timeUnit as TIME_UNIT | null;
  }

  /**
   * Assigns the time unit for the current resource data node.
   *
   * @param {TIME_UNIT} timeUnit
   * @memberof GraphDataResourceImpl
   */
  public setTimeUnit(timeUnit: TIME_UNIT): void {
    this.timeUnit = timeUnit || this.timeUnit;
  }

  /**
   * Returns the resource criteria for current resource data node.
   *
   * @returns {(RESOURCE_CRITERIA | null)}
   * @memberof GraphDataResourceImpl
   */
  public getCriteria(): RESOURCE_CRITERIA | null {
    return this.criteria as RESOURCE_CRITERIA | null;
  }

  /**
   * Assigns resource criteria to current resource data node.
   *
   * @param {RESOURCE_CRITERIA} criteria
   * @memberof GraphDataResourceImpl
   */
  public setCriteria(criteria: RESOURCE_CRITERIA): void {
    this.criteria = criteria || this.criteria;
  }

  /**
   * Returns the hourly idle cost assigned to current resource data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataResourceImpl
   */
  public getHourlyIdleCost(): number | null {
    return this.hourlyIdleCost as number | null;
  }

  /**
   * Assigns the hourly idle cost to current resource data node.
   *
   * @param {number} cost
   * @memberof GraphDataResourceImpl
   */
  public setHourlyIdleCost(cost: number): void {
    this.hourlyIdleCost = cost || this.hourlyIdleCost;
  }

  /**
   * Returns the hourly busy cost assigned to current resource data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataResourceImpl
   */
  public getHourlyBusyCost(): number | null {
    return this.hourlyBusyCost as number | null;
  }

  /**
   * Returns the hourly busy cost assigned to current resource node.
   *
   * @param {number} cost
   * @memberof GraphDataResourceImpl
   */
  public setHourlyBusyCost(cost: number): void {
    this.hourlyBusyCost = cost || this.hourlyBusyCost;
  }

  /**
   * Boolean function to evaluate whether the resource properties is imported from a data table node.
   *
   * @returns {(boolean | null)}
   * @memberof GraphDataResourceImpl
   */
  public isImported(): boolean | null {
    return this.imported as boolean | null;
  }

  /**
   * Assigns the current resource data node to import resource properties from a data table node. False otherwise.
   *
   * @param {boolean} imported
   * @memberof GraphDataResourceImpl
   */
  public setImported(imported: boolean): void {
    this.imported = imported || this.imported;
  }

  /**
   * Returns the data table assigned to the current resource node.
   *
   * @returns {(GraphDataTable | null)}
   * @memberof GraphDataResourceImpl
   */
  public getDataTable(): GraphDataTable | null {
    return this.dataTable as GraphDataTable | null;
  }

  /**
   * Assigns data table to current resource node.
   *
   * @param {GraphDataTable} dataTable
   * @memberof GraphDataResourceImpl
   */
  public setDataTable(dataTable: GraphDataTable): void {
    this.dataTable = dataTable || this.dataTable;
  }

  /**
   * Returns the number of resource assigned to current resource data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataResourceImpl
   */
  public getNumberOfResource(): number | null {
    return this.numberOfResource as number;
  }

  /**
   * Assigns number of resources to the current resource data node.
   *
   * @param {(number | null)} numOfResources
   * @memberof GraphDataResourceImpl
   */
  public setNumberOfResource(numOfResources: number | null): void {
    this.numberOfResource = numOfResources as number;
  }

  /**
   * Serialize the GraphDataResourceImpl as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphDataResourceImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
