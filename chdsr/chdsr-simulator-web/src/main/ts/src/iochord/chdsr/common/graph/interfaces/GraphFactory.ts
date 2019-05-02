import { GraphMonitor } from './components/GraphMonitorNode';
import { GraphBranchNode } from './components/GraphBranchNode';
import { GraphActivityNode } from './components/GraphActivityNode';
import { GraphEventNode } from './components/GraphEventNode';
import { GraphStartEventNode } from './components/GraphStartEventNode';
import { GraphDataResource } from './components/GraphDataResource';
import { GraphDataQueue } from './components/GraphDataQueue';
import { GraphDataFunction } from './components/GraphDataFunction';
import { GraphDataGenerator } from './components/GraphDataGenerator';
import { GraphDataTable } from './components/GraphDataTable';
import { GraphConfiguration } from './GraphConfiguration';
import { GraphConnector } from '@/iochord/chdsr/common/graph/interfaces/GraphConnector';
import { GraphElement } from './GraphElement';
import { GraphNode } from './GraphNode';
import { GraphData } from './GraphData';
import { Graph } from './Graph';
import { GraphPage } from './GraphPage';
import { GraphDataObjectType } from './components/GraphDataObjectType';

export interface GraphFactory {
  fn_graph_factory_create(ref?: Graph): Graph;
  fn_graph_factory_add_page(net: Graph): GraphPage;
  fn_graph_factory_add_data(page: GraphPage, dataType: string): GraphData;
  fn_graph_factory_add_node(page: GraphPage, nodeType: string): GraphNode;
  fn_graph_factory_add_connector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector;
  fn_graph_factory_add_configuration(net: Graph): GraphConfiguration;

  fn_graph_factory_add_data_table(page: GraphPage): GraphDataTable;
  fn_graph_factory_add_object_type(page: GraphPage): GraphDataObjectType;
  fn_graph_factory_add_generator(page: GraphPage): GraphDataGenerator;
  fn_graph_factory_add_function(page: GraphPage): GraphDataFunction;
  fn_graph_factory_add_queue(page: GraphPage): GraphDataQueue;
  fn_graph_factory_add_resource(page: GraphPage): GraphDataResource;

  fn_graph_factory_add_start(page: GraphPage): GraphStartEventNode;
  fn_graph_factory_add_stop(page: GraphPage): GraphEventNode;
  fn_graph_factory_add_activity(page: GraphPage): GraphActivityNode;
  fn_graph_factory_add_branch(page: GraphPage): GraphBranchNode;
  fn_graph_factory_add_monitor(page: GraphPage): GraphMonitor;
}
