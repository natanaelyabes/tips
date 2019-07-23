import { GraphData } from '../GraphData';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataTable extends GraphData {
  getFields(): Map<string, string> | null;
  setFields(fields: Map<string, string>): void;
  getData(): Map<string, Map<string, object>> | null;
  setData(data: Map<string, Map<string, object>>): void;
}
