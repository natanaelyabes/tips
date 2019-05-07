import { GraphEventNode } from './GraphEventNode';

export interface GraphStopEventNode extends GraphEventNode {
  readonly TYPE: string | 'stop';
}
