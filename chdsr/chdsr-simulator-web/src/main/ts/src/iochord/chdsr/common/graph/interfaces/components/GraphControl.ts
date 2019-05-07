import { GraphConfiguration } from '../GraphConfiguration';

export interface GraphControl extends GraphConfiguration {
  readonly TYPE: string | 'control';
}
