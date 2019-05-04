import { GraphData } from './../interfaces/GraphData';
import { GraphElementImpl } from './GraphElementImpl';

export class GraphDataImpl extends GraphElementImpl implements GraphData {
  public static fn_object_deserialize(object: any): Map<string, GraphData> {
    const graphData: GraphData = new GraphDataImpl();
    console.log(object);
    return new Map<string, GraphData>();
  }
  public readonly TYPE: string | 'data' = 'data';

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
