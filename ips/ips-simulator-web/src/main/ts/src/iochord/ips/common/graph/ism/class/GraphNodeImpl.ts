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
  private inputTypesRef?: string[] = new Array<string>();
  private outputTypesRef?: string[] = new Array<string>();
  private inputNodesRef: string[] = new Array<string>();
  private outputNodesRef: string[] = new Array<string>();

  constructor() {
    super();
  }

  public getGroupName(): string | null {
    return this.groupName as string | null;
  }

  public setGroupName(groupName: string): void {
    this.groupName = groupName as string;
  }

  public isReportStatistics(): boolean | null {
    return this.reportStatistics as boolean | null;
  }

  public setReportStatistics(reportStatistics: boolean): void {
    this.reportStatistics = reportStatistics as boolean;
  }

  public accept(elements: GraphElement[]): boolean | null {
    return false;
  }

  public getInputTypesRef(): string[] | null {
    return this.inputTypesRef as string[];
  }

  public setInputTypesRef(inputTypesRef: string[]): void {
    this.inputTypesRef = inputTypesRef as string[];
  }

  public getOutputTypesRef(): string[] | null {
    return this.outputTypesRef as string[];
  }

  public setOutputTypesRef(outputTypesRef: string[]): void {
    this.outputTypesRef = outputTypesRef as string[];
  }

  public getInputNodesRef(): string[] | null {
    return this.inputNodesRef as string[];
  }

  public setInputNodesRef(inputNodesRef: string[]): void {
    this.inputNodesRef = inputNodesRef as string[];
  }

  public getOutputNodesRef(): string[] | null {
    return this.outputNodesRef as string[];
  }

  public setOutputNodesRef(outputNodesRef: string[]): void {
    this.outputNodesRef = outputNodesRef as string[];
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
