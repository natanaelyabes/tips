import { ACTIVITY_TYPE } from './../../enums/ACTIVITY';
import { GraphDataFunction } from './GraphDataFunction';
import { GraphDataQueue } from './GraphDataQueue';
import { GraphDataResource } from './GraphDataResource';
import { GraphNode } from '../GraphNode';

export interface GraphActivityNode extends GraphNode {
  readonly TYPE: string | 'activity';
  fn_graph_activity_node_getType(): ACTIVITY_TYPE;
  fn_graph_activity_node_get_resource(): GraphDataResource;
  fn_graph_activity_node_get_queue(): GraphDataQueue;
  fn_graph_activity_node_get_function(): GraphDataFunction;
  fn_graph_activity_node_get_setup_time_expression(): string;
  fn_graph_activity_node_get_processing_time_expression(): string;
  fn_graph_activity_node_get_unit(): number;
}
