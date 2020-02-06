import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { TSMap } from 'typescript-map';

/**
 * The interface of graph configuration.
 *
 * @export
 * @interface GraphConfiguration
 * @extends {GraphElement}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphConfiguration extends GraphElement {

  /**
   * Returns pages of graph.
   *
   * @returns {(TSMap<string, GraphPage> | null)}
   * @memberof GraphConfiguration
   */
  getPages(): TSMap<string, GraphPage> | null;

  /**
   * Assign pages to the graph.
   *
   * @param {TSMap<string, GraphPage>} pages
   * @memberof GraphConfiguration
   */
  setPages(pages: TSMap<string, GraphPage>): void;
}
