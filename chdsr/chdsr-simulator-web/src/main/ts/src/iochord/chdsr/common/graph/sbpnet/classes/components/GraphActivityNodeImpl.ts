import { TIME_UNIT } from '../../enums/TIME_UNIT';
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

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphActivityNodeImpl extends GraphNodeImpl implements GraphActivityNode {
  public static TYPE: string = 'activity';

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

  private type?: ACTIVITY_TYPE | null = ACTIVITY_TYPE.STANDARD;
  private resource?: GraphDataResource | null;
  private resourceSelectionMethod?: RESOURCE_SELECTION | null = RESOURCE_SELECTION.RANDOM;
  private processingTime?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.CONSTANT;
  private processingTimeParameter?: string | null = '0';
  private setupTime?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.CONSTANT;
  private setupTimeParameter?: string | null = '0';
  private variable?: VARIABLE_TYPE | null;
  private cost?: number | null = 0;
  private reportScrap?: boolean | null = false;
  private queue?: GraphDataQueue | null;
  private function?: GraphDataFunction | null;
  private unit?: TIME_UNIT | null;

  constructor() {
    super();
  }

  public getActivityType(): ACTIVITY_TYPE | null {
    return this.type as ACTIVITY_TYPE | null;
  }

  public setActivityType(type: ACTIVITY_TYPE): void {
    this.type = type || this.type;
  }

  public getResource(): GraphDataResource | null {
    return this.resource as GraphDataResource | null;
  }

  public setResource(resource: GraphDataResource): void {
    this.resource = resource || this.resource;
  }

  public getResourceRef(): string | null {
    return GraphUtil.generateRef(this.getResource() as GraphDataResource);
  }

  public getResourceSelectionMethod(): RESOURCE_SELECTION | null {
    return this.resourceSelectionMethod as RESOURCE_SELECTION | null;
  }

  public setResourceSelectionMethod(method: RESOURCE_SELECTION): void {
    this.resourceSelectionMethod = method || this.resourceSelectionMethod;
  }

  public getSetupTime(): DISTRIBUTION_TYPE | null {
    return this.setupTime as DISTRIBUTION_TYPE | null;
  }

  public setSetupTime(setupTime: DISTRIBUTION_TYPE): void {
    this.setupTime = setupTime || this.setupTime;
  }

  public getSetupTimeParameter(): string | null {
    return this.setupTimeParameter as string | null;
  }

  public setSetupTimeParameter(setupTimeParameter: string): void {
    this.setupTimeParameter = setupTimeParameter || this.setupTimeParameter;
  }

  public getProcessingTime(): DISTRIBUTION_TYPE | null {
    return this.processingTime as DISTRIBUTION_TYPE | null;
  }

  public setProcessingTime(processingTime: DISTRIBUTION_TYPE): void {
    this.processingTime = processingTime || this.processingTime;
  }

  public getProcessingTimeParameter(): string | null {
    return this.processingTimeParameter as string | null;
  }

  public setProcessingTimeParameter(processingTimeParameter: string): void {
    this.processingTimeParameter = processingTimeParameter || this.processingTimeParameter;
  }

  public getVariable(): VARIABLE_TYPE | null {
    return this.variable as VARIABLE_TYPE | null;
  }

  public setVariable(variable: VARIABLE_TYPE): void {
    this.variable = variable || this.variable;
  }

  public isReportScrap(): boolean | null {
    return this.reportScrap as boolean | null;
  }

  public setReportScrap(reportScrap: boolean): void {
    this.reportScrap = reportScrap || this.reportScrap;
  }

  public getCost(): number | null {
    return this.cost as number | null;
  }

  public setCost(cost: number): void {
    this.cost = cost || this.cost;
  }

  public getQueue(): GraphDataQueue | null {
    return this.queue as GraphDataQueue | null;
  }

  public setQueue(queue: GraphDataQueue): void {
    this.queue = queue || this.queue;
  }

  public getQueueRef(): string | null {
    return GraphUtil.generateRef(this.getQueue() as GraphDataQueue);
  }

  public getFunction(): GraphDataFunction | null {
    return this.function as GraphDataFunction | null;
  }

  public setFunction(func: GraphDataFunction): void {
    this.function = func || this.function;
  }

  public getFunctionRef(): string | null {
    return GraphUtil.generateRef(this.getFunction() as GraphDataFunction);
  }

  public getUnit(): TIME_UNIT | null {
    return this.unit as TIME_UNIT | null;
  }

  public setUnit(unit: TIME_UNIT): void {
    this.unit = unit || this.unit;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}