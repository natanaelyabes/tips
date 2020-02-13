import { GraphNode } from '../interfaces/GraphNode';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphNode interface.
 *
 * @export
 * @class GraphFactoryImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphFactory}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphNodeImpl extends GraphElementImpl implements GraphNode {

  /**
   * Instances of GraphNode.
   *
   * @static
   * @type {TSMap<string, GraphNode>}
   * @memberof GraphNodeImpl
   */
  public static instance: TSMap<string, GraphNode> = new TSMap<string, GraphNode>();

  /**
   * The group name of current graph node.
   *
   * @private
   * @type {(string | null)}
   * @memberof GraphNodeImpl
   */
  private groupName?: string | null;

  /**
   * Boolean variable to control the report statistics behavior of current node element.
   *
   * @private
   * @type {(boolean | null)}
   * @memberof GraphNodeImpl
   */
  private reportStatistics?: boolean | null = false;

  /**
   * Input types of current node element as string reference.
   *
   * @private
   * @type {string[]}
   * @memberof GraphNodeImpl
   */
  private inputTypesRef?: string[] = new Array<string>();

  /**
   * Output types of current node element as string reference.
   *
   * @private
   * @type {string[]}
   * @memberof GraphNodeImpl
   */
  private outputTypesRef?: string[] = new Array<string>();

  /**
   * The input nodes of current node object, as string reference.
   *
   * @private
   * @type {string[]}
   * @memberof GraphNodeImpl
   */
  private inputNodesRef: string[] = new Array<string>();

  /**
   * The output nodes of current node object, as string reference.
   *
   * @private
   * @type {string[]}
   * @memberof GraphNodeImpl
   */
  private outputNodesRef: string[] = new Array<string>();

  /**
   * Creates an instance of GraphNodeImpl.
   *
   * @memberof GraphNodeImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the group name of current node.
   *
   * @returns {(string | null)}
   * @memberof GraphNodeImpl
   */
  public getGroupName(): string | null {
    return this.groupName as string | null;
  }

  /**
   * Assigns a group name to the current node.
   *
   * @param {string} groupName
   * @memberof GraphNodeImpl
   */
  public setGroupName(groupName: string): void {
    this.groupName = groupName as string;
  }

  /**
   * Returns the boolean value of whether a statistics is being reported. False otherwise.
   *
   * @returns {(boolean | null)}
   * @memberof GraphNodeImpl
   */
  public isReportStatistics(): boolean | null {
    return this.reportStatistics as boolean | null;
  }

  /**
   * Boolean function to determine whether a statistics is being reported.
   *
   * @param {boolean} reportStatistics
   * @memberof GraphNodeImpl
   */
  public setReportStatistics(reportStatistics: boolean): void {
    this.reportStatistics = reportStatistics as boolean;
  }

  /**
   *
   *
   * @param {GraphElement[]} elements
   * @returns {(boolean | null)}
   * @memberof GraphNodeImpl
   */
  public accept(elements: GraphElement[]): boolean | null {
    return false;
  }

  /**
   * Returns the string references of input types of current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  public getInputTypesRef(): string[] | null {
    return this.inputTypesRef as string[];
  }

  /**
   * Assigns the string references of input types for the current node.
   *
   * @param {string[]} inputTypesRef
   * @memberof GraphNodeImpl
   */
  public setInputTypesRef(inputTypesRef: string[]): void {
    this.inputTypesRef = inputTypesRef as string[];
  }

  /**
   * Returns the string references of output types for the current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  public getOutputTypesRef(): string[] | null {
    return this.outputTypesRef as string[];
  }

  /**
   * Assigns the string references of output types for the current node.
   *
   * @param {string[]} outputTypesRef
   * @memberof GraphNodeImpl
   */
  public setOutputTypesRef(outputTypesRef: string[]): void {
    this.outputTypesRef = outputTypesRef as string[];
  }

  /**
   * Returns the string references of input nodes for the current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  public getInputNodesRef(): string[] | null {
    return this.inputNodesRef as string[];
  }

  /**
   * Assigns the string references of input nodes for the current node.
   *
   * @param {string[]} inputNodesRef
   * @memberof GraphNodeImpl
   */
  public setInputNodesRef(inputNodesRef: string[]): void {
    this.inputNodesRef = inputNodesRef as string[];
  }

  /**
   * Returns the string references of output nodes for the current node.
   *
   * @returns {(string[] | null)}
   * @memberof GraphNodeImpl
   */
  public getOutputNodesRef(): string[] | null {
    return this.outputNodesRef as string[];
  }

  /**
   * Assigns the string references of output nodes for the current node.
   *
   * @param {string[]} outputNodesRef
   * @memberof GraphNodeImpl
   */
  public setOutputNodesRef(outputNodesRef: string[]): void {
    this.outputNodesRef = outputNodesRef as string[];
  }

  /**
   * To attest that the input nodes have adhered the simulation model rule.
   *
   * @returns {(Error | null)}
   * @memberof GraphNodeImpl
   */
  public validateInputNodes(): Error | null {
    return null;
  }

  /**
   * To attest that the output nodes have adhered the simulation model rule.
   *
   * @returns {(Error | null)}
   * @memberof GraphNodeImpl
   */
  public validateOutputNodes(): Error | null {
    return null;
  }

  /**
   * Serialize GraphNodeImpl object as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphNodeImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
