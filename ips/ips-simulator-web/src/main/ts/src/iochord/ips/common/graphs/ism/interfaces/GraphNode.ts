import { GraphElement } from './GraphElement';
import { GraphRule } from './GraphRule';

/**
 * The interface of graph node.
 *
 * @export
 * @interface GraphNode
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export interface GraphNode extends GraphElement, GraphRule {

  /**
   * Returns the group name of the graph node.
   *
   * @returns {(string | null)}
   * @memberof GraphNode
   */
  getGroupName(): string | null;

  /**
   * Assigns group name of the graph node.
   *
   * @param {string} groupName
   * @memberof GraphNode
   */
  setGroupName(groupName: string): void;

  /**
   * Boolean function to assert whether statistics is being reported. False otherwise.
   *
   * @returns {(boolean | null)}
   * @memberof GraphNode
   */
  isReportStatistics(): boolean | null;

  /**
   * Boolean function to assign whether statistics is being reported. False otherwise.
   *
   * @param {boolean} reportStatistics
   * @memberof GraphNode
   */
  setReportStatistics(reportStatistics: boolean): void;

  /**
   * Accept
   *
   * @param {GraphElement[]} elements
   * @returns {(boolean | null)}
   * @memberof GraphNode
   */
  accept(elements: GraphElement[]): boolean | null;

  /**
   * Returns the string references of input types of current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  getInputTypesRef(): string[] | null;

  /**
   * Assigns the string references of input types for the current node.
   *
   * @param {string[]} inputTypesRef
   * @memberof GraphNodeImpl
   */
  setInputTypesRef(inputTypes: string[]): void;

  /**
   * Returns the string references of output types for the current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  getOutputTypesRef(): string[] | null;

  /**
   * Assigns the string references of output types for the current node.
   *
   * @param {string[]} outputTypesRef
   * @memberof GraphNodeImpl
   */
  setOutputTypesRef(outputTypes: string[]): void;

  /**
   * Returns the string references of input nodes for the current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  getInputNodesRef(): string[] | null;

  /**
   * Assigns the string references of input nodes for the current node.
   *
   * @param {string[]} inputNodesRef
   * @memberof GraphNodeImpl
   */
  setInputNodesRef(inputNodes: string[]): void;

  /**
   * Returns the string references of output nodes for the current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  getOutputNodesRef(): string[] | null;

  /**
   * Assigns the string references of output nodes for the current node.
   *
   * @param {string[]} outputNodesRef
   * @memberof GraphNodeImpl
   */
  setOutputNodesRef(outputNodes: string[]): void;
}
