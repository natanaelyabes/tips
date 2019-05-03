import { QUEUE_TYPE } from '../../enums/QUEUE';
import { GraphData } from '../GraphData';

export interface GraphDataQueue extends GraphData {
  readonly TYPE: string | 'queue';
  fn_graph_data_queue_get_type(): QUEUE_TYPE | undefined;
  fn_graph_data_queue_set_type(type: QUEUE_TYPE): void;
  fn_graph_data_queue_get_size(): number | undefined;
  fn_graph_data_queue_set_size(size: number): void;
  fn_graph_data_queue_is_shared(): boolean | undefined;
  fn_graph_data_queue_set_shared(shared: boolean): void;
}
