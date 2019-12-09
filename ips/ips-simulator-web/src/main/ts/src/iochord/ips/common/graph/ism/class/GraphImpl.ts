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
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
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
    graph.setAttributes(object.attributes as TSMap<string, string>);
    graph.setControl(GraphControlImpl.deserialize(object.control) as GraphControl);
    graph.setConfigurations(GraphConfigurationImpl.deserialize(object.configurations) as TSMap<string, GraphConfiguration>);
    // graph.setData(GraphDataImpl.deserialize(object.data) as TSMap<string, GraphData>);
    graph.setPages(GraphPageImpl.deserialize(object.pages) as TSMap<string, GraphPage>);
    return graph;
  }

  private readonly version: string = '1.0';
  private pages?: TSMap<string, GraphPage> | null = new TSMap<string, GraphPage>();
  private configurations?: TSMap<string, GraphConfiguration> = new TSMap<string, GraphConfiguration>();
  private control?: GraphControl | null;
  private data?: TSMap<string, GraphData> | null = new TSMap<string, GraphData>();
  private defaultPage?: GraphPage | null;

  constructor() {
    super();
  }

  public getVersion(): string | null {
    return this.version;
  }

  public getPages(): TSMap<string, GraphPage> | null {
    return this.pages as TSMap<string, GraphPage>;
  }

  public setPages(pages: TSMap<string, GraphPage>): void {
    this.pages = pages as TSMap<string, GraphPage>;
  }

  public getDefaultPage(): GraphPage | null {
    return this.defaultPage as GraphPage | null;
  }

  public setDefaultPage(page: GraphPage): void {
    this.defaultPage = page || this.defaultPage;
  }

  public getConfigurations(): TSMap<string, GraphConfiguration> | null {
    return this.configurations as TSMap<string, GraphConfiguration> | null;
  }

  public setConfigurations(configurations: TSMap<string, GraphConfiguration>): void {
    this.configurations = configurations || this.configurations;
  }

  public getControl(): GraphControl | null {
    return this.control as GraphControl | null;
  }

  public setControl(control: GraphControl): void {
    this.control = control || this.control;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
