import { ACTIVITY_TYPE } from './../../enums/ACTIVITY';
import { GraphDataFunction } from './GraphDataFunction';
import { GraphDataQueue } from './GraphDataQueue';
import { GraphDataResource } from './GraphDataResource';
import { GraphNode } from '../GraphNode';

export interface GraphActivityNode extends GraphNode {
  readonly TYPE: string | 'activity';
  fn_graph_activity_node_get_type(): ACTIVITY_TYPE | undefined;
  fn_graph_activity_node_set_type(type: ACTIVITY_TYPE): void;
  fn_graph_activity_node_get_resource(): GraphDataResource | undefined;
  fn_graph_activity_node_set_resource(resource: GraphDataResource): void;
  fn_graph_activity_node_get_queue(): GraphDataQueue | undefined;
  fn_graph_activity_node_set_queue(queue: GraphDataQueue): void;
  fn_graph_activity_node_get_function(): GraphDataFunction | undefined;
  fn_graph_activity_node_set_function(func: GraphDataFunction): void;
  fn_graph_activity_node_get_setup_time_expression(): string | undefined;
  fn_graph_activity_node_set_setup_time_expression(setupTimeExpression: string): void;
  fn_graph_activity_node_get_processing_time_expression(): string | undefined;
  fn_graph_activity_node_set_processing_time_expression(processingTimeExpression: string): void;
  fn_graph_activity_node_get_unit(): number | undefined;
  fn_graph_activity_node_set_unit(unit: number): void;
}
