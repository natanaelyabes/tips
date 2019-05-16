import { DistributionType } from './../../enums/DISTRIBUTION';
import { GraphDataObjectType } from './GraphDataObjectType';
import { GraphData } from '../GraphData';

export interface GraphDataGenerator extends GraphData {
  // readonly TYPE: string | 'generator';
  fn_graph_data_generator_get_object_type(): GraphDataObjectType | null;
  fn_graph_data_generator_set_object_type(objectType: GraphDataObjectType): void;
  fn_graph_data_generator_get_distribution_type(): DistributionType | null;
  fn_graph_data_generator_set_distribution_type(distributionType: DistributionType): void;
  fn_graph_data_generator_get_expression(): string | null;
  fn_graph_data_generator_set_expression(expression: string): void;
  fn_graph_data_generator_get_entities_per_arrival(): number | null;
  fn_graph_data_generator_set_entities_per_arrival(entitiesPerArrival: number): void;
  fn_graph_data_generator_get_max_arrival(): number | null;
  fn_graph_data_generator_set_max_arrival(maxArrival: number): void;
  fn_graph_data_generator_get_first_creation(): number | null;
  fn_graph_data_generator_set_first_creation(firstCreation: number): void;
}
