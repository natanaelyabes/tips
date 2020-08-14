import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

/**
 * The interface of graph data generator.
 *
 * @export
 * @interface GraphDataGenerator
 * @extends {GraphData}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphDataGenerator extends GraphData {

  /**
   * Returns the object type of generator data node.
   *
   * @returns {(GraphDataObjectType | null)}
   * @memberof GraphDataGenerator
   */
  getObjectType(): GraphDataObjectType | null;

  /**
   * Assigns object type for generator data node.
   *
   * @param {GraphDataObjectType} objectType
   * @memberof GraphDataGenerator
   */
  setObjectType(objectType: GraphDataObjectType): void;

  /**
   * Returns the object type of generator data node as string reference.
   *
   * @returns {(string | null)}
   * @memberof GraphDataGenerator
   */
  getObjectTypeRef(): string | null;

  /**
   * Assigns object type string reference to generator data node.
   *
   * @param {(string | null)} objectType
   * @memberof GraphDataGenerator
   */
  setObjectTypeRef(objectType: string | null): void;

  /**
   * Returns the distribution type of generator data node.
   *
   * @returns {(DISTRIBUTION_TYPE | null)}
   * @memberof GraphDataGenerator
   */
  getDistributionType(): DISTRIBUTION_TYPE | null;

  /**
   * Assigns distribution type to generator data node.
   *
   * @param {DISTRIBUTION_TYPE} distributionType
   * @memberof GraphDataGenerator
   */
  setDistributionType(distributionType: DISTRIBUTION_TYPE): void;

  /**
   * Returns the expression of selected distribution type for the current generator data node.
   *
   * @returns {(string | null)}
   * @memberof GraphDataGenerator
   */
  getExpression(): string | null;

  /**
   * Assigns expression for the selected distribution type for the current generator data node.
   *
   * @param {string} expression
   * @memberof GraphDataGenerator
   */
  setExpression(expression: string): void;

  /**
   * Returns the time unit of generator data node.
   *
   * @returns {(TIME_UNIT | null)}
   * @memberof GraphDataGenerator
   */
  getUnit(): TIME_UNIT | null;

  /**
   * Assigns a time unit for the current generator data node.
   *
   * @param {TIME_UNIT} unit
   * @memberof GraphDataGenerator
   */
  setUnit(unit: TIME_UNIT): void;

  /**
   * Returns the entities per arrival for the current data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataGenerator
   */
  getEntitiesPerArrival(): number | null;

  /**
   * Assigns the entities per arrival for the current generator data node.
   *
   * @param {number} entitiesPerArrival
   * @memberof GraphDataGenerator
   */
  setEntitiesPerArrival(entitiesPerArrival: number): void;

  /**
   * Retuns the maximum number of arrival for the current generator data node.
   *
   * @returns {(number | null)}
   * @memberof GraphDataGenerator
   */
  getMaxArrival(): number | null;

  /**
   * Assigns the maximum number of arrival for the current generator data node.
   *
   * @param {number} maxArrival
   * @memberof GraphDataGenerator
   */
  setMaxArrival(maxArrival: number): void;

  /**
   * Returns the number of entities when created for the first time.
   *
   * @returns {(number | null)}
   * @memberof GraphDataGenerator
   */
  getFirstCreation(): number | null;

  /**
   * Assigns the number of entities when created for the first time.
   *
   * @param {number} firstCreation
   * @memberof GraphDataGenerator
   */
  setFirstCreation(firstCreation: number): void;
}
