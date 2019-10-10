import AnalysisService from './AnalysisService';
import IsmDiscoveryConfiguration from './models/IsmDiscoveryConfiguration';

/**
 *
 * @package ips
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export { IsmDiscoveryConfiguration };

export default class IsmDiscoveryService extends AnalysisService {

  public static readonly BASE_URI: string = AnalysisService.BASE_URI + '';

  public static getInstance(): IsmDiscoveryService {
    if (IsmDiscoveryService.__INSTANCE == null) {
      IsmDiscoveryService.__INSTANCE = new IsmDiscoveryService();
    }
    return IsmDiscoveryService.__INSTANCE;
  }

  private static __INSTANCE: IsmDiscoveryService;

  public discoverIsmGraph(config: IsmDiscoveryConfiguration, completeCallback: any, progressCallback: any) {
    return this.webservicePost(IsmDiscoveryService.BASE_URI + '/discover/ism', config, completeCallback, progressCallback);
  }

}
