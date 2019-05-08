import { GraphMonitorNode } from './components/GraphMonitorNode';
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
  fn_graph_factory_add_page(net: Graph): GraphPage | undefined;
  fn_graph_factory_add_data(page: GraphPage, dataType: string): GraphData | undefined;
  fn_graph_factory_add_node(page: GraphPage, nodeType: string): GraphNode | undefined;
  fn_graph_factory_add_connector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector | undefined;
  fn_graph_factory_add_configuration(net: Graph): GraphConfiguration | undefined;

  fn_graph_factory_add_data_table(page: GraphPage): GraphDataTable | undefined;
  fn_graph_factory_add_object_type(page: GraphPage): GraphDataObjectType | undefined;
  fn_graph_factory_add_generator(page: GraphPage): GraphDataGenerator | undefined;
  fn_graph_factory_add_function(page: GraphPage): GraphDataFunction | undefined;
  fn_graph_factory_add_queue(page: GraphPage): GraphDataQueue | undefined;
  fn_graph_factory_add_resource(page: GraphPage): GraphDataResource | undefined;

  fn_graph_factory_add_start(page: GraphPage): GraphStartEventNode | undefined;
  fn_graph_factory_add_stop(page: GraphPage): GraphEventNode | undefined;
  fn_graph_factory_add_activity(page: GraphPage): GraphActivityNode | undefined;
  fn_graph_factory_add_branch(page: GraphPage): GraphBranchNode | undefined;
  fn_graph_factory_add_monitor(page: GraphPage): GraphMonitorNode | undefined;
}
