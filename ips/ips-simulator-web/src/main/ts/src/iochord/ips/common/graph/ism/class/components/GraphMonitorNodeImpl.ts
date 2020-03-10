import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphMonitorNode } from '../../interfaces/components/GraphMonitorNode';

/**
 * Implementation of GraphMonitorNodeImpl interface.
 *
 * @export
 * @class GraphMonitorNodeImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphMonitorNodeImpl}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphMonitorNodeImpl extends GraphNodeImpl implements GraphMonitorNode {

  /**
   * Field to identify the type of node.
   *
   * @static
   * @type {string}
   * @memberof GraphMonitorNodeImpl
   */
  public static TYPE: string = 'monitor';

  /**
   * Serialize GraphMonitorNodeImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphMonitorNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
