import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphElementImpl } from './GraphElementImpl';
import { TSMap } from 'typescript-map';

/**
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
    GraphConfigurationImpl.instance.set(graphConfiguration.getId() as string, graphConfiguration);
    return new TSMap<string, GraphConfiguration>();
  }

  constructor() {
    super();
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
