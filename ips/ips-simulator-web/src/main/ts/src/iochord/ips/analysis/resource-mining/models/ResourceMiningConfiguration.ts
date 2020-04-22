/**
 * Algorithm option and case mapping settings for Resource Mining API.
 *
 * @export
 * @class ResourceMiningConfiguration
 *
 * @package ips
 * @author N. I. Utama <ichsan83@gmail.com>
 * @since 2020
 */
export default class ResourceMiningConfiguration {

  /**
   * The dataset id of the event log.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public datasetId: string = '';

  /**
   * Selected algorithm for resource mining.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public resMinAlg: string = '';

  /**
   * activate time analysis for resource mining.
   *
   * @type {boolean}
   * @memberof ResourceMiningConfiguration
   */
  public timeAnalysis: boolean = false;

  /**
   * properties of clustering for time analysis of resource mining.
   *
   * @type {array}
   * @memberof ResourceMiningConfiguration
   */
  public propTimeAnalysis: string[] = [];

  /**
   * Selected distance measure algorithm.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public distMesAlg: string = '';

  /**
   * Threshold of distance measure.
   *
   * @type {number}
   * @memberof ResourceMiningConfiguration
   */
  public threshold: number = 0.0;

  /**
   * The column position for case id.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public colCaseId: string = 'c0';

  /**
   * The column position for event activity.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  // public colEventActivity: string = 'c1';
  public colEventActivity: string = 'c6';
  // public colEventActivity: string = 'c1';

  /**
   * The column position for event resource.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  // public colEventResource: string = 'c2';
  public colEventResource: string = 'c12';
  // public colEventResource: string = 'c2';

  /**
   * The column position for event timestamp start.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  // public colEventTimestampStart: string = 'c6';
  public colEventTimestampStart: string = 'c2';
  // public colEventTimestampStart: string = 'c3';

  /**
   * The column position for event timestamp end.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  // public colEventTimestampEnd: string = 'c7';
  public colEventTimestampEnd: string = 'c2';
  // public colEventTimestampEnd: string = 'c4';

  /**
   * Number of rows being skipped.
   *
   * @type {number}
   * @memberof IsmDiscoveryConfiguration
   */
  public skipRows: number = 1;
}
