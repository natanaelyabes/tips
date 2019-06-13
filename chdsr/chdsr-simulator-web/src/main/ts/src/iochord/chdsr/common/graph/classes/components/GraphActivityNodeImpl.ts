import { TIME_UNIT } from './../../enums/TIME_UNIT';
import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphActivityNode } from '../../interfaces/components/GraphActivityNode';
import { ACTIVITY_TYPE } from '../../enums/ACTIVITY';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataQueue } from '../../interfaces/components/GraphDataQueue';
import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { GraphDataFunctionImpl } from './GraphDataFunctionImpl';
import { GraphDataQueueImpl } from './GraphDataQueueImpl';
import { GraphDataResourceImpl } from './GraphDataResourceImpl';
import { RESOURCE_SELECTION } from '../../enums/RESOURCE';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { VARIABLE_TYPE } from '../../enums/VARIABLE';
import { GraphUtil } from '../GraphUtil';

export class GraphActivityNodeImpl extends GraphNodeImpl implements GraphActivityNode {
  public static readonly TYPE: string | null = 'activity';
  public static instance: Map<string, GraphActivityNode> = new Map<string, GraphActivityNode>();

  public static deserialize(object: any): GraphActivityNode | null {
    const graphActivityNode: GraphActivityNode = new GraphActivityNodeImpl();
    graphActivityNode.setId(object.id);
    graphActivityNode.setLabel(object.label);
    graphActivityNode.setType(object.elementType);
    graphActivityNode.setAttributes(object.attributes as Map<string, string>);
    graphActivityNode.setGroupName(object.groupName);
    graphActivityNode.setReportStatistics(object.reportStatistics);
    graphActivityNode.setActivityType(object.type);
    graphActivityNode.setResourceSelectionMethod(object.resourceSelectionMethod);
    graphActivityNode.setProcessingTime(object.processingTime);
    graphActivityNode.setProcessingTimeParameter(object.processingTimeParameter);
    graphActivityNode.setSetupTime(object.setupTime);
    graphActivityNode.setSetupTimeParameter(object.setupTimeParameter);
    graphActivityNode.setUnit(object.unit);
    graphActivityNode.setCost(object.cost);
    graphActivityNode.setReportScrap(object.reportScrap);
    graphActivityNode.setFunction(GraphDataFunctionImpl.instance.get(object.functionRef) as GraphDataFunction);
    graphActivityNode.setQueue(GraphDataQueueImpl.instance.get(object.queueRef) as GraphDataQueue);
    graphActivityNode.setResource(GraphDataResourceImpl.instance.get(object.resourceRef) as GraphDataResource);
    GraphActivityNodeImpl.instance.set(graphActivityNode.getId() as string, graphActivityNode);
    return graphActivityNode;
  }

  private type: ACTIVITY_TYPE | null = ACTIVITY_TYPE.STANDARD;
  private resource: GraphDataResource | null;
  private resourceSelectionMethod: RESOURCE_SELECTION | null = RESOURCE_SELECTION.RANDOM;
  private processingTime: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.CONSTANT;
  private processingTimeParameter: string | null = '0';
  private setupTime: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.CONSTANT;
  private setupTimeParameter: string | null = '0';
  private variable: VARIABLE_TYPE | null;
  private cost: number | null = 0;
  private reportScrap: boolean | null = false;
  private queue: GraphDataQueue | null;
  private function: GraphDataFunction | null;
  private unit: TIME_UNIT | null;

