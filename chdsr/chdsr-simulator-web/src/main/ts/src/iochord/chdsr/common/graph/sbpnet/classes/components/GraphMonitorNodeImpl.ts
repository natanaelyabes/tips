import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphMonitorNode } from '../../interfaces/components/GraphMonitorNode';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphMonitorNodeImpl extends GraphNodeImpl implements GraphMonitorNode {
  public static TYPE: string = 'monitor';
  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
