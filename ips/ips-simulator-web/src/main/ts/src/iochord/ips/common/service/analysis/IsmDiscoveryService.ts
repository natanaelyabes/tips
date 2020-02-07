import AnalysisService from './AnalysisService';
import IsmDiscoveryConfiguration from './models/IsmDiscoveryConfiguration';
export { IsmDiscoveryConfiguration };

/**
 * The ISM discovery service class.
 *
 * @export
 * @class IsmDiscoveryService
 * @extends {AnalysisService}
 *
 * @package ips
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 */
export default class IsmDiscoveryService extends AnalysisService {

  /**
   * Base URI for ISM discovery service class.
   *
   * @static
   * @type {string}
   * @memberof IsmDiscoveryService
   */
  public static readonly BASE_URI: string = AnalysisService.BASE_URI + '';

  /**
   * Returns the instance of ISM discovery service.
   *
   * @static
   * @returns {IsmDiscoveryService}
   * @memberof IsmDiscoveryService
   */
  public static getInstance(): IsmDiscoveryService {
    if (IsmDiscoveryService.__INSTANCE == null) {
      IsmDiscoveryService.__INSTANCE = new IsmDiscoveryService();
    }
    return IsmDiscoveryService.__INSTANCE;
  }

  /**
   * The instance of ISM discovery service
   *
   * @private
   * @static
   * @type {IsmDiscoveryService}
   * @memberof IsmDiscoveryService
   */
  private static __INSTANCE: IsmDiscoveryService;

  /**
   * Discover ISM graph from the web service.
   *
   * @param {IsmDiscoveryConfiguration} config
   * @param {*} completeCallback
   * @param {*} progressCallback
   * @returns
   * @memberof IsmDiscoveryService
   */
  public discoverIsmGraph(config: IsmDiscoveryConfiguration, completeCallback: any, progressCallback: any) {
    return this.webservicePost(IsmDiscoveryService.BASE_URI + '/discover/ism', config, completeCallback, progressCallback);
  }
}
