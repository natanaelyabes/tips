import { GraphData } from '../GraphData';

export interface GraphDataTable extends GraphData {
  readonly TYPE: string | 'datatable'
  fn_graph_data_table_get_fields(): string[];
  fn_graph_data_table_get_data(): Map<string, Map<string, object>>;
}
