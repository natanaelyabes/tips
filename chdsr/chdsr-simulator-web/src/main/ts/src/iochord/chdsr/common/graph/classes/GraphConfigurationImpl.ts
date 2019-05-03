import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphElementImpl } from './GraphElementImpl';

export class GraphConfigurationImpl extends GraphElementImpl implements GraphConfiguration {
  public readonly TYPE: string | 'configuration' = 'configuration';

  constructor() {
    super();
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }
}
