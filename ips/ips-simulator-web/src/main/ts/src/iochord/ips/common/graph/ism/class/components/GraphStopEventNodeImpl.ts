import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphStopEventNode } from '../../interfaces/components/GraphStopEventNode';
import { TSMap } from 'typescript-map';
import { GraphConnectorImpl } from '../GraphConnectorImpl';
import { GraphConnector } from '../../interfaces/GraphConnector';

/**
 * Implementation of GraphStopEventNode interface.
 *
 * @export
 * @class GraphStopEventNodeImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphStopEventNode}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export class GraphStopEventNodeImpl extends GraphEventNodeImpl implements GraphStopEventNode {

  /**
   * Field to identify the type of node.
   *
   * @static
   * @type {string}
   * @memberof GraphStopEventNodeImpl
   */
  public static TYPE: string = 'stop';

  /**
   * Deserialize JSON object to GraphStopEventNodeImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphStopEventNode | null)}
   * @memberof GraphStopEventNodeImpl
   */
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

  /**
   * To attest that the input nodes have adhered the simulation model rule.
   *
   * @returns {(Error | null)}
   * @memberof GraphStopEventNodeImpl
   */
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

  /**
   * To attest that the output nodes have adhered the simulation model rule.
   *
   * @returns {(Error | null)}
   * @memberof GraphStopEventNodeImpl
   */
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

  /**
   * Serialize GraphStopEventNodeImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphStopEventNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
