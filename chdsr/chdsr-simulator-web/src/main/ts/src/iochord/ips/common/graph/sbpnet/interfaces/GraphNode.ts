import { GraphElement } from './GraphElement';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphNode extends GraphElement {
  getGroupName(): string | null;
  setGroupName(groupName: string): void;
  isReportStatistics(): boolean | null;
  setReportStatistics(reportStatistics: boolean): void;
  accept(elements: GraphElement[]): boolean | null;
  getInputTypes(): GraphElement[] | null;
  setInputTypes(inputTypes: GraphElement[]): void;
  getOutputTypes(): GraphElement[] | null;
  setOutputTypes(outputTypes: GraphElement[]): void;
  getInputNodes(): GraphNode[] | null;
  setInputNodes(inputNodes: GraphNode[]): void;
  getOutputNodes(): GraphNode[] | null;
  setOutputNodes(outputNodes: GraphNode[]): void;
}
