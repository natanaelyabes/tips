import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphMonitorNode } from '../../interfaces/components/GraphMonitorNode';

export class GraphMonitorNodeImpl extends GraphNodeImpl implements GraphMonitorNode {
  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
