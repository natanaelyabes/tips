import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphActivityNode } from '../../interfaces/components/GraphActivityNode';
import { ACTIVITY_TYPE } from '../../enums/ACTIVITY';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataQueue } from '../../interfaces/components/GraphDataQueue';
import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { RESOURCE_SELECTION } from '../../enums/RESOURCE';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { VARIABLE_TYPE } from '../../enums/VARIABLE';
import { TSMap } from 'typescript-map';
import { GraphConnectorImpl } from '../GraphConnectorImpl';
import { GraphConnector } from '../../interfaces/GraphConnector';

/**
 * Implementation of GraphActivityNode interface.
 *
 * @export
 * @class GraphActivityNodeImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphActivityNode}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class GraphActivityNodeImpl extends GraphNodeImpl implements GraphActivityNode {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphActivityNodeImpl
   */
  public static TYPE: string = 'activity';

  /**
   * Deserialize JSON object as GraphActivityNode.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphActivityNode | null)}
   * @memberof GraphActivityNodeImpl
   */
  public static deserialize(object: any): GraphActivityNode | null {
    const graphActivityNode: GraphActivityNode = new GraphActivityNodeImpl();
    graphActivityNode.setId(object.id);
    graphActivityNode.setLabel(object.label);
    graphActivityNode.setType(object.elementType);
    graphActivityNode.setAttributes(object.attributes as TSMap<string, string>);
    graphActivityNode.setGroupName(object.groupName);
    graphActivityNode.setReportStatistics(object.reportStatistics);
    graphActivityNode.setActivityType(object.type);
    graphActivityNode.setResourceSelectionMethod(object.resourceSelectionMethod);
    graphActivityNode.setProcessingTime(object.processingTimeDistribution);
    graphActivityNode.setProcessingTimeParameter(object.processingTimeExpression);
    graphActivityNode.setSetupTime(object.setupTime);
    graphActivityNode.setSetupTimeParameter(object.setupTimeParameter);
    graphActivityNode.setUnit(object.unit);
    graphActivityNode.setCost(object.cost);
    graphActivityNode.setReportScrap(object.reportScrap);
    graphActivityNode.setFunctionRef(object.functionRef);
    graphActivityNode.setQueueRef(object.queueRef);
    graphActivityNode.setResourceRef(object.resourceRef);
    graphActivityNode.setImageIcon(object.icon);
    GraphActivityNodeImpl.instance.set(graphActivityNode.getId() as string, graphActivityNode);
    return graphActivityNode;
  }

  /**
   * The type of current activity node.
   *
   * @private
   * @type {(ACTIVITY_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  private type?: ACTIVITY_TYPE | null = ACTIVITY_TYPE.STANDARD;

  /**
   * Resource of current activity node.
   *
   * @private
   * @type {(GraphDataResource | null)}
   * @memberof GraphActivityNodeImpl
   */
  private resource?: GraphDataResource | null;

  /**
   * Resource of current activity node as a string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  private resourceRef?: string | null;

  /**
   * Specify the selection behavior of a resource i.e. how resource were drawn from resource pool.
   *
   * @private
   * @type {(RESOURCE_SELECTION | null)}
   * @memberof GraphActivityNodeImpl
   */
  private resourceSelectionMethod?: RESOURCE_SELECTION | null = RESOURCE_SELECTION.RANDOM;

  /**
   * The length of processing time for current activity node following a distribution model.
   *
   * @private
   * @type {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  private processingTimeDistribution?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.CONSTANT;

  /**
   * The parameter settings for activity processing time according to the selected distribution type.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  private processingTimeExpression?: string | null = '0';

  /**
   * The length of setup time for current activity node following a distribution model.
   *
   * @private
   * @type {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  private setupTime?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.CONSTANT;

  /**
   * The parameter settings for activity setup time according to the selected distribution type.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  private setupTimeParameter?: string | null = '0';

  /**
   * The object type of current activity node.
   *
   * @todo
   * @private
   * @type {(VARIABLE_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  private variable?: VARIABLE_TYPE | null;

  /**
   * Assigns capital cost to current activity node.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphActivityNodeImpl
   */
  private cost?: number | null = 0;

  /**
   * A boolean variable to report scrap of current activity node.
   *
   * @private
   * @type {(boolean | null)}
   * @memberof GraphActivityNodeImpl
   */
  private reportScrap?: boolean | null = false;

  /**
   * Queue of current activity node.
   *
   * @private
   * @type {(GraphDataQueue | null)}
   * @memberof GraphActivityNodeImpl
   */
  private queue?: GraphDataQueue | null;

  /**
   * Queue of current activity node as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  private queueRef?: string | null;

  /**
   * Function of current activity node.
   *
   * @private
   * @type {(GraphDataFunction | null)}
   * @memberof GraphActivityNodeImpl
   */
  private function?: GraphDataFunction | null;

  /**
   * Function of current activity node as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  private functionRef?: string | null;

  /**
   * Time unit of current activity node.
   *
   * @private
   * @type {(TIME_UNIT | null)}
   * @memberof GraphActivityNodeImpl
   */
  private unit?: TIME_UNIT | null;

  /**
   * Creates current instance of GraphActivityNodeImpl.
   * @memberof GraphActivityNodeImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns activity type of activity node.
   *
   * @returns {(ACTIVITY_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getActivityType(): ACTIVITY_TYPE | null {
    return this.type as ACTIVITY_TYPE | null;
  }

  /**
   * Assigns activity type to activity node.
   *
   * @param {ACTIVITY_TYPE} type
   * @memberof GraphActivityNodeImpl
   */
  public setActivityType(type: ACTIVITY_TYPE): void {
    this.type = type || this.type;
  }

  /**
   * Returns resource of activity node.
   *
   * @returns {(GraphDataResource | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getResource(): GraphDataResource | null {
    return this.resource as GraphDataResource | null;
  }

  /**
   * Assigns resource of activity node.
   *
   * @param {GraphDataResource} resource
   * @memberof GraphActivityNodeImpl
   */
  public setResource(resource: GraphDataResource): void {
    this.resource = resource || this.resource;
  }

  /**
   * Returns the string reference of a resource.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getResourceRef(): string | null {
    return this.resourceRef as string;
  }

  /**
   * Assigns a resource as string reference
   *
   * @param {string} resource
   * @memberof GraphActivityNodeImpl
   */
  public setResourceRef(resource: string): void {
    this.resourceRef = resource;
  }

  /**
   * Returns selection method to draw resource out of selection pool.
   *
   * @returns {(RESOURCE_SELECTION | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getResourceSelectionMethod(): RESOURCE_SELECTION | null {
    return this.resourceSelectionMethod as RESOURCE_SELECTION | null;
  }

  /**
   * Assigns selection method to draw resource out of selection pool.
   *
   * @param {RESOURCE_SELECTION} method
   * @memberof GraphActivityNodeImpl
   */
  public setResourceSelectionMethod(method: RESOURCE_SELECTION): void {
    this.resourceSelectionMethod = method || this.resourceSelectionMethod;
  }

  /**
   * Returns setup time of current activity node following the specified distribution model.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getSetupTime(): DISTRIBUTION_TYPE | null {
    return this.setupTime as DISTRIBUTION_TYPE | null;
  }

  /**
   * Assign setup time of current activity node following the specified distribution model.
   *
   * @param {DISTRIBUTION_TYPE} setupTime
   * @memberof GraphActivityNodeImpl
   */
  public setSetupTime(setupTime: DISTRIBUTION_TYPE): void {
    this.setupTime = setupTime || this.setupTime;
  }

  /**
   * Returns the setup time parameter according to the selected distribution type.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getSetupTimeParameter(): string | null {
    return this.setupTimeParameter as string | null;
  }

  /**
   * Assigns the setup time parameter according to the selected distribution type.
   *
   * @param {string} setupTimeParameter
   * @memberof GraphActivityNodeImpl
   */
  public setSetupTimeParameter(setupTimeParameter: string): void {
    this.setupTimeParameter = setupTimeParameter || this.setupTimeParameter;
  }

  /**
   * Returns processing time of current activity node following the specified distribution model.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getProcessingTime(): DISTRIBUTION_TYPE | null {
    return this.processingTimeDistribution as DISTRIBUTION_TYPE | null;
  }

  /**
   * Assigns processing time of current activity node following to the specified distribution model.
   *
   * @param {DISTRIBUTION_TYPE} processingTimeDistribution
   * @memberof GraphActivityNodeImpl
   */
  public setProcessingTime(processingTimeDistribution: DISTRIBUTION_TYPE): void {
    this.processingTimeDistribution = processingTimeDistribution || this.processingTimeDistribution;
  }

  /**
   * Returns the processing time parameter according to the selected distribution type.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getProcessingTimeParameter(): string | null {
    return this.processingTimeExpression as string | null;
  }

  /**
   * Assigns the processing time parameter according to the selected distribution type.
   *
   * @param {string} processingTimeExpression
   * @memberof GraphActivityNodeImpl
   */
  public setProcessingTimeParameter(processingTimeExpression: string): void {
    this.processingTimeExpression = processingTimeExpression || this.processingTimeExpression;
  }

  /**
   * Returns the object type variable of current activity node.
   *
   * @todo
   * @returns {(VARIABLE_TYPE | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getVariable(): VARIABLE_TYPE | null {
    return this.variable as VARIABLE_TYPE | null;
  }

  /**
   * Assigns the object type variable to current activity node.
   *
   * @todo
   * @param {VARIABLE_TYPE} variable
   * @memberof GraphActivityNodeImpl
   */
  public setVariable(variable: VARIABLE_TYPE): void {
    this.variable = variable || this.variable;
  }

  /**
   * Boolean function to attest that the whether activity scrap is reported.
   *
   * @returns {(boolean | null)}
   * @memberof GraphActivityNodeImpl
   */
  public isReportScrap(): boolean | null {
    return this.reportScrap as boolean | null;
  }

  /**
   * Boolean function to control the behavior of activity scrap report.
   *
   * @param {boolean} reportScrap
   * @memberof GraphActivityNodeImpl
   */
  public setReportScrap(reportScrap: boolean): void {
    this.reportScrap = reportScrap || this.reportScrap;
  }

  /**
   * Returns the cost associated to current activity node.
   *
   * @returns {(number | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getCost(): number | null {
    return this.cost as number | null;
  }

  /**
   * Assigns a cost to current activity node.
   *
   * @param {number} cost
   * @memberof GraphActivityNodeImpl
   */
  public setCost(cost: number): void {
    this.cost = cost || this.cost;
  }

  /**
   * Returns the queue object of activity node.
   *
   * @returns {(GraphDataQueue | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getQueue(): GraphDataQueue | null {
    return this.queue as GraphDataQueue | null;
  }

  /**
   * Assigns queue to activity node.
   *
   * @param {GraphDataQueue} queue
   * @memberof GraphActivityNodeImpl
   */
  public setQueue(queue: GraphDataQueue): void {
    this.queue = queue || this.queue;
  }

  /**
   * Returns assigned queue object as string reference from activity node.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getQueueRef(): string | null {
    return this.queueRef as string;
  }

  /**
   * Assigns queue object as string reference to activity node.
   *
   * @param {string} queue
   * @memberof GraphActivityNodeImpl
   */
  public setQueueRef(queue: string): void {
    this.queueRef = queue;
  }

  /**
   * Returns the function assigned to the current activity node.
   *
   * @returns {(GraphDataFunction | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getFunction(): GraphDataFunction | null {
    return this.function as GraphDataFunction | null;
  }

  /**
   * Assigns function object to the current activity node.
   *
   * @param {GraphDataFunction} func
   * @memberof GraphActivityNodeImpl
   */
  public setFunction(func: GraphDataFunction): void {
    this.function = func || this.function;
  }

  /**
   * Returns function assigned to current activity node as reference.
   *
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getFunctionRef(): string | null {
    return this.functionRef as string;
  }

  /**
   * Returns the function assigned to the current activity node as string reference.
   *
   * @param {string} func
   * @memberof GraphActivityNodeImpl
   */
  public setFunctionRef(func: string): void {
    this.functionRef = func;
  }

  /**
   * Returns time unit of current activity node.
   *
   * @returns {(TIME_UNIT | null)}
   * @memberof GraphActivityNodeImpl
   */
  public getUnit(): TIME_UNIT | null {
    return this.unit as TIME_UNIT | null;
  }

  /**
   * Assigns time unit to current activity node.
   *
   * @param {TIME_UNIT} unit
   * @memberof GraphActivityNodeImpl
   */
  public setUnit(unit: TIME_UNIT): void {
    this.unit = unit || this.unit;
  }

  /**
   * To attest the input nodes have adhered the simulation model rule.
   *
   * @override
   * @returns {(Error | null)}
   * @memberof GraphActivityNodeImpl
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
    if (inputNodesMoreThanOne) {
      return new Error('Activity node should not have more than one input node.');
    }

    // Otherwise return nothing
    return null;
  }

  /**
   * To attest the output nodes have adhered the simulation model rule.
   *
   * @override
   * @returns {(Error | null)}
   * @memberof GraphActivityNodeImpl
   */
  public validateOutputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its output nodes
    const outputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getSourceRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const outputNodesMoreThanOne = outputNodes.length >= 1 ? true : false;

    // Assert rule
    if (outputNodesMoreThanOne) {
      return new Error('Activity node should not have more than one output node.');
    }

    // Otherwise return nothing
    return null;
  }

  /**
   * Serialize activity node object as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphActivityNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
