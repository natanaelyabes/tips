/**
 * The interface of JointGraphElementHasSize
 *
 * @export
 * @interface JointGraphElementHasSize
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementHasSize {

  /**
   * Returns the size of current graph element.
   *
   * @returns {({ width: number, height: number } | null)}
   * @memberof JointGraphElementHasSize
   */
  getSize(): { width: number, height: number } | null;

  /**
   * Assigns the size of current graph element.
   *
   * @param {{ width: number, height: number }} size
   * @memberof JointGraphElementHasSize
   */
  setSize(size: { width: number, height: number }): void;
}
