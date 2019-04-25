import { GraphElementInterface } from './GraphElementInterface';
import { GraphPageInterface } from './pages/GraphPageInterface';
import { GraphConfigurationInterface } from './GraphConfigurationInterface';
import { GraphDataNodeInterface } from './base/nodes/GraphDataNodeInterface';

export interface GraphInterface extends GraphElementInterface {
  fn_graph_get_version(): string;
  fn_graph_get_pages(): Map<string, GraphPageInterface>;
  fn_graph_get_configurations(): Map<string, GraphConfigurationInterface>;
  fn_graph_get_data(): Map<string, GraphDataNodeInterface>;
}
