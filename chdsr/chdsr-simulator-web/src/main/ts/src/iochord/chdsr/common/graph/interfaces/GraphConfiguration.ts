import { GraphElement } from './GraphElement';

export interface GraphConfiguration extends GraphElement {
  readonly TYPE: string | 'configuration';
}
