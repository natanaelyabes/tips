import { GraphStartEventNode } from '../../interfaces/components/GraphStartEventNode';
import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { TSMap } from 'typescript-map';
import { GraphConnectorImpl } from '../GraphConnectorImpl';
import { GraphConnector } from '../../interfaces/GraphConnector';

/**
 * Implementation of GraphStartEventNodeImpl interface.
 *
 * @export
 * @class GraphStartEventNodeImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphStartEventNodeImpl}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphStartEventNodeImpl extends GraphEventNodeImpl implements GraphStartEventNode {

  /**
   * Field to identify the type of node.
   *
   * @static
   * @type {string}
   * @memberof GraphStartEventNodeImpl
   */
  public static TYPE: string = 'start';

  /**
   * Deserialize JSON object as GraphStartEventNodeImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphStartEventNode | null)}
   * @memberof GraphStartEventNodeImpl
   */
  public static deserialize(object: any): GraphStartEventNode | null {
    const graphStartEventNode: GraphStartEventNode = new GraphStartEventNodeImpl();
    graphStartEventNode.setId(object.id);
    graphStartEventNode.setLabel(object.label);
    graphStartEventNode.setType(object.elementType);
    graphStartEventNode.setAttributes(object.attributes as TSMap<string, string>);
    graphStartEventNode.setGroupName(object.groupName);
    graphStartEventNode.setReportStatistics(object.reportStatistics);
    graphStartEventNode.setGeneratorRef(object.generatorRef);
    GraphStartEventNodeImpl.instance.set(graphStartEventNode.getId() as string, graphStartEventNode);
    return graphStartEventNode;
  }

  /**
   * Generator data node assigned to the current start event node.
   *
   * @private
   * @type {(GraphDataGenerator | null)}
   * @memberof GraphStartEventNodeImpl
   */
  private generator?: GraphDataGenerator | null;

  /**
   * Generator data node assigned to the current start event node as string reference.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphStartEventNodeImpl
   */
  private generatorRef?: string | null;

  /**
   * Creates an instance of GraphStartEventNodeImpl.
   *
   * @memberof GraphStartEventNodeImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the generator assigned to the current start event node.
   *
   * @returns {(GraphDataGenerator | null)}
   * @memberof GraphStartEventNodeImpl
   */
  public getGenerator(): GraphDataGenerator | null {
    return this.generator as GraphDataGenerator | null;
  }

  /**
   * Assigns generator data node to the current start event node.
   *
   * @param {GraphDataGenerator} generator
   * @memberof GraphStartEventNodeImpl
   */
  public setGenerator(generator: GraphDataGenerator): void {
    this.generator = generator;
  }

  /**
   * Returns the generator assigned to the current start event as string reference.
   *
   * @returns {(string | null)}
   * @memberof GraphStartEventNodeImpl
   */
  public getGeneratorRef(): string | null {
    return this.generatorRef as string;
  }

  /**
   * Assigns the generator to the current start event node as string reference.
   *
   * @param {string} generator
   * @memberof GraphStartEventNodeImpl
   */
  public setGeneratorRef(generator: string): void {
    this.generatorRef = generator;
  }

  /**
   * To attest that the input nodes have adhered the simulation model rule.
   *
   * @override
   * @returns {(Error | null)}
   * @memberof GraphStartEventNodeImpl
   */
  public validateInputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its input nodes
    const inputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getTargetRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const inputNodesMoreThanZero = inputNodes.length >= 0 ? true : false;

    // Assert rule
    if (inputNodesMoreThanZero) {
      return new Error('Start node should not have any input nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /**
   * To attest that the output nodes have adhered the simulation model rule.
   *
   * @override
   * @returns {(Error | null)}
   * @memberof GraphActivityNodeImpl
   */
  public validateOutputNodes(): Error | null {

    // Get all connectors
    const connectors = GraphConnectorImpl.instance;

    // Get its output nodes
    const outputNodes = connectors.values()
      .filter((connector: GraphConnector) => connector.getSourceRef() === this.getId())
      .map((connector: GraphConnector) => connector.getTargetRef());

    // Set rule condition
    const outputNodesMoreThanOne = outputNodes.length >= 1 ? true : false;

    // Assert rule
    if (outputNodesMoreThanOne) {
      return new Error('Start node should not have more than one output nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /**
   * Serialize GraphStartEventNodeImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphStartEventNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
