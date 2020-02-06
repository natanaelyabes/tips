/**
 * The interface of JointGraphElementIsPaintable
 *
 * @export
 * @interface JointGraphElementIsPaintable
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementIsPaintable {

  /**
   * Render graph element to canvas.
   *
   * @param {joint.dia.Graph} graph
   * @memberof JointGraphElementIsPaintable
   */
  render(graph: joint.dia.Graph): void;
}
