import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphActivityNode } from '../../interfaces/components/GraphActivityNode';
import { ACTIVITY_TYPE } from '../../enums/ACTIVITY';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataQueue } from '../../interfaces/components/GraphDataQueue';
import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { GraphDataFunctionImpl } from './GraphDataFunctionImpl';
import { GraphDataQueueImpl } from './GraphDataQueueImpl';
import { GraphDataResourceImpl } from './GraphDataResourceImpl';

export class GraphActivityNodeImpl extends GraphNodeImpl implements GraphActivityNode {
  public static readonly TYPE: 'activity' = 'activity';

  public static fn_object_deserialize(object: any): GraphActivityNode {
    const graphActivityNode: GraphActivityNode = new GraphActivityNodeImpl();
    graphActivityNode.fn_graph_element_set_id(object.id);
    graphActivityNode.fn_graph_element_set_label(object.label);
    graphActivityNode.fn_graph_element_set_type(object.elementType);
    graphActivityNode.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    graphActivityNode.fn_graph_node_set_group_name(object.groupName);
    graphActivityNode.fn_graph_node_set_report_statistics(object.reportStatistics);
    graphActivityNode.fn_graph_activity_node_set_function(GraphDataFunctionImpl.fn_object_deserialize(object.function));
    graphActivityNode.fn_graph_activity_node_set_processing_time_expression(object.processingTimeExpression);
    graphActivityNode.fn_graph_activity_node_set_queue(GraphDataQueueImpl.fn_object_deserialize(object.queue));
    graphActivityNode.fn_graph_activity_node_set_resource(GraphDataResourceImpl.fn_object_deserialize(object.resource));
    graphActivityNode.fn_graph_activity_node_set_setup_time_expression(object.setupTimeExpression);
    graphActivityNode.fn_graph_activity_node_set_type(object.type);
    graphActivityNode.fn_graph_activity_node_set_unit(object.unit);
    return graphActivityNode;
  }

  private type?: ACTIVITY_TYPE;
  private resource?: GraphDataResource;
  private queue?: GraphDataQueue;
  private function?: GraphDataFunction;
  private setupTimeExpression?: string;
  private processingTimeExpression?: string;
  private unit?: number;

  constructor();
  constructor(type: ACTIVITY_TYPE, resource: GraphDataResource, queue: GraphDataQueue, func: GraphDataFunction, setupTimeExpression: string, processingTimeExpression: string, unit: number);
  constructor(type?: ACTIVITY_TYPE, resource?: GraphDataResource, queue?: GraphDataQueue, func?: GraphDataFunction, setupTimeExpression?: string, processingTimeExpression?: string, unit?: number) {
    super();
    this.type = type;
    this.resource = resource;
    this.queue = queue;
    this.function = func;
    this.setupTimeExpression = setupTimeExpression;
    this.processingTimeExpression = processingTimeExpression;
    this.unit = unit;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_activity_node_get_type(): ACTIVITY_TYPE | undefined {
    return this.type;
  }

  public fn_graph_activity_node_set_type(type: ACTIVITY_TYPE): void {
    this.type = type;
  }

  public fn_graph_activity_node_get_resource(): GraphDataResource | undefined {
    return this.resource;
  }

  public fn_graph_activity_node_set_resource(resource: GraphDataResource): void {
    this.resource = resource;
  }

  public fn_graph_activity_node_get_queue(): GraphDataQueue | undefined {
    return this.queue;
  }

  public fn_graph_activity_node_set_queue(queue: GraphDataQueue): void {
    this.queue = queue;
  }

  public fn_graph_activity_node_get_function(): GraphDataFunction | undefined {
    return this.function;
  }

  public fn_graph_activity_node_set_function(func: GraphDataFunction): void {
    this.function = func;
  }

  public fn_graph_activity_node_get_setup_time_expression(): string | undefined {
    return this.setupTimeExpression;
  }

  public fn_graph_activity_node_set_setup_time_expression(setupTimeExpression: string): void {
    this.setupTimeExpression = setupTimeExpression;
  }

  public fn_graph_activity_node_get_processing_time_expression(): string | undefined {
    return this.processingTimeExpression;
  }

  public fn_graph_activity_node_set_processing_time_expression(processingTimeExpression: string): void {
    this.processingTimeExpression = processingTimeExpression;
  }

  public fn_graph_activity_node_get_unit(): number | undefined {
    return this.unit;
  }

  public fn_graph_activity_node_set_unit(unit: number): void {
    this.unit = unit;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
