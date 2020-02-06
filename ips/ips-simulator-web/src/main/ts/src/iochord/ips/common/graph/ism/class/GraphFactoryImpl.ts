import { GraphFactory } from '../interfaces/GraphFactory';
import { Graph } from '../interfaces/Graph';
import { GraphPage } from '../interfaces/GraphPage';
import { GraphData } from '../interfaces/GraphData';
import { GraphNode } from '../interfaces/GraphNode';
import { GraphElement } from '../interfaces/GraphElement';
import { GraphConfiguration } from '../interfaces/GraphConfiguration';
import { GraphDataTable } from '../interfaces/components/GraphDataTable';
import { GraphDataObjectType } from '../interfaces/components/GraphDataObjectType';
import { GraphDataGenerator } from '../interfaces/components/GraphDataGenerator';
import { GraphDataFunction } from '../interfaces/components/GraphDataFunction';
import { GraphDataQueue } from '../interfaces/components/GraphDataQueue';
import { GraphDataResource } from '../interfaces/components/GraphDataResource';
import { GraphStartEventNode } from '../interfaces/components/GraphStartEventNode';
import { GraphActivityNode } from '../interfaces/components/GraphActivityNode';
import { GraphBranchNode } from '../interfaces/components/GraphBranchNode';
import { GraphMonitorNode } from '../interfaces/components/GraphMonitorNode';
import { GraphConnector } from '../interfaces/GraphConnector';
import { GraphImpl } from './GraphImpl';
import { GraphControlImpl } from './components/GraphControlImpl';
import { GraphPageImpl } from './GraphPageImpl';
import { GraphConfigurationImpl } from './GraphConfigurationImpl';
import { DATA_TYPE } from '../enums/DATA';
import { NODE_TYPE } from '../enums/NODE';
import { GraphConnectorImpl } from './GraphConnectorImpl';
import { GraphDataTableImpl } from './components/GraphDataTableImpl';
import { GraphDataObjectTypeImpl } from './components/GraphDataObjectTypeImpl';
import { GraphDataGeneratorImpl } from './components/GraphDataGeneratorImpl';
import { GraphDataFunctionImpl } from './components/GraphDataFunctionImpl';
import { GraphDataQueueImpl } from './components/GraphDataQueueImpl';
import { GraphDataResourceImpl } from './components/GraphDataResourceImpl';
import { GraphStartEventNodeImpl } from './components/GraphStartEventNodeImpl';
import { GraphStopEventNodeImpl } from './components/GraphStopEventNodeImpl';
import { GraphActivityNodeImpl } from './components/GraphActivityNodeImpl';
import { GraphBranchNodeImpl } from './components/GraphBranchNodeImpl';
import { GraphMonitorNodeImpl } from './components/GraphMonitorNodeImpl';
import { GraphStopEventNode } from '../interfaces/components/GraphStopEventNode';
import { TSMap } from 'typescript-map';

