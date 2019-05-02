import { GraphNode } from '../GraphNode';
export interface GraphMonitor extends GraphNode {
  readonly TYPE: string | 'monitor';
}
