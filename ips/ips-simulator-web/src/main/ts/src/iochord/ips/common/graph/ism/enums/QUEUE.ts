/**
 * Enumerations for the types of queue.
 *
 * @export
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export enum QUEUE_TYPE {

  /**
   * Last in first out queue. The last entered token are be taken out from the queue first.
   */
  LIFO = 'LIFO',

  /**
   * First in first out queue. The queues are handled by the order of earliness.
   */
  FIFO = 'FIFO',
}
