import { GraphDataQueue } from './../../interfaces/components/GraphDataQueue';
import { GraphDataImpl } from '../GraphDataImpl';
import { QUEUE_TYPE } from '../../enums/QUEUE';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export class GraphDataQueueImpl extends GraphDataImpl implements GraphDataQueue {
  public static TYPE: string = 'queue';
  public static instance: Map<string, GraphDataQueue> = new Map<string, GraphDataQueue>();

  /** @Override */
  public static deserialize(object: any): GraphDataQueue | null {
    const graphDataQueue: GraphDataQueue = new GraphDataQueueImpl();
    graphDataQueue.setId(object.id);
    graphDataQueue.setLabel(object.label);
    graphDataQueue.setType(object.elementType);
    graphDataQueue.setAttributes(object.attributes as Map<string, string>);
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

  private type?: QUEUE_TYPE | null = QUEUE_TYPE.FIFO;
  private shared?: boolean | null = false;
  private single?: boolean | null = true;
  private size?: number | null = -1;
  private sizes?: Map<string, number> | null = new Map<string, number>() || null;

  constructor() {
    super();
  }

  public getQueueType(): QUEUE_TYPE | null {
    return this.type as QUEUE_TYPE | null;
  }

  public setQueueType(type: QUEUE_TYPE): void {
    this.type = type || this.type;
  }

  public isShared(): boolean | null {
    return this.shared as boolean | null;
  }

  public setShared(shared: boolean): void {
    this.shared = shared || this.shared;
  }

  public isSingle(): boolean | null {
    return this.single as boolean | null;
  }

  public setSingle(single: boolean): void {
    this.single = single || this.single;
  }

  public getSize(): number | null {
    return this.size as number | null;
  }

  public setSize(size: number): void {
    this.size = size || this.size;
  }

  public getSizes(): Map<string, number> | null {
    return this.sizes as Map<string, number> | null;
  }

  public setSizes(sizes: Map<string, number>): void {
    this.sizes = sizes || this.sizes;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
