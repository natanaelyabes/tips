/**
 * Algorithm option and case mapping settings for Resource Mining API.
 *
 * @export
 * @class ResourceMiningConfiguration
 *
 * @package ips
 * @author Nur Ichsan Utama <ichsan83@gmail.com>
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
  public colCaseId: string = 'ci';

  /**
   * The column position for event activity.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public colEventActivity: string = 'ea';

  /**
   * The column position for event originator
   *
   * @type {string}
   * @memberof OriginatorMiningConfiguration
   */
  public colEventResource: string = 'eo';
  
  /**
   * The column position for event resource.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public colEventResource: string = 'er';

  /**
   * The column position for event timestamp start.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public colEventTimestampStart: string = 'es';

  /**
   * The column position for event timestamp end.
   *
   * @type {string}
   * @memberof ResourceMiningConfiguration
   */
  public colEventTimestampEnd: string = 'ec';

  /**
   * Number of rows to be skipped.
   *
   * @type {number}
   * @memberof IsmDiscoveryConfiguration
   */
  public skipRows: number = 1;
}
