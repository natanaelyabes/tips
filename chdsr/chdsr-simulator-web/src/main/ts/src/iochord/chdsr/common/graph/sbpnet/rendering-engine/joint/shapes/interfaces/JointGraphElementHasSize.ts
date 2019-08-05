/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementHasSize {
  getSize(): { width: number, height: number } | null;
  setSize(size: { width: number, height: number }): void;
}
