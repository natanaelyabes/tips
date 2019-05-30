import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphStopEventNode } from '../../interfaces/components/GraphStopEventNode';

export class GraphStopEventNodeImpl extends GraphEventNodeImpl implements GraphStopEventNode {
  public static readonly TYPE: string | null = 'stop';
  public static instance: Map<string, GraphStopEventNode> = new Map<string, GraphStopEventNode>();

  public static deserialize(object: any): GraphStopEventNode | null {
    const graphStopEventNode: GraphStopEventNode = new GraphStopEventNodeImpl();
    graphStopEventNode.setId(object.id);
    graphStopEventNode.setLabel(object.label);
    graphStopEventNode.setType(object.elementType);
    graphStopEventNode.setAttributes(object.attributes as Map<string, string>);
    graphStopEventNode.setGroupName(object.groupName);
    graphStopEventNode.setReportStatistics(object.reportStatistics);
    GraphStopEventNodeImpl.instance.set(graphStopEventNode.getId() as string, graphStopEventNode);
    return graphStopEventNode;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
