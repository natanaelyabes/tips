import { GraphDataObjectType } from '../../interfaces/components/GraphDataObjectType';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { GraphDataImpl } from '../GraphDataImpl';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphDataGeneratorImpl interface.
 *
 * @export
 * @class GraphDataGeneratorImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphDataGeneratorImpl}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class GraphDataGeneratorImpl extends GraphDataImpl implements GraphDataGenerator {

  /**
   * Field to identify the type of node.
   *
   * @static
   * @type {string}
   * @memberof GraphDataGeneratorImpl
   */
  public static TYPE: string = 'generator';

  /**
   * Deserialize JSON object as GraphDataGeneratorImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphDataGenerator | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public static deserialize(object: any): GraphDataGenerator | null {
    const graphDataGenerator: GraphDataGenerator = new GraphDataGeneratorImpl();
    graphDataGenerator.setId(object.id);
    graphDataGenerator.setLabel(object.label);
    graphDataGenerator.setType(object.elementType);
    graphDataGenerator.setAttributes(object.attributes as TSMap<string, string>);
    graphDataGenerator.setDistributionType(object.distributionType);
    graphDataGenerator.setExpression(object.expression);
    graphDataGenerator.setUnit(object.unit);
    graphDataGenerator.setEntitiesPerArrival(object.entitiesPerArrival);
    graphDataGenerator.setMaxArrival(object.maxArrival);
    graphDataGenerator.setFirstCreation(object.firstCreation);
    graphDataGenerator.setObjectTypeRef(object.objectTypeRef);
    GraphDataGeneratorImpl.instance.set(graphDataGenerator.getId() as string, graphDataGenerator);
    return graphDataGenerator;
  }

  /**
   * The object type of current generator data node.
   *
   * @private
   * @type {(GraphDataObjectType | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private objectType?: GraphDataObjectType | null;

  /**
   * The object type of current generator data node as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private objectTypeRef?: string | null;

  /**
   * Departure rate of generator following a distribution model.
   *
   * @private
   * @type {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private distributionType?: DISTRIBUTION_TYPE | null = DISTRIBUTION_TYPE.RANDOM;

  /**
   * Specified experssions for the selected distribution model.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private expression?: string | null = '';

  /**
   * The time unit for generator data node.
   *
   * @private
   * @type {(TIME_UNIT | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private unit?: TIME_UNIT | null;

  /**
   * The number of entities per arrival.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private entitiesPerArrival?: number | null = 1;

  /**
   * Maximum number of entities per arrival.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private maxArrival?: number | null = 0;

  /**
   * The number of entities when it first created.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataGeneratorImpl
   */
  private firstCreation?: number | null = 0;

  /**
   * Creates an instance of GraphDataGeneratorImpl.
   *
   * @memberof GraphDataGeneratorImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the object type of generator data node.
   *
   * @returns {(GraphDataObjectType | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getObjectType(): GraphDataObjectType | null {
    return this.objectType as GraphDataObjectType | null;
  }

  /**
   * Assigns object type for generator data node.
   *
   * @param {GraphDataObjectType} objectType
   * @memberof GraphDataGeneratorImpl
   */
  public setObjectType(objectType: GraphDataObjectType): void {
    this.objectType = objectType || this.objectType;
  }

  /**
   * Returns the object type of generator data node as string reference.
   *
   * @returns {(string | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getObjectTypeRef(): string | null {
    return this.objectTypeRef as string;
  }

  /**
   * Assigns object type string reference to generator data node.
   *
   * @param {(string | null)} objectType
   * @memberof GraphDataGeneratorImpl
   */
  public setObjectTypeRef(objectType: string | null): void {
    this.objectTypeRef = objectType;
  }

  /**
   * Returns the distribution type of generator data node.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getDistributionType(): DISTRIBUTION_TYPE | null {
    return this.distributionType as DISTRIBUTION_TYPE | null;
  }

  /**
   * Assigns distribution type to generator data node.
   *
   * @param {DISTRIBUTION_TYPE} distributionType
   * @memberof GraphDataGeneratorImpl
   */
  public setDistributionType(distributionType: DISTRIBUTION_TYPE): void {
    this.distributionType = distributionType || this.distributionType;
  }

  /**
   * Returns the expression of selected distribution type for the current generator data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getExpression(): string | null {
    return this.expression as string | null;
  }

  /**
   * Assigns expression for the selected distribution type for the current generator data node.
   *
   * @param {string} expression
   * @memberof GraphDataGeneratorImpl
   */
  public setExpression(expression: string): void {
    this.expression = expression || this.expression;
  }

  /**
   * Returns the time unit of generator data node.
   *
   * @returns {(TIME_UNIT | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getUnit(): TIME_UNIT | null {
    return this.unit as TIME_UNIT | null;
  }

  /**
   * Assigns a time unit for the current generator data node.
   *
   * @param {TIME_UNIT} unit
   * @memberof GraphDataGeneratorImpl
   */
  public setUnit(unit: TIME_UNIT): void {
    this.unit = unit || this.unit;
  }

  /**
   * Returns the entities per arrival for the current data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getEntitiesPerArrival(): number | null {
    return this.entitiesPerArrival as number | null;
  }

  /**
   * Assigns the entities per arrival for the current generator data node.
   *
   * @param {number} entitiesPerArrival
   * @memberof GraphDataGeneratorImpl
   */
  public setEntitiesPerArrival(entitiesPerArrival: number): void {
    this.entitiesPerArrival = entitiesPerArrival || this.entitiesPerArrival;
  }

  /**
   * Retuns the maximum number of arrival for the current generator data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getMaxArrival(): number | null {
    return this.maxArrival as number | null;
  }

  /**
   * Assigns the maximum number of arrival for the current generator data node.
   *
   * @param {number} maxArrival
   * @memberof GraphDataGeneratorImpl
   */
  public setMaxArrival(maxArrival: number): void {
    this.maxArrival = maxArrival || this.maxArrival;
  }

  /**
   * Returns the number of entities when created for the first time.
   *
   * @returns {(number | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public getFirstCreation(): number | null {
    return this.firstCreation as number | null;
  }

  /**
   * Assigns the number of entities when created for the first time.
   *
   * @param {number} firstCreation
   * @memberof GraphDataGeneratorImpl
   */
  public setFirstCreation(firstCreation: number): void {
    this.firstCreation = firstCreation || this.firstCreation;
  }

  /**
   * Serialize GraphDataGeneratorImpl as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphDataGeneratorImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
