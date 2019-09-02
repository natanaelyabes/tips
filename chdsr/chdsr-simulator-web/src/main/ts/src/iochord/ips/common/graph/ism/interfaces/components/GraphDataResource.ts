import { TIME_UNIT } from '../../enums/TIME_UNIT';
import { DISTRIBUTION_TYPE } from '../../enums/DISTRIBUTION';
import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';
import { RESOURCE_CRITERIA } from '../../enums/RESOURCE';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataResource extends GraphData {
  getGroupId(): string | null;
  setGroupId(groupId: string): void;
  getData(): GraphDataTable | null;
  setData(data: GraphDataTable): void;
  getDataRef(): string | null;
  getSetupTime(): DISTRIBUTION_TYPE | null;
  setSetupTime(setupTime: DISTRIBUTION_TYPE): void;
  getExpression(): string | null;
  setExpression(expression: string): void;
  getTimeUnit(): TIME_UNIT | null;
  setTimeUnit(timeUnit: TIME_UNIT): void;
  getCriteria(): RESOURCE_CRITERIA | null;
  setCriteria(criteria: RESOURCE_CRITERIA): void;
  getHourlyIdleCost(): number | null;
  setHourlyIdleCost(cost: number): void;
  getHourlyBusyCost(): number | null;
  setHourlyBusyCost(cost: number): void;
  isImported(): boolean | null;
  setImported(imported: boolean): void;
  getDataTable(): GraphDataTable | null;
  setDataTable(dataTable: GraphDataTable): void;
}
