import { GraphStartEventNode } from './../../interfaces/components/GraphStartEventNode';
import { GraphDataImpl } from './../GraphDataImpl';
import { GraphUtil } from './../GraphUtil';
import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { GraphDataGeneratorImpl } from './GraphDataGeneratorImpl';

export class GraphStartEventNodeImpl extends GraphEventNodeImpl implements GraphStartEventNode {
  public static instance: Map<string, GraphStartEventNode> = new Map<string, GraphStartEventNode>();

  public static deserialize(object: any): GraphStartEventNode | null {
    const graphStartEventNode: GraphStartEventNode = new GraphStartEventNodeImpl();
    graphStartEventNode.setId(object.id);
    graphStartEventNode.setLabel(object.label);
    graphStartEventNode.setType(object.elementType);
    graphStartEventNode.setAttributes(object.attributes as Map<string, string>);
    graphStartEventNode.setGroupName(object.groupName);
    graphStartEventNode.setReportStatistics(object.reportStatistics);
    graphStartEventNode.setGenerator(GraphDataGeneratorImpl.instance.get(object.generatorRef) as GraphDataGenerator);
    GraphStartEventNodeImpl.instance.set(graphStartEventNode.getId() as string, graphStartEventNode);
    return graphStartEventNode;
  }

  private generator?: GraphDataGenerator | null;

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
    return GraphUtil.generateRef(this.getGenerator());
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}