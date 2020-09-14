import { BranchMiningRule } from './BranchMiningRule';
/**
 * Object to retreive branch mining result.
 *
 * @export
 * @class BranchMiningResult
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 */
export default class BranchMiningResult {

  /**
   * Process model result.
   *
   * @type {*}
   * @memberof BranchMiningResult
   */
  public processmodel: any = {};

  /**
   * Decision tree result.
   *
   * @type {*}
   * @memberof BranchMiningResult
   */
  public rule: BranchMiningRule[] = [];
}
