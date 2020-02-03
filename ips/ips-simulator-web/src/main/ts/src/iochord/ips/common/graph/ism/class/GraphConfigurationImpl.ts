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
  public static instance: TSMap<string, GraphConfiguration> = new TSMap<string, GraphConfiguration>();

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

  private pages?: TSMap<string, GraphPage> | null = new TSMap<string, GraphPage>();

  constructor() {
    super();
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }

  public getPages(): TSMap<string, GraphPage> | null {
    return this.pages as TSMap<string, GraphPage>;
  }

  public setPages(pages: TSMap<string, GraphPage>): void {
    this.pages = pages as TSMap<string, GraphPage>;
  }
}
