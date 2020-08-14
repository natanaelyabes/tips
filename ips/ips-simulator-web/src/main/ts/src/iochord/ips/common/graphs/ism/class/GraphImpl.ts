import { GraphControl } from '../interfaces/components/GraphControl';
import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphPage } from '../interfaces/GraphPage';
import { GraphElementImpl } from './GraphElementImpl';
import { Graph } from '../interfaces/Graph';
import { GraphData } from '../interfaces/GraphData';
import { GraphPageImpl } from './GraphPageImpl';
import { GraphConfigurationImpl } from './GraphConfigurationImpl';
import { GraphControlImpl } from './components/GraphControlImpl';
import { TSMap } from 'typescript-map';

/**
 * Implementation of Graph interface.
 *
 * @export
 * @class GraphFactoryImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphFactory}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export class GraphImpl extends GraphElementImpl implements Graph {

  /**
   * Field to identify the type of the graph.
   *
   * @static
   * @type {(string | null)}
   * @memberof GraphImpl
   */
  public static readonly TYPE: string | null = 'net';

  /**
   * Deserialize JSON object into GraphImpl.
   *
   * @static
   * @param {*} object
   * @returns {(Graph | null)}
   * @memberof GraphImpl
   */
  public static deserialize(object: any): Graph | null {
    const graph: Graph = new GraphImpl();
    graph.setId(object.id);
    graph.setLabel(object.label);
    graph.setType(object.elementType);
    graph.setAttributes(object.attributes as TSMap<string, string>);
    graph.setControl(GraphControlImpl.deserialize(object.control) as GraphControl);
    graph.setConfigurations(GraphConfigurationImpl.deserialize(object.configurations) as TSMap<string, GraphConfiguration>);
    graph.setPages(GraphPageImpl.deserialize(object.pages) as TSMap<string, GraphPage>);
    return graph;
  }

  /**
   * Version of the graph.
   *
   * @private
   * @type {string}
   * @memberof GraphImpl
   */
  private readonly version: string = '1.0';

  /**
   * Pages of the graph.
   *
   * @private
   * @type {(TSMap<string, GraphPage> | null)}
   * @memberof GraphImpl
   */
  private pages?: TSMap<string, GraphPage> | null = new TSMap<string, GraphPage>();

  /**
   * Configuration settings of the graph.
   *
   * @private
   * @type {TSMap<string, GraphConfiguration>}
   * @memberof GraphImpl
   */
  private configurations?: TSMap<string, GraphConfiguration> = new TSMap<string, GraphConfiguration>();

  /**
   * Control settings of the graph.
   *
   * @private
   * @type {(GraphControl | null)}
   * @memberof GraphImpl
   */
  private control?: GraphControl | null;

  /**
   * Data of the graph.
   *
   * @private
   * @type {(TSMap<string, GraphData> | null)}
   * @memberof GraphImpl
   */
  private data?: TSMap<string, GraphData> | null = new TSMap<string, GraphData>();

  /**
   * The default page of the graph.
   *
   * @private
   * @type {(GraphPage | null)}
   * @memberof GraphImpl
   */
  private defaultPage?: GraphPage | null;

  /**
   * Creates an instance of GraphImpl.
   *
   * @memberof GraphImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the version of the graph.
   *
   * @returns {(string | null)}
   * @memberof GraphImpl
   */
  public getVersion(): string | null {
    return this.version;
  }

  /**
   * Returns the pages of the graph.
   *
   * @returns {(TSMap<string, GraphPage> | null)}
   * @memberof GraphImpl
   */
  public getPages(): TSMap<string, GraphPage> | null {
    return this.pages as TSMap<string, GraphPage>;
  }

  /**
   * Assigns pages to the graph.
   *
   * @param {TSMap<string, GraphPage>} pages
   * @memberof GraphImpl
   */
  public setPages(pages: TSMap<string, GraphPage>): void {
    this.pages = pages as TSMap<string, GraphPage>;
  }

  /**
   * Returns the default page of the graph.
   *
   * @returns {(GraphPage | null)}
   * @memberof GraphImpl
   */
  public getDefaultPage(): GraphPage | null {
    return this.defaultPage as GraphPage | null;
  }

  /**
   * Assigns the default page of the graph.
   *
   * @param {GraphPage} page
   * @memberof GraphImpl
   */
  public setDefaultPage(page: GraphPage): void {
    this.defaultPage = page || this.defaultPage;
  }

  /**
   * Returns the configurations of the graph.
   *
   * @returns {(TSMap<string, GraphConfiguration> | null)}
   * @memberof GraphImpl
   */
  public getConfigurations(): TSMap<string, GraphConfiguration> | null {
    return this.configurations as TSMap<string, GraphConfiguration> | null;
  }

  /**
   * Assigns the configuration settings of the graph.
   *
   * @param {TSMap<string, GraphConfiguration>} configurations
   * @memberof GraphImpl
   */
  public setConfigurations(configurations: TSMap<string, GraphConfiguration>): void {
    this.configurations = configurations || this.configurations;
  }

  /**
   * Returns control settings to the graph.
   *
   * @returns {(GraphControl | null)}
   * @memberof GraphImpl
   */
  public getControl(): GraphControl | null {
    return this.control as GraphControl | null;
  }

  /**
   * Assigns control settings to the graph.
   *
   * @param {GraphControl} control
   * @memberof GraphImpl
   */
  public setControl(control: GraphControl): void {
    this.control = control || this.control;
  }

  /**
   * Serialize GraphImpl as JSON string.
   *
   * @returns {(string | null)}
   * @memberof GraphImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
