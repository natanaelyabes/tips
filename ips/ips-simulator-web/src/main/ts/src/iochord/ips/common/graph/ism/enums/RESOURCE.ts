/**
 * Enumerations for the types of resource selection.
 *
 * @export
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum RESOURCE_SELECTION {

  /**
   * Resource are being drawn from the pool arbitrarily.
   */
  RANDOM,

  /**
   * Resource are being selected using round robin scheduling algorithm.
   */
  ROUND_ROBIN,
}

/**
 * Enumerations for the types of resource criteria.
 *
 * @export
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export enum RESOURCE_CRITERIA {

  /**
   * Draw resource from the pool under the case type criteria.
   */
  CASE_TYPE,
}
