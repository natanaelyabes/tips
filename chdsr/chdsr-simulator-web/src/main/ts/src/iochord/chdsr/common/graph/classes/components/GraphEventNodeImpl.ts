import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphEventNode } from '../../interfaces/components/GraphEventNode';

export class GraphEventNodeImpl extends GraphNodeImpl implements GraphEventNode {
  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
