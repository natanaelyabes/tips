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
  public static readonly TYPE: string | null = 'net';

  public static deserialize(object: any): Graph | null {
    const graph: Graph = new GraphImpl();
    graph.setId(object.id);
    // graph.setLabel(object.label);
    graph.setType(object.elementType);
    graph.setAttributes(object.attributes as Map<string, string>);
    graph.setControl(GraphControlImpl.deserialize(object.control) as GraphControl);
    graph.setConfigurations(GraphConfigurationImpl.deserialize(object.configurations) as Map<string, GraphConfiguration>);
    // graph.setData(GraphDataImpl.deserialize(object.data) as Map<string, GraphData>);
    graph.setPages(GraphPageImpl.deserialize(object.pages) as Map<string, GraphPage>);
    return graph;
  }

  private readonly version: string = '1.0';
  private pages: Map<string, GraphPage> | null = new Map<string, GraphPage>();
  private configurations: Map<string, GraphConfiguration> = new Map<string, GraphConfiguration>();
  private control: GraphControl | null;
  private data: Map<string, GraphData> | null = new Map<string, GraphData>();
  private defaultPage: GraphPage | null;

  constructor();
  constructor(pages: Map<string, GraphPage>, configurations: Map<string, GraphConfiguration>, control: GraphControl, data: Map<string, GraphData>, defaultPage: GraphPage);
  constructor(pages?: Map<string, GraphPage>, configurations?: Map<string, GraphConfiguration>, control?: GraphControl, data?: Map<string, GraphData>, defaultPage?: GraphPage) {
    super();
    this.pages = pages || new Map<string, GraphPage>();
    this.configurations = configurations || new Map<string, GraphConfiguration>();
    this.control = control || null;
    this.data = data || new Map<string, GraphData>();
    this.defaultPage = defaultPage || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getVersion(): string | null {
    return this.version;
  }

  public getPages(): Map<string, GraphPage> | null {
    return this.pages;
  }

  public setPages(pages: Map<string, GraphPage>): void {
    this.pages = pages;
  }

  public getDefaultPage(): GraphPage | null {
    return this.defaultPage;
  }

  public setDefaultPage(page: GraphPage): void {
    this.defaultPage = page;
  }

  public getConfigurations(): Map<string, GraphConfiguration> | null {
    return this.configurations;
  }

  public setConfigurations(configurations: Map<string, GraphConfiguration>): void {
    this.configurations = configurations;
  }

  public getControl(): GraphControl | null {
    return this.control;
  }

  public setControl(control: GraphControl): void {
    this.control = control;
  }

  public getData(): Map<string, GraphData> | null {
    return this.data;
  }

  public setData(data: Map<string, GraphData>): void {
    this.data = data;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
