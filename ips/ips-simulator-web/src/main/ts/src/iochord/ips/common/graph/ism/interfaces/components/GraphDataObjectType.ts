import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 * The interface of graph data object type.
 *
 * @export
 * @interface GraphDataObjectType
 * @extends {GraphData}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataObjectType extends GraphData {

  /**
   * Returns the types of current object type data node.
   *
   * @returns {(TSMap<string, GraphDataTable> | null)}
   * @memberof GraphDataObjectType
   */
  getTypes(): TSMap<string, GraphDataTable> | null;

  /**
   * Assigns the types of current object type data node.
   *
   * @param {TSMap<string, GraphDataTable>} types
   * @memberof GraphDataObjectType
   */
  setTypes(fields: TSMap<string, GraphDataTable>): void;

  /**
   * Returns the types of current object type data node as string reference.
   *
   * @returns {string}
   * @memberof GraphDataObjectType
   */
  getTypeRefs(): string;

  /**
   * Assigns the types of current object type data node as string reference.
   *
   * @param {string} ref
   * @memberof GraphDataObjectType
   */
  setTypeRefs(refs: string): void;
}
