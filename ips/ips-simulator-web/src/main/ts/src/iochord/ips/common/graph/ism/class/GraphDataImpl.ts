import { GraphData } from '../interfaces/GraphData';
import { GraphElementImpl } from './GraphElementImpl';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphData interface.
 *
 * @export
 * @class GraphDataImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphData}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphDataImpl extends GraphElementImpl implements GraphData {

  /**
   * Instances of data node.
   *
   * @static
   * @type {TSMap<string, GraphData>}
   * @memberof GraphDataImpl
   */
  public static instance: TSMap<string, GraphData> = new TSMap<string, GraphData>();

  /**
   * Creates an instance of GraphDataImpl.
   *
   * @memberof GraphDataImpl
   */
  constructor() {
    super();
  }

  /**
   * Serialize GraphDataImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphDataImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
