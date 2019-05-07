import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphStopEventNode } from '../../interfaces/components/GraphStopEventNode';

export class GraphStopEventNodeImpl extends GraphEventNodeImpl implements GraphStopEventNode {
  public static readonly TYPE: 'stop' = 'stop';

  public static fn_object_deserialize(object: any): GraphStopEventNode {
    const graphStopEventNode: GraphStopEventNode = new GraphStopEventNodeImpl();
    graphStopEventNode.fn_graph_element_set_id(object.id);
    graphStopEventNode.fn_graph_element_set_label(object.label);
    graphStopEventNode.fn_graph_element_set_type(object.elementType);
    graphStopEventNode.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    graphStopEventNode.fn_graph_node_set_group_name(object.groupName);
    graphStopEventNode.fn_graph_node_set_report_statistics(object.reportStatistics);
    return graphStopEventNode;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
