import { QUEUE_TYPE } from '../../enums/QUEUE';
import { GraphData } from '../GraphData';
import { TSMap } from 'typescript-map';

/**
 * The interface of graph data queue.
 *
 * @export
 * @interface GraphDataQueue
 * @extends {GraphData}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphDataQueue extends GraphData {

  /**
   * Returns the queue type for the current queue data node.
   *
   * @returns {(QUEUE_TYPE | null)}
   * @memberof GraphDataQueue
   */
  getQueueType(): QUEUE_TYPE | null;

  /**
   * Assigns the queue type for current queue.
   *
   * @param {QUEUE_TYPE} type
   * @memberof GraphDataQueue
   */
  setQueueType(type: QUEUE_TYPE): void;

  /**
   * Returns whether a queue is shared. False otherwise.
   *
   * @returns {(boolean | null)}
   * @memberof GraphDataQueue
   */
  isShared(): boolean | null;

  /**
   * Assigns whether current queue is shared. False otherwise.
   *
   * @param {boolean} shared
   * @memberof GraphDataQueue
   */
  setShared(shared: boolean): void;

  /**
   * Returns whether current queue is a single queue.
   *
   * @returns {(boolean | null)}
   * @memberof GraphDataQueue
   */
  isSingle(): boolean | null;

  /**
   * Assigns queue to be in single mode. False otherwise.
   *
   * @param {boolean} single
   * @memberof GraphDataQueue
   */
  setSingle(single: boolean): void;

  /**
   * Returns the size of queue.
   *
   * @returns {(number | null)}
   * @memberof GraphDataQueue
   */
  getSize(): number | null;

  /**
   * Assign size of queue to the current queue data node.
   *
   * @param {number} size
   * @memberof GraphDataQueue
   */
  setSize(size: number): void;

  /**
   * Returns the list resource ids of current queue data node.
   *
   * @returns {(TSMap<string, number> | null)}
   * @memberof GraphDataQueue
   */
  getSizes(): TSMap<string, number> | null;

  /**
   * Set the list of resource ids for the current queue data node.
   *
   * @param {TSMap<string, number>} sizes
   * @memberof GraphDataQueue
   */
  setSizes(sizes: TSMap<string, number>): void;
}
