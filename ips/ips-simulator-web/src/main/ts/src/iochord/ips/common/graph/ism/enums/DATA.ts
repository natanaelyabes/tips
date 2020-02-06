import { GraphDataTableImpl } from '../class/components/GraphDataTableImpl';
import { GraphDataObjectTypeImpl } from '../class/components/GraphDataObjectTypeImpl';
import { GraphDataGeneratorImpl } from '../class/components/GraphDataGeneratorImpl';
import { GraphDataFunctionImpl } from '../class/components/GraphDataFunctionImpl';
import { GraphDataQueueImpl } from '../class/components/GraphDataQueueImpl';
import { GraphDataResourceImpl } from '../class/components/GraphDataResourceImpl';

/**
 * Global variable of data node types.
 *
 * @export
 * @class GraphPageImpl
 * @extends {GraphElementImpl}
 * @implements {GraphPage}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export const DATA_TYPE = {
  datatable: GraphDataTableImpl,
  generator: GraphDataGeneratorImpl,
  objecttype: GraphDataObjectTypeImpl,
  function: GraphDataFunctionImpl,
  resource: GraphDataResourceImpl,
  queue: GraphDataQueueImpl,
};

/**
 * Enumeration for types of data node.
 *
 * @export
 * @class GraphPageImpl
 * @extends {GraphElementImpl}
 * @implements {GraphPage}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum DATA_TYPE_ENUM {

  /**
   * A datatable object allows user to create custom properties and embedded them in the node or data node object.
   */
  datatable,

  /**
   * A generator data object allows tokens to be generated from start event node.
   */
  generator,

  /**
   * An objecttype specifies the type of the object within node or data node object.
   */
  objecttype,

  /**
   * A function lets user to define its own specific algorithm to process tokens within activity node.
   */
  function,

  /**
   * A resource lets user to define the resource (along with its properties) of an activity.
   */
  resource,

  /**
   * A queue object lets user to define the queue happened within activity nodes.
   */
  queue,
}
