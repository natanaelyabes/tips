import { GraphData } from './../interfaces/GraphData';
import { GraphElementImpl } from './GraphElementImpl';

export class GraphDataImpl extends GraphElementImpl implements GraphData {
  public readonly TYPE: string | 'data' = 'data';

  constructor() {
    super();
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }
}
