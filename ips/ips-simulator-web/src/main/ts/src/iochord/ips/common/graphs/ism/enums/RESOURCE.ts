/**
 * Enumerations for the types of resource selection.
 *
 * @export
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
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
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export enum RESOURCE_CRITERIA {

  /**
   * Draw resource from the pool under the case type criteria.
   */
  CASE_TYPE,
}
