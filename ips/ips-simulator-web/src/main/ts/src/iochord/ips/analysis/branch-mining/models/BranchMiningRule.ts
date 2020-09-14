/**
 * Object to store branch mining rule
 *
 * @export
 * @class BranchMiningRule
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 */
export class BranchMiningRule {

  /**
   * Decision rule in DOT notation.
   *
   * @type {string}
   * @memberof BranchMiningRule
   */
  public decisionRule: string = '';

  /**
   * The name of event that precedes this branch.
   *
   * @type {string}
   * @memberof BranchMiningRule
   */
  public eventName: string = '';

  /**
   * The accuracy of decision tree model.
   *
   * @type {number}
   * @memberof BranchMiningRule
   */
  public modelAccuracy: number = 0;
}
