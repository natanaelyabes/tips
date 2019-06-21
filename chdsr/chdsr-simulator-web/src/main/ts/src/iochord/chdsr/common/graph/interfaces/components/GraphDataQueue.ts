import { QUEUE_TYPE } from '../../enums/QUEUE';
import { GraphData } from '../GraphData';

export interface GraphDataQueue extends GraphData {
  getQueueType(): QUEUE_TYPE | null;
  setQueueType(type: QUEUE_TYPE): void;
  isShared(): boolean | null;
  setShared(shared: boolean): void;
  isSingle(): boolean | null;
  setSingle(single: boolean): void;
  getSize(): number | null;
  setSize(size: number): void;
  getSizes(): Map<string, number> | null;
  setSizes(sizes: Map<string, number>): void;
}