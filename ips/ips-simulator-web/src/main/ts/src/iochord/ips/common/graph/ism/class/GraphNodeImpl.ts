import { GraphNode } from '../interfaces/GraphNode';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphNodeImpl extends GraphElementImpl implements GraphNode {
  public static instance: TSMap<string, GraphNode> = new TSMap<string, GraphNode>();

  private groupName?: string | null;
  private reportStatistics?: boolean | null = false;
  private inputTypes?: GraphElement[] | null;
  private outputTypes?: GraphElement[] | null;
  private inputNodes?: GraphNode[] | null;
  private outputNodes?: GraphNode[] | null;

  constructor() {
    super();
  }

  public getGroupName(): string | null {
    return this.groupName as string | null;
  }

  public setGroupName(groupName: string): void {
    this.groupName = groupName ? groupName : this.groupName;
  }

  public isReportStatistics(): boolean | null {
    return this.reportStatistics as boolean | null;
  }

  public setReportStatistics(reportStatistics: boolean): void {
    this.reportStatistics = reportStatistics !== undefined ? reportStatistics : this.reportStatistics;
  }

  public accept(elements: GraphElement[]): boolean | null {
    return false;
  }

  public getInputTypes(): GraphElement[] | null {
    return null;
  }

  public setInputTypes(inputTypes: GraphElement[]): void {
    this.inputTypes = inputTypes ? inputTypes : this.inputTypes;
  }

  public getOutputTypes(): GraphElement[] | null {
    return null;
  }

  public setOutputTypes(outputTypes: GraphElement[]): void {
    this.outputTypes = outputTypes ? outputTypes : this.outputNodes;
  }

  public getInputNodes(): GraphNode[] | null {
    return null;
  }

  public setInputNodes(inputNodes: GraphNode[]): void {
    this.inputNodes = inputNodes ? inputNodes : this.inputNodes;
  }

  public getOutputNodes(): GraphNode[] | null {
    return null;
  }

  public setOutputNodes(outputNodes: GraphNode[]): void {
    this.outputNodes = outputNodes ? outputNodes : this.outputNodes;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
