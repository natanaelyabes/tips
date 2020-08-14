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
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
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
