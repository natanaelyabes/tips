import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphElementImpl } from './GraphElementImpl';

export class GraphConfigurationImpl extends GraphElementImpl implements GraphConfiguration {
  public static instance: Map<string, GraphConfiguration> = new Map<string, GraphConfiguration>();

  public static deserialize(object: any): Map<string, GraphConfiguration> | null {
    const graphConfiguration: GraphConfiguration = new GraphConfigurationImpl();
    graphConfiguration.setId(object.id);
    graphConfiguration.setLabel(object.label);
    graphConfiguration.setAttributes(object.attributes as Map<string, string>);
    graphConfiguration.setType(object.elementType);
    GraphConfigurationImpl.instance.set(graphConfiguration.getId() as string, graphConfiguration);
    return new Map<string, GraphConfiguration>();
  }

  constructor() {
    super();
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
