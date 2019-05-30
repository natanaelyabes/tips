import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphMonitorNode } from '../../interfaces/components/GraphMonitorNode';

export class GraphMonitorNodeImpl extends GraphNodeImpl implements GraphMonitorNode {
  public static readonly TYPE: string | null = 'monitor';

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
