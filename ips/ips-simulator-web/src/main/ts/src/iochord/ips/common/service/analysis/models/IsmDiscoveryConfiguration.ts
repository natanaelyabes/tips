/**
 * Case mapping settings for ISM discovery API.
 *
 * @export
 * @class IsmDiscoveryConfiguration
 *
 * @package ips
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 */
export default class IsmDiscoveryConfiguration {

  /**
   * The dataset id of the event log.
   *
   * @type {string}
   * @memberof IsmDiscoveryConfiguration
   */
  public datasetId: string = '';

  /**
   * The column position for case id.
   *
   * @type {string}
   * @memberof IsmDiscoveryConfiguration
   */
  public colCaseId: string = 'c0';

  /**
   * The column position for event activity.
   *
   * @type {string}
   * @memberof IsmDiscoveryConfiguration
   */
  public colEventActivity: string = 'c1';

  /**
   * The column position for event timestamp.
   *
   * @type {string}
   * @memberof IsmDiscoveryConfiguration
   */
  public colEventTimestamp: string = 'c121';

  /**
   * Number of rows being skipped.
   *
   * @type {number}
   * @memberof IsmDiscoveryConfiguration
   */
  public skipRows: number = 1;
}
