/**
 * Allows object to be serializable.
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface ObjectSerializable {

  /**
   * Serialize any object as JSON string.
   *
   * @returns {(string | null)}
   * @memberof ObjectSerializable
   */
  serialize(): string | null;
}
