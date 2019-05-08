import { GraphNode } from '../interfaces/GraphNode';
import { GraphElementImpl } from './GraphElementImpl';
import { GraphElement } from '../interfaces/GraphElement';

export class GraphNodeImpl extends GraphElementImpl implements GraphNode {
  public readonly TYPE: string | 'node' = 'node';

  private groupName: string | null;
  private reportStatistics: boolean = false;

  constructor();
  constructor(groupName: string, reportStatistics: boolean);
  constructor(groupName?: string, reportStatistics: boolean = false) {
    super();
    this.groupName = groupName || null;
    this.reportStatistics = reportStatistics || false;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_node_get_group_name(): string | null {
    return this.groupName;
  }

  public fn_graph_node_set_group_name(groupName: string): void {
    this.groupName = groupName;
  }

  public fn_graph_node_is_report_statistics(): boolean {
    return this.reportStatistics;
  }

  public fn_graph_node_set_report_statistics(reportStatistics: boolean): void {
    this.reportStatistics = reportStatistics;
  }

  public fn_graph_node_accept(elements: GraphElement[]): boolean {
    return false;
  }

  public fn_graph_node_set_input_types(inputTypes: GraphElement[]): void {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_get_input_types(): GraphElement[] {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_set_output_types(outputTypes: GraphElement[]): void {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_get_output_types(): GraphElement[] {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_set_input_nodes(inputNodes: GraphNode[]): void {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_get_input_nodes(): GraphNode[] {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_set_output_nodes(outputNodes: GraphNode[]): void {
    throw new Error('Method not implemented.');
  }

  public fn_graph_node_get_output_nodes(): GraphNode[] {
    throw new Error('Method not implemented.');
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
