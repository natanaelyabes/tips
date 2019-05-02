import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

export interface GraphDataGenerator extends GraphData {
  readonly TYPE: string | 'generator';
  fn_graph_data_generator_get_object_type(): GraphDataObjectType;
  fn_graph_data_generator_get_expression(): string;
  fn_graph_data_generator_get_entities_per_arrival(): number;
  fn_graph_data_generator_get_max_arrival(): number;
  fn_graph_data_generator_get_first_creation(): number;
}
