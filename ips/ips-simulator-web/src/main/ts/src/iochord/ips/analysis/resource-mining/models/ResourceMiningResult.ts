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

  public resources: string[] = [];
  public activities: string[] = [];
  public groups: string[] = [];
  public mactgroup: any = {};
  public mgroupres: any = {};
  public mgroupact: any = {};
  public mresgroup: any = {};
}
