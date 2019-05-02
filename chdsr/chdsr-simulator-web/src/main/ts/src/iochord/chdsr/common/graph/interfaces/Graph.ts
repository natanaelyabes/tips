import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphData } from './GraphData';
import { GraphControl } from './components/GraphControl';

export interface Graph extends GraphElement {
  readonly TYPE: string | 'net';
  fn_graph_get_version(): string;
  fn_graph_get_pages(): Map<string, GraphPage>;
  fn_graph_set_pages(): void;

  fn_graph_get_configurations(): Map<string, GraphConfiguration>;
  fn_graph_get_control(): GraphControl;
  fn_graph_get_data(): Map<string, GraphData>;
}
