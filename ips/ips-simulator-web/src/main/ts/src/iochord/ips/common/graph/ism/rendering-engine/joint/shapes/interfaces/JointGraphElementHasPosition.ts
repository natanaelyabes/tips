/**
 * The interface of JointGraphElementHasPosition
 *
 * @export
 * @interface JointGraphElementHasPosition
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementHasPosition {

  /**
   * Returns the position of current graph element within the canvas.
   *
   * @returns {({ x: number, y: number } | null)}
   * @memberof JointGraphElementHasPosition
   */
  getPosition(): { x: number, y: number } | null;

  /**
   * Assigns the position of current graph element within the canvas.
   *
   * @param {{ x: number, y: number }} position
   * @memberof JointGraphElementHasPosition
   */
  setPosition(position: { x: number, y: number }): void;
}
