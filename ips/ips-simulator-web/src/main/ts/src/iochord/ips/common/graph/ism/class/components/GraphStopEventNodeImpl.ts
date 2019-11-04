import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphStopEventNode } from '../../interfaces/components/GraphStopEventNode';
import { TSMap } from 'typescript-map';
import { GraphConnectorImpl } from '../GraphConnectorImpl';
import { GraphConnector } from '../../interfaces/GraphConnector';

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
    graphStopEventNode.setAttributes(object.attributes as TSMap<string, string>);
    graphStopEventNode.setGroupName(object.groupName);
    graphStopEventNode.setReportStatistics(object.reportStatistics);
    GraphStopEventNodeImpl.instance.set(graphStopEventNode.getId() as string, graphStopEventNode);
    return graphStopEventNode;
  }

  /** @Override */
  public validateInputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its input nodes
    const inputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getTargetRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const inputNodesMoreThanOne = inputNodes.length >= 1 ? true : false;

    // Assert rule
    if (inputNodesMoreThanOne) {
      return new Error('Stop node should not have more than one input nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /** @Override */
  public validateOutputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its output nodes
    const outputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getSourceRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const outputNodesMoreThanZero = outputNodes.length >= 0 ? true : false;

    // Assert rule
    if (outputNodesMoreThanZero) {
      return new Error('Stop node should not have any output nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
