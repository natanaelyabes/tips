import { GraphElement } from './GraphElement';
import { GraphPage } from './GraphPage';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphData } from './GraphData';
import { GraphControl } from './components/GraphControl';

export interface Graph extends GraphElement {
  readonly TYPE: string | 'net';
  fn_graph_get_version(): string;
  fn_graph_get_pages(): Map<string, GraphPage> | undefined;
  fn_graph_set_pages(pages: Map<string, GraphPage>): void;
  fn_graph_get_configurations(): Map<string, GraphConfiguration> | undefined;
  fn_graph_set_configurations(configurations: Map<string, GraphConfiguration>): void;
  fn_graph_get_control(): GraphControl | undefined;
  fn_graph_set_control(control: GraphControl): void;
  fn_graph_get_data(): Map<string, GraphData> | undefined;
  fn_graph_set_data(data: Map<string, GraphData>): void;
}
