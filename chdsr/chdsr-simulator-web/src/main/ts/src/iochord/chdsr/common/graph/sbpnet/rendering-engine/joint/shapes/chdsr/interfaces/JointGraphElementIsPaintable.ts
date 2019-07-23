/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementIsPaintable {
  render(graph: joint.dia.Graph): void;
}
