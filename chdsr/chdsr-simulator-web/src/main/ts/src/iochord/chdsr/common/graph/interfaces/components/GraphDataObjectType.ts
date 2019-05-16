import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

export interface GraphDataObjectType extends GraphData {
  // readonly TYPE: string | 'objecttype';
  fn_graph_data_object_type_get_types(): Map<string, GraphDataTable>;
  fn_graph_data_object_type_set_types(fields: Map<string, GraphDataTable>): void;
}
