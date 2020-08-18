/**
 * The interface of JointGraphElementHasMarkup
 *
 * @export
 * @interface JointGraphElementHasMarkup
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export interface JointGraphElementHasMarkup {

  /**
   * Returns the markup of current graph element.
   *
   * @returns {(string | null)}
   * @memberof JointGraphElementHasMarkup
   */
  getMarkup(): string | null;

  /**
   * Assigns the markup for current graph element.
   *
   * @param {string} markup
   * @memberof JointGraphElementHasMarkup
   */
  setMarkup(markup: string): void;

  /**
   * Returns the attribute of current elements.
   *
   * @returns {(joint.dia.Cell.Selectors | null)}
   * @memberof JointGraphElementHasMarkup
   */
  getAttr(): joint.dia.Cell.Selectors | null;

  /**
   * Assigns the attribute of current elements.
   *
   * @param {joint.dia.Cell.Selectors} attr
   * @memberof JointGraphElementHasMarkup
   */
  setAttr(attr: joint.dia.Cell.Selectors): void;
}
