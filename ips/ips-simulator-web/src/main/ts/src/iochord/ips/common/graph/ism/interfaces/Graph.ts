import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphData } from './GraphData';
import { GraphControl } from './components/GraphControl';
import { TSMap } from 'typescript-map';

/**
 *
 * @export
 * @interface Graph
 * @extends {GraphElement}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface Graph extends GraphElement {

  /**
   *
   *
   * @returns {(string | null)}
   * @memberof Graph
   */
  getVersion(): string | null;

  /**
   *
   *
   * @returns {(TSMap<string, GraphPage> | null)}
   * @memberof Graph
   */
  getPages(): TSMap<string, GraphPage> | null;

  /**
   *
   *
   * @param {TSMap<string, GraphPage>} pages
   * @memberof Graph
   */
  setPages(pages: TSMap<string, GraphPage>): void;

  /**
   *
   *
   * @returns {(GraphPage | null)}
   * @memberof Graph
   */
  getDefaultPage(): GraphPage | null;

  /**
   *
   *
   * @param {GraphPage} page
   * @memberof Graph
   */
  setDefaultPage(page: GraphPage): void;

  /**
   *
   *
   * @returns {(TSMap<string, GraphConfiguration> | null)}
   * @memberof Graph
   */
  getConfigurations(): TSMap<string, GraphConfiguration> | null;

  /**
   *
   *
   * @param {TSMap<string, GraphConfiguration>} configurations
   * @memberof Graph
   */
  setConfigurations(configurations: TSMap<string, GraphConfiguration>): void;

  /**
   *
   *
   * @returns {(GraphControl | null)}
   * @memberof Graph
   */
  getControl(): GraphControl | null;

  /**
   *
   *
   * @param {GraphControl} control
   * @memberof Graph
   */
  setControl(control: GraphControl): void;
}