/**
 * Factory class to create GraphImpl object. Implementation of GraphFactory interface.
 *
 * @export
 * @class GraphFactoryImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphFactory}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphFactoryImpl implements GraphFactory {

  /**
   * Returns the instances of GraphFactory.
   *
   * @static
   * @returns {GraphFactory}
   * @memberof GraphFactoryImpl
   */
  public static getInstance(): GraphFactory {
    return this.instance;
  }

  /**
   * Instance of GraphFactory.
   *
   * @private
   * @static
   * @type {GraphFactory}
   * @memberof GraphFactoryImpl
   */
  private static readonly instance: GraphFactory = new GraphFactoryImpl();

  /**
   * The number of graph constructed by GraphFactory.
   *
   * @private
   * @type {number}
   * @memberof GraphFactoryImpl
   */
  private netCounter: number = 0;

  /**
   * Returns the counter of current GraphFactory.
   *
   * @returns {number}
   * @memberof GraphFactoryImpl
   */
  public getCounter(): number {
    return this.netCounter;
  }

  /**
   * Assigns a counter to current GraphFactory.
   *
   * @param {number} counter
   * @memberof GraphFactoryImpl
   */
  public setCounter(counter: number): void {
    this.netCounter = counter;
  }

  /**
   * Create Graph object.
   *
   * @param {(Graph | null)} [ref]
   * @returns {Graph}
   * @memberof GraphFactoryImpl
   */
  public create(ref?: Graph | null): Graph {
    const net: Graph = new GraphImpl();
    net.setId('MODEL-' + this.netCounter);
    this.addPage(net);
    this.addConfiguration(net);
    net.setControl(new GraphControlImpl());
    return net;
  }

  /**
   * Add a page to Graph object.
   *
   * @param {Graph} net
   * @returns {(GraphPage | null)}
   * @memberof GraphFactoryImpl
   */
  public addPage(net: Graph): GraphPage | null {
    let page: GraphPage | null = new GraphPageImpl();
    if (net !== null) {
      const netPage: TSMap<string, GraphPage> | null = net.getPages();
      const size: number = (netPage as TSMap<string, GraphPage>).size();
      page.setId(size.toString());
      (netPage as TSMap<string, GraphPage>).set(net.getId() as string, page);
    } else {
      page = null;
    }
    return page;
  }

  /**
   * Add data to the specified page of the Graph.
   *
   * @param {GraphPage} page
   * @param {string} dataType
   * @returns {(GraphData | null)}
   * @memberof GraphFactoryImpl
   */
  public addData(page: GraphPage, dataType: string): GraphData | null {
    const dt: string = dataType.toLowerCase();
    let data: GraphData | null = new (DATA_TYPE as any)[dt]();
    if (page !== null) {
      try {
        (data as GraphData).setId(`${page.getId()}-${(page.getData() as TSMap<string, GraphData>).size.toString()}`);
        (page.getData() as TSMap<string, GraphData>).set((data as GraphData).getId() as string, (data as GraphData));
      } catch (e) {
        throw new Error(e);
      }
    } else {
      data = null;
    }
    return data;
  }

  /**
   * Add node to the specified page of the Graph
   *
   * @param {GraphPage} page
   * @param {string} nodeType
   * @returns {(GraphNode | null)}
   * @memberof GraphFactoryImpl
   */
  public addNode(page: GraphPage, nodeType: string): GraphNode | null {
    const nt: string = nodeType.toLowerCase();
    let node: GraphNode | null = new (NODE_TYPE as any)[nt]();
    if (page !== null) {
      try {
        (node as GraphNode).setId(`${page.getId()}-${(page.getNodes() as TSMap<string, GraphData>).size.toString()}`);
        (page.getNodes() as TSMap<string, GraphData>).set((node as GraphNode).getId() as string, (node as GraphNode));
      } catch (e) {
        throw new Error(e);
      }
    } else {
      node = null;
    }
    return node;
  }

  /**
   * Add connector to the specified page of the Graph.
   *
   * @param {GraphPage} page
   * @param {GraphElement} source
   * @param {GraphElement} target
   * @returns {(GraphConnector | null)}
   * @memberof GraphFactoryImpl
   */
  public addConnector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector | null {
    let arc: GraphConnector | null = new GraphConnectorImpl();
    if (page !== null) {
      arc.setId(`${page.getId()}-${(page.getConnectors() as TSMap<string, GraphConnector>).size.toString()}`);
      arc.setSource(source);
      arc.setTarget(target);
      (page.getConnectors() as TSMap<string, GraphConnector>).set(arc.getId() as string, arc);
    } else {
      arc = null;
    }
    return arc;
  }

  /**
   * Add configuration to the current graph.
   *
   * @param {Graph} net
   * @returns {(GraphConfiguration | null)}
   * @memberof GraphFactoryImpl
   */
  public addConfiguration(net: Graph): GraphConfiguration | null {
    let config: GraphConfiguration | null = new GraphConfigurationImpl();
    if (net !== null) {
      const netConfig: TSMap<string, GraphConfiguration> | null = net.getConfigurations();
      const size: number = netConfig!.size();
      config.setId(size.toString());
      netConfig!.set(config.getId() as string, config);
    } else {
      config = null;
    }
    return config;
  }

  /**
   * Add data table to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataTable | null)}
   * @memberof GraphFactoryImpl
   */
  public addDataTable(page: GraphPage): GraphDataTable | null {
    return this.addData(page, GraphDataTableImpl.TYPE) as GraphDataTable | null;
  }

  /**
   * Add object type to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataObjectType | null)}
   * @memberof GraphFactoryImpl
   */
  public addObjectType(page: GraphPage): GraphDataObjectType | null {
    return this.addData(page, GraphDataObjectTypeImpl.TYPE) as GraphDataObjectType | null;
  }

  /**
   * Add generator to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataGenerator | null)}
   * @memberof GraphFactoryImpl
   */
  public addGenerator(page: GraphPage): GraphDataGenerator | null {
    return this.addData(page, GraphDataGeneratorImpl.TYPE) as GraphDataGenerator | null;
  }

  /**
   * Add function to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataFunction | null)}
   * @memberof GraphFactoryImpl
   */
  public addFunction(page: GraphPage): GraphDataFunction | null {
    return this.addData(page, GraphDataFunctionImpl.TYPE) as GraphDataFunction | null;
  }

  /**
   * Add queue to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataQueue | null)}
   * @memberof GraphFactoryImpl
   */
  public addQueue(page: GraphPage): GraphDataQueue | null {
    return this.addData(page, GraphDataQueueImpl.TYPE) as GraphDataQueue | null;
  }

  /**
   * Add resource to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphDataResource | null)}
   * @memberof GraphFactoryImpl
   */
  public addResource(page: GraphPage): GraphDataResource | null {
    return this.addData(page, GraphDataResourceImpl.TYPE) as GraphDataResource | null;
  }

  /**
   * Add start event node to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphStartEventNode | null)}
   * @memberof GraphFactoryImpl
   */
  public addStart(page: GraphPage): GraphStartEventNode | null {
    return this.addNode(page, GraphStartEventNodeImpl.TYPE) as GraphStartEventNode | null;
  }

  /**
   * Add stop event node to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphStopEventNode | null)}
   * @memberof GraphFactoryImpl
   */
  public addStop(page: GraphPage): GraphStopEventNode | null {
    return this.addNode(page, GraphStopEventNodeImpl.TYPE) as GraphStopEventNode | null;
  }

  /**
   * Add activity node to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphActivityNode | null)}
   * @memberof GraphFactoryImpl
   */
  public addActivity(page: GraphPage): GraphActivityNode | null {
    return this.addNode(page, GraphActivityNodeImpl.TYPE) as GraphActivityNode | null;
  }

  /**
   * Add branch to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphBranchNode | null)}
   * @memberof GraphFactoryImpl
   */
  public addBranch(page: GraphPage): GraphBranchNode | null {
    return this.addNode(page, GraphBranchNodeImpl.TYPE) as GraphBranchNode | null;
  }

  /**
   * Add monitor to the specified page.
   *
   * @param {GraphPage} page
   * @returns {(GraphMonitorNode | null)}
   * @memberof GraphFactoryImpl
   */
  public addMonitor(page: GraphPage): GraphMonitorNode | null {
    return this.addNode(page, GraphMonitorNodeImpl.TYPE) as GraphMonitorNode | null;
  }
}
