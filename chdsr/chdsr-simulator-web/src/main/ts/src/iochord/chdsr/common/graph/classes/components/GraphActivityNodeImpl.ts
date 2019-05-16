import { GraphNodeImpl } from '../GraphNodeImpl';
import { GraphActivityNode } from '../../interfaces/components/GraphActivityNode';
import { ACTIVITY_TYPE } from '../../enums/ACTIVITY';
import { GraphDataResource } from '../../interfaces/components/GraphDataResource';
import { GraphDataQueue } from '../../interfaces/components/GraphDataQueue';
import { GraphDataFunction } from '../../interfaces/components/GraphDataFunction';
import { GraphDataFunctionImpl } from './GraphDataFunctionImpl';
import { GraphDataQueueImpl } from './GraphDataQueueImpl';
import { GraphDataResourceImpl } from './GraphDataResourceImpl';
import { RESOURCE_SELECTION } from '../../enums/RESOURCE';
import { DistributionType } from '../../enums/DISTRIBUTION';
import { VARIABLE_TYPE } from '../../enums/VARIABLE';

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
    // graphActivityNode.fn_graph_activity_node_set_processing_time_expression(object.processingTimeExpression);
    graphActivityNode.fn_graph_activity_node_set_queue(GraphDataQueueImpl.fn_object_deserialize(object.queue));
    graphActivityNode.fn_graph_activity_node_set_resource(GraphDataResourceImpl.fn_object_deserialize(object.resource));
    // graphActivityNode.fn_graph_activity_node_set_setup_time_expression(object.setupTimeExpression);
    graphActivityNode.fn_graph_activity_node_set_type(object.type);
    graphActivityNode.fn_graph_activity_node_set_unit(object.unit);
    return graphActivityNode;
  }

  private type: ACTIVITY_TYPE | null = ACTIVITY_TYPE.STANDARD;
  private resource: GraphDataResource | null;
  private resourceSelectionMethod: RESOURCE_SELECTION | null;
  private setupTime: DistributionType | null;
  // TODO: Continue from here
  private queue: GraphDataQueue | null;
  private function: GraphDataFunction | null;
  // private setupTimeExpression: string | null;
  // private processingTimeExpression: string | null;
  private unit: number | null;

  constructor();
  constructor(type: ACTIVITY_TYPE, resource: GraphDataResource, queue: GraphDataQueue, func: GraphDataFunction, unit: number);
  constructor(type?: ACTIVITY_TYPE, resource?: GraphDataResource, queue?: GraphDataQueue, func?: GraphDataFunction, unit?: number) {
    super();
    this.type = type || ACTIVITY_TYPE.STANDARD || null;
    this.resource = resource || null;
    this.queue = queue || null;
    this.function = func || null;
    // this.setupTimeExpression = setupTimeExpression || null;
    // this.processingTimeExpression = processingTimeExpression || null;
    this.unit = unit || null;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_activity_node_get_type(): ACTIVITY_TYPE | null {
    return this.type;
  }

  public fn_graph_activity_node_set_type(type: ACTIVITY_TYPE): void {
    this.type = type;
  }

  public fn_graph_activity_node_get_resource(): GraphDataResource | null {
    return this.resource;
  }

  public fn_graph_activity_node_set_resource(resource: GraphDataResource): void {
    this.resource = resource;
  }

  public fn_graph_activity_node_get_resource_selection_method(): RESOURCE_SELECTION | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_resource_selection_method(method: RESOURCE_SELECTION): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_setup_time(): DistributionType | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_setup_time(setupTime: DistributionType): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_setup_time_parameter(): string | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_setup_time_parameter(setupTimeParameter: string): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_processing_time(): DistributionType | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_processing_time(processingTime: DistributionType): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_processing_time_parameter(): string | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_processing_time_parameter(processingTimeParameter: string): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_variable(): VARIABLE_TYPE | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_variable(variable: VARIABLE_TYPE): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_is_report_scrap(): boolean | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_report_scrap(reportScrap: boolean): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_cost(): number | null {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_set_cost(cost: number): void {
    throw new Error("Method not implemented.");
  }

  public fn_graph_activity_node_get_queue(): GraphDataQueue | null {
    return this.queue;
  }

  public fn_graph_activity_node_set_queue(queue: GraphDataQueue): void {
    this.queue = queue;
  }

  public fn_graph_activity_node_get_function(): GraphDataFunction | null {
    return this.function;
  }

  public fn_graph_activity_node_set_function(func: GraphDataFunction): void {
    this.function = func;
  }

  public fn_graph_activity_node_get_unit(): number | null {
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