  constructor();
  constructor(type: ACTIVITY_TYPE, resource: GraphDataResource, resourceSelectionMethod: RESOURCE_SELECTION, processingTime: DISTRIBUTION_TYPE, processingTimeParameter: string, setupTime: DISTRIBUTION_TYPE, setupTimeParameter: string, variable: VARIABLE_TYPE, cost: number, reportScrap: boolean, queue: GraphDataQueue, func: GraphDataFunction, unit: TIME_UNIT);
  constructor(type?: ACTIVITY_TYPE, resource?: GraphDataResource, resourceSelectionMethod?: RESOURCE_SELECTION, processingTime?: DISTRIBUTION_TYPE, processingTimeParameter?: string, setupTime?: DISTRIBUTION_TYPE, setupTimeParameter?: string, variable?: VARIABLE_TYPE, cost?: number, reportScrap?: boolean, queue?: GraphDataQueue, func?: GraphDataFunction, unit?: TIME_UNIT) {
    super();
    this.type = type || ACTIVITY_TYPE.STANDARD || null;
    this.resource = resource || null;
    this.resourceSelectionMethod = resourceSelectionMethod || RESOURCE_SELECTION.RANDOM || null;
    this.processingTime = processingTime || DISTRIBUTION_TYPE.CONSTANT || null;
    this.processingTimeParameter = processingTimeParameter || '0' || null;
    this.setupTime = setupTime || DISTRIBUTION_TYPE.CONSTANT || null;
    this.setupTimeParameter = setupTimeParameter || '0' || null;
    this.variable = variable || null;
    this.cost = 0 || null;
    this.reportScrap = reportScrap || false || null;
    this.queue = queue || null;
    this.function = func || null;
    this.unit = unit || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getActivityType(): ACTIVITY_TYPE | null {
    return this.type;
  }

  public setActivityType(type: ACTIVITY_TYPE): void {
    this.type = type;
  }

  public getResource(): GraphDataResource | null {
    return this.resource;
  }

  public setResource(resource: GraphDataResource): void {
    this.resource = resource;
  }

  public getResourceRef(): string | null {
    return GraphUtil.generateRef(this.getResource() as GraphDataResource);
  }

  public getResourceSelectionMethod(): RESOURCE_SELECTION | null {
    return this.resourceSelectionMethod;
  }

  public setResourceSelectionMethod(method: RESOURCE_SELECTION): void {
    this.resourceSelectionMethod = method;
  }

  public getSetupTime(): DISTRIBUTION_TYPE | null {
    return this.setupTime;
  }

  public setSetupTime(setupTime: DISTRIBUTION_TYPE): void {
    this.setupTime = setupTime;
  }

  public getSetupTimeParameter(): string | null {
    return this.setupTimeParameter;
  }

  public setSetupTimeParameter(setupTimeParameter: string): void {
    this.setupTimeParameter = setupTimeParameter;
  }

  public getProcessingTime(): DISTRIBUTION_TYPE | null {
    return this.processingTime;
  }

  public setProcessingTime(processingTime: DISTRIBUTION_TYPE): void {
    this.processingTime = processingTime;
  }

  public getProcessingTimeParameter(): string | null {
    return this.processingTimeParameter;
  }

  public setProcessingTimeParameter(processingTimeParameter: string): void {
    this.processingTimeParameter = processingTimeParameter;
  }

  public getVariable(): VARIABLE_TYPE | null {
    return this.variable;
  }

  public setVariable(variable: VARIABLE_TYPE): void {
    this.variable = variable;
  }

  public isReportScrap(): boolean | null {
    return this.reportScrap;
  }

  public setReportScrap(reportScrap: boolean): void {
    this.reportScrap = reportScrap;
  }

  public getCost(): number | null {
    return this.cost;
  }

  public setCost(cost: number): void {
    this.cost = cost;
  }

  public getQueue(): GraphDataQueue | null {
    return this.queue;
  }

  public setQueue(queue: GraphDataQueue): void {
    this.queue = queue;
  }

  public getQueueRef(): string | null {
    return GraphUtil.generateRef(this.getQueue() as GraphDataQueue);
  }

  public getFunction(): GraphDataFunction | null {
    return this.function;
  }

  public setFunction(func: GraphDataFunction): void {
    this.function = func;
  }

  public getFunctionRef(): string | null {
    return GraphUtil.generateRef(this.getFunction() as GraphDataFunction);
  }

  public getUnit(): TIME_UNIT | null {
    return this.unit;
  }

  public setUnit(unit: TIME_UNIT): void {
    this.unit = unit;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
