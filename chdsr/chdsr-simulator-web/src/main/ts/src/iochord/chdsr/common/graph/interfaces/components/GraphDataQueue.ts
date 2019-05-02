import { QUEUE_TYPE } from '../../enums/QUEUE';
import { GraphData } from '../GraphData';

export interface GraphDataQueue extends GraphData {
  readonly TYPE: string | 'queue';
  fn_graph_data_queue_get_type(): QUEUE_TYPE;
  fn_graph_data_queue_get_size(): number;
  fn_graph_data_queue_is_shared(): boolean;
}
