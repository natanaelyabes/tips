import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

export interface GraphDataFunction extends GraphData {
  // readonly TYPE: string | 'function';
  fn_graph_data_function_get_input_parameters(): Map<string, GraphDataObjectType> | undefined;
  fn_graph_data_function_set_input_parameters(inputParameters: Map<string, GraphDataObjectType>): void;
  fn_graph_data_function_get_code(): string | undefined;
  fn_graph_data_function_set_code(code: string): void;
  fn_graph_data_function_get_output_variables(): Map<string, GraphDataObjectType> | undefined;
  fn_graph_data_function_set_output_variables(outputVariables: Map<string, GraphDataObjectType>): void;
}
