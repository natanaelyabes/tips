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
import { GraphEventNode } from '../interfaces/components/GraphEventNode';
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
import { GraphStopEventNodeImpl } from './components/GraphEndEventNodeImpl';
import { GraphActivityNodeImpl } from './components/GraphActivityNodeImpl';
import { GraphBranchNodeImpl } from './components/GraphBranchNodeImpl';
import { GraphMonitorNodeImpl } from './components/GraphMonitorNodeImpl';
import { GraphStopEventNode } from '../interfaces/components/GraphStopEventNode';

export class GraphFactoryImpl implements GraphFactory {
  public static fn_graph_factory_get_instance(): GraphFactory {
    return this.instance;
  }

  private static readonly instance: GraphFactory = new GraphFactoryImpl();

  private netCounter: number = 0;

  constructor() {
    //
  }

  public fn_graph_get_counter(): number {
    return this.netCounter;
  }

  public fn_graph_set_counter(counter: number): void {
    this.netCounter = counter;
  }

  public fn_graph_factory_create(ref?: Graph | null): Graph {
    const net: Graph = new GraphImpl();
    net.fn_graph_element_set_id('MODEL-' + this.netCounter);
    this.fn_graph_factory_add_page(net);
    this.fn_graph_factory_add_configuration(net);
    net.fn_graph_set_control(new GraphControlImpl());
    return net;
  }

  public fn_graph_factory_add_page(net: Graph): GraphPage | null {
    if (net !== null) {
      const page: GraphPage = new GraphPageImpl();
      const netPage: Map<string, GraphPage> | null = net.fn_graph_get_pages();
      const size: number = netPage!.size;
      page.fn_graph_element_set_id(size.toString());
      netPage!.set(net.fn_graph_element_get_id() as string, page);
      return page;
    }
    return null;
  }

  public fn_graph_factory_add_data(page: GraphPage, dataType: string): GraphData | null {
    if (page !== null) {
      const dt: string = dataType.toLowerCase();
      try {
        const data: GraphData = new (DATA_TYPE as any)[dt]();
        data.fn_graph_element_set_id(`${page.fn_graph_element_get_id()}-${page.fn_graph_page_get_data().size.toString()}`);
        page.fn_graph_page_get_data().set(data.fn_graph_element_get_id() as string, data);
        return data;
      } catch (e) {
        throw new Error(e);
      }
    }
    return null;
  }

  public fn_graph_factory_add_node(page: GraphPage, nodeType: string): GraphNode | null {
    if (page !== null) {
      const nt: string = nodeType.toLowerCase();
      try {
        const node: GraphNode = new (NODE_TYPE as any)[nt]();
        node.fn_graph_element_set_id(`${page.fn_graph_element_get_id()}-${page.fn_graph_page_get_nodes().size.toString()}`);
        page.fn_graph_page_get_nodes().set(node.fn_graph_element_get_id() as string, node);
        return node;
      } catch (e) {
        throw new Error(e);
      }
    }
    return null;
  }

  public fn_graph_factory_add_connector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector | null {
    if (page !== null) {
      const arc: GraphConnector = new GraphConnectorImpl();
      arc.fn_graph_element_set_id(`${page.fn_graph_element_get_id()}-${page.fn_graph_page_get_arcs().size.toString()}`);
      arc.fn_graph_connector_set_source(source);
      arc.fn_graph_connector_set_target(target);
      page.fn_graph_page_get_arcs().set(arc.fn_graph_element_get_id() as string, arc);
      return arc;
    }
    return null;
  }

  public fn_graph_factory_add_configuration(net: Graph): GraphConfiguration | null {
    if (net !== null) {
      const config: GraphConfiguration = new GraphConfigurationImpl();
      const netConfig: Map<string, GraphConfiguration> | null = net.fn_graph_get_configurations();
      const size: number = netConfig!.size;
      config.fn_graph_element_set_id(size.toString());
      netConfig!.set(config.fn_graph_element_get_id() as string, config);
      return config;
    }
    return null;
  }

  public fn_graph_factory_add_data_table(page: GraphPage): GraphDataTable | null {
    return this.fn_graph_factory_add_data(page, GraphDataTableImpl.TYPE) as GraphDataTable | null;
  }

  public fn_graph_factory_add_object_type(page: GraphPage): GraphDataObjectType | null {
    return this.fn_graph_factory_add_data(page, GraphDataObjectTypeImpl.TYPE) as GraphDataObjectType | null;
  }

  public fn_graph_factory_add_generator(page: GraphPage): GraphDataGenerator | null {
    return this.fn_graph_factory_add_data(page, GraphDataGeneratorImpl.TYPE) as GraphDataGenerator | null;
  }

  public fn_graph_factory_add_function(page: GraphPage): GraphDataFunction | null {
    return this.fn_graph_factory_add_data(page, GraphDataFunctionImpl.TYPE) as GraphDataFunction | null;
  }

  public fn_graph_factory_add_queue(page: GraphPage): GraphDataQueue | null {
    return this.fn_graph_factory_add_data(page, GraphDataQueueImpl.TYPE) as GraphDataQueue | null;
  }

  public fn_graph_factory_add_resource(page: GraphPage): GraphDataResource | null {
    return this.fn_graph_factory_add_data(page, GraphDataResourceImpl.TYPE) as GraphDataResource | null;
  }

  public fn_graph_factory_add_start(page: GraphPage): GraphStartEventNode | null {
    return this.fn_graph_factory_add_node(page, GraphStartEventNodeImpl.TYPE) as GraphStartEventNode | null;
  }

  public fn_graph_factory_add_stop(page: GraphPage): GraphStopEventNode | null {
    return this.fn_graph_factory_add_node(page, GraphStopEventNodeImpl.TYPE) as GraphStopEventNode | null;
  }

  public fn_graph_factory_add_activity(page: GraphPage): GraphActivityNode | null {
    return this.fn_graph_factory_add_node(page, GraphActivityNodeImpl.TYPE) as GraphActivityNode | null;
  }

  public fn_graph_factory_add_branch(page: GraphPage): GraphBranchNode | null {
    return this.fn_graph_factory_add_node(page, GraphBranchNodeImpl.TYPE) as GraphBranchNode | null;
  }

  public fn_graph_factory_add_monitor(page: GraphPage): GraphMonitorNode | null {
    return this.fn_graph_factory_add_node(page, GraphMonitorNodeImpl.TYPE) as GraphMonitorNode | null;
  }
}
