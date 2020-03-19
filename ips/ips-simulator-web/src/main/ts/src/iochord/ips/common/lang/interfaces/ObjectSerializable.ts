/**
 * Allows object to be serializable.
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
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
