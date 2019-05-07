import { GraphConfigurationImpl } from '../GraphConfigurationImpl';
import { GraphControl } from '../../interfaces/components/GraphControl';

export class GraphControlImpl extends GraphConfigurationImpl implements GraphControl {
  public static readonly TYPE: 'control' = 'control';

  /** @Override */
  public static fn_object_deserialize(object: any): any {
    const graphControl: GraphControl = new GraphControlImpl();
    // console.log(object);
    return graphControl;
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
