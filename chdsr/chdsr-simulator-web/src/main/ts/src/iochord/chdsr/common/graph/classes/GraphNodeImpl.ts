import { GraphNode } from '../interfaces/GraphNode';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';

export class GraphNodeImpl extends GraphElementImpl implements GraphNode {
  public static readonly TYPE: string | null = 'node';

  private groupName: string | null;
  private reportStatistics: boolean | null = false;
  private inputTypes: GraphElement[] | null = new Array<GraphElement>();
  private outputTypes: GraphElement[] | null = new Array<GraphElement>();
  private inputNodes: GraphNode[] | null = new Array<GraphNode>();
  private outputNodes: GraphNode[] | null = new Array<GraphNode>();

  constructor();
  constructor(groupName: string, reportStatistics: boolean, inputTypes: GraphElement[], outputTypes: GraphElement[], inputNodes: GraphNode[], outputNodes: GraphNode[]);
  constructor(groupName?: string, reportStatistics: boolean = false, inputTypes?: GraphElement[], outputTypes?: GraphElement[], inputNodes?: GraphNode[], outputNodes?: GraphNode[]) {
    super();
    this.groupName = groupName || null;
    this.reportStatistics = reportStatistics || false;
    this.inputTypes = inputTypes || new Array<GraphElement>() || null;
    this.outputTypes = outputTypes || new Array<GraphElement>() || null;
    this.inputNodes = inputNodes || new Array<GraphNode>() || null;
    this.outputNodes = outputNodes || new Array<GraphNode>() || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getGroupName(): string | null {
    return this.groupName;
  }

  public setGroupName(groupName: string): void {
    this.groupName = groupName;
  }

  public isReportStatistics(): boolean | null {
    return this.reportStatistics;
  }

  public setReportStatistics(reportStatistics: boolean): void {
    this.reportStatistics = reportStatistics;
  }

  public accept(elements: GraphElement[]): boolean | null {
    return false;
  }

  public getInputTypes(): GraphElement[] | null {
    return null;
  }

  public setInputTypes(inputTypes: GraphElement[]): void {
    this.inputTypes = inputTypes;
  }

  public getOutputTypes(): GraphElement[] | null {
    return null;
  }

  public setOutputTypes(outputTypes: GraphElement[]): void {
    this.outputTypes = outputTypes;
  }

  public getInputNodes(): GraphNode[] | null {
    return null;
  }

  public setInputNodes(inputNodes: GraphNode[]): void {
    this.inputNodes = inputNodes;
  }

  public getOutputNodes(): GraphNode[] | null {
    return null;
  }

  public setOutputNodes(outputNodes: GraphNode[]): void {
    this.outputNodes = outputNodes;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
