import AnalysisService from '@/iochord/ips/analysis/services/AnalysisService';
import ResourceMiningConfiguration from '../models/ResourceMiningConfiguration';
export { ResourceMiningConfiguration };

/**
 * The Resource Mining service class.
 *
 * @export
 * @class ResourceMiningService
 * @extends {AnalysisService}
 *
 * @package ips
 * @author N. I. Utama <ichsan83@gmail.com>
 * @since 2020
 */
export default class ResourceMiningService extends AnalysisService {

  /**
   * Base URI for Resource Mining service class.
   *
   * @static
   * @type {string}
   * @memberof ResourceMiningService
   */
  public static readonly BASE_URI: string = AnalysisService.BASE_URI + '/discover';

  /**
   * Returns the instance of Resource Mining service.
   *
   * @static
   * @returns {ResourceMiningService}
   * @memberof ResourceMiningService
   */
  public static getInstance(): ResourceMiningService {
    if (ResourceMiningService.__INSTANCE == null) {
      ResourceMiningService.__INSTANCE = new ResourceMiningService();
    }
    return ResourceMiningService.__INSTANCE;
  }

  /**
   * The instance of Resource Mining service
   *
   * @private
   * @static
   * @type {ResourceMiningService}
   * @memberof ResourceMiningService
   */
  private static __INSTANCE: ResourceMiningService;

  /**
   * Mined Resource from the web service.
   *
   * @param {ResourceMiningConfiguration} config
   * @param {*} completeCallback
   * @param {*} progressCallback
   * @returns
   * @memberof ResourceMiningService
   */
  public mineResource(config: ResourceMiningConfiguration, completeCallback: any, progressCallback: any) {
    return this.webservicePost(ResourceMiningService.BASE_URI + '/resm/mine', config, completeCallback, progressCallback);
  }
}
