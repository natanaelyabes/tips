import { VARIABLE_TYPE } from './../../enums/VARIABLE';
import { DistributionType } from './../../enums/DISTRIBUTION';
import { RESOURCE_SELECTION } from './../../enums/RESOURCE';
import { ACTIVITY_TYPE } from './../../enums/ACTIVITY';
import { GraphDataFunction } from './GraphDataFunction';
import { GraphDataQueue } from './GraphDataQueue';
import { GraphDataResource } from './GraphDataResource';
import { GraphNode } from '../GraphNode';

export interface GraphActivityNode extends GraphNode {
  // readonly TYPE: string | 'activity';
  fn_graph_activity_node_get_type(): ACTIVITY_TYPE | null;
  fn_graph_activity_node_set_type(type: ACTIVITY_TYPE): void;
  fn_graph_activity_node_get_resource(): GraphDataResource | null;
  fn_graph_activity_node_set_resource(resource: GraphDataResource): void;
  fn_graph_activity_node_get_resource_selection_method(): RESOURCE_SELECTION | null;
  fn_graph_activity_node_set_resource_selection_method(method: RESOURCE_SELECTION): void;
  fn_graph_activity_node_get_setup_time(): DistributionType | null;
  fn_graph_activity_node_set_setup_time(setupTime: DistributionType): void;
  fn_graph_activity_node_get_setup_time_parameter(): string | null;
  fn_graph_activity_node_set_setup_time_parameter(setupTimeParameter: string): void;
  fn_graph_activity_node_get_processing_time(): DistributionType | null;
  fn_graph_activity_node_set_processing_time(processingTime: DistributionType): void;
  fn_graph_activity_node_get_processing_time_parameter(): string | null;
  fn_graph_activity_node_set_processing_time_parameter(processingTimeParameter: string): void;
  fn_graph_activity_node_get_variable(): VARIABLE_TYPE | null;
  fn_graph_activity_node_set_variable(variable: VARIABLE_TYPE): void;
  fn_graph_activity_node_is_report_scrap(): boolean | null;
  fn_graph_activity_node_set_report_scrap(reportScrap: boolean): void;
  fn_graph_activity_node_get_queue(): GraphDataQueue | null;
  fn_graph_activity_node_set_queue(queue: GraphDataQueue): void;
  fn_graph_activity_node_get_function(): GraphDataFunction | null;
  fn_graph_activity_node_set_function(func: GraphDataFunction): void;
  fn_graph_activity_node_get_unit(): number | null;
  fn_graph_activity_node_set_unit(unit: number): void;
  fn_graph_activity_node_get_cost(): number | null;
  fn_graph_activity_node_set_cost(cost: number): void;
}
