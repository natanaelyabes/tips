import { GraphElement } from '../GraphElement';
import { GraphNode } from '../base/nodes/GraphNode';
import { GraphConnector } from '../base/arcs/GraphConnector';
import { GraphDataNode } from '../base/nodes/GraphDataNode';

export interface GraphPage extends GraphElement {
  readonly TYPE: string | 'page';
  fn_graph_page_get_data(): Map<string, GraphDataNode>;
  fn_graph_page_get_nodes(): Map<string, GraphNode>;
  fn_graph_page_get_arcs(): Map<string, GraphConnector>;
}
