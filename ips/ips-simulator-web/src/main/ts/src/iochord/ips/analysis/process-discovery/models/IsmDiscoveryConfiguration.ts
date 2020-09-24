/**
 * Case mapping settings for ISM discovery API.
 *
 * @export
 * @class IsmDiscoveryConfiguration
 *
 * @package ts
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
  public colCaseId: string = 'ci';

  /**
   * The column position for event activity.
   *
   * @type {string}
   * @memberof IsmDiscoveryConfiguration
   */
  public colEventActivity: string = 'ea';

  /**
   * The column position for event timestamp.
   *
   * @type {string}
   * @memberof IsmDiscoveryConfiguration
   */
  public colEventTimestamp: string = 'ec';

  public positiveObservationThreshold: number = 0;

  /**
   * The column position for default dependency threshold
   *
   * @type {number}
   * @memberof IsmDiscoveryConfiguration
   */
  public dependencyThreshold: number = -1;

  public connectorPaths: any = {};

  public animatorLength: number = 30;

  public tokenReplay: boolean = false;

  public tokenReplayDecorateLabel: boolean = false;
}
