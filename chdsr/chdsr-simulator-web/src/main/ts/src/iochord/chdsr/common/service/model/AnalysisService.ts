import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class AnalysisService extends BaseService {
  //*/
  // PRODUCTION / COMMIT
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model';
  /*/
  // DEVELOPMENT / LOCAL
  public static readonly BASE_URI: string = BaseService.BASE_URI + '/model/simulation';
  //*/
  
  private static __INSTANCE: AnalysisService;

  public static getInstance(): AnalysisService {
    if (AnalysisService.__INSTANCE == null) {
      AnalysisService.__INSTANCE = new AnalysisService();
    }
    return AnalysisService.__INSTANCE;
  }

}
