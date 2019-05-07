import { GraphDataTableImpl } from '../classes/components/GraphDataTableImpl';
import { GraphDataObjectTypeImpl } from '../classes/components/GraphDataObjectTypeImpl';
import { GraphDataGeneratorImpl } from '../classes/components/GraphDataGeneratorImpl';
import { GraphDataFunctionImpl } from '../classes/components/GraphDataFunctionImpl';
import { GraphDataQueueImpl } from '../classes/components/GraphDataQueueImpl';
import { GraphDataResourceImpl } from '../classes/components/GraphDataResourceImpl';

export const DATA_TYPE = {
  datatable: GraphDataTableImpl,
  objecttype: GraphDataObjectTypeImpl,
  generator: GraphDataGeneratorImpl,
  function: GraphDataFunctionImpl,
  queue: GraphDataQueueImpl,
  resource: GraphDataResourceImpl,
};
