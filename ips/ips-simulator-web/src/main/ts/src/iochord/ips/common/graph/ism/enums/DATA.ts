import { GraphDataTableImpl } from '../classes/components/GraphDataTableImpl';
import { GraphDataObjectTypeImpl } from '../classes/components/GraphDataObjectTypeImpl';
import { GraphDataGeneratorImpl } from '../classes/components/GraphDataGeneratorImpl';
import { GraphDataFunctionImpl } from '../classes/components/GraphDataFunctionImpl';
import { GraphDataQueueImpl } from '../classes/components/GraphDataQueueImpl';
import { GraphDataResourceImpl } from '../classes/components/GraphDataResourceImpl';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export const DATA_TYPE = {
  datatable: GraphDataTableImpl,
  objecttype: GraphDataObjectTypeImpl,
  generator: GraphDataGeneratorImpl,
  function: GraphDataFunctionImpl,
  queue: GraphDataQueueImpl,
  resource: GraphDataResourceImpl,
};

export enum DATA_TYPE_ENUM {
  datatable,
  objecttype,
  generator,
  function,
  queue,
  resource,
}
