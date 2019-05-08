import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

export interface GraphDataResource extends GraphData {
  // readonly TYPE: string | 'resource';
  fn_graph_data_resource_get_group_id(): string | null;
  fn_graph_data_resource_set_group_id(groupId: string): void;
  fn_graph_data_resource_get_data(): GraphDataTable | null;
  fn_graph_data_resource_set_data(data: GraphDataTable): void;
}
