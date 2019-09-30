import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataObjectType extends GraphData {
  getTypes(): TSMap<string, GraphDataTable> | null;
  setTypes(fields: TSMap<string, GraphDataTable>): void;
  getTypeRefs(): TSMap<string, string | null> | null;
}
