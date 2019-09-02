import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataObjectType extends GraphData {
  getTypes(): Map<string, GraphDataTable> | null;
  setTypes(fields: Map<string, GraphDataTable>): void;
  getTypeRefs(): Map<string, string | null> | null;
}
