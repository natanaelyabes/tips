import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphStopEventNode } from '../../interfaces/components/GraphStopEventNode';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphStopEventNodeImpl extends GraphEventNodeImpl implements GraphStopEventNode {
  public static TYPE: string = 'stop';

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
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}