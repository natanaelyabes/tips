/**
 * Allows object to be serializable.
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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
