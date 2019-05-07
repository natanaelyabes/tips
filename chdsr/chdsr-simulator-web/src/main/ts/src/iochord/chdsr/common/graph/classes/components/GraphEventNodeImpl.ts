import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphEventNode } from '../../interfaces/components/GraphEventNode';

export class GraphEventNodeImpl extends GraphNodeImpl implements GraphEventNode {
  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
