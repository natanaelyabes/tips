import { GraphControl } from './../interfaces/components/GraphControl';
import { GraphConfiguration } from './../interfaces/GraphConfiguration';
import { GraphPage } from '../interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { Graph } from '../interfaces/Graph';
import { GraphData } from '../interfaces/GraphData';

export class GraphImpl extends GraphElementImpl implements Graph {
  public readonly TYPE: string | 'net' = 'net';

  private readonly version: string = '1.0';
  private pages: Map<string, GraphPage> = new Map<string, GraphPage>();
  private configurations: Map<string, GraphConfiguration> = new Map<string, GraphConfiguration>();
  private control?: GraphControl;
  private data?: Map<string, GraphData> = new Map<string, GraphData>();

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  fn_graph_get_version(): string {
    throw new Error('Method not implemented.');
  }

  fn_graph_get_pages(): Map<string, GraphPage> {
    throw new Error('Method not implemented.');
  }

  fn_graph_get_configurations(): Map<string, GraphConfiguration> {
    throw new Error('Method not implemented.');
  }

  fn_graph_get_control(): GraphControl {
    throw new Error('Method not implemented.');
  }

  fn_graph_get_data(): Map<string, GraphData> {
    throw new Error('Method not implemented.');
  }
}
