import { GraphStartEventNode } from '../../interfaces/components/GraphStartEventNode';
import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
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
export class GraphStartEventNodeImpl extends GraphEventNodeImpl implements GraphStartEventNode {
  public static TYPE: string = 'start';

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

  private generator?: GraphDataGenerator | null;
  private generatorRef?: string | null;

  constructor() {
    super();
  }

  public getGenerator(): GraphDataGenerator | null {
    return this.generator as GraphDataGenerator | null;
  }

  public setGenerator(generator: GraphDataGenerator): void {
    this.generator = generator;
  }

  public getGeneratorRef(): string | null {
    return this.generatorRef as string;
  }

  public setGeneratorRef(generator: string): void {
    this.generatorRef = generator;
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
    const inputNodesMoreThanZero = inputNodes.length >= 0 ? true : false;

    // Assert rule
    if (inputNodesMoreThanZero) {
      return new Error('Start node should not have any input nodes ');
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
    const outputNodesMoreThanOne = outputNodes.length >= 1 ? true : false;

    // Assert rule
    if (outputNodesMoreThanOne) {
      return new Error('Start node should not have more than one output nodes ');
    }

    // Otherwise return nothing
    return null;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
