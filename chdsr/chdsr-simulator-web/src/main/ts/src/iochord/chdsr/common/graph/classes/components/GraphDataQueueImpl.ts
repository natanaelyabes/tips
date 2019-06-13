import { GraphDataQueue } from './../../interfaces/components/GraphDataQueue';
import { GraphDataImpl } from '../GraphDataImpl';
import { QUEUE_TYPE } from '../../enums/QUEUE';

export class GraphDataQueueImpl extends GraphDataImpl implements GraphDataQueue {
  public static readonly TYPE: string | null = 'queue';
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

  private type: QUEUE_TYPE | null = QUEUE_TYPE.FIFO;
  private shared: boolean | null = false;
  private single: boolean | null = true;
  private size: number | null = -1;
  private sizes: Map<string, number> | null = new Map<string, number>() || null;

  constructor();
  constructor(type: QUEUE_TYPE, shared: boolean, single: boolean, size: number, sizes: Map<string, number>);
  constructor(type?: QUEUE_TYPE, shared?: boolean, single?: boolean, size?: number, sizes?: Map<string, number>) {
    super();
    this.type = type || QUEUE_TYPE.FIFO || null;
    this.shared = shared || false || null;
    this.single = single || true || null;
    this.size = size || -1 || null;
    this.sizes = sizes || new Map<string, number>() || null;
  }

  /** @Override */
  public getType(): string | null {
    return this.TYPE;
  }

  public getQueueType(): QUEUE_TYPE | null {
    return this.type;
  }

  public setQueueType(type: QUEUE_TYPE): void {
    this.type = type;
  }

  public isShared(): boolean | null {
    return this.shared;
  }

  public setShared(shared: boolean): void {
    this.shared = shared;
  }

  public isSingle(): boolean | null {
    return this.single;
  }

  public setSingle(single: boolean): void {
    this.single = single;
  }

  public getSize(): number | null {
    return this.size;
  }

  public setSize(size: number): void {
    this.size = size;
  }

  public getSizes(): Map<string, number> | null {
    return this.sizes;
  }

  public setSizes(sizes: Map<string, number>): void {
    this.sizes = sizes;
  }

  /** @Override */
  public serialize(): string | null {
    return JSON.stringify(this);
  }
}
