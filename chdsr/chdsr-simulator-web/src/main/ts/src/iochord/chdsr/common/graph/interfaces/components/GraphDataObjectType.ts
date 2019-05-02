import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

export interface GraphDataObjectType extends GraphData {
  readonly TYPE: string | 'objecttype';
  fn_graph_data_object_type_get_fields(): Map<string, GraphDataTable>;
}
