import { GraphDataImpl } from '../GraphDataImpl';
import { GraphDataQueue } from '../../interfaces/components/GraphDataQueue';
import { QUEUE_TYPE } from '../../enums/QUEUE';

export class GraphDataQueueImpl extends GraphDataImpl implements GraphDataQueue {
  public static readonly TYPE: 'queue' = 'queue';

  /** @Override */
  public static fn_object_deserialize(object: any): GraphDataQueue {
    const graphDataQueue: GraphDataQueue = new GraphDataQueueImpl();
    graphDataQueue.fn_graph_element_set_id(object.id);
    graphDataQueue.fn_graph_element_set_label(object.label);
    graphDataQueue.fn_graph_element_set_type(object.elementType);
    graphDataQueue.fn_graph_element_set_attributes(object.attributes as Map<string, string>);
    graphDataQueue.fn_graph_data_queue_set_shared(object.shared);
    graphDataQueue.fn_graph_data_queue_set_size(object.size);
    graphDataQueue.fn_graph_data_queue_set_type(object.type);
    return graphDataQueue;
  }

  private type?: QUEUE_TYPE;
  private size?: number;
  private isShared?: boolean;

  constructor();
  constructor(type: QUEUE_TYPE, size: number, isShared: boolean);
  constructor(type?: QUEUE_TYPE, size?: number, isShared?: boolean) {
    super();
    this.type = type;
    this.size = size;
    this.isShared = isShared;
  }

  /** @Override */
  public fn_graph_element_get_type(): string {
    return this.TYPE;
  }

  public fn_graph_data_queue_get_type(): QUEUE_TYPE | undefined {
    return this.type;
  }

  public fn_graph_data_queue_set_type(type: QUEUE_TYPE): void {
    this.type = type;
  }

  public fn_graph_data_queue_get_size(): number | undefined {
    return this.size;
  }

  public fn_graph_data_queue_set_size(size: number): void {
    this.size = size;
  }

  public fn_graph_data_queue_is_shared(): boolean | undefined {
    return this.isShared;
  }

  public fn_graph_data_queue_set_shared(isShared: boolean): void {
    this.isShared = isShared;
  }

  /** @Override */
  public fn_object_serialize(): string {
    return JSON.stringify(this);
  }
}
