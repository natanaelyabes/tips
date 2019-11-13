import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataTable extends GraphData {
  getFields(): TSMap<string, string> | null;
  setFields(fields: TSMap<string, string>): void;
  getData(): TSMap<string, TSMap<string, string>> | null;
  setData(data: TSMap<string, TSMap<string, string>>): void;
}
