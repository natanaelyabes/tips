/**
 * The interface of JointGraphElementIsPaintable
 *
 * @export
 * @interface JointGraphElementIsPaintable
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
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
