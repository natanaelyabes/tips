import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphElementImpl } from './GraphElementImpl';

export class GraphConfigurationImpl extends GraphElementImpl implements GraphConfiguration {
  public static fn_object_deserialize(object: any): Map<string, GraphConfiguration> {
    const graphConfiguration: GraphConfiguration = new GraphConfigurationImpl();
    // console.log(object);
    return new Map<string, GraphConfiguration>();
  }
  public readonly TYPE: string | 'configuration' = 'configuration';

  constructor() {
    super();
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
