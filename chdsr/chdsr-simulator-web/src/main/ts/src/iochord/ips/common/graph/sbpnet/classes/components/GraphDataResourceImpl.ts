import { GraphUtil } from '../GraphUtil';
import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataTable } from '../../interfaces/components/GraphDataTable';
import { RESOURCE_CRITERIA } from '../../enums/RESOURCE';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataResourceImpl extends GraphDataImpl implements GraphDataResource {
  public static TYPE: string = 'resource';
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

  private groupId?: string | null;
  private data?: GraphDataTable | null;
  private setupTime?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.RANDOM;
  private expression?: string | null = '';
  private timeUnit?: TIME_UNIT | null = TIME_UNIT.HOURS;
  private criteria?: RESOURCE_CRITERIA | null;
  private hourlyIdleCost?: number | null = 0;
  private hourlyBusyCost?: number | null = 0;
  private imported?: boolean | null = false;
  private dataTable?: GraphDataTable | null;

  constructor() {
    super();
  }

  public getGroupId(): string | null {
    return this.groupId as string | null;
  }

  public setGroupId(groupId: string): void {
    this.groupId = groupId || this.groupId;
  }

  public getData(): GraphDataTable | null {
    return this.data as GraphDataTable | null;
  }

  public setData(data: GraphDataTable): void {
    this.data = data || this.data;
  }

  public getDataRef(): string | null {
    return GraphUtil.generateRef(this.getData());
  }

  public getSetupTime(): DISTRIBUTION_TYPE | null {
    return this.setupTime as DISTRIBUTION_TYPE | null;
  }

  public setSetupTime(setupTime: DISTRIBUTION_TYPE): void {
    this.setupTime = setupTime || this.setupTime;
  }

  public getExpression(): string | null {
    return this.expression as string | null;
  }

  public setExpression(expression: string): void {
    this.expression = expression || this.expression;
  }

  public getTimeUnit(): TIME_UNIT | null {
    return this.timeUnit as TIME_UNIT | null;
  }

  public setTimeUnit(timeUnit: TIME_UNIT): void {
    this.timeUnit = timeUnit || this.timeUnit;
  }

  public getCriteria(): RESOURCE_CRITERIA | null {
    return this.criteria as RESOURCE_CRITERIA | null;
  }

  public setCriteria(criteria: RESOURCE_CRITERIA): void {
    this.criteria = criteria || this.criteria;
  }

  public getHourlyIdleCost(): number | null {
    return this.hourlyIdleCost as number | null;
  }

  public setHourlyIdleCost(cost: number): void {
    this.hourlyIdleCost = cost || this.hourlyIdleCost;
  }

  public getHourlyBusyCost(): number | null {
    return this.hourlyBusyCost as number | null;
  }

  public setHourlyBusyCost(cost: number): void {
    this.hourlyBusyCost = cost || this.hourlyBusyCost;
  }

  public isImported(): boolean | null {
    return this.imported as boolean | null;
  }

  public setImported(imported: boolean): void {
    this.imported = imported || this.imported;
  }

  public getDataTable(): GraphDataTable | null {
    return this.dataTable as GraphDataTable | null;
  }

  public setDataTable(dataTable: GraphDataTable): void {
    this.dataTable = dataTable || this.dataTable;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
