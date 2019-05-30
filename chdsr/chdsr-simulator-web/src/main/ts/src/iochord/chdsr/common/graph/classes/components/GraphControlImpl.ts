import { GraphConfigurationImpl } from '../GraphConfigurationImpl';
import { GraphControl } from '../../interfaces/components/GraphControl';

export class GraphControlImpl extends GraphConfigurationImpl implements GraphControl {
  public static readonly TYPE: string | null = 'control';

  /** @Override */
  public static deserialize(object: any): any | null {
    const graphControl: GraphControl = new GraphControlImpl();
    return graphControl;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
