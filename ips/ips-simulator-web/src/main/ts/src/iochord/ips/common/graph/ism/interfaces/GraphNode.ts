import { GraphElement } from './GraphElement';
import { GraphRule } from './GraphRule';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphNode extends GraphElement, GraphRule {
  getGroupName(): string | null;
  setGroupName(groupName: string): void;
  isReportStatistics(): boolean | null;
  setReportStatistics(reportStatistics: boolean): void;
  accept(elements: GraphElement[]): boolean | null;
  getInputTypesRef(): string[] | null;
  setInputTypesRef(inputTypes: string[]): void;
  getOutputTypesRef(): string[] | null;
  setOutputTypesRef(outputTypes: string[]): void;
  getInputNodesRef(): string[] | null;
  setInputNodesRef(inputNodes: string[]): void;
  getOutputNodesRef(): string[] | null;
  setOutputNodesRef(outputNodes: string[]): void;
}
