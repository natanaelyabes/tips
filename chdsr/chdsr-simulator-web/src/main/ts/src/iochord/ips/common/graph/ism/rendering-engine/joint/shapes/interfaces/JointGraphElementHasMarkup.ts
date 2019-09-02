/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface JointGraphElementHasMarkup {
  getMarkup(): string | null;
  setMarkup(markup: string): void;
  getAttr(): joint.dia.Cell.Selectors | null;
  setAttr(attr: joint.dia.Cell.Selectors): void;
}
