import { GraphConfigurationImpl } from '../GraphConfigurationImpl';
import { GraphControl } from '../../interfaces/components/GraphControl';

/**
 * Implementation of GraphControlImpl interface.
 *
 * @export
 * @class GraphControlImpl
 * @extends {GraphConfigurationImpl}
 * @implements {GraphControl}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphControlImpl extends GraphConfigurationImpl implements GraphControl {

  /**
   * Deserialize JSON object as GraphControl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(any | null)}
   * @memberof GraphControlImpl
   */
  public static deserialize(object: any): any | null {
    if (object === null) {
      return null;
    }
    const graphControl: GraphControl = new GraphControlImpl();
    return graphControl;
  }

  /**
   * Creates an instance of GraphControlImpl.
   *
   * @memberof GraphControlImpl
   */
  constructor() {
    super();
    this.setType('control');
  }

  /**
   * Serialize control node object as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphControlImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
