import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

export interface GraphDataFunction extends GraphData {
  readonly TYPE: string | 'function';
  fn_graph_data_function_get_input_parameters(): Map<string, GraphDataObjectType>;
  fn_graph_data_function_get_code(): string;
  fn_graph_data_function_get_output_variables(): Map<string, GraphDataObjectType>;
}
