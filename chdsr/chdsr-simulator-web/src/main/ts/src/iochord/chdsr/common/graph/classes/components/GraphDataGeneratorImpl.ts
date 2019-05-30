import { GraphDataObjectTypeImpl } from './GraphDataObjectTypeImpl';
import { GraphDataObjectType } from './../../interfaces/components/GraphDataObjectType';
import { GraphDataGenerator } from './../../interfaces/components/GraphDataGenerator';
import { GraphUtil } from './../GraphUtil';
import { TIME_UNIT } from './../../enums/TIME_UNIT';
import { GraphDataImpl } from '../GraphDataImpl';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';

export class GraphDataGeneratorImpl extends GraphDataImpl implements GraphDataGenerator {
  public static readonly TYPE: string | null = 'generator';
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

  private objectType: GraphDataObjectType | null;
  private distributionType: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.RANDOM;
  private expression: string | null = '';
  private unit: TIME_UNIT | null;
  private entitiesPerArrival: number | null = 1;
  private maxArrival: number | null = 0;
  private firstCreation: number | null = 0;

  constructor();
  constructor(objectType: GraphDataObjectType, expression: string, unit: TIME_UNIT, distributionType: DISTRIBUTION_TYPE, entitiesPerArrival: number, maxArrival: number, firstCreation: number);
  constructor(objectType?: GraphDataObjectType, expression?: string, unit?: TIME_UNIT, distributionType?: DISTRIBUTION_TYPE, entitiesPerArrival?: number, maxArrival?: number, firstCreation?: number) {
    super();
    this.objectType = objectType || null;
    this.distributionType = distributionType || DISTRIBUTION_TYPE.RANDOM || null;
    this.expression = expression || '' || null;
    this.unit = unit || null;
    this.entitiesPerArrival = entitiesPerArrival || 1 || null;
    this.maxArrival = maxArrival || 0 || null;
    this.firstCreation = firstCreation || 0 || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getObjectType(): GraphDataObjectType | null {
    return this.objectType;
  }

  public setObjectType(objectType: GraphDataObjectType): void {
    this.objectType = objectType;
  }

  public getObjectTypeRef(): string | null {
    return GraphUtil.generateRef(this.getObjectType());
  }

  public getDistributionType(): DISTRIBUTION_TYPE | null {
    return this.distributionType;
  }

  public setDistributionType(distributionType: DISTRIBUTION_TYPE): void {
    this.distributionType = distributionType;
  }

  public getExpression(): string | null {
    return this.expression;
  }

  public setExpression(expression: string): void {
    this.expression = expression;
  }

  public getUnit(): TIME_UNIT | null {
    return this.unit;
  }

  public setUnit(unit: TIME_UNIT): void {
    this.unit = unit;
  }

  public getEntitiesPerArrival(): number | null {
    return this.entitiesPerArrival;
  }

  public setEntitiesPerArrival(entitiesPerArrival: number): void {
    this.entitiesPerArrival = entitiesPerArrival;
  }

  public getMaxArrival(): number | null {
    return this.maxArrival;
  }

  public setMaxArrival(maxArrival: number): void {
    this.maxArrival = maxArrival;
  }

  public getFirstCreation(): number | null {
    return this.firstCreation;
  }

  public setFirstCreation(firstCreation: number): void {
    this.firstCreation = firstCreation;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
