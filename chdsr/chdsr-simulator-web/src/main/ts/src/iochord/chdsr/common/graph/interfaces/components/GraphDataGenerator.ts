import { TIME_UNIT } from './../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from './../../enums/DISTRIBUTION';
import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataGenerator extends GraphData {
  getObjectType(): GraphDataObjectType | null;
  setObjectType(objectType: GraphDataObjectType): void;
  getObjectTypeRef(): string | null;
  getDistributionType(): DISTRIBUTION_TYPE | null;
  setDistributionType(distributionType: DISTRIBUTION_TYPE): void;
  getExpression(): string | null;
  setExpression(expression: string): void;
  getUnit(): TIME_UNIT | null;
  setUnit(unit: TIME_UNIT): void;
  getEntitiesPerArrival(): number | null;
  setEntitiesPerArrival(entitiesPerArrival: number): void;
  getMaxArrival(): number | null;
  setMaxArrival(maxArrival: number): void;
  getFirstCreation(): number | null;
  setFirstCreation(firstCreation: number): void;
}
