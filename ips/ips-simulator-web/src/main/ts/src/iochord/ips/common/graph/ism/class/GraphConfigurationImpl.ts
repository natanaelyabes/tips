import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphElementImpl } from './GraphElementImpl';
import { TSMap } from 'typescript-map';
import { GraphPage } from '../interfaces/GraphPage';

/**
 * Implementation of the graph configuration interface.
 *
 * @export
 * @class GraphConfigurationImpl
 * @extends {GraphElementImpl}
 * @implements {GraphConfiguration}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphConfigurationImpl extends GraphElementImpl implements GraphConfiguration {

  /**
   * Instances of configuration node.
   *
   * @static
   * @type {TSMap<string, GraphConfiguration>}
   * @memberof GraphConfigurationImpl
   */
  public static instance: TSMap<string, GraphConfiguration> = new TSMap<string, GraphConfiguration>();

  /**
   * Deserialize JSON object to GraphConfigurationImpl.
   *
   * @static
   * @param {*} object
   * @returns {(TSMap<string, GraphConfiguration> | null)}
   * @memberof GraphConfigurationImpl
   */
  public static deserialize(object: any): TSMap<string, GraphConfiguration> | null {
    const graphConfiguration: GraphConfiguration = new GraphConfigurationImpl();
    graphConfiguration.setId(object.id);
    graphConfiguration.setLabel(object.label);
    graphConfiguration.setAttributes(object.attributes as TSMap<string, string>);
    graphConfiguration.setType(object.elementType);
    graphConfiguration.setPages(object.pages);
    GraphConfigurationImpl.instance.set(graphConfiguration.getId() as string, graphConfiguration);
    return new TSMap<string, GraphConfiguration>();
  }

  /**
   * Pages of graph for a configuration to be applied
   *
   * @private
   * @type {(TSMap<string, GraphPage> | null)}
   * @memberof GraphConfigurationImpl
   */
  private pages?: TSMap<string, GraphPage> | null = new TSMap<string, GraphPage>();

  /**
   * Creates an instance of GraphConfigurationImpl.
   *
   * @memberof GraphConfigurationImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns pages of the GraphConfigurationImpl.
   *
   * @returns {(TSMap<string, GraphPage> | null)}
   * @memberof GraphConfigurationImpl
   */
  public getPages(): TSMap<string, GraphPage> | null {
    return this.pages as TSMap<string, GraphPage>;
  }

  /**
   * Assigns pages to the current configuration node.
   *
   * @param {TSMap<string, GraphPage>} pages
   * @memberof GraphConfigurationImpl
   */
  public setPages(pages: TSMap<string, GraphPage>): void {
    this.pages = pages as TSMap<string, GraphPage>;
  }

  /**
   * Serialize GraphConfigurationImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphConfigurationImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
