import { GraphDataGenerator } from './GraphDataGenerator';
import { GraphEventNode } from './GraphEventNode';

export interface GraphStartEventNode extends GraphEventNode {
  getGenerator(): GraphDataGenerator | null;
  setGenerator(generator: GraphDataGenerator): void;
  getGeneratorRef(): string | null;
}
