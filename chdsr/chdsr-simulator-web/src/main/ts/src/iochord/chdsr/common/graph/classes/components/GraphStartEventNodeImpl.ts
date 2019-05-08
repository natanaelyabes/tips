import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphStartEventNode } from '../../interfaces/components/GraphStartEventNode';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { GraphDataGeneratorImpl } from './GraphDataGeneratorImpl';

export class GraphStartEventNodeImpl extends GraphEventNodeImpl implements GraphStartEventNode {
  public static readonly TYPE: 'start' = 'start';

  public static fn_object_deserialize(object: any): GraphStartEventNode {
    const graphStartEventNode: GraphStartEventNode = new GraphStartEventNodeImpl();
    graphStartEventNode.fn_graph_element_set_id(object.id);
    graphStartEventNode.fn_graph_element_set_label(object.label);
    graphStartEventNode.fn_graph_element_set_type(object.elementType);
    graphStartEventNode.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    graphStartEventNode.fn_graph_node_set_group_name(object.groupName);
    graphStartEventNode.fn_graph_node_set_report_statistics(object.reportStatistics);
    graphStartEventNode.fn_graph_start_event_node_set_generator(GraphDataGeneratorImpl.fn_object_deserialize(object.generator));
    return graphStartEventNode;
  }

  private generator: GraphDataGenerator | null;

  constructor(generator?: GraphDataGenerator) {
    super();
    this.generator = generator || null;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_start_event_node_get_generator(): GraphDataGenerator | null {
    return this.generator;
  }

  public fn_graph_start_event_node_set_generator(generator: GraphDataGenerator): void {
    this.generator = generator;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
