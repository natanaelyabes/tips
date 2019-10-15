import { GraphDataGenerator } from './GraphDataGenerator';
import { GraphEventNode } from './GraphEventNode';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphStartEventNode extends GraphEventNode {
  getGenerator(): GraphDataGenerator | null;
  setGenerator(generator: GraphDataGenerator): void;
  setGeneratorRef(generator: string): void;
  getGeneratorRef(): string | null;
}
