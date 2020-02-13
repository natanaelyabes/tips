import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphEventNode } from '../../interfaces/components/GraphEventNode';

/**
 * Implementation of GraphEventNodeImpl interface.
 *
 * @export
 * @class GraphEventNodeImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphEventNodeImpl}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphEventNodeImpl extends GraphNodeImpl implements GraphEventNode {

  /**
   * Serialize GraphEventNodeImpl as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphEventNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
