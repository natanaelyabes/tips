import { GraphControl } from './../interfaces/components/GraphControl';
import { GraphConfiguration } from './../interfaces/GraphConfiguration';
import { GraphPage } from '../interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { Graph } from '../interfaces/Graph';
import { GraphData } from '../interfaces/GraphData';

export class GraphImpl extends GraphElementImpl implements Graph {
  public readonly TYPE: string | 'net' = 'net';

  private readonly version: string = '1.0';
  private pages?: Map<string, GraphPage> = new Map<string, GraphPage>();
  private configurations?: Map<string, GraphConfiguration> = new Map<string, GraphConfiguration>();
  private control?: GraphControl;
  private data?: Map<string, GraphData> = new Map<string, GraphData>();

  constructor();
  constructor(pages: Map<string, GraphPage>, configurations: Map<string, GraphConfiguration>, control: GraphControl, data: Map<string, GraphData>);
  constructor(pages?: Map<string, GraphPage>, configurations?: Map<string, GraphConfiguration>, control?: GraphControl, data?: Map<string, GraphData>) {
    super();
    this.pages = pages;
    this.configurations = configurations;
    this.control = control;
    this.data = data;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_get_version(): string {
    return this.version;
  }

  public fn_graph_get_pages(): Map<string, GraphPage> | undefined {
    return this.pages;
  }

  public fn_graph_set_pages(pages: Map<string, GraphPage>): void {
    this.pages = pages;
  }

  public fn_graph_get_configurations(): Map<string, GraphConfiguration> | undefined {
    return this.configurations;
  }

  public fn_graph_set_configurations(configurations: Map<string, GraphConfiguration>): void {
    this.configurations = configurations;
  }

  public fn_graph_get_control(): GraphControl | undefined {
    return this.control;
  }

  public fn_graph_set_control(control: GraphControl): void {
    this.control = control;
  }

  public fn_graph_get_data(): Map<string, GraphData> | undefined {
    return this.data;
  }

  public fn_graph_set_data(data: Map<string, GraphData>): void {
    this.data = data;
  }
}
