import { GraphUtil } from './../GraphUtil';
import { TIME_UNIT } from './../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from './../../enums/DISTRIBUTION';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { RESOURCE_CRITERIA } from '../../enums/RESOURCE';

export class GraphDataResourceImpl extends GraphDataImpl implements GraphDataResource {
  public static readonly TYPE: string | null = 'resource';
  public static instance: Map<string, GraphDataResource> = new Map<string, GraphDataResource>();

  /** @Override */
  public static deserialize(object: any): GraphDataResource | null {
    const graphDataResource: GraphDataResource = new GraphDataResourceImpl();
    graphDataResource.setId(object.id);
    graphDataResource.setLabel(object.label);
    graphDataResource.setType(object.elementType);
    graphDataResource.setAttributes(object.attributes as Map<string, string>);
    graphDataResource.setGroupId(object.groupId);
    graphDataResource.setData(object.data);
    graphDataResource.setSetupTime(object.setupTime);
    graphDataResource.setExpression(object.expression);
    graphDataResource.setTimeUnit(object.timeUnit);
    graphDataResource.setCriteria(object.criteria);
    graphDataResource.setHourlyIdleCost(object.hourlyIdleCost);
    graphDataResource.setHourlyBusyCost(object.hourlyBusyCost);
    graphDataResource.setImported(object.imported);
    graphDataResource.setDataTable(object.dataTable);

    GraphDataResourceImpl.instance.set(graphDataResource.getId() as string, graphDataResource);
    return graphDataResource;
  }

  private groupId: string | null;
  private data: GraphDataTable | null;
  private setupTime: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.RANDOM;
  private expression: string | null = '';
  private timeUnit: TIME_UNIT | null = TIME_UNIT.HOURS;
  private criteria: RESOURCE_CRITERIA | null;
  private hourlyIdleCost: number | null;
  private hourlyBusyCost: number | null;
  private imported: boolean | null = false;
  private dataTable: GraphDataTable | null;

  constructor();
  constructor(groupId: string, data: GraphDataTable, setupTime: DISTRIBUTION_TYPE, expression: string, timeUnit: TIME_UNIT, criteria: RESOURCE_CRITERIA, hourlyIdleCost: number, hourlyBusyCost: number, imported: boolean, dataTable: GraphDataTable);
  constructor(groupId?: string, data?: GraphDataTable, setupTime?: DISTRIBUTION_TYPE, expression?: string, timeUnit?: TIME_UNIT, criteria?: RESOURCE_CRITERIA, hourlyIdleCost?: number, hourlyBusyCost?: number, imported?: boolean, dataTable?: GraphDataTable) {
    super();
    this.groupId = groupId || null;
    this.data = data || null;
    this.setupTime = setupTime || null || DISTRIBUTION_TYPE.RANDOM;
    this.expression = expression || null || '';
    this.timeUnit = timeUnit || null || TIME_UNIT.HOURS;
    this.criteria = criteria || null;
    this.hourlyIdleCost = hourlyIdleCost || null;
    this.hourlyBusyCost = hourlyBusyCost || null;
    this.imported = imported || null || false;
    this.dataTable = dataTable || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getGroupId(): string | null {
    return this.groupId;
  }

  public setGroupId(groupId: string): void {
    this.groupId = groupId;
  }

  public getData(): GraphDataTable | null {
    return this.data;
  }

  public setData(data: GraphDataTable): void {
    this.data = data;
  }

  public getDataRef(): string | null {
    return GraphUtil.generateRef(this.getData());
  }

  public getSetupTime(): DISTRIBUTION_TYPE | null {
    return this.setupTime;
  }

  public setSetupTime(setupTime: DISTRIBUTION_TYPE): void {
    this.setupTime = setupTime;
  }

  public getExpression(): string | null {
    return this.expression;
  }

  public setExpression(expression: string): void {
    this.expression = expression;
  }

  public getTimeUnit(): TIME_UNIT | null {
    return this.timeUnit;
  }

  public setTimeUnit(timeUnit: TIME_UNIT): void {
    this.timeUnit = timeUnit;
  }

  public getCriteria(): RESOURCE_CRITERIA | null {
    return this.criteria;
  }

  public setCriteria(criteria: RESOURCE_CRITERIA): void {
    this.criteria = criteria;
  }

  public getHourlyIdleCost(): number | null {
    return this.hourlyIdleCost;
  }

  public setHourlyIdleCost(cost: number): void {
    this.hourlyIdleCost = cost;
  }

  public getHourlyBusyCost(): number | null {
    return this.hourlyBusyCost;
  }

  public setHourlyBusyCost(cost: number): void {
    this.hourlyBusyCost = cost;
  }

  public isImported(): boolean | null {
    return this.imported;
  }

  public setImported(imported: boolean): void {
    this.imported = imported;
  }

  public getDataTable(): GraphDataTable | null {
    return this.dataTable;
  }

  public setDataTable(dataTable: GraphDataTable): void {
    this.dataTable = dataTable;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
