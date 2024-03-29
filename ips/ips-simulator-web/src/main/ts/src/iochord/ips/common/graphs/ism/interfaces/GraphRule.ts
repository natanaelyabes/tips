/**
 * The interface for defining graph rule.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface GraphRule {

  /**
   * To attest that the input nodes have adhered the simulation model rule.
   *
   * @returns {(Error | null)}
   * @memberof GraphNodeImpl
   */
  validateInputNodes(): Error | null;

  /**
   * To attest that the output nodes have adhered the simulation model rule.
   *
   * @returns {(Error | null)}
   * @memberof GraphNodeImpl
   */
  validateOutputNodes(): Error | null;
}
