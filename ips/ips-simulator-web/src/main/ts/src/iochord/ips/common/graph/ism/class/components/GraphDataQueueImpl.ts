import { GraphDataQueue } from '../../interfaces/components/GraphDataQueue';
import { GraphDataImpl } from '../GraphDataImpl';
import { QUEUE_TYPE } from '../../enums/QUEUE';
import { TSMap } from 'typescript-map';

/**
 * Implementation of GraphDataQueueImpl interface.
 *
 * @export
 * @class GraphDataQueueImpl
 * @extends {GraphNodeImpl}
 * @implements {GraphDataQueueImpl}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataQueueImpl extends GraphDataImpl implements GraphDataQueue {

  /**
   * Field to identify the type of the node.
   *
   * @static
   * @type {string}
   * @memberof GraphDataQueueImpl
   */
  public static TYPE: string = 'queue';

  /**
   * Deserialize JSON object as GraphDataQueueImpl.
   *
   * @override
   * @static
   * @param {*} object
   * @returns {(GraphDataQueue | null)}
   * @memberof GraphDataQueueImpl
   */
  public static deserialize(object: any): GraphDataQueue | null {
    const graphDataQueue: GraphDataQueue = new GraphDataQueueImpl();
    graphDataQueue.setId(object.id);
    graphDataQueue.setLabel(object.label);
    graphDataQueue.setType(object.elementType);
    graphDataQueue.setAttributes(object.attributes as TSMap<string, string>);
    graphDataQueue.setQueueType(object.type);
    graphDataQueue.setShared(object.shared);
    graphDataQueue.setSingle(object.single);
    graphDataQueue.setSize(object.size);
    if (object.size > -1) {
      graphDataQueue.setSizes(object.sizes);
    }
    GraphDataQueueImpl.instance.set(graphDataQueue.getId() as string, graphDataQueue);
    return graphDataQueue;
  }

  /**
   * Queue type of the current queue data node.
   *
   * @private
   * @type {(QUEUE_TYPE | null)}
   * @memberof GraphDataQueueImpl
   */
  private type?: QUEUE_TYPE | null = QUEUE_TYPE.FIFO;

  /**
   * Whether a queue is being shared. False otherwise.
   *
   * @private
   * @type {(boolean | null)}
   * @memberof GraphDataQueueImpl
   */
  private shared?: boolean | null = false;

  /**
   * Whether current queue data node is a single queue.
   *
   * @private
   * @type {(boolean | null)}
   * @memberof GraphDataQueueImpl
   */
  private single?: boolean | null = true;

  /**
   * The size of queue. Default to -1 for infinite queue.
   *
   * @private
   * @type {(number | null)}
   * @memberof GraphDataQueueImpl
   */
  private size?: number | null = -1;

  /**
   * The list of resource id for the given queue.
   *
   * @private
   * @type {(TSMap<string, number> | null)}
   * @memberof GraphDataQueueImpl
   */
  private sizes?: TSMap<string, number> | null = new TSMap<string, number>() || null;

  /**
   * Creates an instance of GraphDataQueueImpl.
   *
   * @memberof GraphDataQueueImpl
   */
  constructor() {
    super();
  }

  /**
   * Returns the queue type for the current queue data node.
   *
   * @returns {(QUEUE_TYPE | null)}
   * @memberof GraphDataQueueImpl
   */
  public getQueueType(): QUEUE_TYPE | null {
    return this.type as QUEUE_TYPE | null;
  }

  /**
   * Assigns the queue type for the current queue data node.
   *
   * @param {QUEUE_TYPE} type
   * @memberof GraphDataQueueImpl
   */
  public setQueueType(type: QUEUE_TYPE): void {
    this.type = type as QUEUE_TYPE;
  }

  /**
   * Returns whether a queue is shared. False otherwise.
   *
   * @returns {(boolean | null)}
   * @memberof GraphDataQueueImpl
   */
  public isShared(): boolean | null {
    return this.shared as boolean | null;
  }

  /**
   * Assigns whether current queue is shared. False otherwise.
   *
   * @param {boolean} shared
   * @memberof GraphDataQueueImpl
   */
  public setShared(shared: boolean): void {
    this.shared = shared as boolean;
  }

  /**
   * Returns whether current queue is a single queue.
   *
   * @returns {(boolean | null)}
   * @memberof GraphDataQueueImpl
   */
  public isSingle(): boolean | null {
    return this.single as boolean | null;
  }

  /**
   * Assigns queue to be in single mode. False otherwise.
   *
   * @param {boolean} single
   * @memberof GraphDataQueueImpl
   */
  public setSingle(single: boolean): void {
    this.single = single as boolean;
  }

  /**
   * Returns the size of queue.
   *
   * @returns {(number | null)}
   * @memberof GraphDataQueueImpl
   */
  public getSize(): number | null {
    return this.size as number | null;
  }

  /**
   * Assign size of queue to the current queue data node.
   *
   * @param {number} size
   * @memberof GraphDataQueueImpl
   */
  public setSize(size: number): void {
    this.size = size || this.size;
  }

  /**
   * Returns the list resource ids of current queue data node.
   *
   * @returns {(TSMap<string, number> | null)}
   * @memberof GraphDataQueueImpl
   */
  public getSizes(): TSMap<string, number> | null {
    return this.sizes as TSMap<string, number> | null;
  }

  /**
   * Set the list of resource ids for the current queue data node.
   *
   * @param {TSMap<string, number>} sizes
   * @memberof GraphDataQueueImpl
   */
  public setSizes(sizes: TSMap<string, number>): void {
    this.sizes = sizes || this.sizes;
  }

  /**
   * Serialize GraphDataQueueImpl as JSON string.
   *
   * @override
   * @returns {(string | null)}
   * @memberof GraphDataQueueImpl
   */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
