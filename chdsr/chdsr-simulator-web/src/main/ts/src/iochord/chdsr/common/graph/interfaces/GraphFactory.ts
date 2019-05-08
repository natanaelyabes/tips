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
  fn_graph_factory_add_page(net: Graph): GraphPage | null;
  fn_graph_factory_add_data(page: GraphPage, dataType: string): GraphData | null;
  fn_graph_factory_add_node(page: GraphPage, nodeType: string): GraphNode | null;
  fn_graph_factory_add_connector(page: GraphPage, source: GraphElement, target: GraphElement): GraphConnector | null;
  fn_graph_factory_add_configuration(net: Graph): GraphConfiguration | null;

  fn_graph_factory_add_data_table(page: GraphPage): GraphDataTable | null;
  fn_graph_factory_add_object_type(page: GraphPage): GraphDataObjectType | null;
  fn_graph_factory_add_generator(page: GraphPage): GraphDataGenerator | null;
  fn_graph_factory_add_function(page: GraphPage): GraphDataFunction | null;
  fn_graph_factory_add_queue(page: GraphPage): GraphDataQueue | null;
  fn_graph_factory_add_resource(page: GraphPage): GraphDataResource | null;

  fn_graph_factory_add_start(page: GraphPage): GraphStartEventNode | null;
  fn_graph_factory_add_stop(page: GraphPage): GraphEventNode | null;
  fn_graph_factory_add_activity(page: GraphPage): GraphActivityNode | null;
  fn_graph_factory_add_branch(page: GraphPage): GraphBranchNode | null;
  fn_graph_factory_add_monitor(page: GraphPage): GraphMonitorNode | null;
}
