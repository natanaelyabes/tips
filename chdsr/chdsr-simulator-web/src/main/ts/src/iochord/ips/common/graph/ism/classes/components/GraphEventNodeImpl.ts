import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphEventNode } from '../../interfaces/components/GraphEventNode';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphEventNodeImpl extends GraphNodeImpl implements GraphEventNode {
  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
