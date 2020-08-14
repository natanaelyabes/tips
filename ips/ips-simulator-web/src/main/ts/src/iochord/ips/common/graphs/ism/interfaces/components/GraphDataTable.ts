import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 * The interface of graph data tables.
 *
 * @export
 * @interface GraphDataTable
 * @extends {GraphData}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphDataTable extends GraphData {

  /**
   * Returns the fields of current data table node.
   *
   * @returns {(TSMap<string, string> | null)}
   * @memberof GraphDataTable
   */
  getFields(): TSMap<string, string> | null;

  /**
   * Assigns the fields of current data table node.
   *
   * @param {TSMap<string, string>} fields
   * @memberof GraphDataTable
   */
  setFields(fields: TSMap<string, string>): void;

  /**
   * Returns the rows for current data table node.
   *
   * @returns {(TSMap<string, TSMap<string, any>> | null)}
   * @memberof GraphDataTable
   */
  getData(): TSMap<string, TSMap<string, string>> | null;

  /**
   * Assigns rows to current data table node.
   *
   * @param {TSMap<string, TSMap<string, any>>} data
   * @memberof GraphDataTable
   */
  setData(data: TSMap<string, TSMap<string, string>>): void;
}
