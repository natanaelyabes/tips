import { GraphPage } from '@/iochord/chdsr/common/graph/interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphData } from '../interfaces/GraphData';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphConnector } from '../interfaces/GraphConnector';

export class GraphPageImpl extends GraphElementImpl implements GraphPage {
  public TYPE: string | 'page' = 'page';

  private data: Map<string, GraphData> = new Map<string, GraphData>();
  private nodes: Map<string, GraphNode> = new Map<string, GraphNode>();
  private arcs: Map<string, GraphConnector> = new Map<string, GraphConnector>();

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_page_get_data(): Map<string, GraphData> {
    return this.data;
  }

  public fn_graph_page_get_nodes(): Map<string, GraphNode> {
    return this.nodes;
  }

  public fn_graph_page_get_arcs(): Map<string, GraphConnector> {
    return this.arcs;
  }
}
