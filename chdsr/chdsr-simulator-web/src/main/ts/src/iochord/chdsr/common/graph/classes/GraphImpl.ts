import { GraphControl } from './../interfaces/components/GraphControl';
import { GraphConfiguration } from './../interfaces/GraphConfiguration';
import { GraphPage } from '../interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { Graph } from '../interfaces/Graph';
import { GraphData } from '../interfaces/GraphData';
import { GraphPageImpl } from './GraphPageImpl';
import { GraphConfigurationImpl } from './GraphConfigurationImpl';
import { GraphControlImpl } from './components/GraphControlImpl';
import { GraphDataImpl } from './GraphDataImpl';

export class GraphImpl extends GraphElementImpl implements Graph {
  public static fn_object_deserialize(object: any): Graph {
    const graph: Graph = new GraphImpl();
    graph.fn_graph_element_set_id(object.id);
    graph.fn_graph_element_set_label(object.label || '');
    graph.fn_graph_element_set_type(object.elementType);
    graph.fn_graph_element_set_attributes(object.attributes || {});
    graph.fn_graph_set_pages(GraphPageImpl.fn_object_deserialize(object.pages));
    graph.fn_graph_set_configurations(GraphConfigurationImpl.fn_object_deserialize(object.configurations));
    graph.fn_graph_set_control(GraphControlImpl.fn_object_deserialize(object.control) as GraphControl);
    graph.fn_graph_set_data(GraphDataImpl.fn_object_deserialize(object.data));
    return graph;
  }

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

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
