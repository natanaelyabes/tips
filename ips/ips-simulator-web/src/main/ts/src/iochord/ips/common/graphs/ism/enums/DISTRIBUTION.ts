/**
 * Enumerations for defining distribution type.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export enum DISTRIBUTION_TYPE {

  /**
   * A random distribution type allows tokens to be generated following a certain distribution model.
   */
  RANDOM,

  /**
   * A constant distribution type allows tokens to be generated at constant rate.
   */
  CONSTANT,
}
