import { GraphStartEventNode } from '../../interfaces/components/GraphStartEventNode';
import { GraphEventNodeImpl } from './GraphEventNodeImpl';
import { GraphDataGenerator } from '../../interfaces/components/GraphDataGenerator';
import { TSMap } from 'typescript-map';

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
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
