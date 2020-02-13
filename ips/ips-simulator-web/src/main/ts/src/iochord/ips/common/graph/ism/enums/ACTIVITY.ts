/**
 * Enumeration for activity type.
 *
 * @export
 * @enum {number}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum ACTIVITY_TYPE {

  /**
   * Processes one moving object at a time.
   */
  STANDARD = 'STANDARD',

  /**
   * Processes more than one moving objects at a time.
   */
  CONCURRENT_BATCH = 'CONCURRENT_BATCH',

  /**
   * Processes one moving object as input and produces several moving objects as output.
   */
  SPLIT_MODULE = 'SPLIT_MODULE',
}
