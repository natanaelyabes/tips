/**
 * The interface of JointGraphElementIsPaintable
 *
 * @export
 * @interface JointGraphElementIsPaintable
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
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
