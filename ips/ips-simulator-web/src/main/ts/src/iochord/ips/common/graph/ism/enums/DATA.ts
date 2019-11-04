import { GraphDataTableImpl } from '../class/components/GraphDataTableImpl';
import { GraphDataObjectTypeImpl } from '../class/components/GraphDataObjectTypeImpl';
import { GraphDataGeneratorImpl } from '../class/components/GraphDataGeneratorImpl';
import { GraphDataFunctionImpl } from '../class/components/GraphDataFunctionImpl';
import { GraphDataQueueImpl } from '../class/components/GraphDataQueueImpl';
import { GraphDataResourceImpl } from '../class/components/GraphDataResourceImpl';

/**
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

export enum DATA_TYPE_ENUM {
  datatable,
  generator,
  objecttype,
  function,
  resource,
  queue,
}
