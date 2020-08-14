import { GraphDataGenerator } from './GraphDataGenerator';
import { GraphEventNode } from './GraphEventNode';

/**
 * The interface of graph start event node.
 *
 * @export
 * @interface GraphStartEventNode
 * @extends {GraphEventNode}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphStartEventNode extends GraphEventNode {

  /**
   * Returns the generator assigned to the current start event node.
   *
   * @returns {(GraphDataGenerator | null)}
   * @memberof GraphStartEventNodeImpl
   */
  getGenerator(): GraphDataGenerator | null;

  /**
   * Assigns generator data node to the current start event node.
   *
   * @param {GraphDataGenerator} generator
   * @memberof GraphStartEventNodeImpl
   */
  setGenerator(generator: GraphDataGenerator): void;

  /**
   * Returns the generator assigned to the current start event as string reference.
   *
   * @returns {(string | null)}
   * @memberof GraphStartEventNodeImpl
   */
  getGeneratorRef(): string | null;

  /**
   * Assigns the generator to the current start event node as string reference.
   *
   * @param {string} generator
   * @memberof GraphStartEventNodeImpl
   */
  setGeneratorRef(generator: string): void;
}
