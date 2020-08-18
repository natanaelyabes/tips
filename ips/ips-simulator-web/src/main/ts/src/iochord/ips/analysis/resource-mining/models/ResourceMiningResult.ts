/**
 * Model from Resource Mining API.
 *
 * @export
 * @class ResourceMiningResult
 *
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
 * @since 2020
 */
export default class ResourceMiningResult {

  /**
   * List of resources.
   *
   * @type {string[]}
   * @memberof ResourceMiningResult
   */
  public resources: string[] = [];

  /**
   * List of activities.
   *
   * @type {string[]}
   * @memberof ResourceMiningResult
   */
  public activities: string[] = [];

  /**
   * List of groups.
   *
   * @type {string[]}
   * @memberof ResourceMiningResult
   */
  public groups: string[] = [];

  /**
   * Activity group.
   *
   * @type {*}
   * @memberof ResourceMiningResult
   */
  public mactgroup: any = {};

  /**
   * Resource group.
   *
   * @type {*}
   * @memberof ResourceMiningResult
   */
  public mgroupres: any = {};

  /**
   * Activity group.
   *
   * @type {*}
   * @memberof ResourceMiningResult
   */
  public mgroupact: any = {};

  /**
   * Resource group.
   *
   * @type {*}
   * @memberof ResourceMiningResult
   */
  public mresgroup: any = {};

  /**
   * Time clustering result.
   *
   * @type {*}
   * @memberof ResourceMiningResult
   */
  public timecluster: any = {};

  /**
   * Time analysis result.
   *
   * @type {*}
   * @memberof ResourceMiningResult
   */
  public timeanalysis: any = {};
}
