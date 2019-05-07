import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphMonitorNode } from '../../interfaces/components/GraphMonitorNode';

export class GraphMonitorNodeImpl extends GraphNodeImpl implements GraphMonitorNode {
  public static readonly TYPE: 'monitor' = 'monitor';

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
