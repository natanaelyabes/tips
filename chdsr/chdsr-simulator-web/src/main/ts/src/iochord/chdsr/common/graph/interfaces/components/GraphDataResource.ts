import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

export interface GraphDataResource extends GraphData {
  readonly TYPE: string | 'resource';
  fn_graph_data_resource_get_group_id(): string;
  fn_graph_data_resource_get_data(): GraphDataTable;
}
