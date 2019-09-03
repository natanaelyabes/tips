import { GraphDataObjectTypeImpl } from './GraphDataObjectTypeImpl';
import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { GraphUtil } from '../GraphUtil';
import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { GraphDataImpl } from '../GraphDataImpl';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataGeneratorImpl extends GraphDataImpl implements GraphDataGenerator {
  public static TYPE: string = 'generator';
  public static instance: Map<string, GraphDataGenerator> = new Map<string, GraphDataGenerator>();

  /** @Override */
  public static deserialize(object: any): GraphDataGenerator | null {
    const graphDataGenerator: GraphDataGenerator = new GraphDataGeneratorImpl();
    graphDataGenerator.setId(object.id);
    graphDataGenerator.setLabel(object.label);
    graphDataGenerator.setType(object.elementType);
    graphDataGenerator.setAttributes(object.attributes as Map<string, string>);
    graphDataGenerator.setDistributionType(object.distributionType);
    graphDataGenerator.setExpression(object.expression);
    graphDataGenerator.setUnit(object.unit);
    graphDataGenerator.setEntitiesPerArrival(object.entitiesPerArrival);
    graphDataGenerator.setMaxArrival(object.maxArrival);
    graphDataGenerator.setFirstCreation(object.firstCreation);
    graphDataGenerator.setObjectType(GraphDataObjectTypeImpl.instance.get(object.objectTypeRef) as GraphDataObjectType);
    GraphDataGeneratorImpl.instance.set(graphDataGenerator.getId() as string, graphDataGenerator);
    return graphDataGenerator;
  }

  private objectType?: GraphDataObjectType | null;
  private distributionType?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.RANDOM;
  private expression?: string | null = '';
  private unit?: TIME_UNIT | null;
  private entitiesPerArrival?: number | null = 1;
  private maxArrival?: number | null = 0;
  private firstCreation?: number | null = 0;

  constructor() {
    super();
  }

  public getObjectType(): GraphDataObjectType | null {
    return this.objectType as GraphDataObjectType | null;
  }

  public setObjectType(objectType: GraphDataObjectType): void {
    this.objectType = objectType || this.objectType;
  }

  public getObjectTypeRef(): string | null {
    return GraphUtil.generateRef(this.getObjectType());
  }

  public getDistributionType(): DISTRIBUTION_TYPE | null {
    return this.distributionType as DISTRIBUTION_TYPE | null;
  }

  public setDistributionType(distributionType: DISTRIBUTION_TYPE): void {
    this.distributionType = distributionType || this.distributionType;
  }

  public getExpression(): string | null {
    return this.expression as string | null;
  }

  public setExpression(expression: string): void {
    this.expression = expression || this.expression;
  }

  public getUnit(): TIME_UNIT | null {
    return this.unit as TIME_UNIT | null;
  }

  public setUnit(unit: TIME_UNIT): void {
    this.unit = unit || this.unit;
  }

  public getEntitiesPerArrival(): number | null {
    return this.entitiesPerArrival as number | null;
  }

  public setEntitiesPerArrival(entitiesPerArrival: number): void {
    this.entitiesPerArrival = entitiesPerArrival || this.entitiesPerArrival;
  }

  public getMaxArrival(): number | null {
    return this.maxArrival as number | null;
  }

  public setMaxArrival(maxArrival: number): void {
    this.maxArrival = maxArrival || this.maxArrival;
  }

  public getFirstCreation(): number | null {
    return this.firstCreation as number | null;
  }

  public setFirstCreation(firstCreation: number): void {
    this.firstCreation = firstCreation || this.firstCreation;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
