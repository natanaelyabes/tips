import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphControl } from './components/GraphControl';
import { TSMap } from 'typescript-map';

/**
 * The interface of the graph.
 *
 * @export
 * @interface Graph
 * @extends {GraphElement}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface Graph extends GraphElement {

  /**
   * Return the version of the graph.
   *
   * @returns {(string | null)}
   * @memberof Graph
   */
  getVersion(): string | null;

  /**
   * Returns the pages of the graph.
   *
   * @returns {(TSMap<string, GraphPage> | null)}
   * @memberof Graph
   */
  getPages(): TSMap<string, GraphPage> | null;

  /**
   * Assigns pages to the graph.
   *
   * @param {TSMap<string, GraphPage>} pages
   * @memberof Graph
   */
  setPages(pages: TSMap<string, GraphPage>): void;

  /**
   * Returns the default page of the graph.
   *
   * @returns {(GraphPage | null)}
   * @memberof Graph
   */
  getDefaultPage(): GraphPage | null;

  /**
   * Assigns the default page of the graph.
   *
   * @param {GraphPage} page
   * @memberof Graph
   */
  setDefaultPage(page: GraphPage): void;

  /**
   * Returns the configurations of the graph.
   *
   * @returns {(TSMap<string, GraphConfiguration> | null)}
   * @memberof Graph
   */
  getConfigurations(): TSMap<string, GraphConfiguration> | null;

  /**
   * Assigns the configuration settings of the graph.
   *
   * @param {TSMap<string, GraphConfiguration>} configurations
   * @memberof Graph
   */
  setConfigurations(configurations: TSMap<string, GraphConfiguration>): void;

  /**
   * Returns the control settings to the graph.
   *
   * @returns {(GraphControl | null)}
   * @memberof Graph
   */
  getControl(): GraphControl | null;

  /**
   * Assigns control settings to the graph.
   *
   * @param {GraphControl} control
   * @memberof Graph
   */
  setControl(control: GraphControl): void;
}
