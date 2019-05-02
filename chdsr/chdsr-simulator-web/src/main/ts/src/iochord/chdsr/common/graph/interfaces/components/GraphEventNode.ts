import { GraphNode } from '../GraphNode';

export interface GraphEventNode extends GraphNode {
  readonly TYPE: string | 'event';
}
