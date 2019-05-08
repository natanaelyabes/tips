import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphConnector } from './GraphConnector';
import { GraphData } from './GraphData';

export interface GraphPage extends GraphElement {
  // readonly TYPE: string | 'page';
  fn_graph_page_get_data(): Map<string, GraphData>;
  fn_graph_page_set_data(data: Map<string, GraphData>): void;
  fn_graph_page_get_nodes(): Map<string, GraphNode>;
  fn_graph_page_set_nodes(nodes: Map<string, GraphNode>): void;
  fn_graph_page_get_arcs(): Map<string, GraphConnector>;
  fn_graph_page_set_arcs(arcs: Map<string, GraphConnector>): void;
}
