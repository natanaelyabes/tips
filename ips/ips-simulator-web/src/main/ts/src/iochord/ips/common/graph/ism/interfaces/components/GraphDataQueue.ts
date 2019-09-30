import { QUEUE_TYPE } from '../../enums/QUEUE';
import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataQueue extends GraphData {
  getQueueType(): QUEUE_TYPE | null;
  setQueueType(type: QUEUE_TYPE): void;
  isShared(): boolean | null;
  setShared(shared: boolean): void;
  isSingle(): boolean | null;
  setSingle(single: boolean): void;
  getSize(): number | null;
  setSize(size: number): void;
  getSizes(): TSMap<string, number> | null;
  setSizes(sizes: TSMap<string, number>): void;
}
