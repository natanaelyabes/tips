import { GraphControl } from '../interfaces/components/GraphControl';
import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphPage } from '../interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { Graph } from '../interfaces/Graph';
import { GraphData } from '../interfaces/GraphData';
import { GraphPageImpl } from './GraphPageImpl';
import { GraphConfigurationImpl } from './GraphConfigurationImpl';
import { GraphControlImpl } from './components/GraphControlImpl';
import { GraphDataImpl } from './GraphDataImpl';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphImpl extends GraphElementImpl implements Graph {
  public static readonly TYPE: string | null = 'net';

  public static deserialize(object: any): Graph | null {
    const graph: Graph = new GraphImpl();
    graph.setId(object.id);
    graph.setLabel(object.label);
    graph.setType(object.elementType);
    graph.setAttributes(object.attributes as Map<string, string>);
    graph.setControl(GraphControlImpl.deserialize(object.control) as GraphControl);
    graph.setConfigurations(GraphConfigurationImpl.deserialize(object.configurations) as Map<string, GraphConfiguration>);
    graph.setData(GraphDataImpl.deserialize(object.data) as Map<string, GraphData>);
    graph.setPages(GraphPageImpl.deserialize(object.pages) as Map<string, GraphPage>);
    return graph;
  }

  private readonly version: string = '1.0';
  private pages?: Map<string, GraphPage> | null = new Map<string, GraphPage>();
  private configurations?: Map<string, GraphConfiguration> = new Map<string, GraphConfiguration>();
  private control?: GraphControl | null;
  private data?: Map<string, GraphData> | null = new Map<string, GraphData>();
  private defaultPage?: GraphPage | null;

  constructor() {
    super();
  }

  public getVersion(): string | null {
    return this.version;
  }

  public getPages(): Map<string, GraphPage> | null {
    return this.pages as Map<string, GraphPage> | null;
  }

  public setPages(pages: Map<string, GraphPage>): void {
    this.pages = pages || this.pages;
  }

  public getDefaultPage(): GraphPage | null {
    return this.defaultPage as GraphPage | null;
  }

  public setDefaultPage(page: GraphPage): void {
    this.defaultPage = page || this.defaultPage;
  }

  public getConfigurations(): Map<string, GraphConfiguration> | null {
    return this.configurations as Map<string, GraphConfiguration> | null;
  }

  public setConfigurations(configurations: Map<string, GraphConfiguration>): void {
    this.configurations = configurations || this.configurations;
  }

  public getControl(): GraphControl | null {
    return this.control as GraphControl | null;
  }

  public setControl(control: GraphControl): void {
    this.control = control || this.control;
  }

  public getData(): Map<string, GraphData> | null {
    return this.data as Map<string, GraphData> | null;
  }

  public setData(data: Map<string, GraphData>): void {
    this.data = data || this.data;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
