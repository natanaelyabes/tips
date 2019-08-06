/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementHasPosition {
  getPosition(): { x: number, y: number } | null;
  setPosition(position: { x: number, y: number }): void;
}
