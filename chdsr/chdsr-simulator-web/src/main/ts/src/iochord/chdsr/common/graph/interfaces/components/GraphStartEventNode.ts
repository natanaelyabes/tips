import { GraphDataGenerator } from './GraphDataGenerator';
import { GraphEventNode } from './GraphEventNode';

export interface GraphStartEventNode extends GraphEventNode {
  readonly TYPE: string | 'start';
  fn_graph_start_event_node_get_generator(): GraphDataGenerator | undefined;
  fn_graph_start_event_node_set_generator(generator: GraphDataGenerator): void;
}
