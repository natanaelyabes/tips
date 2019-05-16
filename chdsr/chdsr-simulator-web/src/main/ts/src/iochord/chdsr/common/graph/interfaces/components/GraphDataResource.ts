import { GraphDataFunction } from './GraphDataFunction';
import { GraphDataQueue } from './GraphDataQueue';
import { DistributionType } from './../../enums/DISTRIBUTION';
import { GraphDataTable } from './GraphDataTable';
import { GraphData } from '../GraphData';

export interface GraphDataResource extends GraphData {
  // readonly TYPE: string | 'resource';
  fn_graph_data_resource_get_group_id(): string | null;
  fn_graph_data_resource_set_group_id(groupId: string): void;
  fn_graph_data_resource_get_data(): GraphDataTable | null;
  fn_graph_data_resource_set_data(data: GraphDataTable): void;
  fn_graph_data_resource_get_setup_time(): DistributionType;
  fn_graph_data_resource_set_setup_time(setupTime: DistributionType): void;
  fn_graph_data_resource_get_expression(): string;
  fn_graph_data_resource_set_expression(expression: string): void;
  fn_graph_data_resource_get_time_unit(): number | null;
  fn_graph_data_resource_set_time_unit(timeUnit: number): void;
  fn_graph_data_resource_get_cost(): number | null;
  fn_graph_data_resource_set_cost(cost: number): void;
  fn_graph_data_resource_get_queue(): GraphDataQueue;
  fn_graph_data_resource_set_queue(queue: GraphDataQueue): void;
  fn_graph_data_resource_get_function(): GraphDataFunction;
  fn_graph_data_resource_set_function(fn: GraphDataFunction): void;
}
