import { QUEUE_TYPE } from '../../enums/QUEUE';
import { GraphData } from '../GraphData';

export interface GraphDataQueue extends GraphData {
  // readonly TYPE: string | 'queue';
  fn_graph_data_queue_get_type(): QUEUE_TYPE | null;
  fn_graph_data_queue_set_type(type: QUEUE_TYPE): void;
  fn_graph_data_queue_get_size(): number | null;
  fn_graph_data_queue_set_size(size: number): void;
  fn_graph_data_queue_is_shared(): boolean | null;
  fn_graph_data_queue_set_shared(shared: boolean): void;
  fn_graph_data_queue_is_single(): boolean | null;
  fn_graph_data_queue_set_single(single: boolean): void;
  fn_graph_data_queue_get_sizes(): Map<string, number>;
  fn_graph_data_queue_set_sizes(sizes: Map<string, number>): void;
}
