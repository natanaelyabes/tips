import { GraphConfigurationImpl } from '../GraphConfigurationImpl';
import { GraphControl } from '../../interfaces/components/GraphControl';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphControlImpl extends GraphConfigurationImpl implements GraphControl {
  /** @Override */
  public static deserialize(object: any): any | null {
    if (object === null) {
      return null;
    }
    const graphControl: GraphControl = new GraphControlImpl();
    return graphControl;
  }

  private elementType: string = 'control';

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
