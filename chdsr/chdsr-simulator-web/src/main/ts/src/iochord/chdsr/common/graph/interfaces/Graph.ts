import { GraphElement } from './GraphElement';
import { GraphPage } from './pages/GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphDataNode } from './base/nodes/GraphDataNode';
import { GraphControl } from './GraphControl';

export interface Graph extends GraphElement {
  readonly TYPE: string | 'net';
  fn_graph_get_version(): string;
  fn_graph_get_pages(): Map<string, GraphPage>;
  fn_graph_get_configurations(): Map<string, GraphConfiguration>;
  fn_graph_get_control(): GraphControl;
  fn_graph_get_data(): Map<string, GraphDataNode>;
}
