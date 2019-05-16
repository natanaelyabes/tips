import { GraphData } from '../GraphData';

export interface GraphDataTable extends GraphData {
  // readonly TYPE: string | 'datatable';
  fn_graph_data_table_get_fields(): Map<string, string>;
  fn_graph_data_table_set_fields(fields: Map<string, string>): void;
  fn_graph_data_table_get_data(): Map<string, Map<string, object>>;
  fn_graph_data_table_set_data(data: Map<string, Map<string, object>>): void;
}
